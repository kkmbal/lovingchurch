<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<% try{ %>			
<head>
  <style type="text/css">
	/*textBox*/
	.textBox{border:1px solid #e1e1e1; font-size:12px; color:#8e8e8e; overflow:auto; padding:10px;  line-height:20px; word-wrap: break-word;}
	
  </style>	
</head>
<body>

<!--topNavigation-->
<%//@include file="/WEB-INF/jsp/inc/header.jsp" %>	
<!--//topNavigation-->

<!--body-->
<div class="bodyBackground">
	<div class="bodyArea">

		<!--################ contentArea ##################-->
			<div class="titleArea">
				<span>오류가 발생하였습니다.</span>
			</div>
				
			<div class="space15"></div>
			<ul class="description01">
				<li><s:actionerror/></li>
			</ul>			

			<div class="textBox" style="width:100%; height:60px;">
			<s:property value="%{exception}"/>
			</div>

			<div class="space30"></div>

			<div class="textBox" style="width:100%; height:60px;">
			<s:property value="%{exceptionStack}"/>
			</div>
			<div class="space5"></div>
			<div class="space30"></div>
			
			<table cellpadding="0" cellspacing="0" border="0" class="tbTypeA">
				<col width="*">
				<tbody>
				<tr class="rowEnd">
					<th valign="center"><a href="${pageContext.request.contextPath}">Home으로</a></th>
				</tr>
				</tbody>
			</table>
			

		</div>
		<!--################// contentArea ##################-->

	</div>
	<div class="spaceBottom"></div>
</div>
<!--body-->

<% }catch(Exception e){ %>
			<div class="titleArea">
				<span>오류가 발생하였습니다.</span>
			</div>	
			
			<div class="space30"></div>
			
			<table cellpadding="0" cellspacing="0" border="0" class="tbTypeA">
				<col width="*">
				<tbody>
				<tr class="rowEnd">
					<th valign="center"><a href="${pageContext.request.contextPath}">Home으로</a></th>
				</tr>
				</tbody>
			</table>			
<% } %> 

</body>
</html>
