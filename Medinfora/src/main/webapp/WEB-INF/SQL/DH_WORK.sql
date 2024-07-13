-- 동혁 쿼리

select * from member;

select * from CLASSCODE;

create sequence seq_hIdx
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

select * from HOSPITAL where HPADDR like '%'||'세종'||'%';

select area from KOREAAREA group by area;

select local from KOREAAREA where KOREAAREA.AREA = '세종특별자치시';

select CLASSCODE,CLASSNAME from CLASSCODE;

select * from KOREAAREA where city ='전라북도';

DELETE FROM FINAL_ORAUSER3.KOREAAREA where city like '%'||'강원도'||'%';

select * from HOSPITAL where HPADDR like '%'||'금산군'||'%';

select COUNTRY from KOREAAREA where CITY = '서울특별시' and LOCAL = '마포구'

select HPADDR from HOSPITAL where HPADDR like '%'||'해밀3로'||'%';

select ridx, to_date(CHECKIN,'yyyy-mm-dd'), hidx
from RESERVE

DELETE
FROM KOREAAREA;

select * from KOREAAREA where LOCAL like '%'||'구';

select * from NOTICE;

select CLASSCODE
from HOSPITAL
where HPNAME = '행복한의원' and HPADDR ='전라남도 여수시 동문로 100(공화동)'

select *
from MEMBER
where MIDX = 2;

select  hidx, hpname, hpaddr, hptel
from(
    select CLASSCODEMET.HIDX as hidx,hpname,HPADDR,HPTEL,row_number() over(order by CLASSCODEMET.hidx desc) AS rno
    from CLASSCODEMET join HOSPITAL on CLASSCODEMET.HIDX = HOSPITAL.HIDX
    where HPADDR like '%'||'창원시'||'%'
    group by HPNAME,HPADDR,CLASSCODEMET.HIDX,HPTEL
)

select CLASSCODEMET.HIDX as hidx,hpname,HPADDR,HPTEL,row_number() over(order by CLASSCODEMET.hidx desc) AS rno
from CLASSCODEMET join HOSPITAL on CLASSCODEMET.HIDX = HOSPITAL.HIDX where CLASSCODEMET.CLASSCODE = 'D001'

select count(*)
from MEMBER where MIDX = '2';

select  count(*)
from(
    select CLASSCODEMET.HIDX as hidx,hpname,HPADDR,
    HPTEL,row_number() over(order by CLASSCODEMET.hidx desc) AS rno
    from CLASSCODEMET join HOSPITAL on CLASSCODEMET.HIDX = HOSPITAL.HIDX
    group by HPNAME,HPADDR,CLASSCODEMET.HIDX,HPTEL
)


select trunc(months_between(sysdate, max(to_date(registerday),'YYYY/MM/DD')) ) as lastlogingap
from loginlog where USERID ='redtree2379';

select userid from CLASSCODEMET where hidx = 120 group by userid;

select userid from MEMBER where userid = 'redtree' and PWD = '';