<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko-KR">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글 쓰기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
	<main class="container mt-3">
		<h2 class="text-center">답글 쓰기</h2>
  		<form name="articleForm" method="post" action="${contextPath}/board/addReply.do" enctype="multipart/form-data">
			<input type="hidden" name="parentNO" value="${ articleNO }">
			<div class="mb-3">
    			<label for="title" class="form-label">제목</label>
    			<input type="text" class="form-control" name="title" id="title">
			</div>
			<div class="mb-3">
    			<label for="content" class="form-label">내용</label>
    			<textarea name="content" class="form-control" id="content" rows="5"></textarea>
			</div>
			<div class="mb-3">
				<label for="imageFileName" class="form-label">첨부 파일</label>
				<input class="form-control" type="file" id="imageFileName" name="imageFileName">
			</div>
			<div class="text-center">
				<button type="submit" class="btn btn-primary">답글쓰기</button>
				<button type="reset" class="btn btn-secondary">다시쓰기</button>
				<input type=button class="btn btn-warning" value="목록보기" onClick="backToList()" />
			</div>
		</form>
	</main>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script type="text/javascript">
		function backToList(obj){
			document.location.href = "${contextPath}/board";
		}
	</script>
</body>
</html>
