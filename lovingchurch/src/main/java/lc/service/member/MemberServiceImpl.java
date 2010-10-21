package lc.service.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.LcException;
import lc.common.LcService;

import org.springframework.stereotype.Service;

@Service("MemberService")
public class MemberServiceImpl extends LcService implements MemberService{
	
	public List<Map> getUsers(Map map) throws LcException{
		List<Map> list = null; 
		try{
			//log.debug(sqlMap);
			//log.debug(sqlMap.getSqlMapClient());
			//list = sqlMap.queryForList("selectUsers", null);
			//log.debug(list);
			ArrayList al = new ArrayList();
			
			HashMap hm = new HashMap();
			hm.put("USER_ID", "a");
			hm.put("USER_NM", "김1");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("USER_ID", "b");
			hm.put("USER_NM", "김2");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("USER_ID", "c");
			hm.put("USER_NM", "김3");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("USER_ID", "d");
			hm.put("USER_NM", "김4");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("USER_ID", "e");
			hm.put("USER_NM", "김5");
			al.add(hm);
			
			list = al;
		}catch(Exception e){
			log.error(e.toString(), e);
			throw new LcException(e);
		}
		return list;
	}

}
