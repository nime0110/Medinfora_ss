<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>

<style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
        }
      .board_title strong {
    font-size: 3rem;
}


        a {
            text-decoration: none;
            color: inherit;
        }
        .board_wrap {
            width: 90%;
            max-width: 1000px;
            margin: 50px auto;
        }
        .board_title {
            margin-bottom: 30px;
        }
        .board_title p {
            margin-top: 5px;
            font-size: 1.4rem;
        }
        .bt_wrap {
            margin-top: 30px;
            text-align: center;
            font-size: 0;
        }
        .bt_wrap a {
            display: inline-block;
            min-width: 80px;
            margin-left: 10px;
            padding: 10px;
            border: 1px solid #000;
            border-radius: 2px;
            font-size: 1.4rem;
        }
        .bt_wrap a:first-child {
            margin-left: 0;
        }
        .bt_wrap a.on {
            background: #000;
            color: #fff;
        }
        .board_list {
            width: 100%;
            border-top: 2px solid #000;
            border-collapse: collapse;
        }
        .board_list th, .board_list td {
            border-bottom: 1px solid #ddd;
            padding: 15px;
            text-align: center;
            font-size: 1.4rem;
        }
        .board_list th {
            font-weight: 600;
            background: #f5f5f5;
        }

        /* Responsive styles */
        @media (max-width: 768px) {
            .board_wrap {
                width: 95%;
            }
            .board_list th, .board_list td {
                padding: 10px 5px;
            }
            .board_title p {
                font-size: 1.2rem;
            }
            .bt_wrap a {
                padding: 8px;
                font-size: 1.2rem;
            }
            .board_list th, .board_list td {
                font-size: 1.2rem;
            }
        }

        @media (max-width: 480px) {
            .board_title p {
                font-size: 1rem;
            }
            .bt_wrap a {
                padding: 6px;
                font-size: 1rem;
            }
            .board_list th, .board_list td {
                font-size: 1rem;
                padding: 8px 2px;
            }
        }
    </style>
</head>
<body>
  <div class="board_wrap">
        <div class="board_title">
            <strong>공지사항</strong>
            <p>공지사항을 빠르고 정확하게 안내해드립니다.</p>
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
                        <th class="writer">글쓴이</th>
                        <th class="date">작성일</th>
                        <th class="count">조회</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="num">5</td>
                        <td class="title"><a href="<%= ctxPath %>/WEB-INF/views/notice/noticeView.jsp">글 제목이 들어갑니다.</a></td>
                        <td class="writer">김이름</td>
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">4</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
                        <td class="writer">김이름</td>
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">3</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
                        <td class="writer">김이름</td>
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">2</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
                        <td class="writer">김이름</td>
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                    <tr>
                        <td class="num">1</td>
                        <td class="title"><a href="<%= ctxPath %>/view.html">글 제목이 들어갑니다.</a></td>
                        <td class="writer">김이름</td>
                        <td class="date">2021.1.15</td>
                        <td class="count">33</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
