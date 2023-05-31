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
		<p class="text-center"><a class="btn btn-primary" href="${contextPath}/board/articleForm.do">글쓰기</a></p>
	</main>
</body>
</html>
