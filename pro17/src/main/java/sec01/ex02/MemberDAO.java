package sec01.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con; // db 연결을 위한 connection 변수
	private PreparedStatement pstmt; // sql문 실행을 위한 변수
	private DataSource ds; // connection pool에서 db 연결 정보 조회

	public MemberDAO() {
		try {
			Context ctx = new InitialContext(); // 톰켓에 저장되어 있는 context 정보 조회
			Context env = (Context) ctx.lookup("java:/comp/env");// context에 저장되어 있는 환경정보 조회용
			ds = (DataSource) env.lookup("jdbc/oracle"); // connection pool 정보 조회
		} catch (Exception ex) {
			ex.printStackTrace();// 예외 발생하면 console창에 로그 출력
		}
	}

	// 회원 목록 조회 메서드
	public ArrayList<MemberVO> listMembers() {
		ArrayList<MemberVO> list = new ArrayList<>();
		try {
			con = ds.getConnection(); // connection 하나 가져오기
			String query = "select * from t_member";

			pstmt = con.prepareStatement(query); // query를 이용해서 statement 생성
			ResultSet rs = pstmt.executeQuery();

			// 자료를 다 읽을때 까지 반복(한줄씩)
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				
				MemberVO vo = new MemberVO();
				
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoindate(joinDate);
				
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	//회원 정보 저장
	public void addMember(MemberVO m) {
		try {
			con = ds.getConnection();
			String query = "insert into t_member (id,pwd,name,email) values (?,?,?,?)";
			
			pstmt = con.prepareStatement(query);//query를 이용해서 statement 생성
			//추가할 데이터 세팅
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPwd());
			pstmt.setString(3, m.getName());
			pstmt.setString(4, m.getEmail());
			
			pstmt.executeUpdate(); //추가, 삭제, 수정할 때
			pstmt.close();
			con.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	//회원정보 수정
	public void modMember(MemberVO m) {
		try {
			Connection con = ds.getConnection();
			String query = "update t_member set pwd = ?, name = ?, email = ? where id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, m.getPwd());
			pstmt.setString(2, m.getName());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getId());
			
			pstmt.executeUpdate(); //추가, 삭제, 수정할 때
			pstmt.close();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	//회원 정보 삭제
	public void delMember(String id) {
		try {
			Connection con = ds.getConnection();
			String query = "delete from t_member where id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate(); //추가, 삭제, 수정할 때
			pstmt.close();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	   // 회원 정보 조회
	   public MemberVO infoMember(String id) {
	      MemberVO m = null;

	      try {
	         Connection con = ds.getConnection();
	         String query = "select * from t_member where id = ?";

	         pstmt = con.prepareStatement(query);
	         pstmt.setString(1, id);

	         ResultSet rs = pstmt.executeQuery();

	         while (rs.next()) {
	            m = new MemberVO();

	            m.setId(rs.getString("id"));
	            m.setPwd(rs.getString("pwd"));
	            m.setName(rs.getString("name"));
	            m.setEmail(rs.getString("email"));
	            m.setJoindate(rs.getDate("joindate"));

	            break;
	         }

	         pstmt.close();
	         con.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }

	      return m;
	   }
	   
	   public MemberVO searchMember(String name) {
		      MemberVO m = null;

		      try {
		         Connection con = ds.getConnection();
		         String query = "select * from t_member where name = ?";

		         pstmt = con.prepareStatement(query);
		         pstmt.setString(1, name);

		         ResultSet rs = pstmt.executeQuery();

		         while (rs.next()) {
		            m = new MemberVO();

		            m.setId(rs.getString("id"));
		            m.setPwd(rs.getString("pwd"));
		            m.setName(rs.getString("name"));
		            m.setEmail(rs.getString("email"));
		            m.setJoindate(rs.getDate("joindate"));

		            break;
		         }

		         pstmt.close();
		         con.close();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }

		      return m;
		   }

	   // 회원 존재 여부 확인
	   public boolean isExisted(String id, String pwd) {
	      boolean result = false;

	      try {
	         con = ds.getConnection();
	         String query = """
	            select count(*) as result
	            from t_member
	            where id = ?
	              and pwd = ?
	         """;

	         pstmt = con.prepareStatement(query);

	         pstmt.setString(1, id);
	         pstmt.setString(2, pwd);

	         ResultSet rs = pstmt.executeQuery();

	         rs.next(); // 커서를 첫번째 레코드로 위치시킵니다.

	         result = rs.getInt("result") > 0;
	      } catch (Exception e) {
	         e.printStackTrace();
	      }

	      return result;
	   }
}
