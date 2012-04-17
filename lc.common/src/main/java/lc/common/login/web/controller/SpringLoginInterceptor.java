package lc.common.login.web.controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lc.common.login.domain.UserInfo;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class SpringLoginInterceptor extends HandlerInterceptorAdapter {
    private List<String> exceptLogin;
    private String basicName;
    
	public void setBasicName(String basicName) {
		this.basicName = basicName;
	}
	
	/**
     * 로그인 제외 페이지
     * @param exceptLogin
     */
    public void setExceptLogin(List<String> exceptLogin){
    	this.exceptLogin = exceptLogin;
    }
    
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	
    	try {
	    	UserInfo user = (UserInfo)WebUtils.getSessionAttribute(request, "userInfo");
	    	if(user == null) {
	    		user = new UserInfo();
	    	}
	        if (user == null) {
	        	//체크제외대상 url
	        	if(exceptLogin!= null && exceptLogin.toString().indexOf(request.getServletPath()) != -1){
	                return true;
	            } else {
	            	System.out.println("ng--------------");
	            	requestDespatcher(request, response);
	            	return false;
	            }
	        } else{
	        	System.out.println("ok--------------");
	        	request.setAttribute("userNm", user.getUSER_NM());
	            return true;
	        }
    	}catch(Exception e){
    		throw e;
    	}
    }

	private void requestDespatcher(HttpServletRequest request,	HttpServletResponse response) throws Exception{
    	RequestDispatcher rd = request.getRequestDispatcher(basicName);
    	if(WebUtils.isIncludeRequest(request) || response.isCommitted()){
    		rd.include(request, response);
    	}else{
    		rd.forward(request, response);
    	}
	}
}
