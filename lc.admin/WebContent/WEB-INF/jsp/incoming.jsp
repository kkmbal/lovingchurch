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
			    url:"systemProjectService.listDetailCode.json",
			    colNames:['성명', '주일헌금','십일조','감사헌금','선교헌금','건축헌금','생일헌금','부활절감사헌금','구제헌금'],
			    colModel :[ 
			      {name:'GRP_CD', index:'GRP_CD', width:90, align:'left', editable:false},
			      {name:'CD', index:'CD', width:80, align:'left', editable:true, editrules:{required:true}}, 
			      {name:'CD_NM', index:'CD_NM', width:80, align:'left', editable:true, editrules:{required:true}}, 
			      {name:'CD_DESCR', index:'CD_DESCR', width:80, align:'left', editable:true, editrules:{required:true}},
			      {name:'HIRNK_COMN_CD_KEY', index:'HIRNK_COMN_CD_KEY', width:80, align:'center', editable:true},
			      {name:'SORT_SEQ', index:'SORT_SEQ', width:80, align:'center', editable:true, editrules:{required:true}},
			      {name:'SORT_SEQ', index:'SORT_SEQ', width:80, align:'center', editable:true, editrules:{required:true}},
			      {name:'SORT_SEQ', index:'SORT_SEQ', width:80, align:'center', editable:true, editrules:{required:true}},
			      {name:'SORT_SEQ', index:'SORT_SEQ', width:80, align:'center', editable:true, editrules:{required:true}}
			    ],
			    pager: '#pager1',
			    caption: '헌금관리',
			    height:400
			});	
			 
			 $("#list2").jqGrid({
				    url:"systemProjectService.listDetailCode.json",
				    colNames:['수입항목', '금액', '비고'],
				    colModel :[ 
				      {name:'GRP_CD', index:'GRP_CD', width:90, align:'left', editable:false},
				      {name:'CD', index:'CD', width:80, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'CD_NM', index:'CD_NM', width:80, align:'left', editable:true, editrules:{required:true}} 
				    ],
				    pager: '#pager2',
				    caption: '기타수입관리',
				    height:400
			 });
			 
			 $("#list2").jqGrid('setGridWidth', $("#list2_parent").innerWidth()-2);
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
								<td class="searchBody"> </td>
								<td class="searchBody" align="center">&nbsp;</td>
								<td class="searchBody" align="right"><button id="add1">행추가</button> <button id="delete1">행삭제</button> <button id="save1">저장</button></td>
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
