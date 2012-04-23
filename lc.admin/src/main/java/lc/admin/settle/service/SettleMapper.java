package lc.admin.settle.service;

import java.util.List;
import java.util.Map;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;

public interface SettleMapper {
	
	//주간결산 합계조회
	public Map getInoutSum(Map map) throws Exception;
	//주간결산 수입내역조회
	public List<Map> listIn(Map map) throws Exception;
	//주간결산 지출내역조회
	public List<Map> listOut(Map map) throws Exception;
	

	
}
