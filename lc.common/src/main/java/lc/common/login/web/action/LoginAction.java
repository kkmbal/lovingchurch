package lc.common.login.web.action;

import java.util.Map;

import javax.annotation.Resource;

import lc.common.login.domain.UserInfo;
import lc.common.login.service.LoginService;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Scope("prototype")
@Service("loginAction")
public class LoginAction extends ActionSupport implements ModelDriven<UserInfo>{
	private UserInfo userInfo;
	private String LOGIN_RESULT;
	
	@Resource(name="loginService")
	private LoginService loginService;
	
	public LoginAction(){
		userInfo = new UserInfo();
	}
	
	public String login() throws Exception{
		UserInfo resultLogin = loginService.login(userInfo);
		if(resultLogin == null){
			setLOGIN_RESULT("N");
			return "fail";
		}else{
			ActionContext.getContext().getSession().put("userInfo", resultLogin);
			return "success";
		}
	}
	
	public String logout() throws Exception{
		Map session = ActionContext.getContext().getSession();
		session.remove("userInfo");
		session.remove("LOGIN_RESULT");
		return "logout";
	}
	
	public String findPswd() throws Exception{
		return null;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getModel() {
		return userInfo;
	}

	public String getLOGIN_RESULT() {
		return LOGIN_RESULT;
	}

	public void setLOGIN_RESULT(String lOGINRESULT) {
		LOGIN_RESULT = lOGINRESULT;
	}
	
	
}
