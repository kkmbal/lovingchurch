package lc.service.code;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.LcDAO;
import lc.common.LcException;
import lc.common.LcService;

import org.springframework.stereotype.Service;

@Service("MemberService")
public class CodeServiceImpl extends LcService implements CodeService{
	
	public List<Map> getCodeList(Map map) throws LcException{
		List<Map> list = null; 
		try{
			log.debug(lcDAO);
			log.debug(lcDAO.getSqlMapClient());
			list = lcDAO.getSqlMapClient().queryForList("selectCodeList", null);
			log.debug(list);
		}catch(Exception e){
			log.error(e.toString(), e);
			throw new LcException(e);
		}
		return list;
	}

}
