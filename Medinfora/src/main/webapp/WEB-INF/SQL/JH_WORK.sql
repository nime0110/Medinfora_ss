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

select *
from notice;

delete from mediq where userid = 'user001';
commit;


select *
from media;



insert into media(aidx, qidx, userid, content, writeday)
values(seq_aidx.nextval, 9, 'user001', '<p> 글글글 스마트에딩터로 사진 넣고 첨부파일에도 사진파일 넣어보겠습니다.</p>' ,default);

commit;


select count(*) as cnt
from mediq
where 1=1;

desc mediq;


select count(*) as cnt
from mediq
where 1=1
and subject = 1
and (lower(title) like '%'||lower('글')||'%' or lower(content) like '%'||lower('글')||'%');

WITH Q
AS (
select title, content, subject
from mediq
)
SELECT COUNT(*) AS CNT
FROM media 
WHERE 1=1
and subject = 1
and (lower(title) like '%'||lower('글')||'%' OR lower(Q.content) like '%'||lower('글')||'%' OR lower(A.content) like '%'||lower('글')||'%');

-- 검색을 해보자면
/*
구분을 선택안하고, 전체 내용이자면
먼저 질문테이블에서 글제목이랑, 글내용중에 해당 단어가 있는 컬럼만 구해온다

그다음, 답변테이블에서 질문테이블이랑 조인한 내용중에 해당 단어가 있는 컬럼만 구해온다

그럼 예시로 들자면

질문내용과 제목에 스프링이라는 단어가 포함되어있는 컬럼은 2개가 있다
답변내용중에 단어가 포함되어있는 컬럼은 1개가 있다. 

답변내용이 있는

경우의 수
질문제목에만 해당 단어가 있는 경우 > 질문테이블
질문내용에만 해당 단어가 있는 경우 > 질문테이블
답변내용에만 해당 단어가 있는 경우 > 답변테이블
질문제목과 질문내용에 해당 단어가 있는경우 > 질문테이블
질문제목과 답변내용에 해당 단어가 있는 경우 > 질문, 답변테이블
질문내용과 답변내용에 해당 단어가 있는 경우 > 질문, 답변테이블

질문수 4개 답변수 2개라 가정
이 두개테이블을 합칠 수 있는 방법은?

*/

select count(*) as CNT
from MEDIQ Q FULL JOIN MEDIA A
ON Q.qidx = A.qidx
where 1=1 and subject=1 and (lower(Q.title) like '%'||lower('')||'%' or lower(content) like '%'||lower('')||'%'  or lower(A.content) like '%'||lower('')||'%');


select Q.qidx, Q.userid, title, to_char(to_date(Q.writeday, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') as writeday
     , nvl(Q.imgsrc, ' ') AS imgsrc, Q.acount, Q.open, Q.viewcount
     , nvl(Q.pwd, ' ') AS pwd, Q.subject
from MEDIQ Q FULL JOIN MEDIA A
ON Q.qidx = A.qidx
where 1=1 and lower(A.content) like '%'||lower('')||'%';

-- 전체검색(검색조건 포함)
SELECT distinct qidx, userid, title, writeday, imgsrc, acount, open, viewcount
     , pwd, subject
FROM(
select row_number() over(order by Q.qidx) AS rno
     , Q.qidx, Q.userid, title, to_char(to_date(Q.writeday, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') as writeday
     , nvl(Q.imgsrc, ' ') AS imgsrc, Q.acount, Q.open, Q.viewcount
     , nvl(Q.pwd, ' ') AS pwd, Q.subject
from MEDIQ Q FULL JOIN MEDIA A
ON Q.qidx = A.qidx
where 1=1
)S
WHERE rno between 1 and 10
ORDER BY qidx DESC;

select * from mediq;
select * from media;

select *
from MEDIQ Q FULL JOIN MEDIA A
ON Q.qidx = A.qidx

update MEDIQ set acount = 2
where qidx = 9;

commit;
