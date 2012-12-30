<%@page contentType="text/html;charset=utf-8"%>
  <!-- HEADER: controls the header layout, images, title and subTitle. -->
  <div id="headerBox">
    <div id="headerLeftBox">
      <span class="title">
        	사랑하는 교회
      </span>
      <br />
      <span class="subTitle">
        	관리자
      </span>
    </div>
    <div id="headerRightBox">
      &nbsp;
    </div>
  </div>

  <div id="menuBox">
    <h1>
      교인관리
    </h1>
    <div class="menuGroup">
      <a href="<c:url value='main.do'/>"><img src="${pageContext.request.contextPath}/images/menu_icon.gif" /> 교인</a><span class="noDisplay"> | </span>
    </div>

    <h1>
      수입/지출관리
    </h1>
    <div class="menuGroup">
      <a href="<c:url value='incoming.do'/>"><img src="${pageContext.request.contextPath}/images/menu_icon.gif"  /> 수입</a><span class="noDisplay"> | </span>
      <a href="<c:url value='outgoing.do'/>" title="vector and design work"><img src="${pageContext.request.contextPath}/images/menu_icon.gif"  /> 지출</a><span class="noDisplay"> | </span>
    </div>

    <h1>
      결산관리
    </h1>
    <div class="menuGroup">
      <a href="<c:url value='settleWeek.do'/>"><img src="${pageContext.request.contextPath}/images/menu_icon.gif"  /> 주간</a><span class="noDisplay"> | </span>
      <a href="<c:url value='settleMonth.do'/>"><img src="${pageContext.request.contextPath}/images/menu_icon.gif"  /> 월간</a><span class="noDisplay"> | </span>
      <a href="<c:url value='settleYear.do'/>"><img src="${pageContext.request.contextPath}/images/menu_icon.gif"  /> 년간</a><span class="noDisplay"> | </span>
    </div>
    	
    <h1>
      환경설정
    </h1>
    <div class="menuGroup">
      <a href="<c:url value='code.do'/>"><img src="${pageContext.request.contextPath}/images/menu_icon.gif"  /> 코드관리</a><span class="noDisplay"> | </span>
      <a href="<c:url value='logout.do'/>"><img src="${pageContext.request.contextPath}/images/menu_icon.gif"  /> 로그아웃</a><span class="noDisplay"> | </span>
    </div>

    <!-- MENU FOOTER: could include a site search field here -->
    <div class="menuFooter">
	  &nbsp;
    </div>

  </div>