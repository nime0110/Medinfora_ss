<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String ctxPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/mycomment.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/mypage/mycomment.js"></script>

<div class="container" style="margin-top: 100px;">
    <div class="py-4" align="center">
        <h2 class="nanum-eb size-n">내 댓글 목록</h2>
    </div>
    <form name="commuList">
        <input type="hidden" name="PageNo"/>
        <fieldset>
            <div class="p-4" id="search_flexbox" align="center">
                <span class="">
                    <select class="search_ch sel_1 nanum-b" name="type">
                        <option value='content'>댓글내용</option>
                    </select>
                </span>
                
                <span>
                    <input class="search_ch sel_2 nanum-b" name="word" type="text" placeholder="검색어를 입력해주세요." autocomplete="none"/>
                </span>
                <span>
                    <button class="jh_btn_design search nanum-eb size-s" type="button" onclick="searchList(1)">검색</button>
                </span>
            </div>        
        </fieldset>
    </form>
    
    <div>
        <!-- 타이틀 -->
        <div class="mt-4 px-3 subject">
            <div class="row text-center py-3 nanum-eb size-s">
                <span class="col-10">댓글내용</span>
                <span class="col-2">작성일자</span>
            </div>
        </div>
        <div class="mb-5 px-3" id="commuArea"></div>
    
    <div class="pagination" style="text-align: center;" id="rpageNumber">
    </div>
  </div>
</div>
  
