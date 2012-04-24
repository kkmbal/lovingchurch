package lc.admin.settle.service;

import java.util.List;
import java.util.Map;

public interface SettleMapper {
	
	//주간결산 합계조회
	public Map getInoutSum(Map map) throws Exception;
	//주간결산 수입내역조회
	public List<Map> listIn(Map map) throws Exception;
	//주간결산 지출내역조회
	public List<Map> listOut(Map map) throws Exception;
	//전주이월금
	public Map getNextAmt(Map map) throws Exception;
	//주간결산조회
	public Map getWeekSum(Map map) throws Exception;
	//주간결산상세조회(입금)
	public List<Map> listInWeekSumDetl(Map map) throws Exception;
	//주간결산상세조회(출금)
	public List<Map> listOutWeekSumDetl(Map map) throws Exception;
	
	public void insertWeekSum(Map map)  throws Exception;
	public void updateEndYn(Map map)  throws Exception;
	public void deleteWeekSum(Map map)  throws Exception;
	public void insertWeekSumDetl(Map map)  throws Exception;
	public void deleteWeekSumDetl(Map map)  throws Exception;
	

	
}
