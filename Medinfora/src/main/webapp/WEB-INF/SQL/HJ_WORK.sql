-- 혜정 쿼리

show user;
-- USER이(가) "SYS"입니다.

-- 오라클 계정 생성시 계정명 앞에 c## 붙이지 않고 생성하도록 하겠습니다.
alter session set "_ORACLE_SCRIPT"=true;
-- Session이(가) 변경되었습니다.

create user final_orauser3 identified by gclass default tablespace users; 
-- User FINAL_ORAUSER3이(가) 생성되었습니다.

grant connect, resource, create view, unlimited tablespace to FINAL_ORAUSER3;
-- Grant을(를) 성공했습니다.

-----------------------------------------------------------------------------------
-- 테이블 확인 --
select *
from tab;

-- 테이블 주석 확인 --
select *
from user_tab_comments;

-- 테이블 안 주석 확인 --
select column_name, comments
from user_col_comments
where table_name = '테이블명';
-- LOGINLOG, MEDIA, MEDIQ, RESERVECODE, RESERVE, MEMBER, MEMBERIDX, CLASSCODE, HOSPITAL, NOTICE, KOREAAREA, HOLIDAY, CLASSCODE

----------------------------------------------------------------------------------------
-- === 활동중인 의료종사자인 회원의 수 === --
select count(*)
from member
where midx = 2;

-- === 회원가입된 병원 리스트 가져오기(전체) === --
SELECT H.hidx, hpname, hpaddr, hptel, classcode
FROM
(
    select hidx
    from member
    where midx = 2
) M
JOIN
(
    select *
    from hospital
) H
ON M.hidx = H.hidx;

-- === 시/도, 시/군구, 진료과목, 병원명 으로 검색한 병원리스트 가져오기 === --
SELECT HC.hidx, hpname, hpaddr, hptel, classcode
FROM
(
    select hidx
    from member
    where midx = 2
) M
JOIN
(
    SELECT hidx, hpname, hpaddr, hptel, H.classcode
    FROM
    (
        select hidx, hpname, hpaddr, hptel, classcode
        from hospital
        where 1=1
        and hpaddr like '%' || '인천광역시' || '%' || '서구' || '%'
            and hpname like '%' || '의' || '%' and classcode = 'D001'
    ) H
    JOIN
    (
        select classcode, classname
        from classcode
    ) C
    ON H.classcode = C.classcode
) HC
ON M.hidx = HC.hidx;

-- === 페이징 처리한 시/도, 시/군구, 진료과목, 병원명 으로 검색한 병원리스트 가져오기 === --
SELECT hidx, hpname, hpaddr, hptel, classcode
FROM
(
    SELECT row_number() over(order by HC.hidx desc) AS rno
        , HC.hidx as hidx, hpname, hpaddr, hptel, classcode
    FROM
    (
        select hidx
        from member
        where midx = 2
    ) M
    JOIN
    (
        SELECT hidx, hpname, hpaddr, hptel, H.classcode
        FROM
        (
            select hidx, hpname, hpaddr, hptel, classcode
            from hospital
            where 1=1
            and hpaddr like '%' || '인천광역시' || '%' || '서구' || '%'
                and hpname like '%' || '의' || '%' and classcode = 'D001'
        ) H
        JOIN
        (
            select classcode, classname
            from classcode
        ) C
        ON H.classcode = C.classcode
    ) HC
    ON M.hidx = HC.hidx
    order by hidx
)
WHERE rno BETWEEN 1 AND 10;

-- === 검색결과를 포함한 병원 수 === --
SELECT HC.hidx as hidx, hpname, hpaddr, hptel, classcode
FROM
(
    select hidx
    from member
    where midx = 2
) M
JOIN
(
    SELECT hidx, hpname, hpaddr, hptel, H.classcode
    FROM
    (
        select hidx, hpname, hpaddr, hptel, classcode
        from hospital
        where 1=1
        and hpaddr like '%' || '인천광역시' || '%' || '서구' || '%'
            and hpname like '%' || '의' || '%' and classcode = 'D001'
    ) H
    JOIN
    (
        select classcode, classname
        from classcode
    ) C
    ON H.classcode = C.classcode
) HC
ON M.hidx = HC.hidx;

-- === 검색결과를 포함한 페이징 처리 === --
SELECT hidx, hpname, hpaddr, hptel, classcode
FROM
(
SELECT row_number() over(order by HC.hidx desc) AS rno
    , HC.hidx as hidx, hpname, hpaddr, hptel, classcode
FROM
(
    select hidx
    from member
    where midx = 2
) M
JOIN
(
    SELECT hidx, hpname, hpaddr, hptel, H.classcode
    FROM
    (
        select hidx, hpname, hpaddr, hptel, classcode
        from hospital
        where 1=1
        and hpaddr like '%' || '인천광역시' || '%' || '서구' || '%'
            and hpname like '%' || '의' || '%' and classcode = 'D002'
    ) H
    JOIN
    (
        select classcode, classname
        from classcode
    ) C
    ON H.classcode = C.classcode
) HC
ON M.hidx = HC.hidx
ORDER BY HIDX
)
WHERE rno BETWEEN 1 AND 10;

