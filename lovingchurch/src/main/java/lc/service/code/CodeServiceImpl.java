package lc.service.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.LcException;
import lc.common.LcService;

import org.springframework.stereotype.Service;

@Service("CodeService")
public class CodeServiceImpl extends LcService implements CodeService{
	
	public List<Map> getCodeList(Map map) throws LcException{
		List<Map> list = null; 
		try{
			//log.debug(lcDAO);
			//log.debug(lcDAO.getSqlMapClient());
			//list = sqlMap.queryForList("selectCodeList", null);
			//log.debug(list);
			ArrayList al = new ArrayList();
			
			HashMap hm = new HashMap();
			hm.put("CD_VAL", "001");
			hm.put("CD_NM", "주일헌금");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CD_VAL", "002");
			hm.put("CD_NM", "감사헌금");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CD_VAL", "003");
			hm.put("CD_NM", "선교헌금");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CD_VAL", "004");
			hm.put("CD_NM", "건축헌금");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CD_VAL", "005");
			hm.put("CD_NM", "십일조");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CD_VAL", "006");
			hm.put("CD_NM", "추수감사헌금");
			al.add(hm);
			
			list = al;
		}catch(Exception e){
			log.error(e.toString(), e);
			throw new LcException(e);
		}
		return list;
	}

}
