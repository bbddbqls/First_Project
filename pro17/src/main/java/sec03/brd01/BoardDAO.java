package sec03.brd01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	private Connection con; // db 연결을 위한 connection 변수
	private PreparedStatement pstmt; // SQL문 실행을 위한 변수
	private DataSource ds; // connection pool에서 db 연결 정보 조회

	public BoardDAO() {
		try {
			Context ctx = new InitialContext(); // 톰캣에 저장되어 있는 context 정보 조회를 위한 설정
			Context env = (Context) ctx.lookup("java:/comp/env"); // context에 저장되어 있는 환경(설정) 정보 조회용
			ds = (DataSource) env.lookup("jdbc/oracle"); // connection pool 정보 조회
		} catch (Exception ex) {
			ex.printStackTrace(); // console 창에 로그(메시지) 출력
		}
	}

	public ArrayList<ArticleVO> selectAllArticles() {
		ArrayList<ArticleVO> list = new ArrayList<>();
		
		try {
			con = ds.getConnection();

			String query = """
				SELECT LEVEL, articleNO, parentNO, title, content, id, writeDate
				FROM t_board
				START WITH parentNO = 0 CONNECT BY PRIOR articleNO = parentNO
				ORDER SIBLINGS BY articleNO DESC
			""";
			System.out.println(query);

			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int level = rs.getInt("level");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writedate");

				ArticleVO article = new ArticleVO();
				
				article.setLevel(level);
				article.setArticleNO(articleNO);
				article.setParentNO(parentNO);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWritedate(writeDate);

				list.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
