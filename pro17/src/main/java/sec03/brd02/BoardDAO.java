package sec03.brd02;

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
			
			rs.close();
			pstmt.close();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 신규 글 등록할 때 새 글 번호 가져오기
	public int getNewArticleNO() {
		int articleNO = 0;
		
		try {
			String query = "SELECT max(articleNO) FROM t_board ";

			pstmt = con.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery(query);

			if (rs.next()) articleNO = rs.getInt(1) + 1; // 결과 받아와서 1 더하기
			
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return articleNO; // 새 글 번호 반환
	}
	
	public void insertNewArticle(ArticleVO article) {
		try {
			con = ds.getConnection();

			int articleNO = getNewArticleNO(); // 새 글 번호
			int parentNO = article.getParentNO();
			String title = article.getTitle();
			String content = article.getContent();
			String id = article.getId();
			String imageFileName = article.getImageFileName();
			
			String query = "INSERT INTO t_board (articleNO, parentNO, title, content, imageFileName, id) VALUES (?, ? ,?, ?, ?, ?)";
			System.out.println(query);

			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, articleNO);
			pstmt.setInt(2, parentNO);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			pstmt.setString(6, id);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
