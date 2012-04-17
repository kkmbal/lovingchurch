package lc.common.login.web.controller;

import java.io.Serializable;

import lc.common.login.domain.UserInfo;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Session Scope Bean
 * LC 로그인 사용자 정보 
 *
 */
@Component("lcSessionContext")
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS) 
public class LcSessionContext implements Serializable {

	private static final long serialVersionUID = 3469589899892488045L;

	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
