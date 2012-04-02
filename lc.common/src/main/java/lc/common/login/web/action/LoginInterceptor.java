package lc.common.login.web.action;

import java.util.Map;

import lc.common.login.domain.UserInfo;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//System.out.println("★★★★★★★★★★★ LoginIntercentor");
		
		Map session = invocation.getInvocationContext().getSession();//.put("Greeting", "");
		UserInfo userInfo = (UserInfo)MapUtils.getObject(session, "userInfo", new UserInfo());
		String id = userInfo.getLOGIN_ID();
		
		//System.out.println("0●●●●●●●●●●●●●●●●----------------------->>>id:"+id);
		if(StringUtils.isEmpty(id)){
			//System.out.println("1●●●●●●●●●●●●●●●●----------------------->>>id:"+id);
			//throw new Exception("no.....");
			return "input";
		}

		return invocation.invoke();
	}

}
