<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head> 
	<style>
	.ui-datepicker-calendar{display:none}
	</style>
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
	jQuery(function($){
		$.datepicker.regional['ko'] = {
				closeText: '닫기',
				prevText: '이전달',
				nextText: '다음달',
				currentText: '오늘',
				monthNames: ['1월','2월','3월','4월','5월','6월',
				'7월','8월','9월','10월','11월','12월'],
				monthNamesShort: ['1월','2월','3월','4월','5월','6월',
				'7월','8월','9월','10월','11월','12월'],
				dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
				dayNamesShort: ['일','월','화','수','목','금','토'],
				dayNamesMin: ['일','월','화','수','목','금','토'],
				weekHeader: 'Wk',
				dateFormat: 'yy-mm',
				onClose:function(dateText, inst){
						var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
						var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
						$(this).datepicker('setDate', new Date(year, month, 1));
				},
				firstDay: 0,
				isRTL: false,
				showMonthAfterYear: true};	
		$.datepicker.setDefaults($.datepicker.regional['ko']);
	});
	$(document).ready(function () {

		//실행일자
		$("#DT").datepicker();		
		$("#DT").val(formatDate(new Date(), "yyyy-MM"));	
		
		fnMakeYearSelect("#cmbYear", "전체");
		
		$("#tabs").tabs();
		
		 $("#list1").jqGrid({
		    url:"settleService.listIn.lc",
		    colNames:['항목', '금액'],
		    colModel :[ 
		      {name:'CAL_YMD', index:'CAL_YMD', width:100, align:'left', editable:false},
		      {name:'INOUT_AMT', index:'INOUT_AMT', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}}
		    ],
		    caption: '금주수입내역',
		    multiselect:false,
		    height:400,
		    rowNum:0,
		    footerrow:true,
		    userDataOnFooter:true,	
  			 loadComplete:function(data){
  				 if(data != null){
  					 //합계
  					var INOUT_AMT_sum = $("#list1").jqGrid('getCol', 'INOUT_AMT', false, 'sum');
					
					$("#list1").jqGrid('footerData', 'set', { CAL_YMD: '합계', INOUT_AMT: INOUT_AMT_sum});
  				 }
  			 }		    
		    
		});	
		 
		 $("#list2").jqGrid({
			    url:"settleService.listOut.lc",
			    colNames:['항목', '금액'],
			    colModel :[ 
			      {name:'CAL_YMD', index:'CAL_YMD', width:100, align:'left', editable:false},
			      {name:'INOUT_AMT', index:'INOUT_AMT', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}}
			    ],
			    caption: '금주지출내역',
			    multiselect:false,
			    height:400,
			    rowNum:0,
			    footerrow:true,
			    userDataOnFooter:true,	
	  			 loadComplete:function(data){
	  				 if(data != null){
	  					 //합계
	  					var INOUT_AMT_sum = $("#list2").jqGrid('getCol', 'INOUT_AMT', false, 'sum');
						
						$("#list2").jqGrid('footerData', 'set', { CAL_YMD: '합계', INOUT_AMT: INOUT_AMT_sum});
	  				 }
	  			 }			    
			});	
		 
		 $("#list1").jqGrid('setGridWidth', $("#list1_parent").innerWidth()/2-2);
		 $("#list2").jqGrid('setGridWidth', $("#list1_parent").innerWidth()/2-2);
		 
		 $("#list3").jqGrid({
			    url:"settleService.listMonSum.lc",
			    colNames:['년월', '전달이월금', '이번달수입계', '이번달지출계', '이번달마감잔액','마감여부'],
			    colModel :[ 
			      {name:'CAL_YM', index:'CAL_YMD', width:100, align:'left', editable:false},
			      {name:'PREV_AMT', index:'PREV_AMT', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'IN_AMT', index:'IN_AMT', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'OUT_AMT', index:'OUT_AMT', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'NEXT_AMT', index:'NEXT_AMT', width:100, align:'right', editable:false, formatter: 'currency',formatoptions:{thousandsSeparator:","}},
			      {name:'END_YN', index:'END_YN', width:100, align:'center', editable:false}
			    ],
			    pager: '#pager3',
			    caption: '월간결산목록',
			    multiselect:false,
			    sortname: 'CAL_YM',
			    height:400,
			    rowNum:20,
			    footerrow:true,
			    userDataOnFooter:true,			    
			    ondblClickRow : function(rowid, iRow, iCol, e){
			  		if(rowid != null) {
			  			var ret = $(this).jqGrid('getRowData', rowid);
						if(ret.CAL_YM != ""){
							$("#DT").val(ret.CAL_YM);
							$("#tabs-2-link").trigger("click");
							$("#search").trigger("click");
						}
			  		}
		  		},			    
			    beforeRequest : function(){
		         	//초기값 필요할때.
		  			jQuery(this).jqGrid('setGridParam',{postData:{ userdata:{} }} );
	  			},
	  			 loadComplete:function(data){
	  				 if(data != null){
	  					 //합계
						var inAmtSum = $("#list3").jqGrid('getCol', 'IN_AMT', false, 'sum');
						var outAmtSum = $("#list3").jqGrid('getCol', 'OUT_AMT', false, 'sum');
						
						$("#list3").jqGrid('footerData', 'set', { CAL_YM: '합계', IN_AMT: inAmtSum, OUT_AMT: outAmtSum});
	  				 }
	  			 }	  			
			});		 
		 
			//Grid 검색
			$("#search3").click(function(){
				var search_data = {};
				search_data.YEAR = $("#cmbYear").val();
				$("#list3").fnSelGrid("settleService.listMonSum.lc", search_data);
				return false;
			});			 
		 
			//Grid+form 검색
			$("#search").click(function(){
				var search_data = {};
				//search_data.CAL_YM = $("#DT").val().replace(/-/gi,"").substring(0, 6);
				search_data.CAL_YM = $("#DT").val().replace(/-/gi,"");
				search_data.INOUT_CD = "01";
				$("#list1").fnSelGrid("settleService.listInMonth.lc", search_data);
				
				search_data = {};
				//search_data.CAL_YM = $("#DT").val().replace(/-/gi,"").substring(0, 6);
				search_data.CAL_YM = $("#DT").val().replace(/-/gi,"");
				search_data.INOUT_CD = "02";
				$("#list2").fnSelGrid("settleService.listOutMonth.lc", search_data);
				
				//$("#CAL_YM").val($("#DT").val().replace(/-/gi,"").substring(0, 6));
				$("#CAL_YM").val($("#DT").val().replace(/-/gi,""));
				//$("#CAL_YMD").val($("#DT").val().replace(/-/gi,""));
	 			 fnSubmitAjax('settleService.getMonthSum.lc', 'frm1', fnResult);

				return false;
			});	
			
			//저장
			$("#save").click( function() {
				//$("#CAL_YM").val($("#DT").val().replace(/-/gi,"").substring(0, 6));
				$("#CAL_YM").val($("#DT").val().replace(/-/gi,""));
	 			 fnSubmitAjax('settleService.saveMonthSum.lc', 'CAL_YM', fnEndResult);
	 			 
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
			
			$("#excel").click(function(){
				//$("#CAL_YM").val($("#DT").val().replace(/-/gi,"").substring(0, 6));
				$("#CAL_YM").val($("#DT").val().replace(/-/gi,""));
				//$("#CAL_YMD").val($("#DT").val().replace(/-/gi,""));
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



<!-- Layout container starts -->
<div id="layoutBox">

<%@ include file="/WEB-INF/jsp/inc/topmenu.jsp" %>

  <div id="mainContent">
    <h1>월간결산관리</h1>
 
		<div class="content" id="contentArea">
			<!-- 각 화면 내용 들어갈 부분 --> 
			<div id="tabs">
				<ul>
					<li><a href="#tabs-1" id="tabs-1-link">결산목록</a></li>
					<li><a href="#tabs-2" id="tabs-2-link">결산상세</a></li>
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
								<td class="searchBody">년 <select style="width:100" id="cmbYear"></select> </td>
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
								    
			        <p id="list1_parent"></p>
					<table id="list3"><tr><td/></tr></table>
					<div id="pager3"></div>			    
			    </div>			
			
			    <div id="tabs-2">			
			
			
			
			
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
									<td class="searchBody">년월</td>
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
						<br>	
						
						<!-- form -->
						<form name="frm1" id="frm1" method="post" action="excel_month_sum.do">
						<input type="hidden" name="CAL_YM" id="CAL_YM">
			<!--			<input type="hidden" name="CAL_YMD" id="CAL_YMD">-->
						</form>			
						<table border="0" cellpadding=0 cellspacing=0  width="100%">
							<colgroup>
								<col width="25%"/>
								<col width="25%"/>
								<col width="25%"/>
								<col width="25%"/>
							</colgroup>
							<thead>
								<tr>
									<td height="1" class="boardHline" colspan="4"></td>
								</tr>
							</thead>
							<tbody>
							<tr height="26">
								<td class="boardHead">전달이월금+금월수입계</td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="prevThisSum" id="prevThisSum" readonly>
								</td>
								<td class="boardHead">금월지출계</td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="thisOut1" id="thisOut1" readonly>
								</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">금월마감잔액</td>
								<td class="boardBody" colspan="3">
									<input type="text" style="width:100%;" name="thisEnd" id="thisEnd" readonly>
								</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>	
							</tr>
							<tr height="26">
								<td class="boardHead" colspan="2">수입부</td>
								<td class="boardHead" colspan="2">지출부</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>							
							<tr height="26">
								<td class="boardHead">금월계</td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="thisIn" id="thisIn" readonly>
								</td>
								<td class="boardHead">금월계</td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="thisOut2" id="thisOut2" readonly>
								</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">전달이월계</td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="prevEnd" id="prevEnd" readonly>
								</td>
								<td class="boardHead"> </td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="nextEnd" id="nextEnd" readonly>
								</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>
							<tr height="26">
								<td class="boardHead">합계</td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="thisInSum" id="thisInSum" readonly>
								</td>
								<td class="boardHead">합계</td>
								<td class="boardBody">
									<input type="text" style="width:100%;" name="thisOutSum" id="thisOutSum" readonly>
								</td>
							</tr>
							<tr>
								<td height=1 class="boardSline" colspan="4"></td>
							</tr>				
							</tbody>
						</table>
						<br>			
						
						<!-- 그리드 박스 -->
						<table border="0" cellpadding=0 cellspacing=0  width="100%">
							<colgroup>
								<col width="25%"/>
								<col width="25%"/>
								<col width="25%"/>
								<col width="25%"/>
							</colgroup>
							<tr>
								<td>
									<table id="list1"><tr><td/></tr></table>
								</td>
								<td>
									<table id="list2"><tr><td/></tr></table>
								</td>
							</tr>
						</table>
										
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
									<td class="searchBody"> <a href="#"><img src="${pageContext.request.contextPath}/img/excel.gif" id="excel" title="주간결산" style="display:none"></a> </td>
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

