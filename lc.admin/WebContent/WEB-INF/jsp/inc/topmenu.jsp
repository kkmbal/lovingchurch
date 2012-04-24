<%@page contentType="text/html;charset=utf-8"%>
<div class="globalArea" id="globalArea">
	<ul>
		<li class="logo"> <li>
		<li>
			 │ <a href="<c:url value='logout.do'/>">로그아웃</a> │
		</li>
	</ul>
</div>
<div class="menuArea">
	<ul>
		<li><a href="<c:url value='main.do'/>" id="topmenu0">Member</a></li>
		<li><a href="<c:url value='incoming.do'/>" id="topmenu1" onmouseover="Topmenu(1)">Account</a></li>
		<li><a href="<c:url value='settleWeek.do'/>" id="topmenu2" onmouseover="Topmenu(2)">Settle</a></li>
		<li><a href="<c:url value='code.do'/>" id="topmenu3" onmouseover="Topmenu(3)">Management</a></li>
		<li class="end"><img src="${pageContext.request.contextPath}/img/etc/menuEnd.gif"></li>
	</ul>
</div>
<div class="subArea">
	<ul id="subArea1" class="submenu">
		<li class="first" style="width:72px;"></li>
		<li><a href="<c:url value='incoming.do'/>" id="submenu11">수입</a></li>
		<li class="end"><a href="<c:url value='outgoing.do'/>" id="submenu12">지출</a></li>
	</ul>
	<ul id="subArea2" class="submenu">
		<li class="first" style="width:195px;"></li>
		<li><a href="<c:url value='settleWeek.do'/>" id="submenu21">주간</a></li>
		<li><a href="<c:url value='settleMonth.do'/>" id="submenu21">월간</a></li>
<!--		<li class="end"><a href="<c:url value='settleYear.do'/>" id="submenu22">년간</a></li>-->
	</ul>
	<ul id="subArea3" class="submenu">
		<li class="first" style="width:345px;"></li>
		<li class="end"><a href="<c:url value='code.do'/>" id="submenu31">코드관리</a></li>
	</ul>	
</div>