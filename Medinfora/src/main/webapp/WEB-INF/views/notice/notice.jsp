<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항</title>
    <style>
        body {
            font-family: 'Nanum Gothic', sans-serif;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #f8f9fa;
            padding: 10px 20px;
        }
        .header h1 {
            margin: 0;
            display: inline-block;
        }
        .nav {
            background-color: #343a40;
            padding: 10px 20px;
            color: white;
        }
        .nav a {
            color: white;
            text-decoration: none;
            padding: 8px 16px;
            display: inline-block;
        }
        .nav a:hover {
            background-color: #495057;
        }
        .content {
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #dee2e6;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f8f9fa;
            text-align: center;
        }
        td {
            text-align: center;
        }
        .search-form {
            margin-bottom: 20px;
            text-align: center;
        }
        .search-form input[type="text"] {
            padding: 5px;
            width: 200px;
        }
        .search-form button {
            padding: 5px 10px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>공지사항</h1>
    </div>
    <div class="nav">
        <a href="#">건강소식</a>
        <a href="#">건강교육자료실</a>
        <a href="#">알기쉬운 건강용어</a>
        <a href="#">미디어 자료실</a>
        <a href="#">관련링크</a>
        <a href="#">공지사항</a>
    </div>
    <div class="content">
        <div class="search-form">
            <form action="notice_board.jsp" method="get">
                <input type="text" name="query" placeholder="검색">
                <button type="submit">검색</button>
            </form>
        </div>
        <table>
            <thead>
                <tr>
                    <th style="width: 70px;">순번</th>
                    <th style="width: 70px;">글번호</th>
                    <th style="width: 300px;">제목</th>
                    <th style="width: 70px;">성명</th>
                    <th style="width: 150px;">날짜</th>
                    <th style="width: 60px;">조회수</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Sample data, replace with database query result
                    String[][] notices = {
                        {"1", "1001", "너와 내 가족 혈압 알기! 5월 이벤트 당첨자 발표", "관리자", "2024-06-14", "60"},
                        {"2", "1002", "청소년 맞춤형 건강정보 제안 이벤트", "관리자", "2024-06-06", "1303"},
                        {"3", "1003", "너와 내 가족 혈압 알기! 5월 이벤트", "관리자", "2024-05-16", "4477"},
                        {"4", "1004", "국가건강정보포털 폐기기 이벤트 당첨자 발표", "관리자", "2023-11-15", "502"},
                        {"5", "1005", "국가건강정보포털 폐기기 이벤트", "관리자", "2023-10-18", "1254"},
                        {"6", "1006", "무엇이든 물어보세요~ 9월 월간세알 당첨자 발표", "관리자", "2023-09-20", "67"},
                        {"7", "1007", "나는 월간세알을 챙겨보다 사다. 7월 이벤트 당첨자 발표", "관리자", "2023-08-16", "68"},
                        {"8", "1008", "8월 이벤트_무엇이든 물어보세요", "관리자", "2023-08-16", "837"},
                        {"9", "1009", "나는 월간세알을 챙겨보다 사다. 7월 이벤트 공지 & 당첨자 발표", "관리자", "2023-07-18", "296"},
                        {"10", "1010", "나는 월간세알을 챙겨보다 사다. 6월 이벤트", "관리자", "2023-06-21", "912"}
                    };

                    for (String[] notice : notices) {
                        out.println("<tr>");
                        for (String field : notice) {
                            out.println("<td>" + field + "</td>");
                        }
                        out.println("</tr>");
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
