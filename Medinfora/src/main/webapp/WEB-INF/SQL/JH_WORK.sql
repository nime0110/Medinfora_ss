-- 지훈 쿼리

show user;

SELECT * 
FROM all_tables;


SELECT * 
FROM user_tables;


select *
from USER_SEQUENCES;


select count(*)
from member;

select count(*)
from member
where userid = 'test001' and pwd = 'qwer1234$';

select *
from memberidx;

select *
from member;


SELECT userid, email, name, address, detailaddress, birthday, mobile, gender, M.registerday, midx
       , pwdupdategap
       , NVL(lastlogingap, trunc( months_between(sysdate, M.registerday) )) AS lastlogingap
FROM
(
    select userid, email, name, address, detailaddress, birthday, mobile, gender, registerday, midx
           , trunc( months_between(sysdate, pwdupdateday) ) as pwdupdategap
    from member
    where (midx between 0 and 8) and userid = 'test001' and pwd = 'qwer1234$'
)M
CROSS JOIN
(
    select trunc( months_between(sysdate, max(registerday)) ) as lastlogingap
    from loginlog
    where userid = 'test001'
) L;





SELECT userid, name , pwdchangegap, idle, email, mobile,
       NVL(lastlogingap, trunc( months_between(sysdate, registerday) )) AS lastlogingap,
       postcode, address, detailaddress, extraaddress
FROM
(
    select userid, name , coin, point, registerday, idle, email, mobile,
           trunc( months_between(sysdate, lastpwdchangedate) ) as pwdchangegap,
           postcode, address, detailaddress, extraaddress
    from tbl_member
    where status = 1 and userid = #{userid} and pwd = #{pwd}
) M
CROSS JOIN
(
    select trunc( months_between(sysdate, max(logindate)) ) as lastlogingap
    from tbl_loginhistory
    where fk_userid = #{userid}
) H


update member set mIdx = 3
from member


-- 로그 남기기 sql
insert into member(userid, ip, registerday)
values();

select *
from loginlog
order by registerday desc;



-- 로그인 sql 문 수정

SELECT userid, email, name, address, detailaddress, birthday, mobile, gender, M.registerday, midx
       , pwdupdategap
       , NVL(lastlogingap, trunc( months_between(sysdate, M.registerday) )) AS lastlogingap
FROM
(
    select userid, email, name, address, detailaddress, birthday, mobile, gender, registerday, midx
           , trunc( months_between(sysdate, pwdupdateday) ) as pwdupdategap
    from member
    where (midx between 0 and 8) and userid = 'test001' and pwd = 'qwer1234$'
)M
CROSS JOIN
(
    select trunc( months_between(sysdate, max(registerday)) ) as lastlogingap
    from loginlog
    where userid = 'test001'
) L;


SELECT userid, email, name, address, detailAddress, birthday, mobile, gender, M.registerday, midx, hidx, loginmethod
       , pwdUpdategap
       , NVL(lastlogingap, trunc( months_between(sysdate, M.registerday) )) AS lastlogingap
FROM
(
    select userid, email, name, address, detailAddress, birthday, mobile, gender, registerday, midx, hidx, loginmethod
           , trunc( months_between(sysdate, pwdupdateday) ) as pwdUpdategap
    from member
    where (midx between 0 and 8)
   -- <choose>
        --<when test="loginmethod =='0'">
           -- and userid = 'test001' and pwd = 'qwer1234$'
        --</when>
        --<when test="loginmethod =='1'"> 
            and userid = 'ramen001_kakao'
        --</when>
    --</choose>
)M
CROSS JOIN
(
    select trunc( months_between(sysdate, max(registerday)) ) as lastlogingap
    from loginlog
    where userid = 'ramen001_kakao'
) L;


select *
from member;




SELECT count(*)
FROM
(
    select userid, email, name, address, detailAddress, birthday, mobile, gender, registerday, midx, hidx, loginmethod
           , trunc( months_between(sysdate, pwdupdateday) ) as pwdUpdategap
    from member
    where (midx between 0 and 8)
   -- <choose>
        --<when test="loginmethod =='0'">
           -- and userid = 'test001' and pwd = 'qwer1234$'
        --</when>
        --<when test="loginmethod =='1'"> 
            and loginmethod ='1' and userid = 'ramen001_kakao'
        --</when>
    --</choose>
)M
CROSS JOIN
(
    select trunc( months_between(sysdate, max(registerday)) ) as lastlogingap
    from loginlog
    where userid = 'ramen001_kakao'
) L;


