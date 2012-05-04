<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>  
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
	    var mem = '';
		$(document).ready(function () {
			//실행일자
			$("#DT1").datepicker();		
			$("#DT1").val(formatDate(new Date(), "yyyy-MM-dd"));
			
			$("#DT2").datepicker();		
			$("#DT2").val(formatDate(new Date(), "yyyy-MM-dd"));
			
			$("#DT3").datepicker();		
			$("#DT3").val(formatDate(new Date(), "yyyy-MM-dd"));			
			
			fnMakeSelectComm("#cmbDona", "DONA_CD", "전체");
					
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
			      {name:'DONA_CD_07', index:'DONA_CD_07', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}, hidden:true},
			      {name:'DONA_CD_08', index:'DONA_CD_08', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}, hidden:true},
			      {name:'DONA_CD_09', index:'DONA_CD_09', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}, hidden:true},
			      {name:'DONA_CD_10', index:'DONA_CD_10', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}, hidden:true},
			      {name:'DONA_CD_11', index:'DONA_CD_11', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:","}, editrules:{number:true}, hidden:true},
			      {name:'CAL_YMD', index:'CAL_YMD', width:80, align:'right', editable:false, hidden:true},
			      {name:'USER_KEY', index:'USER_KEY', width:80, align:'right', editable:false, hidden:true}
			    ],
			    pager: '#pager1',
			    caption: '헌금관리',
			    height:400,
			    rowNum:100,
				rowList:[100,200,300],
				sortable: true,
			    footerrow:true,
			    userDataOnFooter:true,	
	  			 loadComplete:function(data){
	  				 if(data != null){
	  					 //합계
	  					var DONA_CD_01_sum = $("#list1").jqGrid('getCol', 'DONA_CD_01', false, 'sum');
	  					var DONA_CD_02_sum = $("#list1").jqGrid('getCol', 'DONA_CD_02', false, 'sum');
	  					var DONA_CD_03_sum = $("#list1").jqGrid('getCol', 'DONA_CD_03', false, 'sum');
	  					var DONA_CD_04_sum = $("#list1").jqGrid('getCol', 'DONA_CD_04', false, 'sum');
	  					var DONA_CD_05_sum = $("#list1").jqGrid('getCol', 'DONA_CD_05', false, 'sum');
	  					var DONA_CD_06_sum = $("#list1").jqGrid('getCol', 'DONA_CD_06', false, 'sum');
	  					var DONA_CD_07_sum = $("#list1").jqGrid('getCol', 'DONA_CD_07', false, 'sum');
	  					var DONA_CD_08_sum = $("#list1").jqGrid('getCol', 'DONA_CD_08', false, 'sum');
	  					var DONA_CD_09_sum = $("#list1").jqGrid('getCol', 'DONA_CD_09', false, 'sum');
	  					var DONA_CD_10_sum = $("#list1").jqGrid('getCol', 'DONA_CD_10', false, 'sum');
	  					var DONA_CD_11_sum = $("#list1").jqGrid('getCol', 'DONA_CD_11', false, 'sum');
						
						$("#list1").jqGrid('footerData', 'set', { USER_NM: '합계', DONA_CD_01: DONA_CD_01_sum, 
																				   DONA_CD_02: DONA_CD_02_sum,
																				   DONA_CD_03: DONA_CD_03_sum,
																				   DONA_CD_04: DONA_CD_04_sum,
																				   DONA_CD_05: DONA_CD_05_sum,
																				   DONA_CD_06: DONA_CD_06_sum,
																				   DONA_CD_07: DONA_CD_07_sum,
																				   DONA_CD_08: DONA_CD_08_sum,
																				   DONA_CD_09: DONA_CD_09_sum,
																				   DONA_CD_10: DONA_CD_10_sum,
																				   DONA_CD_11: DONA_CD_11_sum
																				   });
	  				 }
	  			 }			    
			});	
			 
			 jQuery("#list1").jqGrid('navGrid','#pager1',{add:false,edit:false,del:false,search:false,refresh:false});
			 jQuery("#list1").jqGrid('navButtonAdd','#pager1',{
			     caption: "Columns",
			     title: "Reorder Columns",
			     onClickButton : function (){
			         jQuery("#list1").jqGrid('columnChooser');
			     }
			 });
			 jQuery("#list1").jqGrid('gridResize',{minWidth:600,maxWidth:960,minHeight:400, maxHeight:400});
			 
			 
			 $("#list2").jqGrid({
				    url:"inoutService.listIn.lc",
				    colNames:['수입항목', '금액', '비고','CAL_YMD','INOUT_SEQ_NO'],
				    colModel :[ 
				      {name:'INOUT_ITEM_CD', index:'INOUT_ITEM_CD', width:90, align:'left', editable:true, edittype: "select", editoptions: {value: fnGetGridSelectComm('IN_CD') }, formatter:'select', editrules:{number:true}},
				      {name:'INOUT_AMT', index:'INOUT_AMT', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:",", defaultValue: '0'}, editrules:{number:true}}, 
				      {name:'REMARK', index:'REMARK', width:80, align:'left', editable:true}, 
				      {name:'CAL_YMD', index:'CAL_YMD', width:80, align:'left', editable:false, hidden:true}, 
				      {name:'INOUT_SEQ_NO', index:'INOUT_SEQ_NO', width:80, align:'left', editable:false, hidden:true} 
				    ],
				    pager: '#pager2',
				    caption: '기타수입관리',
				    sortname: 'INOUT_ITEM_CD',
				    height:400,
				    footerrow:true,
				    userDataOnFooter:true,	
		  			 loadComplete:function(data){
		  				 if(data != null){
		  					 //합계
		  					var INOUT_AMT_sum = $("#list2").jqGrid('getCol', 'INOUT_AMT', false, 'sum');
							
							$("#list2").jqGrid('footerData', 'set', { INOUT_ITEM_CD: '합계', INOUT_AMT: INOUT_AMT_sum});
		  				 }
		  			 }					    
			 });
			 
			 $("#list3").jqGrid({
				    url:"inoutService.listDonationEach.lc",
				    colNames:['헌금명', '성명', '금액','비고','CAL_YMD','INOUT_SEQ_NO'],
				    colModel :[ 
				      {name:'INOUT_ITEM_CD', index:'INOUT_ITEM_CD', width:90, align:'left', editable:true, edittype: "select", editoptions: {value: fnGetGridSelectComm('DONA_CD') }, formatter:'select', editrules:{number:true}},
				      //{name:'USER_KEY', index:'USER_KEY', width:80, align:'left', editable:true, edittype: 'custom', editable:true, editoptions : {custom_element : autocomplete_element, custom_value : autocomplete_value}}, 
				      {name:'USER_KEY', index:'USER_KEY', width:80, align:'left', editable:true, edittype: "select", editoptions: {value: fnGetGridSelect("memberService.listAllMember.lc", "VALUE", "LABEL") }, formatter:'select', editrules:{number:true}},
				      {name:'INOUT_AMT', index:'INOUT_AMT', width:80, align:'right', editable:true, formatter: 'currency',formatoptions:{thousandsSeparator:",", defaultValue: '0'}, editrules:{number:true}}, 
				      {name:'REMARK', index:'REMARK', width:80, align:'left', editable:true},
				      {name:'CAL_YMD', index:'CAL_YMD', width:80, align:'left', editable:false, hidden:true}, 
				      {name:'INOUT_SEQ_NO', index:'INOUT_SEQ_NO', width:80, align:'left', editable:false, hidden:true} 
				    ],
				    pager: '#pager3',
				    caption: '헌금관리',
				    sortname: 'INOUT_ITEM_CD',
				    rowNum:100,
					rowList:[100,200,300],
				    height:400,
				    footerrow:true,
				    userDataOnFooter:true,	
		  			 loadComplete:function(data){
		  				 if(data != null){
		  					 //합계
		  					var INOUT_AMT_sum = $("#list3").jqGrid('getCol', 'INOUT_AMT', false, 'sum');
							
							$("#list3").jqGrid('footerData', 'set', { INOUT_ITEM_CD: '합계', INOUT_AMT: INOUT_AMT_sum});
		  				 }
		  			 }				    
			 });			 
			 
			 $("#list2").jqGrid('setGridWidth', $("#list2_parent").innerWidth()-2);
			 $("#list3").jqGrid('setGridWidth', $("#list2_parent").innerWidth()-2);
			 
				//Grid 검색
				$("#search1").click(function(){
					// 1. 특정 검색어 사용시
					var search_data = {};
					search_data.CAL_YMD = $("#DT1").val().replace(/-/gi,"");
					$("#list1").fnSelGrid("inoutService.listDonation.lc", search_data);

					return false;
				});	
				
				$("#search3").click(function(){
					// 1. 특정 검색어 사용시
					var search_data = {};
					search_data.CAL_YMD = $("#DT3").val().replace(/-/gi,"");
					search_data.USER_NM = $("#userNm").val();
					search_data.INOUT_ITEM_CD = $("#cmbDona").val();
					$("#list3").fnSelGrid("inoutService.listDonationEach.lc", search_data);
					
					return false;
				});				
				
				//Grid 저장(insert+update)
				$("#save1").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT1").val().replace(/-/gi,"");
					$("#list1").fnSaveGrid2("inoutService.saveDonation.lc", search_data);
					return false;
				});				
			 
				$("#save3").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT3").val().replace(/-/gi,"");
					$("#list3").fnSaveGrid("inoutService.saveDonationEach.lc", search_data);
					return false;
				});				
				
				$("#delete3").click( function() {
					var search_data = {};
					search_data.CAL_YMD = $("#DT3").val().replace(/-/gi,"");
					$("#list3").fnDelGrid("inoutService.deleteDonationEach.lc", search_data);
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

				
				//Grid 행추가
				$("#add3").click( function() {
					$("#list3").fnAddGrid();
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
				
				$("#sumDetl").click(function(){
					var calYmd = $("#DT1").val();
					location.href = "${pageContext.request.contextPath}/settleWeek.do?calYmd="+calYmd;
				});
				
		});
		
		function autocomplete_element(value, options) {  
			 //alert(mem)
			  var ac = $('<input type="text"/>');  
			  ac.val(value);  
			  //ac.autocomplete({source: ["test","values", "later", "db-query"]});  
			  //ac.autocomplete({source: mem});
			  ac.autocomplete({source: "memberService.listAllMember.lc"});
			  return ac;  

		}  

		function autocomplete_value(elem) {  
		  return $(elem).val();  
		} 		
		
		function fnResult(data){
			var nameSel = JSON.stringify(data).toLowerCase();
			mem = nameSel;
		}
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
					<li><a href="#tabs-1" id="tabs-1-link">교인별헌금목록</a></li>
					<li><a href="#tabs-2" id="tabs-2-link">헌금별목록</a></li>
					<li><a href="#tabs-3" id="tabs-3-link">기타수입</a></li>
				</ul>			
			
			    <div id="tabs-1">
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
								<td class="searchBody"><input type="text" style="width:80px;" readonly name="DT1" id="DT1">  </td>
								<td class="searchBody" align="left"> <button id="search1">검색</button></td>
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
								<td class="searchBody" align="right"> <button id="sumDetl">결산상세</button> <button id="save1">저장</button></td>
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
							<col width="500"/>
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
								<td class="searchBody"><input type="text" style="width:80px;" readonly name="DT3" id="DT3">&nbsp;&nbsp;성명 <input name="userNm" name="userNm" id="userNm"> &nbsp;&nbsp;헌금 <select style="width:100" id="cmbDona"></select> </td>
								<td class="searchBody" align="left"> <button id="search3">검색</button></td>
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
					
					<!-- 그리드 박스 -->
					<table id="list3"><tr><td/></tr></table>
					<div id="pager3"></div>		
					
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
								<td class="searchBody">  </td>
								<td class="searchBody" align="center">&nbsp;</td>
								<td class="searchBody" align="right"><button id="add3">행추가</button> <button id="delete3">행삭제</button> <button id="save3">저장</button></td>
							</tr>
						</tbody> 
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
				</div>				
				
				
			    <div id="tabs-3">
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
								<td class="searchBody"><input type="text" style="width:80px;" readonly name="DT2" id="DT2"></td>
								<td class="searchBody" align="left"><button id="search2">검색</button></td>
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
