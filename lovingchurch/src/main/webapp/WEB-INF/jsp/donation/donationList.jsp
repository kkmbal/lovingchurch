<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">
function submitData(n){
	var obj = fm.chk;
	for(var i=0;i<obj.length;i++){
		var val = obj[i].value.split("|")[0];
		obj[i].value = val;
		if(obj[i].checked){
			for(var j=0;j<n;j++){
				var k = eval("fm."+obj[i].value+"_key["+j+"].value");
				var amt = eval("fm."+obj[i].value+"["+j+"].value");
				var result = k+"^"+amt;
				val = val + "|" + result;
			}
			alert(val);
			obj[i].value = val;
		}
	}
}
</script>
<table width="90%" border="1" cellspacing="0"  style="border-left : 1px solid #ACA899;">
	<tr>
		<td>날짜1</td>
		<td><input type="text" name="ymd"></input></td>
		<td>구분</td>
		<td><select name="gubun">
				<option value="1">주일헌금금</option> 
			</select>
		</td>
		<td>
			<button size="10" onclick="location.href='donationList.html'">조회</button>
			<button size="10" onclick="submitData(${fn:length(codeList)})">저장</button>
		</td>
	</tr>
</table>
<form name="fm" action="" method="post">
<table width="90%" border="1" cellspacing="0"  style="border-left : 1px solid #ACA899;">
	<tr>
		<td> </td>
		<td>성명</td>
		<c:forEach var="code" items="${codeList}">
			<td><c:out value="${code.CD_NM}"/></td>
		</c:forEach>
	</tr>
	<c:forEach var="user" items="${userList}">
		<tr>
			<td><input type="checkbox" name="chk" value="<c:out value="${user.USER_ID}"/>"></input></td>
			<td><c:out value="${user.USER_NM}"/></td>
			<c:forEach var="code" items="${codeList}">
				<td>
					<c:set var="flag" scope="request" value="0"/>
					<c:forEach var="donation" items="${donationList}">
						<c:if test="${donation.INOUT_DETL_CD == code.CD_VAL && donation.USER_ID == user.USER_ID}">
							<input type="text" name="<c:out value="${user.USER_ID}"/>" value="<c:out value="${donation.DO_AMT}"/>"></input>
							<input type="hidden" name="<c:out value="${user.USER_ID}"/>_key" value="U^<c:out value="${donation.SEQ_NO}"/>"></input>
							<c:set var="flag" scope="request" value="1"/>
							<!--  % break; % -->
						</c:if>
					</c:forEach>
					<c:if test="${flag == '0'}">
						<input type="text" name="<c:out value="${user.USER_ID}"/>" value=""></input>
						<input type="hidden" name="<c:out value="${user.USER_ID}"/>_key" value="I^0"></input>
					</c:if>
				</td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>
</form>