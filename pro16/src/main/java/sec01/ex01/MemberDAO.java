package sec01.ex01;

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
   private PreparedStatement pstmt; // SQL문 실행을 위한 변수
   private DataSource ds; // connection pool에서 db 연결 정보 조회

   public MemberDAO() {
      try {
         Context ctx = new InitialContext(); // 톰캣에 저장되어 있는 context 정보 조회를 위한 설정
         Context env = (Context) ctx.lookup("java:/comp/env"); // context에 저장되어 있는 환경(설정) 정보 조회용
         ds = (DataSource) env.lookup("jdbc/oracle"); // connection pool 정보 조회
      } catch (Exception ex) {
         ex.printStackTrace(); // console 창에 로그(메시지) 출력
      }
   }
   
   // ID 중복 확인
   public boolean olverlappedID(String id) {
      boolean result = false;

      try {
         con = ds.getConnection();
         // 문자열 블럭
         String query = """ 
            select count(*) as result
            from t_member
            where id = ?
         """;

         pstmt = con.prepareStatement(query);

         pstmt.setString(1, id);

         ResultSet rs = pstmt.executeQuery();

         rs.next(); // 커서를 첫번째 레코드로 위치시킵니다.

         result = rs.getInt("result") > 0;
      } catch (Exception e) {
         e.printStackTrace();
      }

      return result;
   }

   // 회원 존재 여부 확인
   public boolean isExisted(String id, String pwd) {
      boolean result = false;

      try {
         con = ds.getConnection();
         // 문자열 블럭
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