package notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import notice.vo.BoardFileVO;
import notice.vo.BoardVO;
import util.ConnectionFactory;
import util.JDBCClose;

public class BoardDAO {
	/**
	 * 모든 게시글 조회
	 * 
	 * @return BoardVO객체 리스트
	 */
	public List<BoardVO> selectAllBoard() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BoardVO> boardList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			// TODO Auto-generated catch block
			sql.append(" select no, title, writer, to_char(reg_date, 'yyyy-mm-dd') as reg_date ");
			sql.append(" from t_board ");
			sql.append(" order by no desc ");

			pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO board = new BoardVO();

				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getString("reg_date"));

				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return boardList;
	}
	
	public List<BoardVO> selectAll(){
		Connection conn = null;
		List<BoardVO> list = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConn();
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from t_board" );
			
			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardVO board = new BoardVO();
				
				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getString("reg_date"));
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * 모든 게시글 조회
	 * 
	 * @return BoardVO객체 리스트
	 */
	public List<BoardVO> selectBoardList(int start, int end) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BoardVO> boardList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			// TODO Auto-generated catch block
			sql.append(" select * from ( ");
			sql.append(" select rownum as rnum, c.* from ( ");

			/*
			 * sql.
			 * append(" select no, title, writer, to_char(reg_date, 'yyyy-mm-dd hh24-mi') as reg_date "
			 * ); sql.append(" from t_board b "); sql.append(" order by no desc ");
			 */
			sql.append(" select no, title, writer, content, view_cnt ");
			sql.append(" , case when to_char(reg_date, 'yyyy-mm-dd') >= to_char(sysdate, 'yyyy-mm-dd')  ");
			sql.append(" then to_char(reg_date,'hh24-mi') ");
			sql.append(" else to_char(reg_date, 'yyyy-mm-dd') end reg_date ");
			sql.append(" from ( select * from t_board order by reg_date desc) ");

			sql.append(" ) c ");
			sql.append(" )where rnum between ? and ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO board = new BoardVO();

				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getString("reg_date"));

				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return boardList;
	}

	public List<BoardVO> searchBoardList(int start, int end, String search, String category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BoardVO> boardList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from ( ");
			sql.append(" select rownum as rnum, c.* from ( ");
			sql.append(" select no, title, writer, to_char(reg_date, 'yyyy-mm-dd hh24-mi') as reg_date ");
			sql.append(" from t_board b ");
			sql.append(" order by no desc ");
			sql.append(" ) c ");
			sql.append(" )where rnum between ? and ? ");

			if (category.equals("writer"))
				sql.append(" and writer = ?");
			if (category.equals("title"))
				sql.append(" and title = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			pstmt.setString(3, search);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO board = new BoardVO();

				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getString("reg_date"));

				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return boardList;
	}

	/**
	 * 검색한 모든 결과
	 * 
	 * @param search
	 * @param category
	 * @return
	 */
	public List<BoardVO> searchAllBoard(String search, String category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BoardVO> boardList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" select no, title, writer, to_char(reg_date, 'yyyy-mm-dd') as reg_date ");
			sql.append(" from t_board ");

			if (category.equals("writer"))
				sql.append(" where writer = ?");
			if (category.equals("title"))
				sql.append(" where title = ?");

			sql.append(" order by no desc ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, search);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO board = new BoardVO();

				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getString("reg_date"));

				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return boardList;
	}

	/**
	 * 게시글 추가
	 * 
	 * @param board
	 * @return 반영된 행 갯수
	 */
	public int insertBoard(BoardVO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" insert into t_board (no, title, writer, content)");
			sql.append(" values (seq_t_board_no.nextval, ?, ?, ? ) ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 게시글 번호로 게시글 조회
	 * 
	 * @param no
	 * @return 게시글 객체
	 */
	public BoardVO selectByNo(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		BoardVO board = new BoardVO();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" select * ");
			sql.append(" from t_board ");
			sql.append(" where no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setViewCnt(rs.getInt("view_cnt"));
				board.setRegDate(rs.getString("reg_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return board;
	}

	/**
	 * 게시글 삭제
	 * 
	 * @param no
	 * @return 반영된 행 갯수
	 */
	public int deleteBoard(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" delete t_board ");
			sql.append(" where no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 게시글 업데이트
	 * 
	 * @param board
	 * @return 반영된 행 갯수
	 */
	public int updateBoard(BoardVO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" update t_board set");
			sql.append(" title = ?, ");
			sql.append(" writer = ?, ");
			sql.append(" content = ? ");
			sql.append(" where no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getNo());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public synchronized void viewBoard(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" update t_board set");
			sql.append(" view_cnt = view_cnt + 1 ");
			sql.append(" where no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int selectNo() {
		String sql = "select seq_t_board_no.nextval from dual";

		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			rs.next();

			return rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int insertFile(BoardFileVO fileVO) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_board_file ( ");
		sql.append(" no, board_no, file_ori_name ");
		sql.append(" , file_save_name, file_size ) ");
		sql.append(" values( seq_t_board_file_no.nextval, ?, ?, ?, ? ) ");

		int result = 0;
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		) {
			pstmt.setInt(1, fileVO.getBoardNo());
			pstmt.setString(2, fileVO.getFileOriName());
			pstmt.setString(3, fileVO.getFileSaveName());
			pstmt.setInt(4, fileVO.getFileSize());
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<BoardFileVO> selectFileList(int boardNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardFileVO> fileList = new ArrayList<>();

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" select * ");
			sql.append(" from t_board_file ");
			sql.append(" where board_no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNo);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				BoardFileVO file = new BoardFileVO();
				file.setNo(rs.getInt("no"));
				file.setBoardNo(boardNo);
				file.setFileOriName(rs.getString("file_ori_name"));
				file.setFileSaveName(rs.getString("file_save_name"));
				file.setFileSize(rs.getInt("file_size"));
				fileList.add(file);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fileList;
	}
	
	/**
	 * 파일테이블 모든 정보 조회
	 * @return
	 */
	public List<Integer> selectAllFileList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> boardNoList = new ArrayList<>();

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" select board_no ");
			sql.append(" from t_board_file ");

			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			while(rs.next()) {
				int no = rs.getInt("board_no");
				if( ! boardNoList.contains(no))
					boardNoList.add(no);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return boardNoList;
	}
	
	public int deleteFile(int boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" delete t_board_file ");
			sql.append(" where board_no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
