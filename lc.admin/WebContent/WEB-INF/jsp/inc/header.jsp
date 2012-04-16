<%@page contentType="text/html;charset=utf-8"%>
<div class="globalArea" id="globalArea">
	<ul>
		<li class="logo"><a href="<s:url action='client01_list_client'/>"><img src="${pageContext.request.contextPath}/img/logo02.gif"></a> <b>mVoIPCenter Management Site</b><li>
		<li>
			<a href="<s:url action='logoutLoginMng'/>">로그아웃</a> │ <!-- <a href="#">사이트맵</a> │ --><a href="http://www.mvoipcenter.com" target="_blank">서비스 사이트</a> │ <a href="http://cp.mvoipcenter.com" target="_blank">가맹점 사이트</a>
		</li>
	</ul>
</div>
<div class="menuArea">
	<ul>
		<li><a href="#" id="topmenu0" onmouseover="Topmenu(0)">HOME</a></li>
		<li><a href="<s:url action='client01_list_client'/>" id="topmenu1" onmouseover="Topmenu(1)">Client</a></li>
		<li><a href="<s:url action='application01_list_application'/>" id="topmenu2" onmouseover="Topmenu(2)">Application</a></li>
		<li><a href="<s:url action='service01_list_service'/>" id="topmenu3" onmouseover="Topmenu(3)">Service</a></li>
		<li><a href="<s:url action='charge01'/>" id="topmenu4" onmouseover="Topmenu(4)">Charge</a></li>
		<li><a href="<s:url action='payment05_charge'/>" id="topmenu5" onmouseover="Topmenu(5)">Payment</a></li>
		<li><a href="#" id="topmenu6" onmouseover="Topmenu(6)">Report</a></li>
		<li><a href="<s:url action='noticeList'/>" id="topmenu7" onmouseover="Topmenu(7)">C/S</a></li>
		<li class="end"><img src="${pageContext.request.contextPath}/img/menuEnd.gif"></li>
	</ul>
</div>
<div class="subArea">
	<ul id="subArea1" class="submenu">
		<li class="first" style="width:72px;"></li>
		<li><a href="<s:url action='client01_list_client'/>" id="submenu11">고객관리</a></li>
		<li class="end"><a href="<s:url action='client02_list_client'/>" id="submenu12">고객현황</a></li>
	</ul>
	<ul id="subArea2" class="submenu">
		<li class="first" style="width:195px;"></li>
		<li><a href="<s:url action='application01_list_application'/>" id="submenu21">신청 정보 관리</a></li>
		<li class="end"><a href="<s:url action='application02_list_application'/>" id="submenu22">신청 현황 관리</a></li>
	</ul>
	<ul id="subArea3" class="submenu">
		<li class="first" style="width:205px;"></li>
		<li><a href="<s:url action='service01_list_service'/>" id="submenu31">서비스 현황 관리</a></li>
		<li><a href="<s:url action='service02_list_service'/>" id="submenu32">한도 변경 내역</a></li>
		<li><a href="<s:url action='service03_list_service'/>" id="submenu33">통화 내역 조회</a></li>
		<li class="end"><a href="<s:url action='service04_list_service'/>" id="submenu34">앱콜 유저 조회</a></li>
	</ul>
	<ul id="subArea4" class="submenu">
		<li class="first" style="width:330px;"></li>
		<li><a href="<s:url action='charge01_charge'/>" id="submenu41">충전요금관리</a></li>
		<li><a href="<s:url action='charge02_charge'/>" id="submenu42">충전요금현황</a></li>
		<li><a href="<s:url action='charge05_charge'/>" id="submenu43">청구요금관리</a></li>
		<li class="end"><a href="<s:url action='charge06_charge'/>" id="submenu44">청구요금현황</a></li>
	</ul>
	<ul id="subArea5" class="submenu">
		<li class="first" style="width:560px;"></li>
		<li><a href="<s:url action='payment05_charge'/>" id="submenu53">대기 내역 관리</a></li>
		<li><a href="<s:url action='payment01_charge'/>" id="submenu51">결제 내역 관리</a></li>
		<li class="end"><a href="<s:url action='payment02_charge'/>" id="submenu52">결제 내역 현황</a></li>
	</ul>
	<ul id="subArea6" class="submenu">
		<li class="first" style="width:100px;"></li>
	</ul>
	<ul id="subArea7" class="submenu">
		<li class="first" style="width:320px;"></li>
		<li><a href="<s:url action='noticeList'/>" id="submenu71">공지 사항 관리</a></li>
		<li><a href="<s:url action='dataList'/>" id="submenu72">자료실 관리</a></li>
		<li><a href="<s:url action='faqList'/>" id="submenu73">자주묻는 질문 관리</a></li>
		<li><a href="<s:url action='qnaList'/>" id="submenu74">온라인 문의 관리</a></li>
		<li><a href="<s:url action='testList'/>" id="submenu75">시험 신청 관리</a></li>
		<li class="end"><a href="<s:url action='submit01List'/>" id="submenu76">장애 신고 관리</a></li>
	</ul>
</div>