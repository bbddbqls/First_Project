package sec03.brd06;

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

	public void modArticle(ArticleVO article) {
		boardDAO.updateArticle(article);
	}

	public ArrayList<String> removeArticle(int articleNO) {
		// 게시물 삭제 전에 먼저 첨부 파일 목록 조회
		ArrayList<String> fileList = boardDAO.selectRemovedArticlesFile(articleNO);

		boardDAO.deleteArticle(articleNO);

		return fileList;
	}
}
