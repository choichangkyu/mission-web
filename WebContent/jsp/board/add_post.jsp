<%@page import="notice.vo.BoardFileVO"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="notice.vo.BoardVO"%>
<%@page import="notice.dao.BoardDAO"%>
<%@page import="util.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String saveFolder = "C:/lecture/web-workspace/Mission-Web/WebContent/upload";
	MultipartRequest multi = new MultipartRequest(request, saveFolder, 
			1024 * 1024 * 3, "utf-8", new AcornFileNamePolicy());
	BoardDAO dao = new BoardDAO();
	
	String title = multi.getParameter("title");
	String writer = multi.getParameter("writer");
	String content = multi.getParameter("content");
	int no = dao.selectNo(); // boardno

	BoardVO board = new BoardVO();
	board.setTitle(title);
	board.setWriter(writer);
	board.setContent(content);
	board.setNo(no);
	int cnt = dao.insertBoard(board);

	// 첨부파일 저장
	Enumeration<String> files = multi.getFileNames();
	while(files.hasMoreElements()) {
		String fileName = files.nextElement();
		File f = multi.getFile(fileName);
		if(f != null){
			
			BoardFileVO file = new BoardFileVO();
			file.setFileOriName(multi.getOriginalFileName(fileName));
			file.setFileSaveName(multi.getFilesystemName(fileName));
			file.setFileSize((int)f.length());
			file.setBoardNo(no);
			
			dao.insertFile(file);
		}
	}
	/* String title = request.getParameter("title");
	String writer = request.getParameter("writer");
	String content = request.getParameter("content");

	BoardVO board = new BoardVO();
	board.setTitle(title);
	board.setWriter(writer);
	board.setContent(content);
	
	BoardDAO dao = new BoardDAO();
	int cnt = dao.insertBoard(board); */
%>
<script>
	
<%if (cnt != 0) {%>
	alert("글 등록완료");
<%} else {%>
	alert("글 등록실패");
<%}%>
	location.href = "/Mission-Web/page/board/list_post.jsp";
</script>