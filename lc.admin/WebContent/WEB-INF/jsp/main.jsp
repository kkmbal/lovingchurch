<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>  
	<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
	<script type="text/javascript" >
		$(document).ready(function () {
			 $("#list").jqGrid({
			    url:"memberService.listMember.lc",
			    colNames:['아이디', '성명','비밀번호','생년월일','자택','HP','가입일자','직분','권한','출교','USER_KEY','DONA_NM'],
			    colModel :[ 
			      {name:'USER_ID', index:'USER_ID', width:90, align:'left', editable:false, editrules:{required:true}, hidden:true},
			      {name:'USER_NM', index:'USER_NM', width:80, align:'left', editable:true, editrules:{required:true}}, 
			      {name:'PASSWORD', index:'PASSWORD', width:80, align:'left', editable:false, editrules:{required:true}, hidden:true}, 
			      {name:'BIRTH_YMD', index:'BIRTH_YMD', width:80, align:'center', editable:true}, 
			      {name:'TEL_NO', index:'TEL_NO', width:80, align:'left', editable:true}, 
			      {name:'HP_NO', index:'HP_NO', width:80, align:'left', editable:true}, 
			      {name:'REG_YMD', index:'REG_YMD', width:80, align:'center', editable:false},
			      {name:'DUTY_CD', index:'DUTY_CD', width:80, align:'left', editable:true, edittype: "select", editoptions: {value: fnGetGridSelectComm('DUTY_CD') }, formatter:'select'},
			      {name:'AUTH_CD', index:'AUTH_CD', width:80, align:'left', editable:true, edittype: "select", editoptions: {value: fnGetGridSelectComm('AUTH_CD') }, formatter:'select'},
			      {name:'USE_YN', index:'USE_YN', width:80, align:'center', editable:true, hidden:true},
			      {name:'USER_KEY', index:'USER_KEY', width:80, align:'center', editable:false, hidden:true},
			      {name:'DONA_NM', index:'DONA_NM', width:80, align:'center', editable:false, hidden:true}
			    ], 
			    pager: '#pager',
			    caption: '교인관리',
			    sortname: 'USER_NM',
			    height:500,
				rowNum:20
			});	
			 
				//Grid 검색
				$("#search").click(function(){
					// 1. 특정 검색어 사용시
					var search_data = {};
					search_data.USER_NM = $("#userNm").val();
					$("#list").fnSelGrid("memberService.listMember.lc", search_data);

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
					search_data.USER_NM = $("#userNm").val();
					$("#list").fnDelGrid("memberService.deleteMember.lc", search_data);
					return false;
				});

				//Grid 저장(insert+update)
				$("#save").click( function() {
					var search_data = {};
					search_data.USER_NM = $("#userNm").val();
					$("#list").fnSaveGrid("memberService.saveMember.lc", search_data);
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
		<li class="title">교인 관리</li>
		<li class="directory"><img src="${pageContext.request.contextPath}/img/etc/icn_home.gif">	>	교인관리</li>
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
						<td class="searchBody">성명</td>
						<td class="searchBody"><input name="userNm" id="userNm"></td>
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
