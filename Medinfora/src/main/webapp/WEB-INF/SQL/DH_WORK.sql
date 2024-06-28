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

select * from HOSPITAL where HPADDR like '%'||'자치도'||'%';

