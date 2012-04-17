<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>  
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
		$(document).ready(function () {
			//프로젝트
			fnMakeSelect("systemProjectService.listProjectByName.json", null, "#cmbPjt", "", null, false, "1");

			  $("#list1").jqGrid({
				    url:"systemProjectService.listGroup.json",
				    colNames:['프로젝트', '그룹코드','그룹코드명','그룹코드설명','상위코드','정렬순서','COMN_CD_KEY','h_proj_key','h_cd',   'GRP_CD', 'ETC_USE_TEXT','ETC_USE_TEXT2','ETC_USE_TEXT3','ETC_USE_TEXT4','USE_YN'],
				    colModel :[ 
				      {name:'PROJ_KEY', index:'PROJ_KEY', width:150, editable:true ,edittype: "select", editoptions: {value: fnGetGridSelect('systemProjectService.listProjectByName.json') }, formatter:'select', editrules:{required:true}},
				      {name:'CD', index:'CD', width:150, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'CD_NM', index:'CD_NM', width:150, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'CD_DESCR', index:'CD_DESCR', width:150, align:'left', editable:true, editrules:{required:true}},
				      {name:'HIRNK_COMN_CD_KEY', index:'HIRNK_COMN_CD_KEY', width:60, align:'center', editable:true},
				      {name:'SORT_SEQ', index:'SORT_SEQ', width:60, align:'center', editable:true, editrules:{required:true}},
				      {name:'COMN_CD_KEY', index:'COMN_CD_KEY', width:60, hidden:true},
				      {name:'H_PROJ_KEY', index:'H_PROJ_KEY', width:60, hidden:true},
				      {name:'H_CD', index:'H_CD', width:60, hidden:true},
				      {name:'GRP_CD', index:'GRP_CD', width:60, hidden:true},
				      {name:'ETC_USE_TEXT', index:'ETC_USE_TEXT', width:60, hidden:true},
				      {name:'ETC_USE_TEXT2', index:'ETC_USE_TEXT2', width:60, hidden:true},
				      {name:'ETC_USE_TEXT3', index:'ETC_USE_TEXT3', width:60, hidden:true},
				      {name:'ETC_USE_TEXT4', index:'ETC_USE_TEXT4', width:60, hidden:true},
				      {name:'USE_YN', index:'USE_YN', width:60, hidden:true}
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
				    colNames:['그룹코드', '상세코드','상세코드명','코드설명','상위코드','정렬순서','기타사용문자1','기타사용문자2','기타사용문자3','기타사용문자4',  'COMN_CD_KEY','PROJ_KEY','USE_YN'],
				    colModel :[ 
				      {name:'GRP_CD', index:'GRP_CD', width:90, align:'left', editable:false},
				      {name:'CD', index:'CD', width:80, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'CD_NM', index:'CD_NM', width:80, align:'left', editable:true, editrules:{required:true}}, 
				      {name:'CD_DESCR', index:'CD_DESCR', width:80, align:'left', editable:true, editrules:{required:true}},
				      {name:'HIRNK_COMN_CD_KEY', index:'HIRNK_COMN_CD_KEY', width:80, align:'center', editable:true},
				      {name:'SORT_SEQ', index:'SORT_SEQ', width:80, align:'center', editable:true, editrules:{required:true}},
				      {name:'ETC_USE_TEXT', index:'ETC_USE_TEXT', width:80, align:'left', editable:true},
				      {name:'ETC_USE_TEXT2', index:'ETC_USE_TEXT2', width:80, align:'left', editable:true},
				      {name:'ETC_USE_TEXT3', index:'ETC_USE_TEXT3', width:80, align:'left', editable:true},
				      {name:'ETC_USE_TEXT4', index:'ETC_USE_TEXT4', width:80, align:'left', editable:true},
				      {name:'COMN_CD_KEY', index:'COMN_CD_KEY', width:80, align:'left', hidden:true},
				      {name:'PROJ_KEY', index:'PROJ_KEY', width:80, align:'left', hidden:true},
				      {name:'USE_YN', index:'USE_YN', width:80, align:'left', hidden:true}
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
						<td class="searchBody">프로젝트 <select style="width:350px" id="cmbPjt"></select></td>
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