-- 병원 테이블 조회
select *
from hospital;


select userid, email
from member
where (midx between 0 and 8) and userid = "";

SELECT hpname
FROM(
select row_number() over(order by hidx desc) AS rno, hpname
from hospital
)H
WHERE rno between 1 and 5;


SELECT hpname, hpaddr
FROM(
    select row_number() over(order by hidx desc) AS rno,
           hpname,hpaddr, hidx
    from hospital
    where lower(hpaddr) like '%'||lower('해밀3로')||'%'  
)H
WHERE rno between 1 and 8
group by hpname;

<when test='searchType == "hpname"'></when>
<when test='searchType == "hpaddr"'>hpaddr</when>

SELECT hpaddr, count(*)
FROM(
select hpname, hpaddr
from hospital
)H
GROUP BY hpaddr;

WHIH A
AS(
    SELECT hpname ,
           hpaddr ,
           count(*) AS CNT
    FROM
    (
    select hpname, hpaddr, hidx
    from hospital
    )H
    GROUP BY hpname, hpaddr
    ORDER BY CNT
)
SELECT A.COUNT(*)
FROM A



WITH A AS (
    SELECT hpname,hpaddr
    FROM (
        SELECT hpname, hpaddr
        FROM hospital
        GROUP BY hpname, hpaddr
    ) H
    GROUP BY hpname, hpaddr
)
SELECT count(*)
FROM A



select *
from hospital
where hpname = '한울내과의원';



select *
from hospital
 where lower(hpname) like '%'||lower('행복한의원')||'%'
 
 -- 검색한 병원 총 수

WITH B
AS(
    SELECT count(*) as CNT
    FROM(
        select hpname, hpaddr
        from hospital
        where lower(hpname) like '%'||lower('행복한의원')||'%'
        )H
    GROUP BY hpname, hpaddr
)
SELECT COUNT(*)
FROM B
--------------

WITH B
AS(
    SELECT hpname, hpaddr, hptel
    FROM(
        select row_number() over(order by hidx) AS rno
             , hpname, hpaddr, replace(hptel, '-', '') as hptel
        from hospital
        where lower(hpname) like '%'||lower('행복한의원')||'%'
        )H
    WHERE rno between 1 and 10
    GROUP BY hpname, hpaddr, hptel
)
SELECT hpname, hpaddr, hptel
FROM B
ORDER BY hpname


-- 검색한 병원 리스트 sql(이거 데이터 누락됨 위에게 맞음)
SELECT hpname, hpaddr, hptel
FROM(
    select row_number() over(order by hidx desc) AS rno, hpname, hpaddr
         , replace(hptel, '-', '') as hptel
    from hospital
    where lower(hpname) like '%'||lower('행복한의원')||'%'
    )H
WHERE rno between 1 and 10
group by hpname, hpaddr, hptel
order by hpname


WITH B AS (
    SELECT COUNT(*) AS CNT
    FROM (
        SELECT hpname, hpaddr
        FROM hospital
        WHERE LOWER(hpname) LIKE '%' || LOWER('우리') || '%'
    ) H
    GROUP BY hpname, hpaddr
)
SELECT COUNT(*) AS TotalCount
FROM B;

-- 하나 데이터가져오기(중복되면 맨 위에거만)
SELECT hidx, hpname, hpaddr, hptel
FROM(
    select row_number() over(order by hidx) AS rno
         , hidx, hpname, hpaddr, replace(hptel, '-', '') as hptel
    from hospital
    where hpname = '행복한의원' and hpaddr = '광주광역시 북구 서암대로 179, 2층 (신안동)'
)H
WHERE rno = 1

desc member;

select * from member;


delete from member
where userid = 'user001';

commit;

update member set midx = 0
where userid = 'jhkvng9546';

commit;

-- 디폴드값 보기
select COLUMN_NAME, DATA_TYPE, DATA_LENGTH, NULLABLE, DATA_DEFAULT
from user_tab_columns
where table_name = 'MEDIQ';

alter table mediq
add subject number(1) not null;

commit;

-- 시퀀스 생성한거조회
SELECT sequence_name, min_value, max_value, increment_by, last_number
FROM user_sequences;


-- 질문테이블 시퀀스 생성
create sequence seq_qidx
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

create sequence seq_aidx
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


select *
from mediq;

delete from mediq where userid = 'user001';
commit;


