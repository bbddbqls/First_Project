package sec03.brd02;

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

	public void addArticle(ArticleVO article){
		boardDAO.insertNewArticle(article);
	}
}
