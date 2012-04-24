package lc.common.util;

import java.util.HashMap;
import java.util.Map;

import lc.common.grid.domain.GridInVO;

public class GridUtil {
	public enum GRID_OPER { INSERT, UPDATE, DELETE, SELECT };
	
	/**
	 * 작업유형 리턴하는 함수(INSERT, UPDATE, DELETE, SELECT)
	 * @param savedata
	 * @return GRID_OPER
	 */
	public static GRID_OPER getOperation(Map<String, String> savedata){
		if(savedata != null && savedata.get("oper") != null){
			return GRID_OPER.valueOf(savedata.get("oper").toUpperCase());
		}
		return GRID_OPER.SELECT;
	}

	/**
	 * GridInVO 에서 페이징 관련 데이터를 추출하는 함수
	 * @param giVO
	 * @return Map
	 */
	public static Map<String, String> getPager(GridInVO giVO) {
		Map<String, String> pager = new HashMap<String, String>();
	    //pager.put("page", giVO.getPage()); //oracle
	    pager.put("page", String.valueOf((Integer.parseInt(giVO.getPage())-1) * Integer.parseInt(giVO.getRows()) )); //mysql
	    pager.put("rows", giVO.getRows());
	    pager.put("sidx", giVO.getSidx());
	    pager.put("sord", giVO.getSord());
	    
	    return pager;
	}
}
