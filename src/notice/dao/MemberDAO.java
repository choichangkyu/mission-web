package notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import notice.vo.BoardVO;
import notice.vo.MemberVO;
import util.ConnectionFactory;
import util.JDBCClose;

public class MemberDAO {
	/**
	 * 모든 회원 조회
	 * 
	 * @return MemberVO객체 리스트
	 */
	public List<MemberVO> selectAllMember() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MemberVO> memberList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			// TODO Auto-generated catch block
			sql.append(" select id, name, to_char(reg_date, 'YYYY-mm-dd') reg_date ");
			sql.append(" from t_member ");
			sql.append(" order by reg_date asc ");

			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberVO member = new MemberVO();

				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setRegDate(rs.getString("reg_date"));
				
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return memberList;
	}
	
	public List<MemberVO> selectAllMember(int start, int end) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MemberVO> memberList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			// TODO Auto-generated catch block
			sql.append(" select id, name, to_char(reg_date, 'YYYY-mm-dd') reg_date ");
			sql.append(" from t_member ");
			sql.append(" where rownum >= ? and rownum <= ? ");
			sql.append(" order by reg_date asc ");
			
			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberVO member = new MemberVO();
				
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setRegDate(rs.getString("reg_date"));
				
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return memberList;
	}

	/**
	 * 회원 추가
	 * @param board
	 * @return 반영된 행 갯수
	 */
	public int insertMember(MemberVO member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" insert into t_member (id, name, password, email_id, ");
			sql.append(" email_domain, tel1, tel2, tel3, post, basic_addr, detail_addr) ");
			sql.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail_id());
			pstmt.setString(5, member.getEmail_domain());
			pstmt.setString(6, member.getTel1());
			pstmt.setString(7, member.getTel2());
			pstmt.setString(8, member.getTel3());
			pstmt.setString(9, member.getPost());
			pstmt.setString(10, member.getBasic_addr());
			pstmt.setString(11, member.getDetail_addr());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 로그인 회원 조회
	 * @param no
	 * @return 게시글 객체
	 */
	public MemberVO selectForLogin(MemberVO member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO result = null;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" select id, name, type ");
			sql.append(" from t_member ");
			sql.append(" where id = ? ");
			sql.append(" and password = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new MemberVO();
				
				result.setId(rs.getString("id"));
				result.setName(rs.getString("name"));
				result.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 아이디로 회원 조회
	 * @param no
	 * @return 게시글 객체
	 */
	public MemberVO selectById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		MemberVO member = new MemberVO();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" select id, name, email_id, email_domain, ");
			sql.append(" tel1, tel2, tel3, ");
			sql.append(" post, basic_addr, detail_addr, ");
			sql.append(" reg_date ");
			sql.append(" from t_member ");
			sql.append(" where id = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setEmail_id(rs.getString("email_id"));
				member.setEmail_domain(rs.getString("email_domain"));
				member.setTel1(rs.getString("tel1"));
				member.setTel2(rs.getString("tel2"));
				member.setTel3(rs.getString("tel3"));
				member.setPost(rs.getString("post"));
				member.setBasic_addr(rs.getString("basic_addr"));
				member.setDetail_addr(rs.getString("detail_addr"));
				member.setRegDate(rs.getString("reg_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
	}
	
	public int dele_user(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" delete from t_member ");
			sql.append(" where id = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
