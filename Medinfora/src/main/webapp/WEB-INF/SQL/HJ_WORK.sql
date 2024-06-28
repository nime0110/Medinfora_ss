-- 혜정 쿼리

show user;
-- USER이(가) "SYS"입니다.

-- 오라클 계정 생성시 계정명 앞에 c## 붙이지 않고 생성하도록 하겠습니다.
alter session set "_ORACLE_SCRIPT"=true;
-- Session이(가) 변경되었습니다.

create user final_orauser3 identified by gclass default tablespace users; 
-- User FINAL_ORAUSER3이(가) 생성되었습니다.

grant connect, resource, create view, unlimited tablespace to FINAL_ORAUSER3;
-- Grant을(를) 성공했습니다.

-----------------------------------------------------------------------------------
-- 테이블 확인 --
select *
from tab;

-- 테이블 주석 확인 --
select *
from user_tab_comments;

-- 테이블 안 주석 확인 --
select column_name, comments
from user_col_comments
where table_name = '테이블명';
-- LOGINLOG, MEDIA, MEDIQ, RESERVECODE, RESERVE, MEMBER, MEMBERIDX, CLASSCODE, HOSPITAL, NOTICE

----------------------------------------------------------------------------------------
