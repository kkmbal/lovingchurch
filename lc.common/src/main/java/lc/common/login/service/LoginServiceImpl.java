package lc.common.login.service;

import lc.common.login.domain.UserInfo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired(required=true)
	private LoginMapper loginMapper;
	
	public UserInfo login(UserInfo userInfo) throws Exception{
		return loginMapper.login(userInfo);
	}
}
