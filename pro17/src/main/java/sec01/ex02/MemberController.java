package sec01.ex02;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO memberDAO;
	
	//서블릿이 최초로 실행될 때 불러지는 메서드
	public void init() throws ServletException{
		memberDAO = new MemberDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		String action = request.getPathInfo();//member 주소 뒤에 붙어 있는 주소
		String nextPage = "";
		//회원 목록
		if(action == null || action.equals("/listMembers.do")) {
			List<MemberVO> memberList = memberDAO.listMembers();
			request.setAttribute("membersList", memberList);
			nextPage = "/test03/listMembers.jsp";
		}else if(action.equals("/addMember.do")) {
			//회원 정보 추가
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			MemberVO v = new MemberVO(id, pwd, name, email);
			memberDAO.addMember(v);
			
			nextPage = "/member/listMembers.do";
		}else if(action.equals("/modMember.do")) {
			//회원 정보 수정
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			MemberVO v = new MemberVO(id, pwd, name, email);
			memberDAO.modMember(v);
			
			nextPage = "/member/listMembers.do";
		}else if(action.equals("/delMemberForm.do")) {
			//회원 정보 삭제
			String id = request.getParameter("id");
			
			memberDAO.delMember(id);
			request.setAttribute("msg", "deleted");
			nextPage = "/member/listMembers.do";
		}else if(action.equals("/memberForm.do")) {
			//회원 추가 하는 입력 폼
			nextPage = "/test03/memberForm.jsp";
		}else if(action.equals("/modMemberForm.do")) {
			//회원 수정 하는 입력 폼
			String id = request.getParameter("id");
			
			MemberVO m = memberDAO.infoMember(id);
			request.setAttribute("memInfo", m);
			
			nextPage = "/test03/delMemberForm.jsp";
		}else {
			List<MemberVO> memberList = memberDAO.listMembers();
			request.setAttribute("membersList", memberList);
			nextPage = "/test03/listMembers.jsp";
		}
		RequestDispatcher dp = request.getRequestDispatcher(nextPage);
		dp.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
