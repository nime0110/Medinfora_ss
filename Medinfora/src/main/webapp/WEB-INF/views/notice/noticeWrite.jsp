<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <% String ctxPath = request.getContextPath(); %>
 <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/notice/notice.js"></script>
<body>
    <div class="board_wrap">
        <div class="board_title">
            <strong>공지사항</strong>
            
        </div>
        <div class="board_write_wrap">
            <div class="board_write">
                <div class="title">
                    <dl class="border-bottom">
                        <dt>제목</dt>
                        <dd><input type="text" placeholder="제목 입력" value="글 제목이 들어갑니다"></dd>
                    </dl>
                
                
                </div>
                <div class="cont">
                 <dt>내용</dt>
                    <textarea placeholder="내용 입력">
글 내용이 들어갑니다.
글 내용이 들어갑니다.
글 내용이 들어갑니다.
글 내용이 들어갑니다.
글 내용이 들어갑니다.
글 내용이 들어갑니다.
글 내용이 들어갑니다.
글 내용이 들어갑니다.</textarea>

               
                </div>
            </div>
            <div class="bt_wrap">
                <a href="view.html" class="on">수정</a>
                <a href="view.html">취소</a>
            </div>
        </div>
    </div>
</body>