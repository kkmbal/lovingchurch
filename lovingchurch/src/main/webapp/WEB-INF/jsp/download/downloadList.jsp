<%@ include file="/taglibs.jsp"%>
<<script type="text/javascript">
<!--
function down(no){
	location.href = "<c:out value="${ctx}"/>/download.html";
}
//-->
</script>

<table width="90%" border="1" cellspacing="0"  style="border-left : 1px solid #ACA899;">
	<tr>
		<td>NO</td>
		<td>양식명</td>
		<td>다운로드</td>
	</tr>
	
	<c:forEach var="down" items="${downloadlList}">	
		<tr>
			<td>> </td>
			<td><c:out value="${down.FORM_NM}"/></td>
			<td><a href="javascript:down('<c:out value="${down.DOWN_NO}"/>')"><img src="<c:out value="${ctx}"/>/images/xls.gif" border="0"/></a></td>
		</tr>
	</c:forEach>
</table>
