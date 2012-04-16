<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<%@include file="/WEB-INF/jsp/inc/title.jsp" %>	
	<script type="text/javascript" >
		window.onload = function(){			 
			var oneDepth = 2; //1Depth Top menu		
			var twoDepth = 21; //2Depth Top menu		

			//menuInclude
			Topmenu(oneDepth);
			Submenu(twoDepth);
			document.getElementById("globalArea").onmouseover =  document.getElementById("bodyArea").onmouseover = function(){Topmenu(oneDepth);}
		}

		$(document).ready(function(){ 

		});	
		
		
	</script>
</head>
<body>

<!--topNavigation-->
<%@include file="/WEB-INF/jsp/inc/header.jsp" %>	
<!--//topNavigation-->

<!--#################################### Contents Area #########################################-->
<div class="bodyArea" id="bodyArea">
	<!--title-->
	<div class="titleArea">
		<ul>
			<li class="title">신청 정보 관리</li>
			<li class="directory"><img src="${pageContext.request.contextPath}/img/icn_home.gif">	>	Application > <b>신청 정보 관리</b></li>
		</ul>
	</div>
	<div class="space10"></div>
	<!--//title-->

	<!--검색조건-->
	<form id="frm" name="frm" method="post" action="<s:url action='application01_list_application'/>">	
	<table cellpadding="0" cellspacing="0" border="0" class="tbTypeA">
		<col width="90"><col width="*"><col width="90"><col width="160"><col width="220">
		<tr>
			<td><img src="${pageContext.request.contextPath}/img/bl03.gif"> 신청일시</td>
			<td>
				<script src="${pageContext.request.contextPath}/js/pop_calendar.js" language="JavaScript" type="text/javascript"></script><!-- 달력-->
				<input type="text" id="calendar_st" name="calendar_st" value="" title="YYYY.MM.DD"  style="width:75px;" readonly onclick="empty_input(this);">
				<a href="javascript:displayCalendarFor('calendar_st');"><img src="${pageContext.request.contextPath}/img/icn_cal.gif" alt="달력"></a>
				<select style="width:50px;" name="HOUR_FROM" id="HOUR_FROM">
					<option value="00">0시</option>
					<option value="01">1시</option>
					<option value="02">2시</option>
					<option value="03">3시</option>
					<option value="04">4시</option>
					<option value="05">5시</option>
					<option value="06">6시</option>
					<option value="07">7시</option>
					<option value="08">8시</option>
					<option value="09">9시</option>
					<option value="10">10시</option>
					<option value="11">11시</option>
					<option value="12">12시</option>
					<option value="13">13시</option>
					<option value="14">14시</option>
					<option value="15">15시</option>
					<option value="16">16시</option>
					<option value="17">17시</option>
					<option value="18">18시</option>
					<option value="19">19시</option>
					<option value="20">20시</option>
					<option value="21">21시</option>
					<option value="22">22시</option>
					<option value="23">23시</option>
				</select>				~
				<input type="text"  id="calendar_ed" name="calendar_ed"  value="" title="YYYY.MM.DD"  style="width:75px;" readonly onclick="empty_input(this);">
				<a href="javascript:displayCalendarFor('calendar_ed');"><img src="${pageContext.request.contextPath}/img/icn_cal.gif" alt="달력"></a>
				<select style="width:50px;" name="HOUR_TO" id="HOUR_TO">
					<option value="00">0시</option>
					<option value="01">1시</option>
					<option value="02">2시</option>
					<option value="03">3시</option>
					<option value="04">4시</option>
					<option value="05">5시</option>
					<option value="06">6시</option>
					<option value="07">7시</option>
					<option value="08">8시</option>
					<option value="09">9시</option>
					<option value="10">10시</option>
					<option value="11">11시</option>
					<option value="12">12시</option>
					<option value="13">13시</option>
					<option value="14">14시</option>
					<option value="15">15시</option>
					<option value="16">16시</option>
					<option value="17">17시</option>
					<option value="18">18시</option>
					<option value="19">19시</option>
					<option value="20">20시</option>
					<option value="21">21시</option>
					<option value="22">22시</option>
					<option value="23">23시</option>
				</select>

			</td>
			<td><img src="${pageContext.request.contextPath}/img/bl03.gif"> 신청구분</td>
			<td>
				<select style="width:130px;" name="REQ_DIV_CD" id="REQ_DIV_CD"><option value="">전체</option></select>
			</td>
			<td rowspan="2"><button class="btn01" id="search">검색</button></td>
		</tr>
		<tr>
			<td><img src="${pageContext.request.contextPath}/img/bl03.gif"> 검색구분</td>
			<td>
				<select style="width:130px;" name="APPL_SRCH_CD" id="APPL_SRCH_CD">
				<option value="">선택</option>
				<option value="01">가맹점ID</option>
				<option value="02">상호명</option>
				<option value="03">신청자</option>
				</select>
				<input type="text" style="width:225px;" name="APPL_SRCH_VAL" id="APPL_SRCH_VAL" maxlength="20">
			</td>
			<td><img src="${pageContext.request.contextPath}/img/bl03.gif"> 관리상태</td>
			<td>
				<select style="width:130px;" name="PROC_STAGE_CD" id="PROC_STAGE_CD"><option value="">전체</option></select>
			</td>
		</tr>
	</table>
	<!--//검색조건-->

	<!--리스트-->
	<div class="listHead">
		<span>검색결과 : 총 ${apply.ROWCOUNT}건</span>
		<span class="right">
			<select style="width:105px;" name="listSize" id="listSize"><option value="10" checked>10건씩 정렬</option><option value="20">20건씩 정렬</option></select>
		</span>
	</div>
	</form>
	
	<table cellpadding="0" cellspacing="0" border="0" class="tbTypeB">
		<col width="45"><col width="110"><col width="*"><col width="95"><col width="*">
		<col width="*"><col width="*"><col width="105"><col width="105"><col width="105">
		<col width="70">		
		<tr>
			<th>&nbsp;</th>
			<th>신청일시</th>
			<th>가맹점ID</th>
			<th>상호명</th>
			<th>신청자</th>
			<th>구분</th>
			<th>상품</th>
			<th>총 신청비용</th>
			<th><!-- 총 보증보험 -->보증보험금액<br>(보험료)</th>
			<th><!-- 총 보증금 -->담보보증금액</th>
			<th class="end">관리상태</th>
		</tr>
