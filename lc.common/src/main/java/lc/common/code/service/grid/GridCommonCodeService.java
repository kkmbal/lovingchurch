package lc.common.code.service.grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;

public interface GridCommonCodeService {
	//그룹코드리스트조회
	public GridOutVO listGroup(GridInVO giVO) throws Exception;
	//그룹코드저장
	public GridOutVO saveGroupCode(GridInVO giVO) throws Exception;
	//그룹코드삭제
	public GridOutVO deleteGroupCode(GridInVO giVO) throws Exception;
	//상세코드리스트조회
	public GridOutVO listDetailCode(GridInVO giVO) throws Exception;
	//상세코드저장
	public GridOutVO saveDetailCode(GridInVO giVO) throws Exception;
	//상세코드삭제
	public GridOutVO deleteDetailCode(GridInVO giVO) throws Exception;
	//하위코드조회
	public List<Map> getCommSubCd(HashMap map) throws Exception;
	
	
}
