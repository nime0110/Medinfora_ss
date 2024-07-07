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
    SELECT hpname,
           hpaddr
    FROM (
        SELECT hpname, hpaddr
        FROM hospital
    ) H
    GROUP BY hpname, hpaddr
)
SELECT count(*)
FROM A
where CNT > 2



select *
from hospital
where hpaddr = '충청남도 태안군 안면읍 응지마을길 10';



select *
from hospital
 where lower(hpname) like '%'||lower('행복한의원')||'%'

SELECT hpname, hpaddr, hptel
FROM(
    select row_number() over(order by hidx desc) AS rno, hpname, hpaddr, hptel
    from hospital
    where lower() like '%'||lower('해밀3로')||'%'
    )H
WHERE rno between 11 and 20
group by hpname, hpaddr, hptel







