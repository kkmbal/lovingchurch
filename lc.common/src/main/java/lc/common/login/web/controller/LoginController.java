package lc.common.login.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lc.common.login.domain.UserInfo;
import lc.common.login.service.LoginService;
import lc.common.util.SecurityUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@Resource(name="loginService")
	private LoginService loginService;
	
	@Resource(name="lcSessionContext")
	private LcSessionContext lcSessionContext;


	
    @RequestMapping("/login.do")
    public String login(HttpServletRequest request, 
    		            //@RequestParam("user_id") String user_id, 
    		            //@RequestParam("pwd") String pwd,
    		            ModelMap model) throws Exception {
    	
    	String user_id = request.getParameter("USER_ID");
		String pwd = request.getParameter("PASSWORD");
    	
		UserInfo user = new UserInfo();
    	user.setUSER_ID(user_id);
    	//user.setPASSWORD(SecurityUtil.encrypt(pwd));
    	user.setPASSWORD(pwd);
    	
    	UserInfo loginUser = loginService.login(user);
    	if(loginUser != null){	
    		request.getSession().setAttribute("userInfo", loginUser);
    		lcSessionContext.setUserInfo(user);

			return "redirect:/main.do";
    	}else{
    		model.addAttribute("message", "login_error");
    		return "redirect:/index.jsp";
    	}
    }
    
    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request) {
    	System.out.println(">>>>>>=logout");
        //세션삭제
    	request.getSession().removeAttribute("userInfo");
    	request.getSession().invalidate();
    	
    	return "redirect:/index.jsp";
    }
    
}
