<%@ include file="/taglibs.jsp"%>

<table width="90%" border="1" cellspacing="0"  style="border-left : 1px solid #ACA899;">
	<tr>
		<td>id</td>
		<td>name</td>
	</tr>
	<c:forEach var="user" items="${userList}">
		<tr>
			<td><c:out value="${user.USER_ID}"/></td>
			<td><c:out value="${user.USER_NM}"/></td>
		</tr>
	</c:forEach>
</table>