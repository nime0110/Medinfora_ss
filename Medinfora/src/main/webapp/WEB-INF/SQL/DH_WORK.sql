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

