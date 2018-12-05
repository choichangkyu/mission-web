<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="notice.dao.BoardDAO"%>
<%@page import="util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="notice.vo.BoardVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("utf-8");
	String search = request.getParameter("search");
	String category = request.getParameter("category");
	
	BoardDAO dao = new BoardDAO();
	
	//페이징
	List<BoardVO> allBoardList = dao.searchAllBoard(search, category);
	String r_page = request.getParameter("page");

	int _page = r_page == null ? 1 : Integer.parseInt(r_page);
	int post_size = 5;
	int start = 1 + (_page - 1) * post_size; // 1:1 2:6
	int end = _page * post_size; // 1:5 2:10

	List<BoardVO> BoardList = dao.searchBoardList(start, end, search, category);

	int step = (int) Math.ceil((double) allBoardList.size() / post_size);
	
	System.out.println(allBoardList.size());
	//변수
	pageContext.setAttribute("search", search);
	pageContext.setAttribute("category", category);
	pageContext.setAttribute("step", step);
	pageContext.setAttribute("boardList", BoardList);
	
%>
<script type="text/javascript">
	$(document).ready(function() {
		$("input").click(function(event) {
			var name = $(event.target).attr("name");
			switch (name) {
			case "new_post":
				location.href = "add_post.jsp";
				break;
			case "mem_list":
				location.href = "/Mission-Web/page/member/list_user.jsp";
				break;
			}
		});
		
		$("form[name=search]").submit(function(){
			if( $("input[name=search]").val() == "" ){
				alert("검색어를 입력하세요");
				return false;
			}
			return true;
		});
	});
	function go_detail(no) {
		<c:if test="${ not empty member }">
		location.href = "detail_post.jsp?no=" + no;
		</c:if>
		<c:if test="${ empty member }">
		alert("로그인하세요");
		</c:if>
	}
</script>
<div align="center">
	<hr>
	<h3>"${ search }" 검색 결과</h3>
	
	<form class="R" action="/Mission-Web/page/board/search_post.jsp" name="search" method="GET">
		<select name="category">
			<option value="">선택하세요</option>
			<option value="writer">글쓴이</option>
			<option value="title">제목</option>
		</select>
		<input type="text" name="search">
		<input type="submit" value="검색">		
	</form>
	
	<table class="list_table">
		<tr>
			<th width="7%">번호</th>
			<th>제목</th>
			<th width="16%">글쓴이</th>
			<th width="20%">등록일</th>
		</tr>
		<c:forEach items="${boardList}" var="board">
			<tr>
				<td>${board.no }</td>
				<td><a href="javascript:go_detail('${board.no }')"> <c:out
							value="${board.title }" />
				</a></td>
				<td>${board.writer }</td>
				<td>${ board.regDate}</td>
			</tr>
		</c:forEach>
	</table>

	<div id="page_step">
		<c:forEach var="i" begin="1" end="${step}" step="1">
			<a class="page_link" href="search_post.jsp?page=${i}&search=${search}&category=${category}">${i}</a>
		</c:forEach>
	</div>
	<c:if test="${ not empty member }">
		<div class="R">
			<input class="btn" type="button" name="new_post" value="새글등록">
		</div>
	</c:if>
</div>