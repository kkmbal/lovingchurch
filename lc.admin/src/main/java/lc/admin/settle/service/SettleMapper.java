package lc.admin.settle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SettleMapper {
	
	//주간결산 목록조회
	public List<Map> listWeekSum(Map map) throws Exception;
	public int listWeekSumCount(Map map) throws Exception;
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
	public Map getEndYnForPeriod(HashMap map)   throws Exception;
	
	//******************************************************************
	//월간결산 목록조회
	public List<Map> listMonSum(Map map) throws Exception;
	public int listMonSumCount(Map map) throws Exception;
	//월간결산조회
	public Map getMonthSum(Map map) throws Exception;
	//월간결산 수입,지출내역조회
	public List<Map> listInOutMonth(Map map) throws Exception;
	//전달이월금
	public Map getNextAmtMonth(Map map) throws Exception;
	public Map getWeekSumLast(HashMap map) throws Exception;
	
	public void insertMonSum(Map map)  throws Exception;
	public void deleteMonSum(Map map)  throws Exception;
	public void updateMonthEndYn(Map map)  throws Exception;
	//******************************************************************
	//수입예산
	public List<Map> listInYear(HashMap map)  throws Exception;
	//지출예산
	public List<Map> listOutYear(HashMap map)  throws Exception;
	//예산결산
	public List<Map> listYearSum(HashMap map)  throws Exception;
	

	
}
