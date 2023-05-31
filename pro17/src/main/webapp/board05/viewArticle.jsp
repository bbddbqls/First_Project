<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html lang="ko-KR">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글보기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
	<main class="container mt-3">
		<h2 class="text-center">글 보기</h2>
  		<form name="articleForm" method="post" onsubmit="return modify()" action="${contextPath}/board/modArticle.do" enctype="multipart/form-data">
			<div class="mb-3">
    			<label for="title" class="form-label">글번호</label>
    			<input type="text" class="form-control" value="${article.articleNO }" name="articleNO" readonly />
			</div>
			<div class="mb-3">
    			<label for="id" class="form-label">작성자ID</label>
    			<input type="text" class="form-control" name="id" id="id" value="${article.id}" readonly>
			</div>
			<div class="mb-3">
    			<label for="title" class="form-label">제목</label>
    			<input type="text" class="form-control" name="title" id="title" value="${article.title}">
			</div>
			<div class="mb-3">
    			<label for="content" class="form-label">내용</label>
    			<textarea name="content" class="form-control" id="content" rows="5">${article.content}</textarea>
			</div>
			<div class="mb-3">
				<label for="imageFileName" class="form-label">첨부 파일</label>
				<input class="form-control" type="file" id="imageFileName" name="imageFileName">
<c:if test="${not empty article.imageFileName }">
				<p>현재 첨부 파일 : ${article.imageFileName} <a href="${contextPath}/download.do?imageFileName=${article.imageFileName}&articleNO=${article.articleNO }">파일 받기</a></p>
</c:if>
			</div>
			<div class="text-center">
				<button type="submit" class="btn btn-primary">수정</button>
				<button type="button" class="btn btn-danger" onClick="deleteArticle(${ article.articleNO })">삭제</button>
				<button type="button" class="btn btn-info">답글작성</button>
				<button type="reset" class="btn btn-secondary">다시쓰기</button>
				<input type=button class="btn btn-warning" value="목록보기" onClick="backToList()" />
			</div>
		</form>
	</main>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script type="text/javascript">
		function modify() {
			let ans = confirm("수정하시겠습니까?");
			
			if (!ans) return false; // 취소했을 때
		}

		function deleteArticle(articleNO) {
			let ans = confirm("삭제하시겠습니까?");
			
			if (!ans) return; // 취소했을 때
			
			document.location.href = "${contextPath}/board/removeArticle.do?articleNO="+articleNO; // 삭제로 redirect
		}

		function backToList(){
			document.location.href = "${contextPath}/board";
		}
	</script>
</body>
</html>
