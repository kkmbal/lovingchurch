<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>  
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
	$(document).ready(function () {

		fnMakeYearSelect("#cmbYear1", "전체");
		fnMakeYearSelect("#cmbYear2", "전체");
		fnMakeYearSelect("#cmbYear3", "전체");
		
		$("#tabs").tabs();
		
		 $("#list1").jqGrid({
		    url:"settleService.listInYear.lc",
		    colNames:['월', '주정헌금', '십일조헌금', '감사헌금', '선교헌금', '건축헌금', '절기헌금', '기타헌금', '계'],
		    colModel :[ 
		      {name:'CAL_YMD', index:'CAL_YMD', width:100, align:'left', editable:false},
		      {name:'AMT1', index:'AMT1', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
		      {name:'AMT2', index:'AMT2', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
		      {name:'AMT3', index:'AMT3', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
		      {name:'AMT4', index:'AMT4', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
		      {name:'AMT5', index:'AMT5', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
		      {name:'AMT6', index:'AMT6', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
		      {name:'AMT7', index:'AMT7', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
		      {name:'SUMM', index:'SUMM', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}}
		    ],
		    caption: '수입예산',
		    multiselect:false,
		    height:400,
		    rowNum:0,
		    footerrow:true,
		    userDataOnFooter:true,	
  			 loadComplete:function(data){
  				 if(data != null){
  					 //합계
  					var S_AMT1 = $("#list1").jqGrid('getCol', 'AMT1', false, 'sum');
  					var S_AMT2 = $("#list1").jqGrid('getCol', 'AMT2', false, 'sum');
  					var S_AMT3 = $("#list1").jqGrid('getCol', 'AMT3', false, 'sum');
  					var S_AMT4 = $("#list1").jqGrid('getCol', 'AMT4', false, 'sum');
  					var S_AMT5 = $("#list1").jqGrid('getCol', 'AMT5', false, 'sum');
  					var S_AMT6 = $("#list1").jqGrid('getCol', 'AMT6', false, 'sum');
  					var S_AMT7 = $("#list1").jqGrid('getCol', 'AMT7', false, 'sum');
  					var S_SUM = $("#list1").jqGrid('getCol', 'SUMM', false, 'sum');
					
					$("#list1").jqGrid('footerData', 'set', { CAL_YMD: '계', AMT1: S_AMT1,  AMT2: S_AMT2, AMT3: S_AMT3, AMT4: S_AMT4, AMT5: S_AMT5, AMT6: S_AMT6, AMT7: S_AMT7, SUMM: S_SUM});
  				 }
  			 }		    
		    
		});	
		 
		 $("#list2").jqGrid({
			    url:"settleService.listOutYear.lc",
			    colNames:['항목', '비고', '금액'],
			    colModel :[ 
			      {name:'CD_NM', index:'CD_NMS', width:100, align:'left', editable:false},
			      {name:'DESC', index:'DESC', width:100, align:'left', editable:false},
			      {name:'INOUT_AMT', index:'INOUT_AMT', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}}
			    ],
			    caption: '지출결산',
			    multiselect:false,
			    height:400,
			    rowNum:0,
			    footerrow:true,
			    userDataOnFooter:true,	
	  			 loadComplete:function(data){
	  				 if(data != null){
	  					 //합계
	  					var INOUT_AMT_sum = $("#list2").jqGrid('getCol', 'INOUT_AMT', false, 'sum');
						
						$("#list2").jqGrid('footerData', 'set', { CD_NM: '합계', DESC:'', INOUT_AMT: INOUT_AMT_sum});
	  				 }
	  			 }			    
			});	
		 
		// $("#list1").jqGrid('setGridWidth', $("#list1_parent").innerWidth()/2-2);
		 $("#list2").jqGrid('setGridWidth', $("#list1_parent").innerWidth()/2-2);
		 $("#list3").jqGrid('setGridWidth', $("#list1_parent").innerWidth()/2-2);
		 
		 $("#list3").jqGrid({
			    url:"settleService.listYearSum.lc",
			    colNames:['헌금내용', '년결산', '년결산', '년예산', '년예산','비고'],
			    colModel :[ 
			      {name:'INOUT', index:'INOUT', width:130, align:'left', editable:false},
			      {name:'PREV_YEAR_SUM', index:'PREV_YEAR_SUM', width:130, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'YEAR_SUM', index:'YEAR_SUM', width:130, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'YEAR_COST', index:'YEAR_COST', width:130, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'NEXT_YEAR_COST', index:'NEXT_YEAR_COST', width:130, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'DESC', index:'DESC', width:250, align:'center', editable:true}
			    ],
			    caption: '예산결산',
			    multiselect:false,
			    sortname: 'CAL_YM',
			    height:400,
			    rowNum:20,
			    footerrow:true,
			    userDataOnFooter:true,			    
			    beforeRequest : function(){
		         	//초기값 필요할때.
	  			},
	  			 loadComplete:function(data){
	  				 if(data != null){
	  					 //합계
						var S_PREV_YEAR_SUM = $("#list3").jqGrid('getCol', 'PREV_YEAR_SUM', false, 'sum');
						var S_YEAR_SUM = $("#list3").jqGrid('getCol', 'YEAR_SUM', false, 'sum');
						var S_YEAR_COST = $("#list3").jqGrid('getCol', 'YEAR_COST', false, 'sum');
						var S_NEXT_YEAR_COST = $("#list3").jqGrid('getCol', 'NEXT_YEAR_COST', false, 'sum');
						
						$("#list3").jqGrid('footerData', 'set', { INOUT: '합계', PREV_YEAR_SUM: S_PREV_YEAR_SUM, YEAR_SUM: S_YEAR_SUM, YEAR_COST:S_YEAR_COST, NEXT_YEAR_COST:S_NEXT_YEAR_COST});
	  				 }
	  			 }	  			
			});		 
		 
			//Grid 검색
			$("#search1").click(function(){
				var search_data = {};
				search_data.YEAR = $("#cmbYear1").val();
				$("#list1").fnSelGrid("settleService.listInYear.lc", search_data);
				return false;
			});			 
			$("#search2").click(function(){
				var search_data = {};
				search_data.YEAR = $("#cmbYear2").val();
				$("#list2").fnSelGrid("settleService.listOutYear.lc", search_data);
				return false;
			});			 
			$("#search3").click(function(){
				var search_data = {};
				search_data.YEAR = $("#cmbYear3").val();
				$("#list3").fnSelGrid("settleService.listYearSum.lc", search_data);
				return false;
			});			 
		 
							
			
			//마감
			$("#end").click( function() {
				if(!confirm("마감하시겠습니까?")){
					return false;
				}				
				//$("#CAL_YM").val($("#DT").val().replace(/-/gi,"").substring(0, 6));
				$("#CAL_YM").val($("#DT").val().replace(/-/gi,""));
	 			 fnSubmitAjax('settleService.saveMonthEndYn.lc', 'CAL_YM', fnEndResult);
	 			 
				return false;
			});	
			
			$("#excel1").click(function(){
				$("#CAL_YM").val($("#cmbYear1").val());
				$("#frm1").attr("action", "excel_in_year_sum.do");
				$("#frm1").submit();
			});
			$("#excel2").click(function(){
				$("#CAL_YM").val($("#cmbYear2").val());
				$("#frm1").attr("action", "excel_out_year_sum.do");
				$("#frm1").submit();
			});
			$("#excel3").click(function(){
				$("#CAL_YM").val($("#cmbYear3").val());
				$("#frm1").attr("action", "excel_year_sum.do");
				$("#frm1").submit();
			});
	});
	
	function fnResult(data){
		$("#prevThisSum").val(data.prevThisSum); //전주+금주
		$("#thisOut1").val(data.thisOut); //금주지출
		$("#thisEnd").val(data.thisEnd); //금주마감
		$("#thisIn").val(data.thisIn); //금주수입계
		$("#thisOut2").val(data.thisOut); //금주지출계
		$("#prevEnd").val(data.prevEnd); //전주이월계
		$("#nextEnd").val(data.nextEnd); //다음주이월계
		$("#thisInSum").val(data.thisInSum); //금주수입합계
		$("#thisOutSum").val(data.thisOutSum); //금주지출합계
		
		if(data.END_YN == 'Y'){
			$("#save").hide();
			$("#end").hide();
			$("#excel").show();
		}else{
			$("#save").show();
			$("#end").show();
			$("#excel").hide();
		}
		$("#excel").show();
	}
	
	function fnEndResult(data){
		alert(data.SUCCESS);
		if(data.END_YN == 'Y'){
			$("#save").hide();
			$("#end").hide();
			$("#excel").show();
		}
		$("#excel").show();
		
		$("#search").trigger("click");
	}
	</script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/inc/topmenu.jsp" %>

<div class="bodyArea">
<!--title-->
<div class="titleArea">
	<ul>
		<li class="title">년간결산 관리</li>
		<li class="directory"><img src="${pageContext.request.contextPath}/img/etc/icn_home.gif">	결산    >	년간결산관리</li>
	</ul>
</div>
<!--title-->
	
<!--################ contentArea ##################-->
	<div class="content" id="contentArea">
			
			<!-- 각 화면 내용 들어갈 부분 --> 
			
			<!-- form -->
			<form name="frm1" id="frm1" method="post" action="excel_in_year_sum.do">
			<input type="hidden" name="YEAR" id="CAL_YM">
			</form>			
			
								
			<div id="tabs">
				<ul>
					<li><a href="#tabs-1" id="tabs-1-link">수입예산</a></li>
					<li><a href="#tabs-2" id="tabs-2-link">지출예산</a></li>
					<li><a href="#tabs-3" id="tabs-3-link">예산결산</a></li>
				</ul>			
			
			    <div id="tabs-1">
					<table class="form-layout"  border="0" cellspacing="0" cellpadding="0">
						<colgroup>
							<col width="100"/>
							<col width="100"/>
							<col width="*"/>
						</colgroup>
						<thead>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="searchBody">년 <select style="width:100" id="cmbYear1"></select> </td>
								<td class="searchBody"> </td>
								<td class="searchBody" align="left"><button id="search1">검색</button></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
								    
			        <p id="list1_parent"></p>
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
									<td class="searchBody"> <a href="#"><img src="${pageContext.request.contextPath}/img/excel.gif" id="excel1" title="수입예산" style="display:"></a> </td>
									<td class="searchBody" align="center">&nbsp;</td>
									<td class="searchBody" align="right"> </td>
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
			
					<table class="form-layout"  border="0" cellspacing="0" cellpadding="0">
						<colgroup>
							<col width="100"/>
							<col width="100"/>
							<col width="*"/>
						</colgroup>
						<thead>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="searchBody">년 <select style="width:100" id="cmbYear2"></select> </td>
								<td class="searchBody"> </td>
								<td class="searchBody" align="left"><button id="search2">검색</button></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
								    
			        <p id="list2_parent"></p>
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
									<td class="searchBody"> <a href="#"><img src="${pageContext.request.contextPath}/img/excel.gif" id="excel2" title="지출예산" style="display:"></a> </td>
									<td class="searchBody" align="center">&nbsp;</td>
									<td class="searchBody" align="right"> </td>
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
			
					<table class="form-layout"  border="0" cellspacing="0" cellpadding="0">
						<colgroup>
							<col width="100"/>
							<col width="100"/>
							<col width="*"/>
						</colgroup>
						<thead>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="searchBody">년 <select style="width:100" id="cmbYear3"></select> </td>
								<td class="searchBody"> </td>
								<td class="searchBody" align="left"><button id="search3">검색</button></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td class="searchHline" colspan="3"></td>
							</tr>
						</tfoot>
					</table>
								    
			        <p id="list3_parent"></p>
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
									<td class="searchBody"> <a href="#"><img src="${pageContext.request.contextPath}/img/excel.gif" id="excel3" title="예산결산" style="display:none"></a> </td>
									<td class="searchBody" align="center">&nbsp;</td>
									<td class="searchBody" align="right"><button id="save">저장</button> <button id="end">마감처리</button></td>
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
