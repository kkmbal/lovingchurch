package lc.admin.member.service;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;

public interface MemberService {
	//조회
	public GridOutVO listMember(GridInVO giVO) throws Exception;
	//저장
	public GridOutVO saveMember(GridInVO giVO) throws Exception;
	//삭제
	public GridOutVO deleteMember(GridInVO giVO) throws Exception;
	
	
}
