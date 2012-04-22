<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>  
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
		$(document).ready(function () {

			  $("#list1").jqGrid({
				    url:"systemProjectService.listGroup.json",
				    colNames:['그룹코드','그룹코드명','정렬순서','기타값1','기타값2','사용여부','CD_VAL'],
				    colModel :[ 
				      {name:'CD_VAL', index:'CD_VAL', width:100, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'CD_NM', index:'CD_NM', width:100, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'ORD_NO', index:'ORD_NO', width:100, align:'left', editable:true, editrules:{required:true}},
				      {name:'ETC1_VAL', index:'ETC1_VAL', width:100, align:'center', editable:true},
				      {name:'ETC2_VAL', index:'ETC2_VAL', width:100, align:'center', editable:true},
				      {name:'USE_YN', index:'USE_YN', width:60, editable:true},
				      {name:'HICD_VAL', index:'HICD_VAL', width:60, editable:false}
				    ],
				    pager: '#pager1',
				    caption: '그룹코드정보',
				    height:240,
				    onCellSelect: function(rowid, iCol, cellcontent, e) {
						if(rowid != null) {
							var ret = $(this).jqGrid('getRowData', rowid);
							if(ret.H_PROJ_KEY != "" && ret.H_CD != ""){
								var search_data = {};
								search_data.PROJ_KEY = ret.H_PROJ_KEY;
								search_data.GRP_CD = ret.H_CD;
								$("#list2").fnSelGrid("systemProjectService.listDetailCode.json", search_data);
							}
						}
			  		},
				    beforeRequest : function(){
				        //초기값 필요할때.
				  		jQuery(this).jqGrid('setGridParam',{postData:{ userdata:{GRP_CD:"GRP_CD"} }} );
			  		}
			 });			 

			  
			  $("#list2").jqGrid({
				    url:"systemProjectService.listDetailCode.json",
				    colNames:['그룹코드','코드','코드명','정렬순서','기타값1','기타값2','사용여부'],
				    colModel :[ 
				      {name:'HICD_VAL', index:'HICD_VAL', width:100, editable:false},
				      {name:'CD_VAL', index:'CD_VAL', width:100, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'CD_NM', index:'CD_NM', width:100, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'ORD_NO', index:'ORD_NO', width:100, align:'left', editable:true, editrules:{required:true}},
				      {name:'ETC1_VAL', index:'ETC1_VAL', width:100, align:'center', editable:true},
				      {name:'ETC2_VAL', index:'ETC2_VAL', width:100, align:'center', editable:true},
				      {name:'USE_YN', index:'USE_YN', width:60, editable:true}
				    ],
				    caption: '상세코드정보',
				    height:240
			 });	

			//Grid 검색
			$("#search").click(function(){
				// 1. 특정 검색어 사용시
				var search_data = {};
				search_data.PROJ_KEY = $("#cmbPjt").val();
				$("#list1").fnSelGrid("systemProjectService.listGroup.json", search_data);

				$("#list2").jqGrid('clearGridData');
				return false;
			});
			  
			//Grid 행추가
			$("#add1").click( function() {
				var initdata = {};
				initdata.PROJ_KEY = $("#cmbPjt").val();
				$("#list1").fnAddGrid(initdata);
				return false;
			});	

			//Grid 삭제
			$("#delete1").click( function() {
				var search_data = {};
				search_data.PROJ_KEY = $("#cmbPjt").val();
				$("#list1").fnDelGrid("systemProjectService.deleteGroupCode.json", search_data);
				return false;
			});

			//Grid 저장(insert+update)
			$("#save1").click( function() {
				var search_data = {};
				search_data.PROJ_KEY = $("#cmbPjt").val();
				$("#list1").fnSaveGrid("systemProjectService.saveGroupCode.json", search_data);
				return false;
			});	

			//Grid 행추가
			$("#add2").click( function() {
				var id = $("#list1").jqGrid('getGridParam','selrow');
				if (id)	{
					var ret = $("#list1").jqGrid('getRowData',id);
					if(ret.H_PROJ_KEY != "" && ret.H_CD != ""){
						var initdata = {};
						initdata.PROJ_KEY = ret.H_PROJ_KEY;
						initdata.GRP_CD = ret.H_CD;
						$("#list2").fnAddGrid(initdata);
					}
				}
				return false;
			});	

			//Grid 삭제
			$("#delete2").click( function() {
				var search_data = {};
				var id = $("#list1").jqGrid('getGridParam','selrow');
				if (id)	{
					var ret = $("#list1").jqGrid('getRowData',id);
					if(ret.H_PROJ_KEY != "" && ret.H_CD != ""){
						search_data.PROJ_KEY = ret.H_PROJ_KEY;
						search_data.GRP_CD = ret.H_CD;
					}
				}				
				$("#list2").fnDelGrid("systemProjectService.deleteDetailCode.json", search_data);
				return false;
			});

			//Grid 저장(insert+update)
			$("#save2").click( function() {
				var search_data = {};
				var id = $("#list1").jqGrid('getGridParam','selrow');
				if (id)	{
					var ret = $("#list1").jqGrid('getRowData',id);
					if(ret.H_PROJ_KEY != "" && ret.H_CD != ""){
						search_data.PROJ_KEY = ret.H_PROJ_KEY;
						search_data.GRP_CD = ret.H_CD;
					}
				}
				
				$("#list2").fnSaveGrid("systemProjectService.saveDetailCode.json", search_data);
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
		<li class="title">코드 관리</li>
		<li class="directory"><img src="${pageContext.request.contextPath}/img/etc/icn_home.gif">	>	코드관리</li>
	</ul>
</div>
<!--title-->
	
<!--################ contentArea ##################-->
	<div class="content" id="contentArea">
			
			<!-- 각 화면 내용 들어갈 부분 --> 
			<form name="frm" id="frm">
			<table class="form-layout"  border="0" cellspacing="0" cellpadding="0">
				<colgroup>
					<col width="*"/>
					<col width="100"/>
				</colgroup>
				<thead>
					<tr>
						<td class="searchHline" colspan="2"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="searchBody"> </td>
						<td class="searchBody" align="center"><button id="search">검색</button></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td class="searchHline" colspan="2"></td>
					</tr>
				</tfoot>
			</table>
			</form>
			<p></p>		<br>	
			
			
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
			
			<br>			
			
			
			<!-- 그리드 박스 -->
			<table id="list2"><tr><td/></tr></table>
			
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
			
			<!-- 각 화면 내용 들어갈 부분 --> 
								
		    
	</div>
	<!--################ contentArea ##################-->
	
</div>	
<footer>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</footer>	

</body>
</html>
