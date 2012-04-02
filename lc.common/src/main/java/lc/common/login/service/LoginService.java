package lc.common.login.service;

import lc.common.login.domain.UserInfo;

public interface LoginService {
	public UserInfo login(UserInfo userInfo) throws Exception;
}
