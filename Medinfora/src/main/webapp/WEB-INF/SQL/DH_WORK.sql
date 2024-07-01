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