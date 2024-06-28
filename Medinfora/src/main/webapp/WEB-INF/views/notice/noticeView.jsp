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
        <div class="board_view_wrap">
            <div class="board_view">
                <div class="title">
                    <h2>글 제목이 들어갑니다.</h2>
                    <div class="info">
                        <dl>
                            <dt>작성일</dt>
                            <dd>2021.1.16</dd>
                        </dl>
                        <dl>
                            <dt>조회</dt>
                            <dd>33</dd>
                        </dl>
                    </div>
                </div>
                <div class="cont">
                    글 내용이 들어갑니다<br>
                    글 내용이 들어갑니다<br>
                    글 내용이 들어갑니다<br>
                    글 내용이 들어갑니다<br>
                    글 내용이 들어갑니다<br>
                    글 내용이 들어갑니다<br>
                    글 내용이 들어갑니다<br>
                    글 내용이 들어갑니다
                </div>
            </div>
            <div class="bt_wrap_view">
                <a href="javascript:history.back();" class="on">목록</a>
                <a href="#">수정</a>
                <a href="#">삭제</a>
            </div>
        </div>
        </div>
</body>