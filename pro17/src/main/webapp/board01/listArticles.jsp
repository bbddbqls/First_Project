<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html lang="ko-KR">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<table align="center" border="1"  width="80%"  >
	<tr height="10" align="center"  bgcolor="lightgreen">
		<td >글번호</td>
		<td >작성자</td>
		<td >제목</td>
		<td >작성일</td>
	</tr>
<c:choose>
	<c:when test="${empty articlesList }" >
    <tr>
		<td colspan="4"><h3>등록된 글이 없습니다.</h3></td>
	</tr>
	</c:when>
	<c:when test="${ not empty articlesList }" >
    	<c:forEach  var="article" items="${ articlesList }" varStatus="articleNum" >
	<tr align="center">
		<td width="10%">${articleNum.count}</td>
		<td width="10%">${article.id }</td>
		<td align='left'>
			<c:choose>
				<c:when test='${article.level > 1 }'>
			<span><c:forEach begin="1" end="${ article.level }" step="1">&nbsp;&nbsp;</c:forEach></span>
			<span style="font-size:12px;">[답변]</span><a href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
				</c:when>
				<c:otherwise>
			<a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
				</c:otherwise>
			</c:choose>
		</td>
		<td  width="100px">${article.writedate}</td>
	</tr>
		</c:forEach>
	</c:when>
</c:choose>
</table>
<a  class="cls1"  href="#"><p class="cls2">글쓰기</p></a>
</body>
</html>
