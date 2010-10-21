package lc.service.donation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.LcException;
import lc.common.LcService;

import org.springframework.stereotype.Service;

@Service("DonationService")
public class DonationServiceImpl extends LcService implements DonationService{
	
	@SuppressWarnings("unchecked")
	public List<Map> getDonationList(Map map) throws LcException{
		List<Map> list = null; 
		try{
//			log.debug(lcDAO);
//			log.debug(lcDAO.getSqlMapClient());
//			list = lcDAO.getSqlMapClient().queryForList("selectDonationList", null);
//			log.debug(list);
			//log.debug(sqlMap.queryForList("selectDonationList", null));
			ArrayList al = new ArrayList();
			
			HashMap hm = new HashMap();
			hm.put("CAL_YMD", "201020"); //계산일자 pk
			hm.put("SEQ_NO", "1"); //일련번호  pk
			hm.put("USER_ID", "a"); //사용자아이디
			hm.put("INOUT_CD", "입금"); //입출구분코드
			hm.put("INOUT_DETL_CD", "001"); //입출항목코드
			hm.put("DO_AMT", "10000"); //금액
			hm.put("CONTENT", ""); //내용
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CAL_YMD", "201020"); //계산일자 pk
			hm.put("SEQ_NO", "2"); //일련번호  pk
			hm.put("USER_ID", "b"); //사용자아이디
			hm.put("INOUT_CD", "입금"); //입출구분코드
			hm.put("INOUT_DETL_CD", "001"); //입출항목코드
			hm.put("DO_AMT", "20000"); //금액
			hm.put("CONTENT", ""); //내용
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CAL_YMD", "201020"); //계산일자 pk
			hm.put("SEQ_NO", "3"); //일련번호  pk
			hm.put("USER_ID", "c"); //사용자아이디
			hm.put("INOUT_CD", "입금"); //입출구분코드
			hm.put("INOUT_DETL_CD", "001"); //입출항목코드
			hm.put("DO_AMT", "15000"); //금액
			hm.put("CONTENT", ""); //내용
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CAL_YMD", "201020"); //계산일자 pk
			hm.put("SEQ_NO", "4"); //일련번호  pk
			hm.put("USER_ID", "d"); //사용자아이디
			hm.put("INOUT_CD", "입금"); //입출구분코드
			hm.put("INOUT_DETL_CD", "002"); //입출항목코드
			hm.put("DO_AMT", "30000"); //금액
			hm.put("CONTENT", ""); //내용
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CAL_YMD", "201020"); //계산일자 pk
			hm.put("SEQ_NO", "5"); //일련번호  pk
			hm.put("USER_ID", "e"); //사용자아이디
			hm.put("INOUT_CD", "입금"); //입출구분코드
			hm.put("INOUT_DETL_CD", "003"); //입출항목코드
			hm.put("DO_AMT", "50000"); //금액
			hm.put("CONTENT", ""); //내용
			al.add(hm);
			
			hm = new HashMap();
			hm.put("CAL_YMD", "201020"); //계산일자 pk
			hm.put("SEQ_NO", "6"); //일련번호  pk
			hm.put("USER_ID", "e"); //사용자아이디
			hm.put("INOUT_CD", "입금"); //입출구분코드
			hm.put("INOUT_DETL_CD", "004"); //입출항목코드
			hm.put("DO_AMT", "55000"); //금액
			hm.put("CONTENT", ""); //내용
			al.add(hm);
			
			list = al;
			
		}catch(Exception e){
			log.error(e.toString(), e);
			throw new LcException(e);
		}
		return list;
	}

}
