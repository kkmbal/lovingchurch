<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>  
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
		$(document).ready(function () {
			//실행일자
			$("#DT").datepicker();		
			$("#DT").val(formatDate(new Date(), "yyyy-MM-dd"));	
			
			 $("#list").jqGrid({
			    url:"systemProjectService.listDetailCode.json",
			    colNames:['아이디', '성명','생년월일','가입일자','직분','권한'],
			    colModel :[ 
			      {name:'GRP_CD', index:'GRP_CD', width:90, align:'left', editable:false},
			      {name:'CD', index:'CD', width:80, align:'left', editable:true, editrules:{required:true}}, 
			      {name:'CD_NM', index:'CD_NM', width:80, align:'left', editable:true, editrules:{required:true}}, 
			      {name:'CD_DESCR', index:'CD_DESCR', width:80, align:'left', editable:true, editrules:{required:true}},
			      {name:'HIRNK_COMN_CD_KEY', index:'HIRNK_COMN_CD_KEY', width:80, align:'center', editable:true},
			      {name:'SORT_SEQ', index:'SORT_SEQ', width:80, align:'center', editable:true, editrules:{required:true}}
			    ],
			    pager: '#pager',
			    caption: '헌금관리',
			    height:400
			});		
		});
	</script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/inc/topmenu.jsp" %>

<div class="bodyArea">
<!--title-->
<div class="titleArea">
	<ul>
		<li class="title">주간결산 관리</li>
		<li class="directory"><img src="${pageContext.request.contextPath}/img/etc/icn_home.gif">	결산    >	주간결산관리</li>
	</ul>
</div>
<!--title-->
	
<!--################ contentArea ##################-->
	<div class="content" id="contentArea">
			
			<!-- 각 화면 내용 들어갈 부분 --> 
			<form name="frm" id="frm">
			<table class="form-layout"  border="0" cellspacing="0" cellpadding="0">
				<colgroup>
					<col width="50"/>
					<col width="*"/>
					<col width="100"/>
				</colgroup>
				<thead>
					<tr>
						<td class="searchHline" colspan="3"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="searchBody">날짜</td>
						<td class="searchBody"><input type="text" style="width:80px;" readonly name="DT" id="DT"></td>
						<td class="searchBody" align="center"><button id="search">검색</button></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td class="searchHline" colspan="3"></td>
					</tr>
				</tfoot>
			</table>
			</form>
			<p></p>		<br>	
			
			
			<!-- 그리드 박스 -->
			<table id="list"><tr><td/></tr></table>
			<div id="pager"></div>		
			
			<!-- 버튼 박스 -->
			<table class="form-layout" border="0" cellspacing="0" cellpadding="0">
				<colgroup>
					<col width="30%"/>
					<col width="40%"/>
					<col width="30%"/>
				</colgroup>
				<thead>
					<tr>
						<td class="searchHline" colspan="3"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="searchBody"> </td>
						<td class="searchBody" align="center">&nbsp;</td>
						<td class="searchBody" align="right"><button id="add">행추가</button> <button id="delete">행삭제</button> <button id="save">저장</button></td>
					</tr>
				</tbody> 
				<tfoot>
					<tr>
						<td class="searchHline" colspan="3"></td>
					</tr>
				</tfoot>
			</table>					
			
			<!-- 각 화면 내용 들어갈 부분 --> 
								
		    
	</div>
	<!--################ contentArea ##################-->
	
</div>	
<footer>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</footer>	

</body>
</html>
