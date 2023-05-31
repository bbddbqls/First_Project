package sec03.brd03;

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

	public ArticleVO viewArticle(int articleNO) {
		ArticleVO article = null;

		article = boardDAO.selectArticle(articleNO);

		return article;
	}
}
