<%@ include file="/taglibs.jsp"%>
<table width="90%" border="1" cellspacing="0"  style="border-left : 1px solid #ACA899;">
	<tr>
		<td>날짜1</td>
		<td><input type="text" name="ymd"></input></td>
		<td>구분</td>
		<td><select name="gubun">
				<option value="1">주일헌금금</option> 
			</select>
		</td>
		<td><button size="10">조회</button></td>
	</tr>
</table>
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