-----------------------------------------------------------------------------------------
-- === 시퀀스 생성(reserve) === --
create sequence seq_ridx
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-- Sequence SEQ_RIDX이(가) 생성되었습니다.

-------------------------------------------------------------------------------------------
-- 날짜가 공휴일인지 체크
select count(*)
from holiday
where holiday_date = '2020-01-01'

-- 현재시간 이후, 선택한 날짜의 예약이 가득 찬 경우 확인
select count(*)
from reserve
where hidx = 1
    and to_date(checkin,'yyyy-mm-dd hh24:mi:ss') > to_char(to_date(sysdate,'yyyy-mm-dd hh24:mi:ss'))

-- 병원의 오픈시간과 마감시간 파악
select hidx, 
        nvl(starttime1, ' ') as starttime1, nvl(starttime2, ' ') as starttime2, nvl(starttime3, ' ') as starttime3
        , nvl(starttime4, ' ') as starttime4, nvl(starttime5, ' ') as starttime5, nvl(starttime6, ' ') as starttime6
        , nvl(starttime7, ' ') as starttime7, nvl(starttime8,' ') as starttime8
        , nvl(endtime1, ' ') as endtime1, nvl(endtime2, ' ') as endtime2, nvl(endtime3, ' ') as endtime3
        , nvl(endtime4, ' ') as endtime4, nvl(endtime5, ' ') as endtime5, nvl(endtime6, ' ') as endtime6
        , nvl(endtime7, ' ') as endtime7, nvl(endtime8, ' ') as endtime8
from hospital
where hidx = '1';

-- 현재시간 이후, 선택한 날짜와 예약일이 같은 경우
select ridx, userid, reportday, checkin, symptom, rcode, hidx
from reserve
where hidx = 1
    and to_date(checkin,'yyyy-mm-dd hh24:mi:ss') > to_char(to_date(sysdate,'yyyy-mm-dd hh24:mi:ss'))
    and checkin = '2024-07-08 15:00:00'

-- 선택한 날의 예약 개수 파악 
select ridx, userid, reportday, checkin, symptom, rcode, hidx
from reserve
where to_date(checkin,'yyyy-mm-dd hh24:mi:ss') > to_char(to_date('2024-07-08','yyyy-mm-dd hh24:mi:ss'))














-- 현재시간 이후, 병원과 요일 파악하여 진료예약 가능한 업무시간 파악하기(보류 이전꺼부터 할거)
select ridx, userid, reportday, checkin, symptom, rcode, hidx
from reserve
-- RESERVECODE, RESERVE, HOLIDAY

select rcode, rStatus
from reservecode

SELECT H.hidx, starttime1, starttime2, starttime3, starttime4, starttime5, starttime6, starttime7, starttime8
    , endtime1, endtime2, endtime3, endtime4, endtime5, endtime6, endtime7, endtime8
FROM
(
    select ridx, checkin, hidx
    from reserve
    where hidx = 1
        and to_date(checkin,'yyyy-mm-dd hh24:mi:ss') > to_char(to_date('2024-07-06 20:00:00','yyyy-mm-dd hh24:mi:ss'))   -- 오늘날짜
) R
JOIN
(
    select hidx, 
        starttime1, starttime2, starttime3, starttime4, starttime5, starttime6, starttime7, starttime8
        , endtime1, endtime2, endtime3, endtime4, endtime5, endtime6, endtime7, endtime8
    from hospital
) H
ON R.hidx = H.hidx

-----------------------------------------------------------------------------------------------------------------

SELECT H.hidx, starttime1, starttime2, starttime3, starttime4, starttime5, starttime6, starttime7, starttime8
    , endtime1, endtime2, endtime3, endtime4, endtime5, endtime6, endtime7, endtime8
FROM
(
    select ridx, checkin, hidx
    from reserve
    where hidx = 1
        and to_date(checkin,'yyyy-mm-dd hh24:mi:ss') > to_char(to_date('2024-07-06 20:00:00','yyyy-mm-dd hh24:mi:ss'))   -- 오늘날짜
) R
JOIN
(
    select hidx, 
        starttime1, starttime2, starttime3, starttime4, starttime5, starttime6, starttime7, starttime8
        , endtime1, endtime2, endtime3, endtime4, endtime5, endtime6, endtime7, endtime8
    from hospital
) H
ON R.hidx = H.hidx