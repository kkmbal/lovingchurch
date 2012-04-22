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
				    url:"inoutService.listIn.lc",
				    colNames:['지출항목', '금액', '비고','CAL_YMD','INOUT_SEQ_NO'],
				    colModel :[ 
				      {name:'INOUT_ITEM_CD', index:'INOUT_ITEM_CD', width:90, align:'left', editable:true, edittype: "select", editoptions: {value: fnGetGridSelectComm('OUT_CD') }, formatter:'select'},
				      {name:'INOUT_AMT', index:'INOUT_AMT', width:80, align:'left', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:",", defaultValue: '0'}, editrules:{number:true}}, 
				      {name:'REMARK', index:'REMARK', width:80, align:'left', editable:true}, 
				      {name:'CAL_YMD', index:'CAL_YMD', width:80, align:'left', editable:false}, 
				      {name:'INOUT_SEQ_NO', index:'INOUT_SEQ_NO', width:80, align:'left', editable:false} 
				    ],
				    pager: '#pager',
				    caption: '지출관리',
				    sortname: 'INOUT_ITEM_CD',
				    height:400
			 });	
			 
				$("#search").click(function(){
					// 1. 특정 검색어 사용시
					var search_data = {};
					search_data.CAL_YMD = $("#DT").val().replace(/-/gi,"");
					$("#list").fnSelGrid("inoutService.listOut.lc", search_data);

					return false;
				});
				
				//Grid 행추가
				$("#add").click( function() {
					$("#list").fnAddGrid();
					return false;
				});	

				//Grid 삭제
				$("#delete").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT").val().replace(/-/gi,"");
					$("#list").fnDelGrid("inoutService.deleteOut.lc", search_data);
					return false;
				});

				//Grid 저장(insert+update)
				$("#save").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT").val().replace(/-/gi,"");
					$("#list").fnSaveGrid("inoutService.saveOut.lc", search_data);
					return false;
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
		<li class="title">지출 관리</li>
		<li class="directory"><img src="${pageContext.request.contextPath}/img/etc/icn_home.gif">	회계   >	지출관리</li>
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
