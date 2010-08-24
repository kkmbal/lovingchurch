package lc.service.member;

import java.util.List;
import java.util.Map;

import lc.common.LcException;

public interface MemberService{
	public List<Map> getUsers(Map map) throws LcException;
}
