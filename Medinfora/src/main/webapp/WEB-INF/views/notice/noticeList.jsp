<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
 <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" /> 
 <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/header.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/notice/notice.js"></script>

</head>
<body>
 
  <div class="board_title nanum-b size-b">
      <h1>공지사항</h1>
      <hr>
  </div>
         <section class="board-container">
        <ul class="board-list"> 
            <li class="board-item nanum-n">
                <div class="board-item-body">
             
                    <span class="board-item-title" style="font-wight: bold; font-size: 20px; color: rgb(78, 89, 104); display: block; text-overflow:ellipsis;"><a href="<%= ctxPath %>/notice/noticeView.bibo">여기에 글이 들어갈 예정입니다</a></span>
                   <span class="attach_sh">
   <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;"></a>
                        </span>
                    <span class="board-item-date">2024.04.30</span>
                </div>
                
            </li>
     
        </ul>
        <ul class="board-list"> 
            <li class="board-item nanum-n">
                <div class="board-item-body">
             
                    <span class="board-item-title" style="font-wight: bold; font-size: 20px; color: rgb(78, 89, 104); display: block; text-overflow:ellipsis;"><a href="<%= ctxPath %>/notice/noticeView.bibo">여기에 글이 들어갈 예정입니다</a></span>
                   <span class="attach_sh">
   <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;"></a>
                        </span>
                    <span class="board-item-date">2024.04.30</span>
                </div>
                
            </li>
     
        </ul>
        <ul class="board-list"> 
            <li class="board-item nanum-n">
                <div class="board-item-body">
             
                    <span class="board-item-title" style="font-wight: bold; font-size: 20px; color: rgb(78, 89, 104); display: block; text-overflow:ellipsis;"><a href="<%= ctxPath %>/notice/noticeView.bibo">여기에 글이 들어갈 예정입니다</a></span>
                   <span class="attach_sh">
   <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;"></a>
                        </span>
                    <span class="board-item-date">2024.04.30</span>
                </div>
                
            </li>
     
        </ul>
         <ul class="board-list"> 
            <li class="board-item nanum-n">
                <div class="board-item-body">
             
                    <span class="board-item-title" style="font-wight: bold; font-size: 20px; color: rgb(78, 89, 104); display: block; text-overflow:ellipsis;"><a href="<%= ctxPath %>/notice/noticeView.bibo">여기에 글이 들어갈 예정입니다</a></span>
                   <span class="attach_sh">
   <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;"></a>
                        </span>
                    <span class="board-item-date">2024.04.30</span>
                </div>
                
            </li>
     
        </ul>
         <ul class="board-list"> 
            <li class="board-item nanum-n">
                <div class="board-item-body">
             
                    <span class="board-item-title" style="font-wight: bold; font-size: 20px; color: rgb(78, 89, 104); display: block; text-overflow:ellipsis;"><a href="<%= ctxPath %>/notice/noticeView.bibo">여기에 글이 들어갈 예정입니다여기에 글이 들어갈 예정입니다여기에 글이 들어갈 예정입니다여기에 글이 들어갈 예정입니다여기에 글이 들어갈 예정입니다</a></span>
                   <span class="attach_sh">
   <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;"></a>
                        </span>
                    <span class="board-item-date">2024.04.30</span>
                </div>
                
            </li>
     
        </ul>
    </section>
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
	       <div class="sh-button">
             <a href="<%= ctxPath %>/notice/noticeWrite.bibo"> <button class="btn btn-primary me-md-2" style="border-radius:60%;float: right;">글 올리기</button></a>
            </div>
	    </div>
    
</body>
