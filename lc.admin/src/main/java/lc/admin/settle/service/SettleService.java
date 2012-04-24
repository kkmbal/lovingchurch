package lc.admin.settle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;

public interface SettleService {
	
	//주간결산 합계조회
	public Map getInoutSum(HashMap map) throws Exception;
	//주간결산 수입내역조회
	public GridOutVO listIn(GridInVO giVO) throws Exception;
	public List<Map> listIn(HashMap map) throws Exception;
	//주간결산 지출내역조회
	public GridOutVO listOut(GridInVO giVO) throws Exception;	
	public List<Map> listOut(HashMap map) throws Exception;
	
	//주간결산저장
	public Map saveWeekSum(HashMap map) throws Exception;
	//마감처리
	public Map saveEndYn(HashMap map) throws Exception;
	//마감여부조회
	public Map getEndYn(HashMap map) throws Exception;
}
