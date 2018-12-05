<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		

	});
	
</script>
<div align="center">
	<hr width="100%">
	<h3>게시글 등록</h3>
	<hr width="100%">

	<form name="wForm" method="post"
		action="<%=request.getContextPath()%>/board/add_post.do"
		enctype="multipart/form-data">
		<table class="post_table">
			<tr>
				<th width="23%">제목</th>
				<td><input type="text" name="title" style="width: 100%;"></td>
			</tr>
			<tr>
				<th width="23%">글쓴이</th>
				<td class="L">
					<span>${ member.id}</span>
					<input type="hidden" name="writer" value="${member.id }">
				</td>
			</tr>
			<tr>
				<th width="23%">내용</th>
				<td><textarea rows="7" cols="20" name="content"
						style="width: 100%;"></textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<input type="file" name="attachfile1">
					<input type="file" name="attachfile2">
				</td>
			</tr>
		</table>
		<input type="submit" name="new_post" value="등록"> <input
			type="button" name="post_list" value="목록">
	</form>
</div>