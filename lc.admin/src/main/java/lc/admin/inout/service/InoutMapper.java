package lc.admin.inout.service;

import java.util.List;
import java.util.Map;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;

public interface InoutMapper {
	
	//헌금조회
	public List<Map> listDonation(Map map) throws Exception;
	//ROWCNT조회
	public int listDonationCount(Map map) throws Exception;
	//추가
	public void insertInout(Map map) throws Exception;
	//Max Seq
	public String getMaxInoutSeq(Map map) throws Exception;
	//헌금수정
	public void updateDonation(Map map) throws Exception;
	//헌금조회
	public Map getDonation(Map map) throws Exception;
	
	//수입지출 조회
	public List<Map> listInout(Map map) throws Exception;
	//ROWCNT조회
	public int listInoutCount(Map map) throws Exception;
	//수입지출 수정
	public void updateInout(Map map) throws Exception;
	//수입지출 삭제
	public void deleteInout(Map map) throws Exception;
	
	// --------------------------------------------------------
	// Excel
	// --------------------------------------------------------
	public List<Map> listDonationExcel(Map map) throws Exception;
	public List<Map> listDonationSumExcel(Map map) throws Exception;
	public Map getDonationSumExcel(Map map) throws Exception;
	
	
}
