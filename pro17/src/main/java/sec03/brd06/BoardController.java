package sec03.brd06;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

//@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String ARTICLE_IMAGE_REPO ="C:\\prj\\upload"; // 파일이 업로드될 폴더
	BoardService boardService;
	ArticleVO articleVO;

	public void init(ServletConfig config) throws ServletException {
		boardService = new BoardService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");

		String board = "/board06";
		String nextPage = "";
		String action = request.getPathInfo();

		ArrayList<ArticleVO> articlesList = new ArrayList<>();
		
		try {
			if (action == null || action.equals("/")) action = "/listArticles.do";

			switch (action) {
				case "/listArticles.do" -> {
					articlesList = boardService.listArticles();

					request.setAttribute("articlesList", articlesList);

					nextPage = board + "/listArticles.jsp";
				}
				
				case "/articleForm.do" -> {
					nextPage = board + "/articleForm.jsp"; // 글쓰기 폼
				}

				case "/addArticle.do" -> { // 글 등록
					Map<String, String> articleMap = upload(request, response);

					String title = articleMap.get("title");
					String content = articleMap.get("content");
					String imageFileName = articleMap.get("imageFileName");

					articleVO = new ArticleVO();

					articleVO.setParentNO(0);
					articleVO.setId("hong"); // 로그인한 사용자 ID, 임시로 hong으로 고정
					articleVO.setTitle(title);
					articleVO.setContent(content);
					articleVO.setImageFileName(imageFileName);

					boardService.addArticle(articleVO);

					//nextPage = "/board/listArticles.do";
					PrintWriter pw = response.getWriter();
					pw.print("""
							<script> 
								alert("새글을 추가했습니다."); 
								document.location.href = "%s/board";
							</script>
						""".formatted( request.getContextPath() ) ); // /pro17
				}

				case "/viewArticle.do" -> {
					String articleNO = request.getParameter("articleNO");

					articleVO = boardService.viewArticle(Integer.parseInt(articleNO));
					
					request.setAttribute("article", articleVO);
					nextPage = board + "/viewArticle.jsp";
				}

				case "/modArticle.do" -> {
					Map<String, String> articleMap = upload(request, response);

					int articleNO = Integer.parseInt(articleMap.get("articleNO"));
					String id = articleMap.get("id");
					String title = articleMap.get("title");
					String content = articleMap.get("content");
					String imageFileName = articleMap.get("imageFileName");

					articleVO = new ArticleVO();

					articleVO.setParentNO(0);
					articleVO.setArticleNO(articleNO);
					articleVO.setId(id);
					articleVO.setTitle(title);
					articleVO.setContent(content);
					articleVO.setImageFileName(imageFileName);

					boardService.modArticle(articleVO);

					PrintWriter pw = response.getWriter();
					pw.print("""
							<script> 
								alert("글을 수정했습니다."); 
								document.location.href = "%s/board/viewArticle.do?articleNO=%d";
							</script>
						""".formatted( request.getContextPath(), articleNO ) );
				}

				case "/removeArticle.do" -> {
					int articleNO = Integer.parseInt(request.getParameter("articleNO"));

					// 지워야할 파일 목록
					ArrayList<String> fileList = boardService.removeArticle(articleNO);
					
					// 파일 삭제
					for (String fname : fileList) {
						File file = new File(ARTICLE_IMAGE_REPO + "\\" + fname);
						
						if (file.exists()) FileUtils.delete(file);
					}

					PrintWriter pw = response.getWriter();
					pw.print("""
							<script> 
								alert("글을 삭제했습니다."); 
								document.location.href = "%s/board";
							</script>
						""".formatted( request.getContextPath() ) );
				}

				case "/replyForm.do" -> {
					int articleNO = Integer.parseInt(request.getParameter("articleNO"));

					request.setAttribute("articleNO", articleNO);
					nextPage = board + "/replyForm.jsp";
				}

				case "/addReply.do" -> {
					Map<String, String> articleMap = upload(request, response);

					int parentNO = Integer.parseInt(articleMap.get("parentNO"));
					String title = articleMap.get("title");
					String content = articleMap.get("content");
					String imageFileName = articleMap.get("imageFileName");

					articleVO.setParentNO(parentNO);
					articleVO.setId("lee");
					articleVO.setTitle(title);
					articleVO.setContent(content);
					articleVO.setImageFileName(imageFileName);

					boardService.addArticle(articleVO);

					PrintWriter pw = response.getWriter();
					pw.print("""
						<script> 
							alert("답글을 추가했습니다."); 
							document.location.href = "%s/board";
						</script>
					""".formatted( request.getContextPath() ) );
					
					nextPage = "";
				}

				default -> {
					nextPage = board + "/listArticles.jsp";
				}
			}

			if (!nextPage.equals("")) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> articleMap = new HashMap<String, String>(); // 일반 항목 임시 저장용
		
		// 파일 저장을 위한 사전 준비
		File currentDirPath = new File(ARTICLE_IMAGE_REPO);
		DiskFileItemFactory factory = new DiskFileItemFactory();

		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024); // 파일 읽기를 위한 공간
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> items = upload.parseRequest(request);

			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);

				if (fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + "=" + fileItem.getString("UTF-8"));
					articleMap.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				} else {
					System.out.println("파라미터명:" + fileItem.getFieldName());
					System.out.println("파일크기:" + fileItem.getSize() + "bytes");

					if (fileItem.getSize() > 0) {
						// 파일 이름 잘라 내는 과정
						int idx = fileItem.getName().lastIndexOf("\\");
						if (idx == -1) idx = fileItem.getName().lastIndexOf("/");

						String fileName = fileItem.getName().substring(idx + 1); // 경로에서 파일 이름만 가져 오기

						// 확장자 구분하기
						String[] buf = fileName.split("\\.");
						
						// 파일 이름을 고유 이름으로 변경, 확장자 붙이기 
						fileName = UUID.randomUUID().toString() + "." + (buf.length >= 2 ? buf[buf.length - 1] : "");
						System.out.println("파일명:" + fileName);

						articleMap.put(fileItem.getFieldName(), fileName);
						File uploadFile = new File(currentDirPath + "/" + fileName);

						fileItem.write(uploadFile);
					} // end if
				} // end if
			} // end for
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return articleMap;
	}
}


















