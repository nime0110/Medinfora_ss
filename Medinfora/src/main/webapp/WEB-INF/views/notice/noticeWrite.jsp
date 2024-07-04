<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/noticeMedia.css" />
<script type="text/javascript" src="<%= ctxPath %>/resources/js/notice/notice.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var obj = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: obj,
            elPlaceHolder: "content",
            sSkinURI: "<%= ctxPath %>/resources/smarteditor/SmartEditor2Skin.html",
            htParams: {
                bUseToolbar: true,
                bUseVerticalResizer: true,
                bUseModeChanger: true,
            }
        });

        $("button#on").click(function(){
            obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);

            const TITLE = $("input[name='title']").val().trim();
            if(TITLE == ""){
                alert("글 제목을 입력하세요!!");
                return;
            }

            let CONTENT = $("textarea[name='content']").val().trim();
            CONTENT = CONTENT.replace(/&nbsp;/gi, "");
            CONTENT = CONTENT.substring(CONTENT.indexOf("<p>") + 3);
            CONTENT = CONTENT.substring(0, CONTENT.indexOf("</p>"));

            if(CONTENT.trim().length == 0) {
                alert("글 내용을 입력하세요!!");
                return;
            }

            const frm = document.noticeWriteFrm;
            frm.method = "post";
            frm.action = "<%= ctxPath %>/noticeWriteEnd.bibo";
            frm.submit();
        });
    });
</script>

<div class="board_wrap">
    <div class="board_title">
        <p class="nanum-eb size-b">공지사항 글 등록하기</p>
    </div>
    <br>
    <div style="display: flex;">
        <div style="margin: auto; padding-left: 3%;">
            <h2 style="margin-bottom: 30px;">글쓰기</h2>
            <form name="noticeWriteFrm">
                <table style="width: 1024px" class="table table-bordered">
                    <tr>
                        <th style="width: 15%;">제목</th>
                        <td>
                            <input type="text" name="TITLE" size="100" maxlength="200" />
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 15%;">내용</th>
                        <td>
                            <textarea style="width: 100%; height: 612px;" name="CONTENT" id="CONTENT"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 15%; background-color: #DDDDDD;"> 파일첨부</th>
                        <td>
                            <input type="file" name="attach"/>
                        </td>
                    </tr>
                </table>
                <div class="writebtn" style="margin: 20px;">
                    <button type="button" class="btn btn-secondary btn-sm mr-3" id="on">글쓰기</button>
                    <button type="button" class="btn btn-secondary btn-sm" onclick="javascript:history.back()">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>
