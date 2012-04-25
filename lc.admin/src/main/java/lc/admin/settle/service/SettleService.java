package lc.admin.settle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;

public interface SettleService {
	
	//******************************************************************
	//주간결산목록조회
	public GridOutVO listWeekSum(GridInVO giVO) throws Exception;
	//주간결산 합계조회
	public Map getWeekSum(HashMap map) throws Exception;
	//주간결산 수입내역조회
	public GridOutVO listIn(GridInVO giVO) throws Exception;
	public List<Map> listInExcel(HashMap map) throws Exception;
	//주간결산 지출내역조회
	public GridOutVO listOut(GridInVO giVO) throws Exception;	
	public List<Map> listOutExcel(HashMap map) throws Exception;
	
	//주간결산저장
	public Map saveWeekSum(HashMap map) throws Exception;
	//마감처리
	public Map saveWeekEndYn(HashMap map) throws Exception;
	//마감여부조회
	public String getEndYnForPeriod(HashMap map) throws Exception;
	//******************************************************************
	//월간결산목록조회
	public GridOutVO listMonSum(GridInVO giVO) throws Exception;
	//월간결산 합계조회
	public Map getMonthSum(HashMap map) throws Exception;
	//월간결산 수입내역조회
	public GridOutVO listInMonth(GridInVO giVO) throws Exception;
	public List<Map> listInMonthExcel(HashMap map) throws Exception;
	//월간결산 지출내역조회
	public GridOutVO listOutMonth(GridInVO giVO) throws Exception;	
	public List<Map> listOutMonthExcel(HashMap map) throws Exception;
	
	//월간결산저장
	public Map saveMonthSum(HashMap map) throws Exception;
	//월마감처리
	public Map saveMonthEndYn(HashMap map) throws Exception;
	//******************************************************************
}
