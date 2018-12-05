<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/include/head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
$(document).ready(function(){	
	<c:if test="${ empty member }">
	alert("로그인하세요");
	history.back(1);
	</c:if>
});
</script>
</head>
<body>
	<header>
		<%@ include file="/page/include/header.jsp"%>
	</header>
	<section>
		<%@ include file="/jsp/board/add_post_form.jsp"%>
	</section>
	<footer>
		<%@ include file="/page/include/footer.jsp"%>
	</footer>

</body>
</html>