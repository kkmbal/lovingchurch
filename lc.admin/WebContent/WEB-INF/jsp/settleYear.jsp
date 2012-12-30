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
				$("#CAL_YM").val($("#cmbYear3").val());
				fnSubmitAjax("settleService.listYearSum.lc", 'CAL_YM', fnResult);
				return false;
			});			 
		 
			//저장
			$("#save").click( function() {
				$("#CAL_YM").val($("#cmbYear3").val());
				if($("#CAL_YM").val() == ""){
					alert("년도를 선택하세요.");
					return false;
				}
				if(!confirm("저장하시겠습니까?")){
					return false;
				}				
				
				$("#CAL_YM2").val($("#CAL_YM").val());
	 			 fnSubmitAjax('settleService.saveYearSum.lc', 'frm2', fnEndResult);
	 			 
				return false;
			});							
			
			//마감
			$("#end").click( function() {
				if(!confirm("마감하시겠습니까?")){
					return false;
				}				
				//$("#CAL_YM").val($("#DT").val().replace(/-/gi,"").substring(0, 6));
				$("#CAL_YM").val($("#cmbYear3").val());
	 			 fnSubmitAjax('settleService.saveYearEndYn.lc', 'CAL_YM', fnEndResult);
	 			 
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
		var y1 = $("#cmbYear3").val();
		var y2 = parseInt(y1)+1;
		var y3 = parseInt(y1)+2;
		$("#thisYear1").val(y1);		
		$("#nextYear1").val(y2);		
		$("#thisYear2").val(y1);		
		$("#nextYear2").val(y2);		
		$("#nextYear3").val(y2);		
		$("#nextYear4").val(y3);		
		
		
		$("#PREV_SUM1_AMT").val(data.PREV_SUM1_AMT); 
		$("#SUM1_AMT").val(data.SUM1_AMT); 
		$("#PREV_EXP_SUM1_AMT").val(data.PREV_EXP_SUM1_AMT);  
		$("#EXP_SUM1_AMT").val(data.EXP_SUM1_AMT); 
		$("#REMARK1").val(data.REMARK1);  

		$("#PREV_SUM2_AMT").val(data.PREV_SUM2_AMT); 
		$("#SUM2_AMT").val(data.SUM2_AMT); 
		$("#PREV_EXP_SUM2_AMT").val(data.PREV_EXP_SUM2_AMT);  
		$("#EXP_SUM2_AMT").val(data.EXP_SUM2_AMT); 
		$("#REMARK2").val(data.REMARK2);  
		

		$("#PREV_SUM3_AMT").val(data.PREV_SUM3_AMT); 
		$("#SUM3_AMT").val(data.SUM3_AMT); 
		$("#PREV_EXP_SUM3_AMT").val(data.PREV_EXP_SUM3_AMT);  
		$("#EXP_SUM3_AMT").val(data.EXP_SUM3_AMT); 
		$("#REMARK3").val(data.REMARK3);  
		

		$("#PREV_SUM4_AMT").val(data.PREV_SUM4_AMT); 
		$("#SUM4_AMT").val(data.SUM4_AMT); 
		$("#PREV_EXP_SUM4_AMT").val(data.PREV_EXP_SUM4_AMT);  
		$("#EXP_SUM4_AMT").val(data.EXP_SUM4_AMT); 
		$("#REMARK4").val(data.REMARK4);  
		

		$("#PREV_SUM5_AMT").val(data.PREV_SUM5_AMT); 
		$("#SUM5_AMT").val(data.SUM5_AMT); 
		$("#PREV_EXP_SUM5_AMT").val(data.PREV_EXP_SUM5_AMT);  
		$("#EXP_SUM5_AMT").val(data.EXP_SUM5_AMT); 
		$("#REMARK5").val(data.REMARK5);  
		

		$("#PREV_SUM6_AMT").val(data.PREV_SUM6_AMT); 
		$("#SUM6_AMT").val(data.SUM6_AMT); 
		$("#PREV_EXP_SUM6_AMT").val(data.PREV_EXP_SUM6_AMT);  
		$("#EXP_SUM6_AMT").val(data.EXP_SUM6_AMT); 
		$("#REMARK6").val(data.REMARK6);  
		

		$("#PREV_SUM7_AMT").val(data.PREV_SUM7_AMT); 
		$("#SUM7_AMT").val(data.SUM7_AMT); 
		$("#PREV_EXP_SUM7_AMT").val(data.PREV_EXP_SUM7_AMT);  
		$("#EXP_SUM7_AMT").val(data.EXP_SUM7_AMT); 
		$("#REMARK7").val(data.REMARK7);  
		

		$("#PREV_SUM_AMT").val(data.PREV_SUM_AMT); 
		$("#SUM_AMT").val(data.SUM_AMT); 
		$("#PREV_EXP_SUM_AMT").val(data.PREV_EXP_SUM_AMT);  
		$("#EXP_SUM_AMT").val(data.EXP_SUM_AMT); 
		//$("#REMARK1").val(data.REMARK1);  
		
		if(data.END_YN == 'Y'){
			$("#save").hide();
			$("#end").hide();
			$("#excel3").show();
		}else{
			$("#save").show();
			$("#end").show();
			$("#excel3").hide();
		}
		$("#excel3").show();
	}
	
	function fnEndResult(data){
		alert(data.SUCCESS);
		if(data.END_YN == 'Y'){
			$("#save").hide();
			$("#end").hide();
			$("#excel3").show();
		}
		$("#excel3").show();
		
		$("#search3").trigger("click");
	}		
	</script>

</head>

<body>



<!-- Layout container starts -->
<div id="layoutBox">

<%@ include file="/WEB-INF/jsp/inc/topmenu.jsp" %>

  <div id="mainContent">
    <h1>년간결산관리</h1>
  
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
								    
						<!-- form -->
						<form name="frm2" id="frm2" method="post">
						<input type="hidden" name="YEAR" id="CAL_YM2">
								
						<table border="0" cellpadding=0 cellspacing=0  width="100%">
							<colgroup>
								<col width="16%"/>
								<col width="16%"/>
								<col width="16%"/>
								<col width="16%"/>
								<col width="16%"/>
								<col width="17%"/>
							</colgroup>
							<thead>
								<tr>
									<td height="1" class="boardHline" colspan="6"></td>
								</tr>
							</thead>
							<tbody>
							<tr height="26">
								<td colspan="6" class="boardHead"><input type="text" style="width:50px;" name="thisYear1" id="thisYear1" readonly>년 수입결산 / <input type="text" style="width:50px;" name="nextYear1" id="nextYear1" readonly>년 수입예산</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">헌금내용/구분</td>
								<td class="boardHead"><input type="text" style="width:50px;" name="thisYear2" id="thisYear2" readonly>년 결산</td>
								<td class="boardHead"><input type="text" style="width:50px;" name="nextYear2" id="nextYear2" readonly>년 결산</td>
								<td class="boardHead"><input type="text" style="width:50px;" name="nextYear3" id="nextYear3" readonly>년 예산</td>
								<td class="boardHead"><input type="text" style="width:50px;" name="nextYear4" id="nextYear4" readonly>년 예산</td>
								<td class="boardHead">비고</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>	
							</tr>
							<tr height="26">
								<td class="boardHead">주정헌금</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM1_AMT" id="PREV_SUM1_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM1_AMT" id="SUM1_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM1_AMT" id="PREV_EXP_SUM1_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM1_AMT" id="EXP_SUM1_AMT"  ></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="REMARK1" id="REMARK1"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>							
							<tr height="26">
								<td class="boardHead">십일조헌금</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM2_AMT" id="PREV_SUM2_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM2_AMT" id="SUM2_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM2_AMT" id="PREV_EXP_SUM2_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM2_AMT" id="EXP_SUM2_AMT"  ></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="REMARK2" id="REMARK2"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">감사헌금</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM3_AMT" id="PREV_SUM3_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM3_AMT" id="SUM3_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM3_AMT" id="PREV_EXP_SUM3_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM3_AMT" id="EXP_SUM3_AMT"  ></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="REMARK3" id="REMARK3"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>				
							<tr height="26">
								<td class="boardHead">선교헌금</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM4_AMT" id="PREV_SUM4_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM4_AMT" id="SUM4_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM4_AMT" id="PREV_EXP_SUM4_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM4_AMT" id="EXP_SUM4_AMT"  ></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="REMARK4" id="REMARK4"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">건축헌금</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM5_AMT" id="PREV_SUM5_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM5_AMT" id="SUM5_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM5_AMT" id="PREV_EXP_SUM5_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM5_AMT" id="EXP_SUM5_AMT"  ></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="REMARK5" id="REMARK5"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">절기헌금</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM6_AMT" id="PREV_SUM6_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM6_AMT" id="SUM6_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM6_AMT" id="PREV_EXP_SUM6_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM6_AMT" id="EXP_SUM6_AMT"  ></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="REMARK6" id="REMARK6"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">기타</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM7_AMT" id="PREV_SUM7_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM7_AMT" id="SUM7_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM7_AMT" id="PREV_EXP_SUM7_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM7_AMT" id="EXP_SUM7_AMT"  ></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="REMARK7" id="REMARK7"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">합계</td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_SUM_AMT" id="PREV_SUM_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="SUM_AMT" id="SUM_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="PREV_EXP_SUM_AMT" id="PREV_EXP_SUM_AMT" readonly></td>
								<td class="boardBody"><input type="text" style="width:100%;" name="EXP_SUM_AMT" id="EXP_SUM_AMT" readonly></td>
								<td class="boardBody"></td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							
							
							</tbody>
						</table>		
						</form>	
						
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


   
    <br />

    

<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
  </div>

  <div class="spacer">
  </div>

</div>
<!-- Layout container ends -->

</body>
</html>

