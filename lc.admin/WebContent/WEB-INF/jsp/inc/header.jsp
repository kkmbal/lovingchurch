<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/WEB-INF/tlds/kmbal.tld" prefix="k" %>
<%
	response.setHeader("Cache-Control","no-store");  
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader("Expires",0); 
	if (request.getProtocol().equals("HTTP/1.1")) 
	response.setHeader("Cache-Control", "no-cache");  
%>

<title>:::   :::</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>

    <%-- <link href="${pageContext.request.contextPath}/css/design.css" rel="stylesheet" type="text/css" charset="utf-8" /> --%>

	<!-- 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drifting_screen.css" />
	<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drifting_print.css" media="print" /> --%>	
	
	<!-- jquery ui themes -->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/smoothness/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/redmond/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/humanity/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/pepper-grinder/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/sunny/jquery-ui-1.8.18.custom.css" />-->
<!--	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/flick/jquery-ui-1.8.18.custom.css" />-->
	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/css/cupertino/jquery-ui-1.8.18.custom.css" />
	
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

	
	<!-- jquery plugin -->
	<script src="${pageContext.request.contextPath}/js/json2.js"></script> 
	<script src="${pageContext.request.contextPath}/js/form2js.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.blockUI.js"></script>
	
	<!-- LC 공통 js -->
	<script src="${pageContext.request.contextPath}/js/js_common.js"></script>	
	<script src="${pageContext.request.contextPath}/js/dateFormat.js"></script>	
	
<script type="text/javascript" >
//상단 1뎁스메뉴
function Topmenu(value){
	if(value=='0'){	
		disable1D();
	}
	else if(value=='1'){				
		if (document.getElementById("topmenu1")==null){	 Tmessage();	}		
		disable1D();
		document.getElementById("topmenu1").className="onOver";
		document.getElementById("subArea1").style.display="block";
	}
	else if(value=='2'){				
		if (document.getElementById("topmenu2")==null){	 Tmessage();	}		
		disable1D();
		document.getElementById("topmenu2").className="onOver";
		document.getElementById("subArea2").style.display="block";
	}
	else if(value=='3'){				
		if (document.getElementById("topmenu3")==null){	 Tmessage();	}		
		disable1D();
		document.getElementById("topmenu3").className="onOver";
		document.getElementById("subArea3").style.display="block";
	}	
	else{	 Tmessage();	}		
	function Tmessage(){alert("해당되는 1뎁스메뉴가 없습니다");	 return;}
}

function disable1D(){
	document.getElementById("subArea1").style.display="none";
	document.getElementById("subArea2").style.display="none";
	document.getElementById("subArea3").style.display="none";
	document.getElementById("topmenu1").className="onOut";
	document.getElementById("topmenu2").className="onOut";
	document.getElementById("topmenu3").className="onOut";
}

function Submenu(value){
	if(value=='0'){	
	}
	else if(value=='11'){				
		if (document.getElementById("submenu11")==null){	 Tmessage();	}		
		document.getElementById("submenu11").className="onSub";
	}
	else if(value=='12'){				
		if (document.getElementById("submenu12")==null){	 Tmessage();	}		
		document.getElementById("submenu12").className="onSub";
	}
	else if(value=='21'){				
		if (document.getElementById("submenu21")==null){	 Tmessage();	}		
		document.getElementById("submenu21").className="onSub";
	}
	else if(value=='22'){				
		if (document.getElementById("submenu22")==null){	 Tmessage();	}		
		document.getElementById("submenu22").className="onSub";
	}
	else if(value=='31'){				
		if (document.getElementById("submenu31")==null){	 Tmessage();	}		
		document.getElementById("submenu31").className="onSub";
	}
	else if(value=='32'){				
		if (document.getElementById("submenu32")==null){	 Tmessage();	}		
		document.getElementById("submenu32").className="onSub";
	}
	else if(value=='33'){				
		if (document.getElementById("submenu33")==null){	 Tmessage();	}		
		document.getElementById("submenu33").className="onSub";
	}
	else if(value=='34'){				
		if (document.getElementById("submenu34")==null){	 Tmessage();	}		
		document.getElementById("submenu34").className="onSub";
	}
	else if(value=='41'){				
		if (document.getElementById("submenu41")==null){	 Tmessage();	}		
		document.getElementById("submenu41").className="onSub";
	}
	else if(value=='42'){				
		if (document.getElementById("submenu42")==null){	 Tmessage();	}		
		document.getElementById("submenu42").className="onSub";
	}
	else if(value=='43'){				
		if (document.getElementById("submenu43")==null){	 Tmessage();	}		
		document.getElementById("submenu43").className="onSub";
	}
	else if(value=='44'){				
		if (document.getElementById("submenu44")==null){	 Tmessage();	}		
		document.getElementById("submenu44").className="onSub";
	}
	else if(value=='51'){				
		if (document.getElementById("submenu51")==null){	 Tmessage();	}		
		document.getElementById("submenu51").className="onSub";
	}
	else if(value=='52'){				
		if (document.getElementById("submenu52")==null){	 Tmessage();	}		
		document.getElementById("submenu52").className="onSub";
	}
	else if(value=='53'){				
		if (document.getElementById("submenu53")==null){	 Tmessage();	}		
		document.getElementById("submenu53").className="onSub";
	}

	else if(value=='71'){				
		if (document.getElementById("submenu71")==null){	 Tmessage();	}		
		document.getElementById("submenu71").className="onSub";
	}
	else if(value=='72'){				
		if (document.getElementById("submenu72")==null){	 Tmessage();	}		
		document.getElementById("submenu72").className="onSub";
	}
	else if(value=='73'){				
		if (document.getElementById("submenu73")==null){	 Tmessage();	}		
		document.getElementById("submenu73").className="onSub";
	}
	else if(value=='74'){				
		if (document.getElementById("submenu74")==null){	 Tmessage();	}		
		document.getElementById("submenu74").className="onSub";
	}
	else if(value=='75'){				
		if (document.getElementById("submenu75")==null){	 Tmessage();	}		
		document.getElementById("submenu75").className="onSub";
	}
	else if(value=='76'){				
		if (document.getElementById("submenu76")==null){	 Tmessage();	}		
		document.getElementById("submenu76").className="onSub";
	}
	else{	 Tmessage();	}		
	function Tmessage(){alert("해당되는 2뎁스메뉴가 없습니다");	 return;}
}
</script>
	
