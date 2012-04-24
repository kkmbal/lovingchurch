<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>  
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
		$(document).ready(function () {
			//실행일자
			$("#DT1").datepicker();		
			$("#DT1").val(formatDate(new Date(), "yyyy-MM-dd"));
			
			$("#DT2").datepicker();		
			$("#DT2").val(formatDate(new Date(), "yyyy-MM-dd"));
			
			$("#tabs").tabs();
			
			 $("#list1").jqGrid({
			    url:"inoutService.listDonation.lc",
			    colNames:['성명', '주일','십일조','감사','선교','건축','생일','부활절','추수감사','구제','성탄감사','신년감사','CAL_YMD','USER_KEY'],
			    colModel :[ 
			      {name:'USER_NM', index:'USER_NM', width:90, align:'left', editable:false},
			      {name:'DONA_CD_01', index:'DONA_CD_01', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:",",defaultValue: '0'}, editrules:{number:true}}, 
			      {name:'DONA_CD_02', index:'DONA_CD_02', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}}, 
			      {name:'DONA_CD_03', index:'DONA_CD_03', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_04', index:'DONA_CD_04', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_05', index:'DONA_CD_05', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_06', index:'DONA_CD_06', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_07', index:'DONA_CD_07', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_08', index:'DONA_CD_08', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_09', index:'DONA_CD_09', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_10', index:'DONA_CD_10', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'DONA_CD_11', index:'DONA_CD_11', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}},
			      {name:'CAL_YMD', index:'CAL_YMD', width:80, align:'right', editable:false, hidden:tru},
			      {name:'USER_KEY', index:'USER_KEY', width:80, align:'right', editable:false, hidden:tru}
			    ],
			    pager: '#pager1',
			    caption: '헌금관리',
			    height:400
			});	
			 
			 jQuery("#list1").jqGrid('navGrid','#pager1',{add:false,edit:false,del:false,search:false,refresh:false});
			 jQuery("#list1").jqGrid('navButtonAdd','#pager1',{
			     caption: "Columns",
			     title: "Reorder Columns",
			     onClickButton : function (){
			         jQuery("#list1").jqGrid('columnChooser');
			     }
			 });
			 jQuery("#list1").jqGrid('gridResize',{minWidth:600,maxWidth:1000,minHeight:400, maxHeight:400});
			 
			 
			 $("#list2").jqGrid({
				    url:"inoutService.listIn.lc",
				    colNames:['수입항목', '금액', '비고','CAL_YMD','INOUT_SEQ_NO'],
				    colModel :[ 
				      {name:'INOUT_ITEM_CD', index:'INOUT_ITEM_CD', width:90, align:'left', editable:true, edittype: "select", editoptions: {value: fnGetGridSelectComm('IN_CD') }, formatter:'select', editrules:{number:true}},
				      {name:'INOUT_AMT', index:'INOUT_AMT', width:80, align:'left', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:",", defaultValue: '0'}, editrules:{number:true}}, 
				      {name:'REMARK', index:'REMARK', width:80, align:'left', editable:true}, 
				      {name:'CAL_YMD', index:'CAL_YMD', width:80, align:'left', editable:false, hidden:true}, 
				      {name:'INOUT_SEQ_NO', index:'INOUT_SEQ_NO', width:80, align:'left', editable:false, hidden:true} 
				    ],
				    pager: '#pager2',
				    caption: '기타수입관리',
				    sortname: 'INOUT_ITEM_CD',
				    height:400
			 });
			 
			 $("#list2").jqGrid('setGridWidth', $("#list2_parent").innerWidth()-2);
			 
				//Grid 검색
				$("#search1").click(function(){
					// 1. 특정 검색어 사용시
					var search_data = {};
					search_data.CAL_YMD = $("#DT1").val().replace(/-/gi,"");
					$("#list1").fnSelGrid("inoutService.listDonation.lc", search_data);

					return false;
				});	
				
				//Grid 저장(insert+update)
				$("#save1").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT1").val().replace(/-/gi,"");
					$("#list1").fnSaveGrid("inoutService.saveDonation.lc", search_data);
					return false;
				});				
			 
				
				$("#search2").click(function(){
					// 1. 특정 검색어 사용시
					var search_data = {};
					search_data.CAL_YMD = $("#DT2").val().replace(/-/gi,"");
					$("#list2").fnSelGrid("inoutService.listIn.lc", search_data);

					return false;
				});
				
				//Grid 행추가
				$("#add2").click( function() {
					$("#list2").fnAddGrid();
					return false;
				});	

				//Grid 삭제
				$("#delete2").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT2").val().replace(/-/gi,"");
					$("#list2").fnDelGrid("inoutService.deleteIn.lc", search_data);
					return false;
				});

				//Grid 저장(insert+update)
				$("#save2").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT2").val().replace(/-/gi,"");
					$("#list2").fnSaveGrid("inoutService.saveIn.lc", search_data);
					return false;
				});	
				 
				$("#excel").click(function(){
					$("#CAL_YMD").val($("#DT1").val().replace(/-/gi,""));
					$("#frm1").submit();
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
		<li class="title">수입 관리</li>
		<li class="directory"><img src="${pageContext.request.contextPath}/img/etc/icn_home.gif">	회계    >	수입관리</li>
	</ul>
</div>
<!--title-->
	
<!--################ contentArea ##################-->
	<div class="content" id="contentArea">
			
			<!-- 각 화면 내용 들어갈 부분 --> 
			<form name="frm1" id="frm1" method="post" action="excel_in_list.do">
			<input type="hidden" name="CAL_YMD" id="CAL_YMD">
			</form>
						
			<div id="tabs">
				<ul>
					<li><a href="#tabs-1" id="tabs-1-link">헌금</a></li>
					<li><a href="#tabs-2" id="tabs-2-link">기타수입</a></li>
				</ul>			
			
			    <div id="tabs-1">
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
								<td class="searchBody"><input type="text" style="width:80px;" readonly name="DT1" id="DT1"></td>
								<td class="searchBody" align="center"> <button id="search1">검색</button></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
					</form>
					<br>	
					
					<p id="list2_parent"></p>
					<!-- 그리드 박스 -->
					<table id="list1"><tr><td/></tr></table>
					<div id="pager1"></div>		
					
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
								<td class="searchBody"> <a href="#"><img src="${pageContext.request.contextPath}/img/excel.gif" id="excel" title="수입내역"></a> </td>
								<td class="searchBody" align="center">&nbsp;</td>
								<td class="searchBody" align="right"> <button id="save1">저장</button></td>
							</tr>
						</tbody> 
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
				</div>	
				
			    <div id="tabs-2">
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
								<td class="searchBody"><input type="text" style="width:80px;" readonly name="DT2" id="DT2"></td>
								<td class="searchBody" align="center"><button id="search2">검색</button></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
					</form>
					<br>	
					
					<p>
					<!-- 그리드 박스 -->
					<table id="list2"><tr><td/></tr></table>
					<div id="pager2"></div>		
					
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
								<td class="searchBody" align="right"><button id="add2">행추가</button> <button id="delete2">행삭제</button> <button id="save2">저장</button></td>
							</tr>
						</tbody> 
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
				</div>
												
			</div>
			<!-- 각 화면 내용 들어갈 부분 --> 
								
		    
	</div>
	<!--################ contentArea ##################-->
	
</div>	
<footer>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</footer>	

</body>
</html>