<s:iterator value="listApply">		
		<tr>
			<td class="dummy"><input type="checkbox" class="input_none" value="<s:property value="LOGIN_ID"/>" orderdt="<s:property value="ORDERDT"/>"></td>
			<td class="dummy1"><s:property value="ORDERDT_NM"/></td>
			<td class="dummy1"><s:property value="LOGIN_ID"/></td>
			<td class="dummy1"><s:property value="CUSTOMERNM"/></td>
			<td class="dummy1"><s:property value="REQ_NAME"/></td>
			<td class="dummy1"><s:property value="REQ_DIV_CD_NM"/></td>
			<td class="dummy1"><s:property value="APPLY_CNT"/>건</td>
			<td class="right numf dummy1"><s:property value="PRD_CD_NM"/>원</td>
			<td class="right dummy1"><span class="numf"><s:property value="INSU_AMT"/>원</span><s:if test="%{INSU_AMT_FEE != null && INSU_AMT_FEE != ''}"><br>(<span class="numf"><s:property value="INSU_AMT_FEE"/>원</span>)</s:if></td>
			<td class="right numf dummy1" ><s:property value="SPE_AMT"/>원</td>
			<td class="end dummy1 receipt_class"><s:property value="PROC_STAGE_CD_NM"/></td>
		</tr>
</s:iterator>		
	</table>			

	<div class="paging">
				  <g:paging name="${apply}" action="application01_list_application.do">
				  	<g:get property="firstPage"/>
				  	<g:get property="prevPageGroup"/>
				  	<g:get property="paging"/>
				  	<g:get property="nextPageGroup"/>
				  	<g:get property="lastPage"/>
				  </g:paging>
	</div>

	<div class="btnArea">
		<button class="btn02" id="search04">신청 정보 조회</button>
		<button class="btn02" id="email">안내 메일 발송</button>
		<button class="btn03" id="search03">신청 현황 관리</button>
		<button class="btn03" id="delete">신청 내역 삭제</button>
	</div>
	<!--//리스트-->

</div>
<!--####################################// Contents Area #########################################-->

<!--signature-->
<%@include file="/WEB-INF/jsp/inc/footer.jsp" %>	
<!--//signature-->

</body>
</html>