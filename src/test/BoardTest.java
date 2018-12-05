package test;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import notice.dao.BoardDAO;
import notice.vo.BoardVO;
import util.ConnectionFactory;

public class BoardTest {
	
	public void DB접속테스트() throws Exception {
		Connection conn = new ConnectionFactory().getConnection();
		
		assertNull(conn);
	}
	
	@Test
	public void 게시판조회테스트() throws Exception {
		B b = new B();
	}
      
}


class A {

	public A() {
		super();
		System.out.println("Aa");
	}
	
	
}

class B extends A{
	public B() {
		
	}

}