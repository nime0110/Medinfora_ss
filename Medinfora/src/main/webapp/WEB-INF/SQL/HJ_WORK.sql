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
-- LOGINLOG, MEDIA, MEDIQ, RESERVECODE, RESERVE, MEMBER, MEMBERIDX, CLASSCODE, HOSPITAL, NOTICE

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


select * 
from CLASSCODE
