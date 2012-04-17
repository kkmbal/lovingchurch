package lc.common.code.service.grid;

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
	
	
}
