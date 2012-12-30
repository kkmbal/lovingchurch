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
				      {name:'INOUT_AMT', index:'INOUT_AMT', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:",", defaultValue: '0'}, editrules:{number:true}}, 
				      {name:'REMARK', index:'REMARK', width:80, align:'left', editable:true}, 
				      {name:'CAL_YMD', index:'CAL_YMD', width:80, align:'left', editable:false, hidden:true}, 
				      {name:'INOUT_SEQ_NO', index:'INOUT_SEQ_NO', width:80, align:'left', editable:false, hidden:true} 
				    ],
				    pager: '#pager',
				    caption: '지출관리',
				    sortname: 'INOUT_ITEM_CD',
				    height:400,
				    rowNum:20,
				    footerrow:true,
				    userDataOnFooter:true,	
		  			 loadComplete:function(data){
		  				 if(data != null){
		  					 //합계
		  					var INOUT_AMT_sum = $("#list").jqGrid('getCol', 'INOUT_AMT', false, 'sum');
							
							$("#list").jqGrid('footerData', 'set', { INOUT_ITEM_CD: '합계', INOUT_AMT: INOUT_AMT_sum});
		  				 }
		  			 }				    
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
				
				$("#sumDetl").click(function(){
					var calYmd = $("#DT").val();
					location.href = "${pageContext.request.contextPath}/settleWeek.do?calYmd="+calYmd;
				});				
		});
	</script>

</head>

<body>



<!-- Layout container starts -->
<div id="layoutBox">

<%@ include file="/WEB-INF/jsp/inc/topmenu.jsp" %>

  <div id="mainContent">
    <h1>지출관리</h1>

		<div class="content" id="contentArea">
			<!-- 각 화면 내용 들어갈 부분 --> 
			<form name="frm" id="frm">
			<table class="form-layout"  border="0" cellspacing="0" cellpadding="0">
				<colgroup>
					<col width="50"/>
					<col width="150"/>
					<col width="*"/>
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
						<td class="searchBody" align="left"><button id="search">검색</button></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td class="searchHline" colspan="3"></td>
					</tr>
				</tfoot>
			</table>
			</form>
			<p></p>	
			
			
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
						<td class="searchBody" align="right"><button id="sumDetl">결산상세</button> <button id="add">행추가</button> <button id="delete">행삭제</button> <button id="save">저장</button></td>
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


   
    <br />

    

<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
  </div>

  <div class="spacer">
  </div>

</div>
<!-- Layout container ends -->

</body>
</html>

