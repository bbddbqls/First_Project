<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 출력창</title>
<c:choose>
	<c:when test="${msg == 'deleted' }">
		<script>
			window.onload = function() {
				alert("회원을 삭제했습니다.");
			}
		</script>
	</c:when>
</c:choose>
</head>
<body>
	<p>회원정보</p>
	<table align="center" border="1">
		<tr align="center" bgcolor="lightgreen">
			<td width="7%"><b>아이디</b></td>
			<td width="7%"><b>비밀번호</b></td>
			<td width="7%"><b>이름</b></td>
			<td width="7%"><b>이메일</b></td>
			<td width="7%"><b>가입일</b></td>
			<td width="7%"><b>수정</b></td>
			<td width="7%"><b>삭제</b></td>
		</tr>
		<c:choose>
			<c:when test="${ empty membersList }">
				<tr align="center">
					<td colspan="6">등록된 회원이 없습니다.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="mem" items="${ membersList}">
					<tr align="center">
						<td>${mem.id }</td>
						<td>${mem.pwd }</td>
						<td>${mem.name }</td>
						<td>${mem.email }</td>
						<td>${mem.joindate }</td>
						<td><a href="${contextPath}/member/modMemberForm.do?id=${mem.id}">수정</a></td>
						<td><a href="${contextPath}/member/delMemberForm.do?id=${mem.id}">삭제</a></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	<p>
		<a href="${contextPath }/member/memberForm.do">회원 가입하기</a>
	</p>
</body>
</html>