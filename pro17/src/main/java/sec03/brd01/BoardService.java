package sec03.brd01;

import java.util.ArrayList;

public class BoardService {
	BoardDAO boardDAO;

	public BoardService() {
		boardDAO = new BoardDAO();
	}

	public ArrayList<ArticleVO> listArticles() {
		ArrayList<ArticleVO> articlesList = boardDAO.selectAllArticles();

		return articlesList;
	}
}
