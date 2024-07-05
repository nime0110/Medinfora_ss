<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/header.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/notice/notice.js"></script>

<script type="text/javascript">
function click_on(nidx){
    //alert(nidx);
    $("input[name='nidx']").val(nidx);
    const frm = document.goViewFrm;
    frm.method = "post";
    frm.action = "<%= ctxPath%>/view.bibo";
    frm.submit();
}



<%-- function goView(seq){
    const goBackURL = "${requestScope.goBackURL}";

    const frm = document.goViewFrm;
    frm.seq.value = seq;
    frm.goBackURL.value = goBackURL;

    frm.method = "post";
    frm.action = "<%= ctxPath%>/noticeView.action";
    frm.submit();
} --%>
</script>

<body>
    <div class="board_title">
        <p class="nanum-eb size-b">공지사항</p>
    </div>
    <br>
    <section class="board-container">
        <div class="df nanum-eb size-s">
            <a href="<%= ctxPath %>/notice/noticeWrite.bibo">글 등록하기</a>
        </div>
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
        <c:out value="${requestScope.pageBar}" escapeXml="false" />
    </div>
</body>
</html>
