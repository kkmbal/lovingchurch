<%@ page isErrorPage="true"%>
<%@page contentType="text/html;charset=utf-8"%>
<% response.setStatus(HttpServletResponse.SC_OK); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko"> 
<head>
  <style type="text/css">
 
	/*titleArea*/
	.titleArea {width:100%; height:71px; padding-top:20px; background:url("img/bgTop.gif") repeat-x 0 0;}
		  
	/*textBox*/
	.textBox{border:1px solid #e1e1e1; font-size:12px; color:#8e8e8e; overflow:auto; padding:10px;  line-height:20px; word-wrap: break-word;}
	.popText{width:100%; border-bottom:1px solid #d0d8da;}
		.popText td{height:115px; padding:10px 0 10px 0; color:#658ead; text-align:center; font-weight:bold;}
		
  </style>
</head>
<body class="noScroll">  

<div class="titleArea"> 
</div>

<table class="popText">
	<tr>
		<td>
			페이지가 존재하지 않습니다.	
		</td>
	</tr>
</table>

</body>
</html>