<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/kmbal.tld" prefix="g" %>
<%
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader("Expires",0); 
	if (request.getProtocol().equals("HTTP/1.1")) 
	response.setHeader("Cache-Control", "no-cache");  
%>

<title>::: Loving Church :::</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>

    <link href="${pageContext.request.contextPath}/css/design.css" rel="stylesheet" type="text/css" charset="utf-8" />

	<!-- 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
	
	<!-- jquery ui themes -->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/smoothness/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/redmond/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/humanity/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/pepper-grinder/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/sunny/jquery-ui-1.8.18.custom.css" />-->
	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/flick/jquery-ui-1.8.18.custom.css" />
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/cupertino/jquery-ui-1.8.18.custom.css" />-->
	
	<!-- jqgrid CSS -->
	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery.jqGrid-4.3.1/css/ui.jqgrid.css" />
	
	<!-- jquery -->
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/js/jquery-1.7.1.js"></script>
	
	<!-- jquery ui -->
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/js/jquery-ui-1.8.18.custom.js"></script>
	
	<!-- jqGrid -->
	<script src="${pageContext.request.contextPath}/js/jquery.jqGrid-4.3.1/js/i18n/grid.locale-en.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.jqGrid-4.3.1/js/jquery.jqGrid.src.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.jqGrid-4.3.1/plugins/grid.postext.js" type="text/javascript"></script>

	<script src="${pageContext.request.contextPath}/js/jquery.treeview.js"></script>
	
	<!-- jquery plugin -->
	<script src="${pageContext.request.contextPath}/js/json2.js"></script> 
	<script src="${pageContext.request.contextPath}/js/form2js.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.blockUI.js"></script>
	
	<!-- fms 공통 js -->
	<script src="${pageContext.request.contextPath}/js_common.js"></script>	
	<script src="${pageContext.request.contextPath}/js/dateFormat.js"></script>	
	
