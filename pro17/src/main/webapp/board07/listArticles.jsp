<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html lang="ko-KR">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
	<main class="container">
		<table class="table table-hover">
		    <thead>
		        <tr>
		            <th scope="col">글번호</th>
		            <th scope="col">작성자</th>
		            <th scope="col">제목</th>
		            <th scope="col" class="text-center">작성일</th>
		        </tr>
		    </thead>
		    <tbody>
<c:choose>
	<c:when test="${empty articlesList }" >
		        <tr>
		            <td colspan="4"><h3 class="text-center">등록된 글이 없습니다.</h3></td>
		        </tr>
	</c:when>
	<c:otherwise>
		<c:forEach  var="article" items="${ articlesList }" varStatus="articleNum" >
				<tr>
					<td>${articleNum.count}</td>
					<td>${article.id }</td>
					<td>
			<c:choose>
				<c:when test='${article.level > 1 }'>
						<span><c:forEach begin="1" end="${ article.level }" step="1">&nbsp;&nbsp;&nbsp;</c:forEach>[답변]</span>
						<a href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
				</c:when>
				<c:otherwise>
						<a href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
				</c:otherwise>
			</c:choose>
					</td>
					<td class="text-end">${article.writedate}</td>
				</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
		    </tbody>
		</table>

		<!-- 페이징 처리 -->
		<nav>
			<ul class="pagination justify-content-center">
<!-- 이전 10개 페이지 -->
<c:choose>
	<c:when test="${ pageNum <= pagePerScreen }">
				<li class="page-item disabled"><a class="page-link" href="#">◀</a></li>
	</c:when>
	<c:otherwise>
				<li class="page-item"><a class="page-link" href="${contextPath}/board?pageNum=${ startPage - 1 }">◀</a></li>
	</c:otherwise>
</c:choose>

<!-- 1페이지, 2페이지, 3페이지 .... -->
<c:forEach var="page" begin="${ startPage }" end="${ endPage }" step="1">
	<c:choose>
		<c:when test="${ page == pageNum }">
				<li class="page-item active"><a class="page-link" href="#">${ page }</a></li>
		</c:when>
		<c:otherwise>
				<li class="page-item"><a class="page-link" href="${contextPath}/board?pageNum=${ page }">${ page }</a></li>
		</c:otherwise>
	</c:choose>
</c:forEach>

<!-- 이후 10개 페이지 -->
<c:choose>
	<c:when test="${ endPage != totalPage }">
				<li class="page-item disabled"><a class="page-link" href="${contextPath}/board?pageNum=${ endPage + 1 }">▶</a></li>
	</c:when>
	<c:otherwise>
				<li class="page-item disabled"><a class="page-link" href="#">▶</a></li>
	</c:otherwise>
</c:choose>
			</ul>
		</nav>

		<p class="text-center"><a class="btn btn-primary" href="${contextPath}/board/articleForm.do">글쓰기</a></p>
	</main>
</body>
</html>
