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
values()





