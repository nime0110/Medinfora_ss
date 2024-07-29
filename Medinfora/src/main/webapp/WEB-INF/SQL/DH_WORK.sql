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

select Hpname
from HOSPITAL
where Hpname like '%'||'서울'||'%' or HPADDR like '%'||'서울'||'%' group by Hpname, HPADDR;

select TITLE
from MEDIQ
where TITLE like '%'||'비타민'||'%' or CONTENT like '%'||'비타민'||'%';

select CONTENT
from MEDIA
where CONTENT like '%'||'비타민'||'%';

select TITLE
from NOTICE
where TITLE like '%'||'홈'||'%' or CONTENT like '%'||'홈'||'%';

select rno,HPNAME,hpaddr,AGENCY
from(
    select row_number() over(order by HPNAME) as rno, HPNAME, HPADDR, AGENCY
    from HOSPITAL
    where Hpname like '%'||'창원'||'%' group by HPNAME, HPADDR, AGENCY
)where rno = '3';

select title, CONTENT, WRITEDAY, ACOUNT, VIEWCOUNT
from (
    select row_number() over (order by WRITEDAY) as rno,title, CONTENT, WRITEDAY, ACOUNT, VIEWCOUNT
    from MEDIQ
    where OPEN = 1 and (TITLE like '%'||'비타민'||'%' or CONTENT like '%'||'비타민'||'%')
)where rno between 1 and 5;

select title, CONTENT, WRITEDAY, acount, viewcount
from (
    select row_number() over (order by MEDIA.WRITEDAY) as rno, MEDIA.CONTENT, TITLE, MEDIA.WRITEDAY,acount, viewcount
    from MEDIA join mediq on MEDIA.QIDX = mediq.QIDX
    where OPEN = 1 and MEDIA.CONTENT like '%'||'첨부'||'%'
)where rno between 1 and 5;

select TITLE,CONTENT,WRITEDAY,VIEWCNT
from (
    select row_number() over (order by WRITEDAY desc) as rno,title, CONTENT, WRITEDAY, VIEWCNT
    from NOTICE
    where TITLE like '%'||''||'%' or CONTENT like '%'||''||'%'
)where rno between 1 and 5;

select HPNAME,hpaddr,AGENCY, HPTEL, hidx
		from(
		    select row_number() over(order by HPNAME) as rno, HPNAME, HPADDR, AGENCY, HPTEL, HIDX
		    from HOSPITAL
		    where Hpname like '%'||'창원'||'%'
		)where rno between 1 and 5

select USERID
from CLASSCODEMET
where HIDX ='';

select SEARCHWORD
from
(
    select row_number() over (order by cnt desc) as rno, SEARCHWORD, cnt
    from
    (
        select SEARCHWORD, count(*) as cnt
        from SEARCHLOG
        group by SEARCHWORD
    )
)
where rno between 1 and 5;

select hidx,HPNAME,HPNAME,AGENCY,HPTEL
from
(
    select row_number() over (order by HPNAME) as rno, HOSPITAL.HIDX as hidx,HPNAME,HPADDR,AGENCY,HPTEL
    from CLASSCODEMET join HOSPITAL on CLASSCODEMET.HIDX = HOSPITAL.HIDX
    where HPNAME like '%'||'창원'||'%'
    group by HOSPITAL.HIDX, HPNAME, HPADDR, AGENCY, HPTEL
)
where rno between 1 and 5;

select hidx,HPNAME,HPaddr,AGENCY,HPTEL
		from
		(
		    select row_number() over (order by HPNAME) as rno, HOSPITAL.HIDX as hidx,HPNAME,HPADDR,AGENCY,HPTEL
		    from CLASSCODEMET join HOSPITAL on CLASSCODEMET.HIDX = HOSPITAL.HIDX
		    where HPNAME like '%'||'창원'||'%'
		    group by HOSPITAL.HIDX, HPNAME, HPADDR, AGENCY, HPTEL
		)
		where rno between 1 and 5


select Hpname,HPaddr,AGENCY,HPTEL
		from(
		    select row_number() over(order by HPNAME) as rno, HPNAME, HPADDR, AGENCY, HPTEL
		    from HOSPITAL
		    where Hpname like '%'||'비타민'||'%' group by HPNAME, HPADDR, AGENCY, HPTEL
		)where rno between 1 and 5;

select HIDX
from (select hidx, row_number() over (order by HIDX) as rno
      from HOSPITAL
      where hpname = '비타민가정의원')
where rno = 1;

select count(*)
from(
    select row_number() over(order by HPNAME) as rno, HPNAME, HPADDR, AGENCY, HPTEL
    from HOSPITAL
    where Hpname like '%'||'비타민'||'%'
    group by HPNAME, HPADDR, AGENCY, HPTEL
);

select count(*)
from SEARCHLOG where substr(REGISTERDAY,0,10) = '2024-07-23'
and userid != 'Anonymous';

select searchword, count
from
(
select row_number() over (order by count(*) desc) as rno, SEARCHWORD, count(*) as count
 from (select SEARCHWORD
       from SEARCHLOG
       where substr(REGISTERDAY, 0, 10) between substr(to_char(sysdate-7,'yyyy-mm-dd'), 0, 10)) and substr(to_char(sysdate,'yyyy-mm-dd'), 0, 10))
 group by SEARCHWORD
) where rno between 1 and 7;


select title, CONTENT, WRITEDAY, ACOUNT, VIEWCOUNT,qidx
		from (
		    select row_number() over (order by WRITEDAY desc) as rno,title, CONTENT, WRITEDAY, ACOUNT, VIEWCOUNT,QIDX
		    from MEDIQ
		    where OPEN = 1 and (TITLE like '%'||'사이트'||'%' or CONTENT like '%'||'사이트'||'%')
		)where rno between 1 and 5;

select TITLE, CONTENT, WRITEDAY, ACOUNT, VIEWCOUNT, QIDX
		from (
		    select row_number() over (order by MEDIA.WRITEDAY desc) as rno, MEDIA.CONTENT, TITLE, MEDIA.WRITEDAY,acount, viewcount, MEDIQ.QIDX as qidx
		    from MEDIA join mediq on MEDIA.QIDX = mediq.QIDX
		    where OPEN = 1 and MEDIA.CONTENT like '%'||'중고'||'%'
		)where rno between 1 and 5;

select SEARCHWORD,REGISTERDAY
from SEARCHLOG where userid = 'redtree2379'
order by  REGISTERDAY desc;

