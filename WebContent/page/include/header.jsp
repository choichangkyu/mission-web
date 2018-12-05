<%@page import="notice.vo.MemberVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
MemberVO member = (MemberVO)session.getAttribute("member");
%>
<link rel="stylesheet" href="/Mission-Web/assets/css/header.css">
<script type="text/javascript">
	$(document).ready(function(){
		$("form[name=login_form]").submit(function(){
			if( $("input[name=login_id]").val() == "" ){
				$("input[name=login_id]").focus();
				alert("아이디를 입력하세요");
				return false;
			} else if( $("input[name=login_password]").val() == "" ){
				$("input[name=login_password]").focus();
				alert("비밀번호를 입력하세요");
				return false;
			}
			return true;
		});
	});
</script>
<div id="header_content">
	<a id="main_link" href="/Mission-Web"> 
		<img alt="google_logo" src="/Mission-Web/assets/images/logo.png">
	</a>
	<nav id="gnb">
		<ul>
			<li><a href="/Mission-Web/page/member/list_user.jsp">회원관리</a></li>
			<li><a href="/Mission-Web/page/board/list_post.jsp">게시판</a></li>
			<li><a href="/Mission-Web/page/member/add_user.jsp">회원가입</a></li>
		</ul>
	</nav>
	
	<div id="user_info">
	<c:choose>
		<c:when test="${ member == null }">
		<form name="login_form" action="/Mission-Web/jsp/member/login.jsp" method="POST">
			<input class="login" type="text" name="login_id" placeholder="Id">
			<input class="login" type="password" name="login_password" placeholder="Password">
	
			<input type="submit" class="btn btn-default" value="로그인">
		</form>
		</c:when>
		<c:otherwise>
			<a>${ member.name }님</a>
			<a href="/Mission-Web/jsp/member/logout.jsp">로그아웃</a>
			<a href="/Mission-Web/page/member/detail_user.jsp?id=${member.id}">마이페이지</a>
		</c:otherwise>
	</c:choose>
	</div>
</div>