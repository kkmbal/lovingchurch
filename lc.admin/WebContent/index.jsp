<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<%@include file="/WEB-INF/jsp/inc/title.jsp" %>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.18.custom/js/jquery.cookie.js"></script>	
	<script type="text/javascript" >
		window.onload = function(){
			var id = $.cookie('m_mvoip_id');
			var chk = $.cookie('m_chk_mvoip_id');
			if(chk != null && chk == 'chk_true'){
				$("[id=saveCookie]").attr("checked","checked");
				if(id != null){
					$("#LOGIN_ID").val(id);
				}
			}
			
			if(id == null){
				$("#LOGIN_ID").focus();
			}
			else{
				$("#PASSWORD").focus();
			}
		}
		
		function go_submit(){
			if($("#LOGIN_ID").val() == ""){
				alert("ID를 입력하세요");
				return;
			}				
			if($("#PASSWORD").val() == ""){
				alert("비밀번호를 입력하세요");
				return;
			}	
			//------------------------------------
			if($("[id=saveCookie]").is(":checked")){
				$.cookie('m_mvoip_id', $('#LOGIN_ID').val());
				$.cookie('m_chk_mvoip_id', "chk_true");
			}			
			//------------------------------------	
			$('form').submit();			
		}
		
		$(document).ready(function(){
			$('#login_login').click(function() {
				go_submit();
				return false;
			});	

			$("#PASSWORD").keyup(function(e){
				if(e.keyCode == 13){
					go_submit();
					return false;
				}
			});
			
			if("N" == 's:property value="LOGIN_RESULT"'){
				alert("존재하지 않는 아이디 입니다.");
			}		
		});


	</script>	
</head>
<body>

<div class="loginTop">
	
</div>
<div class="loginMIddle">

<form name="frm" method="post" action="<c:url value='loginLoginMng'/>">
	<table class="loginArea" style="width:336px;" align="right">
		<tr><th><img src="${pageContext.request.contextPath}/img/etc/box_login01.gif"></th></tr>			
		<tr>
			<td height="50">
				<img src="${pageContext.request.contextPath}/img/etc/bl01.gif"> <img src="${pageContext.request.contextPath}/img/etc/text_login03.gif">
			</td>
		</tr>
		<tr>
			<td>
				<table class="loginbox" style="width:278px;">
					<col width="30"><col width="*"><col width="62">
					<tr>
						<td><img src="${pageContext.request.contextPath}/img/etc/text_login02.gif"></td>
						<td><input type="text" style="width:160px;" name="LOGIN_ID" id="LOGIN_ID" value="admin" tabindex="1"></td>
						<td><input type="checkbox" class="input_none" id="saveCookie"> ID저장</td>
					</tr>
					<tr>
						<td><img src="${pageContext.request.contextPath}/img/etc/text_login01.gif"></td>
						<td><input type="password" style="width:160px;" name="PASSWORD" id="PASSWORD" value="admin" tabindex="2"></td>
						<td><a href="#"><img src="${pageContext.request.contextPath}/img/etc/btn_login01.gif" alt="로그인" id="login_login"></a></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><th><img src="${pageContext.request.contextPath}/img/etc/box_login02.gif"></th></tr>			
	</table>
</form>

</div>
</body>
</html>