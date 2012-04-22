package lc.admin.inout.service;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;

public interface InoutService {
	//헌금조회
	public GridOutVO listDonation(GridInVO giVO) throws Exception;
	//헌금저장
	public GridOutVO saveDonation(GridInVO giVO) throws Exception;
	
	//수입조회
	public GridOutVO listIn(GridInVO giVO) throws Exception;
	//수입저장
	public GridOutVO saveIn(GridInVO giVO) throws Exception;
	//수입삭제
	public GridOutVO deleteIn(GridInVO giVO) throws Exception;
	
	//지출조회
	public GridOutVO listOut(GridInVO giVO) throws Exception;
	//지출저장
	public GridOutVO saveOut(GridInVO giVO) throws Exception;
	//지출삭제
	public GridOutVO deleteOut(GridInVO giVO) throws Exception;
}
