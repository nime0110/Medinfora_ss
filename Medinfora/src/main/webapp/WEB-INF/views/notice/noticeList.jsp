<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/header.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/footer.css"/>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/notice/notice.js"></script>

<script type="text/javascript">
function click_on(nidx){
    $("input[name='nidx']").val(nidx);
    const frm = document.goViewFrm;
    frm.method = "post";
    frm.action = "<%= ctxPath%>/view.bibo";
    frm.submit();
}


</script>

<body>
    <div class="board_title">
        <p class="nanum-eb size-b">공지사항</p>
        <div class="notice-1">
       <div class="notice-btn1 nanum-eb mb-5">
   <button type="button" class="notice-btn btn btn-lg mr-5"onclick="location.href='<%= ctxPath %>/notice/noticeEdit.bibo'">글 작성하기</button>
 </div>
 </div> 
    </div>
   
    <hr style="max-width: 80%; margin: auto;">
    <section class="board-container">
       
        <ul class="board-list">
            <c:forEach var="notice" items="${requestScope.noticeListdto}">
                <li class="board-item nanum-n">
                    <div class="board-item-body" onclick="click_on('${notice.nidx}')">
                        <span class="board-item-title nanum-n" style="font-weight: bold; font-size: 20px; color: rgb(78, 89, 104); display: block; text-overflow:ellipsis;">
                            <p>${notice.title}</p>
                        </span>
                        <c:if test="${notice.filename != null}">
                            <span class="attach_sh">
                                <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;">
                            </span>
                        </c:if>
                        <span class="board-item-date">${notice.writeday}</span>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </section>

    <form name="goViewFrm">
        <input type="hidden" name="nidx" value=""/>
    </form>

  <div class="w-100 d-flex justify-content-center pt-3">
        <c:out value="${pageBar}" escapeXml="false" />
    </div>
</body>
</html>
