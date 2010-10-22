package lc.service.download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.LcException;
import lc.common.LcService;

import org.springframework.stereotype.Service;

@Service("DownloadService")
public class DownloadServiceImpl  extends LcService implements DownloadService{
	@SuppressWarnings("unchecked")
	public List<Map> getDownloadList(Map map) throws LcException{
		List<Map> list = null; 
		try{
			ArrayList al = new ArrayList();
			
			HashMap hm = new HashMap();
			hm.put("FORM_NM", "일일회계결산");
			hm.put("DOWN_NO", "1");
			al.add(hm);
			
			hm = new HashMap();
			hm.put("FORM_NM", "헌금명단");
			hm.put("DOWN_NO", "2");
			al.add(hm);

			list = al;
			
		}catch(Exception e){
			log.error(e.toString(), e);
			throw new LcException(e);
		}
		return list;
	}
}