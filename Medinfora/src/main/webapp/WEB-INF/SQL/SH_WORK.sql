-- 승혜 쿼리

show USER
select *
from tab


/*
/* 이거 수업시간 때꺼임 
create table tbl_board
(seq         number                not null    -- 글번호
,fk_userid   varchar2(20)          not null    -- 사용자ID
@ -20,4 +20,81 @@ create table tbl_board
,constraint FK_tbl_board_fk_userid foreign key(fk_userid) references tbl_member(userid)
,constraint CK_tbl_board_status check( status in(0,1) )
);
*/
*/

desc notice;
/*
이름       널?       유형             
-------- -------- -------------- 
NIDX     NOT NULL NUMBER         
USERID   NOT NULL VARCHAR2(20)   
TITLE    NOT NULL NVARCHAR2(50)  
CONTENT  NOT NULL CLOB           
VIEWCNT  NOT NULL NUMBER(3)      
WRITEDAY NOT NULL NVARCHAR2(20)  
FILENAME          NVARCHAR2(300) 
ORGNAME           NVARCHAR2(300) 
FILESIZE          NUMBER      
*/

create sequence seq_notice
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle;


select *
from user_sequences;

from 

insert into notice(NIDX, USERID, title, content, viewcnt, writeday) values(seq_notice.nextval, 'test001', '두번째입니다','두번째 글입니다',0,20240702  ); 
commit;
/*

    <insert id="" parameterType="NoticeDTO">
        insert into notice (NIDX, USERID, TITLE, CONTENT, VIEWCNT, WRITEDAY)
        values (seq_notice.nextval, #{USERID}, #{TITLE}, #{CONTENT}, #{VIEWCNT}, #{WRITEDAY})
    </insert>
    */
select nidx, userid, title, content, viewcnt,  TO_CHAR(TO_DATE(writeday, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') AS writeday, filename, orgname, filesize
from notice
order by nidx desc;
/* 삭제할 때 drop */
   /* <insert id="" parametertype="noticedto">
        insert into notice (nidx, userid, title, content, viewcnt, writeday)
        values (seq_notice.nextval, #{userid}, #{title}, #{content}, #{viewcnt}, #{writeday})
    </insert>
   */
    
    select count(*)
    from notice
    
    
    
    SELECT nidx, userid, title, content, viewcnt, TO_CHAR(TO_DATE(writeday, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') AS writeday, filename, orgname, filesize
      FROM
      (
       select row_number() over(order by nidx desc) AS rno 
            , nidx, userid, content, title
            , viewcnt, TO_CHAR(TO_DATE(writeday, 'yyyy-mm-dd hh24:mi:ss')) AS writeday 
           ,filename, orgname, filesize
       from notice
   ) V
      WHERE rno between 1 and 10
        
        
        SELECT * FROM notice;
        
        
        SELECT nidx, userid, title, content, viewcnt, 
       TO_CHAR(TO_DATE(writeday, 'YYYYMMDD'), 'yyyy-mm-dd') AS writeday,
       filename, orgname, filesize
FROM (
    SELECT rownum AS rno, nidx, userid, title, content, viewcnt, writeday, filename, orgname, filesize
    FROM notice
    WHERE 1=1
    ORDER BY nidx DESC
)
WHERE rno BETWEEN 1 AND 10;