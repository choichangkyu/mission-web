<%@page import="notice.vo.BoardFileVO"%>
<%@page import="java.util.List"%>
<%@page import="notice.dao.BoardDAO"%>
<%@page import="notice.vo.BoardVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	int no = Integer.parseInt(request.getParameter("no")); // 게시물 번호
	BoardDAO dao = new BoardDAO();

	/*String type = request.getParameter("type");
	 if(! type.equals("edit"))
		dao.viewBoard(no); */
	String back_url = request.getHeader("REFERER"); // 이전url이 수정페이지가 아니면 조회수 업
	System.out.println(back_url);
	if (back_url.indexOf("board/edit_post") == -1)
		dao.viewBoard(no);

	BoardVO board = dao.selectByNo(no); // 현재 게시물 
	List<BoardFileVO> fileList = dao.selectFileList(no);
	
	pageContext.setAttribute("board", board);
	pageContext.setAttribute("fileList", fileList);
%>
<script type="text/javascript">
$(document).ready(function(){
	$("input").click(function(event){
		var name = $(event.target).attr("name");
		switch(name){
		case "post_edit":
			if( confirm("글을 수정하시겠습니까?") )
				location.href="/Mission-Web/page/board/edit_post.jsp?no=<%=no%>";
			break;
		case "post_dele":
			if( confirm("글을 삭제하시겠습니까?") )
				location.href="/Mission-Web/jsp/board/dele_post.jsp?no=<%=no%>";
			break;
		case "post_list":
			location.href = "list_post.jsp";
			break;
		}
	});
	
	$("img.download").click(function(){
		$("form[name=downForm]").submit();
	});
	
});
</script>
<div align="center">
	<hr>
	<h3>게시글 상세</h3>
	<hr>
	<table style="width: 100%;">

		<c:if test="${ board != null }">
			<tr>
				<th width="23%">번호</th>
				<td>${ board.no }</td>
			</tr>
			<tr>
				<th width="23%">작성날짜</th>
				<td>${ board.regDate }</td>
			</tr>
			<tr>
				<th width="23%">제목</th>
				<td><c:out value="${ board.title }" /></td>
			</tr>

			<tr>
				<th width="23%">글쓴이</th>
				<td>${ board.writer }</td>
			</tr>
			<tr>
				<th width="23%">내용</th>
				<td><c:out value="${ board.content }" /></td>
			</tr>
			<tr>
				<th width="23%">조회수</th>
				<td>${ board.viewCnt }</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<c:forEach items="${ fileList }" var="file" >
						<form name="downForm" action="/Mission-Web/jsp/board/fileDownload.jsp" method="GET">
							<img class="download" src="/Mission-Web/upload/${file.fileSaveName}" width="100px">
							<input type="hidden" name="oriName" value="${file.fileOriName}">
							<input type="hidden" name="saveName" value="${file.fileSaveName}">
						</form>
					</c:forEach>
				
				</td>
			</tr>

		</c:if>
	</table>
	<c:if test="${ member.id == board.writer }">
	<input class="btn" type="button" name="post_edit" value="수정"> 
	<input class="btn" type="button" name="post_dele" value="삭제">
	</c:if>
	<input class="btn" type="button" name="post_list" value="목록">
</div>