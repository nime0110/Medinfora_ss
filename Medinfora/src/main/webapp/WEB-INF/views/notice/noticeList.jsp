<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/notice/notice.js"></script>

</head>
<body>
    <div class="board_wrap">
        <div class="board_title">
            <div>
                <strong>공지사항</strong>
                <p>공지사항을 빠르고 정확하게 안내해드립니다.</p>
            </div>
            <div class="bt_wrap">
                <a href="<%= ctxPath %>/write.html" class="on">등록</a>
            </div>
        </div>
        <div class="board_list_wrap">
            <table class="board_list">
                <thead>
                    <tr>
                        <th class="num">번호</th>
                        <th class="title">제목</th>
                        <th class="date">작성일</th>
                        <th class="count">조회</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="num">5</td>
                        <td class="title"><a href="<%= ctxPath %>/WEB-INF/views/notice/noticeView.jsp">글 제목이 들어갑니다.</a></td>
            
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">4</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
                 
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">3</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
               
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">2</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
             
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">1</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
      
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                </tbody>

            </table>
 			<div class="w-100 d-flex justify-content-center pt-3">
	        <ul class="pagination reserve_pagebar nanum-n size-s">
	            <li class="page-item">
	                <a class="page-link" href="#" aria-label="Previous">
	                    <span aria-hidden="true">&laquo;</span>
	                </a>
	            </li>
	            <li class="page-item">
	                <a class="page-link nowPage" href="#">1</a>
	            </li>
	            <li class="page-item">
	                <a class="page-link" href="#">2</a>
	            </li>
	            <li class="page-item">
	                <a class="page-link" href="#">3</a>
	            </li>
	            <li class="page-item">
	                <a class="page-link" href="#" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
	        </ul>
	    </div>
        </div>
    </div>
</body>
</html>
