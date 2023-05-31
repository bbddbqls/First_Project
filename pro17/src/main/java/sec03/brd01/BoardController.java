package sec03.brd01;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardService boardService;
	ArticleVO articleVO;

	public void init(ServletConfig config) throws ServletException {
		boardService = new BoardService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");

		String nextPage = "";
		String action = request.getPathInfo();

		ArrayList<ArticleVO> articlesList = new ArrayList<>();
		
		try {
			if (action == null || action.equals("/")) action = "/listArticles.do";

			switch (action) {
				case "/listArticles.do" -> {
					articlesList = boardService.listArticles();

					request.setAttribute("articlesList", articlesList);

					nextPage = "/board01/listArticles.jsp";
				}
				
				default -> {
					nextPage = "/board01/listArticles.jsp";
				}
			}

			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
