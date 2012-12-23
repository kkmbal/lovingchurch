package lc.admin.settle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;
import lc.common.login.domain.UserInfo;
import lc.common.login.web.controller.LcSessionContext;
import lc.common.util.GridUtil;
import lc.common.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("settleService")
public class SettleServiceImpl implements SettleService {
	@Resource(name="lcSessionContext")
	private LcSessionContext lcSessionContext;
	
	@Autowired(required=true)
	private SettleMapper	settleMapper;
	
	// ----------------------------------------------------------------------------------
	// 주간
	// ----------------------------------------------------------------------------------
	//주간결산목록조회
	public GridOutVO listWeekSum(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.putAll(GridUtil.getPager(giVO)); 
		List<Map> list = settleMapper.listWeekSum(userdata);
		int rowCnt = settleMapper.listWeekSumCount(userdata);
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			gridOutVO.setPaging(giVO, rowCnt); 
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}
	
	
	//주간결산 수입내역조회
	public GridOutVO listIn(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.put("INOUT_CD", "01"); //입금
		
		List<Map> list = listInExcel(new HashMap(userdata));
		
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}
	
	public List<Map> listInExcel(HashMap map) throws Exception{
		map.put("INOUT_CD", "01"); //입금
		
		Map weekSum = settleMapper.getWeekSum(map);
		List<Map> list = null;
		if(weekSum == null){
			list = settleMapper.listIn(map);
		}else{
			list = settleMapper.listInWeekSumDetl(map);
		}
		return list;
	}
	
	//주간결산 지출내역조회
	public GridOutVO listOut(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.put("INOUT_CD", "02"); //출금
		
		List<Map> list = listOutExcel(new HashMap(userdata));
		
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}
	
	public List<Map> listOutExcel(HashMap map) throws Exception{
		map.put("INOUT_CD", "02"); //출금
		
		Map weekSum = settleMapper.getWeekSum(map);
		List<Map> list = null;
		if(weekSum == null){
			list = settleMapper.listOut(map);
		}else{
			list = settleMapper.listOutWeekSumDetl(map);
		}
		return list;
	}
	
	//주간결산 합계조회
	public Map getWeekSum(HashMap map) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		
		int thisIn = 0;  //금주수입계
		int thisOut = 0; //금주지출계
		int prevEnd = 0; //전주이월계
		int thisInSum = 0; //수입합계
		int thisOutSum = 0; //지출합계
		int thisEnd = 0; //금주마감
		int prevThisSum = 0; //전주+금주
		
		Map weekSum = settleMapper.getWeekSum(map);
		
		//WEEK_INOUT에서 조회
		if(weekSum == null){
			map.put("INOUT_CD", "01"); //입금
			Map inSum = settleMapper.getInoutSum(map);
			
			map.put("INOUT_CD", "02"); //출금
			Map outSum = settleMapper.getInoutSum(map);
			
			Map nextAmt = settleMapper.getNextAmt(map); //전주이월금
			
			
			if(inSum != null){
				thisIn = Integer.parseInt(inSum.get("INOUT_AMT").toString());
			}
			if(outSum != null){
				thisOut = Integer.parseInt(outSum.get("INOUT_AMT").toString());
			}
			if(nextAmt != null){
				prevEnd = Integer.parseInt(nextAmt.get("NEXT_AMT").toString());
			}
			thisInSum = thisIn + prevEnd;
			thisOutSum = thisOut;
			prevThisSum = thisInSum;
			thisEnd = prevThisSum - thisOutSum;
			
			result.put("END_YN", "N"); //마감여부
		}else{
			//WEEK_SUM에서 조회
			thisIn = Integer.parseInt(weekSum.get("IN_AMT").toString());
			thisOut = Integer.parseInt(weekSum.get("OUT_AMT").toString());
			prevEnd = Integer.parseInt(weekSum.get("PREV_AMT").toString());

			thisInSum = thisIn + prevEnd;
			thisOutSum = thisOut;
			prevThisSum = thisInSum;
			thisEnd = prevThisSum - thisOutSum;
			
			result.put("END_YN", (String)weekSum.get("END_YN")); //마감여부
		}
		
		result.put("thisIn", StringUtil.commaMask(thisIn));
		result.put("thisOut", StringUtil.commaMask(thisOut));
		result.put("prevEnd", StringUtil.commaMask(prevEnd));
		result.put("thisInSum", StringUtil.commaMask(thisInSum));
		result.put("thisOutSum", StringUtil.commaMask(thisOutSum));
		result.put("thisEnd", StringUtil.commaMask(thisEnd));
		result.put("prevThisSum", StringUtil.commaMask(prevThisSum));
		
		return result;
	}
	
	//주간결산저장
	public Map saveWeekSum(HashMap map) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		Map result = new HashMap();
		
//		//전주마감처리여부 조회
//		Map nextAmt = settleMapper.getNextAmt(map);
//		if(nextAmt != null){
//			String endYn = (String)nextAmt.get("END_YN");
//			String calYmd = (String)nextAmt.get("CAL_YMD");
//			if(!"Y".equals(endYn)){
//				result.put("SUCCESS", "마감처리 되지 않은 날짜가 있습니다.("+calYmd+")"); 
//				return result;
//			}
//		}
		
		
		
		//주간결산 존재유무 조회
		Map weekSum = settleMapper.getWeekSum(map);
		map.put("CRE_ID", UserInfo.getUSER_ID());
		map.put("UPD_ID", UserInfo.getUSER_ID());
		map.put("END_YN", "N");		
		
		if(weekSum == null){
			//insert
			settleMapper.insertWeekSum(map);
			settleMapper.insertWeekSumDetl(map);
		}else{
			String endYn = (String)weekSum.get("END_YN");
			if("Y".equals(endYn)){
				result.put("SUCCESS", "이미 마감처리되었습니다."); 
				return result;
			}else{
				//delete
				settleMapper.deleteWeekSum(map);
				settleMapper.deleteWeekSumDetl(map);
				
				//insert
				settleMapper.insertWeekSum(map);
				settleMapper.insertWeekSumDetl(map);
			}
		}
		
		result.put("SUCCESS", "저장되었습니다.");
		return result;
	}
	
	//마감처리
	public Map saveWeekEndYn(HashMap map) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		Map result = new HashMap();
		
		//전주마감처리여부 조회
		Map nextAmt = settleMapper.getNextAmt(map);
		if(nextAmt != null){
			String endYn = (String)nextAmt.get("END_YN");
			String calYmd = (String)nextAmt.get("CAL_YMD");
			if(!"Y".equals(endYn)){
				result.put("SUCCESS", "마감처리 되지 않은 날짜가 있습니다.("+calYmd+")"); 
				return result;
			}
		}
		
		//주간결산 존재유무 조회
		Map weekSum = settleMapper.getWeekSum(map);
		if(weekSum == null){
			result.put("SUCCESS", "저장을 먼저 하세요."); 
			return result;
		}else{
			String endYn = (String)weekSum.get("END_YN");
			if("Y".equals(endYn)){
				result.put("SUCCESS", "이미 마감처리되었습니다."); 
				return result;
			}
			
			map.put("UPD_ID", UserInfo.getUSER_ID());
			map.put("END_YN", "Y"); //마감여부
			
			settleMapper.updateEndYn(map);
		}
		
		result.put("SUCCESS", "마감되었습니다.");
		result.put("END_YN", "Y"); //마감여부
		return result;
	}
	
	// ----------------------------------------------------------------------------------
	public String getEndYnForPeriod(HashMap map) throws Exception{
		Map endYnForPeriod = settleMapper.getEndYnForPeriod(map);
		String endYn = "N";
		if(endYnForPeriod != null){
			endYn = (String)endYnForPeriod.get("END_YN");
		}else if(map.get("CAL_YMD") == null){
			endYn = "Y";
		}
		return endYn;
	}
	
	// ----------------------------------------------------------------------------------
	// 월간
	// ----------------------------------------------------------------------------------
	//월간결산목록조회
	public GridOutVO listMonSum(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.putAll(GridUtil.getPager(giVO)); 
		List<Map> list = settleMapper.listMonSum(userdata);
		int rowCnt = settleMapper.listMonSumCount(userdata);
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			gridOutVO.setPaging(giVO, rowCnt); 
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}
	
	//월간결산 수입내역조회
	public GridOutVO listInMonth(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.put("INOUT_CD", "01"); //입금
		
		List<Map> list = listInMonthExcel(new HashMap(userdata));
		
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}
	
	public List<Map> listInMonthExcel(HashMap map) throws Exception{
		map.put("INOUT_CD", "01"); //입금
		return settleMapper.listInOutMonth(map);
	}
	
	//월간결산 지출내역조회
	public GridOutVO listOutMonth(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.put("INOUT_CD", "02"); //출금
		
		List<Map> list = listOutMonthExcel(new HashMap(userdata));
		
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}
	
	public List<Map> listOutMonthExcel(HashMap map) throws Exception{
		map.put("INOUT_CD", "02"); //출금
		return settleMapper.listInOutMonth(map);
	}
	
	//월간결산 합계조회
	public Map getMonthSum(HashMap map) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		
		int thisIn = 0;  //금주수입계
		int thisOut = 0; //금주지출계
		int prevEnd = 0; //전주이월계
		int thisInSum = 0; //수입합계
		int thisOutSum = 0; //지출합계
		int thisEnd = 0; //금주마감
		int prevThisSum = 0; //전주+금주
		
		Map monSum = settleMapper.getMonthSum(map);
		
		//WEEK_SUM에서 조회
		if(monSum == null){
			Map nextAmtMon = settleMapper.getNextAmtMonth(map);
			Map weekSum = settleMapper.getWeekSumLast(map);
			
			if(weekSum != null){
				thisIn = Integer.parseInt(weekSum.get("IN_AMT").toString());
				thisOut = Integer.parseInt(weekSum.get("OUT_AMT").toString());
			}
			if(nextAmtMon != null){
				prevEnd = Integer.parseInt(nextAmtMon.get("NEXT_AMT").toString());
			}
			
			thisInSum = thisIn + prevEnd;
			thisOutSum = thisOut;
			prevThisSum = thisInSum;
			thisEnd = prevThisSum - thisOutSum;
			
			result.put("END_YN", "N"); //마감여부
		}else{
			//MON_SUM에서 조회
			thisIn = Integer.parseInt(monSum.get("IN_AMT").toString());
			thisOut = Integer.parseInt(monSum.get("OUT_AMT").toString());
			prevEnd = Integer.parseInt(monSum.get("PREV_AMT").toString());

			thisInSum = thisIn + prevEnd;
			thisOutSum = thisOut;
			prevThisSum = thisInSum;
			thisEnd = prevThisSum - thisOutSum;
			
			result.put("END_YN", (String)monSum.get("END_YN")); //마감여부
		}
		
		result.put("thisIn", StringUtil.commaMask(thisIn));
		result.put("thisOut", StringUtil.commaMask(thisOut));
		result.put("prevEnd", StringUtil.commaMask(prevEnd));
		result.put("thisInSum", StringUtil.commaMask(thisInSum));
		result.put("thisOutSum", StringUtil.commaMask(thisOutSum));
		result.put("thisEnd", StringUtil.commaMask(thisEnd));
		result.put("prevThisSum", StringUtil.commaMask(prevThisSum));
		
		return result;
	}	
	
	//월간결산저장
	public Map saveMonthSum(HashMap map) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		Map result = new HashMap();
		
//		//전달마감처리여부 조회
//		Map nextAmt = settleMapper.getNextAmtMonth(map);
//		if(nextAmt != null){
//			String endYn = (String)nextAmt.get("END_YN");
//			String calYm = (String)nextAmt.get("CAL_YM");
//			if(!"Y".equals(endYn)){
//				result.put("SUCCESS", "마감처리 되지 않은 달이 있습니다.("+calYm+")");
//				return result;
//			}
//		}
		
		
		
		//월간결산 존재유무 조회
		Map monSum = settleMapper.getMonthSum(map);
		map.put("CRE_ID", UserInfo.getUSER_ID());
		map.put("UPD_ID", UserInfo.getUSER_ID());
		map.put("END_YN", "N");		
		
		if(monSum == null){
			//insert
			settleMapper.insertMonSum(map);
		}else{
			String endYn = (String)monSum.get("END_YN");
			if("Y".equals(endYn)){
				result.put("SUCCESS", "이미 마감처리되었습니다.");
				return result;
			}else{
				//delete
				settleMapper.deleteMonSum(map);
				
				//insert
				settleMapper.insertMonSum(map);
			}
		}
		
		result.put("SUCCESS", "저장되었습니다.");
		return result;
	}
	
	//월마감처리
	public Map saveMonthEndYn(HashMap map) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		Map result = new HashMap();
		
		//전달마감처리여부 조회
		Map nextAmt = settleMapper.getNextAmtMonth(map);
		if(nextAmt != null){
			String endYn = (String)nextAmt.get("END_YN");
			String calYm = (String)nextAmt.get("CAL_YM");
			if(!"Y".equals(endYn)){
				result.put("SUCCESS", "마감처리 되지 않은 달이 있습니다.("+calYm+")"); 
				return result;
			}
		}
		
		//주간결산 존재유무 조회
		Map monSum = settleMapper.getMonthSum(map);
		if(monSum == null){
			result.put("SUCCESS", "저장을 먼저 하세요."); 
			return result;
		}else{
			String endYn = (String)monSum.get("END_YN");
			if("Y".equals(endYn)){
				result.put("SUCCESS", "이미 마감처리되었습니다."); 
				return result;
			}
			
			map.put("UPD_ID", UserInfo.getUSER_ID());
			map.put("END_YN", "Y"); //마감여부
			
			settleMapper.updateMonthEndYn(map);
		}
		
		result.put("SUCCESS", "마감되었습니다.");
		result.put("END_YN", "Y"); //마감여부
		return result;
	}


	//수입예산
	@Override
	public GridOutVO listInYear(GridInVO giVO) throws Exception {
		Map<String, String> userdata = giVO.getUserdata();
		
		List<Map> list = settleMapper.listInYear(new HashMap(userdata));
		
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}

	//지출예산
	@Override
	public GridOutVO listOutYear(GridInVO giVO) throws Exception {
		Map<String, String> userdata = giVO.getUserdata();
		
		List<Map> list = settleMapper.listOutYear(new HashMap(userdata));
		
		GridOutVO gridOutVO = new GridOutVO();
		if(list != null && list.size() > 0){
			List<Map<String, String>> listRow = new ArrayList<Map<String, String>>();
			for(Map<String, String> vo : list){
				listRow.add(vo);
			}
			gridOutVO.setRows(listRow);
		}
		return gridOutVO;
	}


	//예산결산
	@Override
	public Map listYearSum(HashMap map) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		

		
		//올해
		Map yearSum = settleMapper.getYearSum(map);
		String year = (String)map.get("YEAR");
		if(year == null || "".equals(year)) return result;
		
		//지난해
		HashMap prevMap = new HashMap();
		prevMap.put("YEAR", String.valueOf(Integer.parseInt(year) - 1));
		Map prevYearSum = settleMapper.getYearSum(prevMap);
		
		
		// -------------------- 초기화 ------------------------------
		result.put("PREV_SUM1_AMT", "0"); //작년주정현금
		result.put("SUM1_AMT", "0"); //올해주정현금
		result.put("PREV_EXP_SUM1_AMT", "0"); //작년책정예산-주정헌금
		result.put("EXP_SUM1_AMT", "0"); //올해책정예산-주정헌금
		result.put("REMARK1", ""); //비고-주정현금
    	
		result.put("PREV_SUM2_AMT", "0"); //작년십일조현금
		result.put("SUM2_AMT", "0"); //올해십일조현금
		result.put("PREV_EXP_SUM2_AMT", "0"); //작년책정예산-십일조헌금
		result.put("EXP_SUM2_AMT", "0"); //올해책정예산-십일조헌금
		result.put("REMARK2", ""); //비고-십일조현금
    	
    	result.put("PREV_SUM3_AMT", "0"); //작년감사현금
    	result.put("SUM3_AMT", "0"); //올해감사현금
    	result.put("PREV_EXP_SUM3_AMT", "0"); //작년책정예산-감사헌금
    	result.put("EXP_SUM3_AMT", "0"); //올해책정예산-감사헌금
    	result.put("REMARK3", ""); //비고-감사현금
    	
    	result.put("PREV_SUM4_AMT", "0"); //작년선교현금
    	result.put("SUM4_AMT", "0"); //올해선교현금
    	result.put("PREV_EXP_SUM4_AMT", "0"); //작년책정예산-선교헌금
    	result.put("EXP_SUM4_AMT", "0"); //올해책정예산-선교헌금
    	result.put("REMARK4", ""); //비고-주정현금
    	
    	result.put("PREV_SUM5_AMT", "0"); //작년건축현금
    	result.put("SUM5_AMT", "0"); //올해건축현금
    	result.put("PREV_EXP_SUM5_AMT", "0"); //작년책정예산-건축헌금
    	result.put("EXP_SUM5_AMT", "0"); //올해책정예산-건축헌금
    	result.put("REMARK5", ""); //비고-건축현금
    	
    	result.put("PREV_SUM6_AMT", "0"); //작년절기현금
    	result.put("SUM6_AMT", "0"); //올해절기현금
    	result.put("PREV_EXP_SUM6_AMT", "0"); //작년책정예산-절기헌금
    	result.put("EXP_SUM6_AMT", "0"); //올해책정예산-절기헌금
    	result.put("REMARK6", ""); //비고-절기현금
    	
    	result.put("PREV_SUM7_AMT", "0"); //작년기타현금
    	result.put("SUM7_AMT", "0"); //올해기타현금
    	result.put("PREV_EXP_SUM7_AMT", "0"); //작년책정예산-기타헌금
    	result.put("EXP_SUM7_AMT", "0"); //올해책정예산-기타헌금
    	result.put("REMARK7", ""); //비고-기타현금
		// -------------------- 초기화 ------------------------------
		
		
		//WEEK_SUM에서 조회
		if(yearSum == null){
			Map listYearSum = settleMapper.listYearSum(map);

			if(listYearSum != null){
				result.put("PREV_SUM1_AMT", StringUtil.commaMask(listYearSum.get("PREV_SUM1_AMT").toString())); //작년주정현금
				result.put("SUM1_AMT", StringUtil.commaMask(listYearSum.get("SUM1_AMT").toString())); //올해주정현금
				result.put("PREV_EXP_SUM1_AMT", StringUtil.commaMask(listYearSum.get("EXP_SUM1_AMT").toString())); //작년책정예산-주정헌금
				result.put("EXP_SUM1_AMT", "0"); //올해책정예산-주정헌금
				result.put("REMARK1", (listYearSum.get("REMARK1").toString())); //비고-주정현금
		    	
				result.put("PREV_SUM2_AMT", StringUtil.commaMask(listYearSum.get("PREV_SUM2_AMT").toString())); //작년십일조현금
				result.put("SUM2_AMT", StringUtil.commaMask(listYearSum.get("SUM2_AMT").toString())); //올해십일조현금
				result.put("PREV_EXP_SUM2_AMT", StringUtil.commaMask(listYearSum.get("EXP_SUM2_AMT").toString())); //작년책정예산-십일조헌금
				result.put("EXP_SUM2_AMT", "0"); //올해책정예산-십일조헌금
				result.put("REMARK2", (listYearSum.get("REMARK2").toString())); //비고-십일조현금
		    	
		    	result.put("PREV_SUM3_AMT", StringUtil.commaMask(listYearSum.get("PREV_SUM3_AMT").toString())); //작년감사현금
		    	result.put("SUM3_AMT", StringUtil.commaMask(listYearSum.get("SUM3_AMT").toString())); //올해감사현금
		    	result.put("PREV_EXP_SUM3_AMT", StringUtil.commaMask(listYearSum.get("EXP_SUM3_AMT").toString())); //작년책정예산-감사헌금
		    	result.put("EXP_SUM3_AMT", "0"); //올해책정예산-감사헌금
		    	result.put("REMARK3", (listYearSum.get("REMARK3").toString())); //비고-감사현금
		    	
		    	result.put("PREV_SUM4_AMT", StringUtil.commaMask(listYearSum.get("PREV_SUM4_AMT").toString())); //작년선교현금
		    	result.put("SUM4_AMT", StringUtil.commaMask(listYearSum.get("SUM4_AMT").toString())); //올해선교현금
		    	result.put("PREV_EXP_SUM4_AMT", StringUtil.commaMask(listYearSum.get("EXP_SUM4_AMT").toString())); //작년책정예산-선교헌금
		    	result.put("EXP_SUM4_AMT", "0"); //올해책정예산-선교헌금
		    	result.put("REMARK4", (listYearSum.get("REMARK4").toString())); //비고-주정현금
		    	
		    	result.put("PREV_SUM5_AMT", StringUtil.commaMask(listYearSum.get("PREV_SUM5_AMT").toString())); //작년건축현금
		    	result.put("SUM5_AMT", StringUtil.commaMask(listYearSum.get("SUM5_AMT").toString())); //올해건축현금
		    	result.put("PREV_EXP_SUM5_AMT", StringUtil.commaMask(listYearSum.get("EXP_SUM5_AMT").toString())); //작년책정예산-건축헌금
		    	result.put("EXP_SUM5_AMT", "0"); //올해책정예산-건축헌금
		    	result.put("REMARK5", (listYearSum.get("REMARK5").toString())); //비고-건축현금
		    	
		    	result.put("PREV_SUM6_AMT", StringUtil.commaMask(listYearSum.get("PREV_SUM6_AMT").toString())); //작년절기현금
		    	result.put("SUM6_AMT", StringUtil.commaMask(listYearSum.get("SUM6_AMT").toString())); //올해절기현금
		    	result.put("PREV_EXP_SUM6_AMT", StringUtil.commaMask(listYearSum.get("EXP_SUM6_AMT").toString())); //작년책정예산-절기헌금
		    	result.put("EXP_SUM6_AMT", "0"); //올해책정예산-절기헌금
		    	result.put("REMARK6", (listYearSum.get("REMARK6").toString().toString())); //비고-절기현금
		    	
		    	result.put("PREV_SUM7_AMT", StringUtil.commaMask(listYearSum.get("PREV_SUM7_AMT").toString())); //작년기타현금
		    	result.put("SUM7_AMT", StringUtil.commaMask(listYearSum.get("SUM7_AMT").toString())); //올해기타현금
		    	result.put("PREV_EXP_SUM7_AMT", StringUtil.commaMask(listYearSum.get("EXP_SUM7_AMT").toString())); //작년책정예산-기타헌금
		    	result.put("EXP_SUM7_AMT", "0"); //올해책정예산-기타헌금
		    	result.put("REMARK7", (listYearSum.get("REMARK7").toString())); //비고-기타현금			
			}
			result.put("END_YN", "N"); //마감여부
		}else{
			if(prevYearSum != null){
				result.put("PREV_SUM1_AMT", StringUtil.commaMask(prevYearSum.get("SUM1_AMT").toString())); //작년주정현금
				result.put("SUM1_AMT", StringUtil.commaMask(yearSum.get("SUM1_AMT").toString())); //올해주정현금
				result.put("PREV_EXP_SUM1_AMT", StringUtil.commaMask(prevYearSum.get("EXP_SUM1_AMT").toString())); //작년책정예산-주정헌금
				result.put("EXP_SUM1_AMT", StringUtil.commaMask(yearSum.get("EXP_SUM1_AMT").toString())); //올해책정예산-주정헌금
				result.put("REMARK1", (prevYearSum.get("REMARK1").toString())); //비고-주정현금
		    	
				result.put("PREV_SUM2_AMT", StringUtil.commaMask(prevYearSum.get("SUM2_AMT").toString())); //작년십일조현금
				result.put("SUM2_AMT", StringUtil.commaMask(yearSum.get("SUM2_AMT").toString())); //올해십일조현금
				result.put("PREV_EXP_SUM2_AMT", StringUtil.commaMask(prevYearSum.get("EXP_SUM2_AMT").toString())); //작년책정예산-십일조헌금
				result.put("EXP_SUM2_AMT", StringUtil.commaMask(yearSum.get("EXP_SUM2_AMT").toString())); //올해책정예산-십일조헌금
				result.put("REMARK2", (prevYearSum.get("REMARK2").toString())); //비고-십일조현금
		    	
		    	result.put("PREV_SUM3_AMT", StringUtil.commaMask(prevYearSum.get("SUM3_AMT").toString())); //작년감사현금
		    	result.put("SUM3_AMT", StringUtil.commaMask(yearSum.get("SUM3_AMT").toString())); //올해감사현금
		    	result.put("PREV_EXP_SUM3_AMT", StringUtil.commaMask(prevYearSum.get("EXP_SUM3_AMT").toString())); //작년책정예산-감사헌금
		    	result.put("EXP_SUM3_AMT", StringUtil.commaMask(yearSum.get("EXP_SUM3_AMT").toString())); //올해책정예산-감사헌금
		    	result.put("REMARK3", (prevYearSum.get("REMARK3").toString())); //비고-감사현금
		    	
		    	result.put("PREV_SUM4_AMT", StringUtil.commaMask(prevYearSum.get("SUM4_AMT").toString())); //작년선교현금
		    	result.put("SUM4_AMT", StringUtil.commaMask(yearSum.get("SUM4_AMT").toString())); //올해선교현금
		    	result.put("PREV_EXP_SUM4_AMT", StringUtil.commaMask(prevYearSum.get("EXP_SUM4_AMT").toString())); //작년책정예산-선교헌금
		    	result.put("EXP_SUM4_AMT", StringUtil.commaMask(yearSum.get("EXP_SUM4_AMT").toString())); //올해책정예산-선교헌금
		    	result.put("REMARK4", (prevYearSum.get("REMARK4").toString())); //비고-주정현금
		    	
		    	result.put("PREV_SUM5_AMT", StringUtil.commaMask(prevYearSum.get("SUM5_AMT").toString())); //작년건축현금
		    	result.put("SUM5_AMT", StringUtil.commaMask(yearSum.get("SUM5_AMT").toString())); //올해건축현금
		    	result.put("PREV_EXP_SUM5_AMT", StringUtil.commaMask(prevYearSum.get("EXP_SUM5_AMT").toString())); //작년책정예산-건축헌금
		    	result.put("EXP_SUM5_AMT", StringUtil.commaMask(yearSum.get("EXP_SUM5_AMT").toString())); //올해책정예산-건축헌금
		    	result.put("REMARK5", (prevYearSum.get("REMARK5").toString())); //비고-건축현금
		    	
		    	result.put("PREV_SUM6_AMT", StringUtil.commaMask(prevYearSum.get("SUM6_AMT").toString())); //작년절기현금
		    	result.put("SUM6_AMT", StringUtil.commaMask(yearSum.get("SUM6_AMT").toString())); //올해절기현금
		    	result.put("PREV_EXP_SUM6_AMT", StringUtil.commaMask(prevYearSum.get("EXP_SUM6_AMT").toString())); //작년책정예산-절기헌금
		    	result.put("EXP_SUM6_AMT", StringUtil.commaMask(yearSum.get("EXP_SUM6_AMT").toString())); //올해책정예산-절기헌금
		    	result.put("REMARK6", (prevYearSum.get("REMARK6").toString())); //비고-절기현금
		    	
		    	result.put("PREV_SUM7_AMT", StringUtil.commaMask(prevYearSum.get("SUM7_AMT").toString())); //작년기타현금
		    	result.put("SUM7_AMT", StringUtil.commaMask(yearSum.get("SUM7_AMT").toString())); //올해기타현금
		    	result.put("PREV_EXP_SUM7_AMT", StringUtil.commaMask(prevYearSum.get("EXP_SUM7_AMT").toString())); //작년책정예산-기타헌금
		    	result.put("EXP_SUM7_AMT", StringUtil.commaMask(yearSum.get("EXP_SUM7_AMT").toString())); //올해책정예산-기타헌금
		    	result.put("REMARK7", (prevYearSum.get("REMARK7").toString())); //비고-기타현금
			}else{

			}
			result.put("END_YN", (String)yearSum.get("END_YN")); //마감여부
		}
		
		long PREV_SUM_AMT = Long.parseLong(result.get("PREV_SUM1_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_SUM2_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_SUM3_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_SUM4_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_SUM5_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_SUM6_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_SUM7_AMT").toString().replaceAll(",", ""));
    	long SUM_AMT = Long.parseLong(result.get("SUM1_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("SUM2_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("SUM3_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("SUM4_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("SUM5_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("SUM6_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("SUM7_AMT").toString().replaceAll(",", ""));
    	long PREV_EXP_SUM_AMT = Long.parseLong(result.get("PREV_EXP_SUM1_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_EXP_SUM2_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_EXP_SUM3_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_EXP_SUM4_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_EXP_SUM5_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_EXP_SUM6_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("PREV_EXP_SUM7_AMT").toString().replaceAll(",", ""));
    	long EXP_SUM_AMT = Long.parseLong(result.get("EXP_SUM1_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("EXP_SUM2_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("EXP_SUM3_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("EXP_SUM4_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("EXP_SUM5_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("EXP_SUM6_AMT").toString().replaceAll(",", "")) + Long.parseLong(result.get("EXP_SUM7_AMT").toString().replaceAll(",", ""));
    	
    	result.put("PREV_SUM_AMT", StringUtil.commaMask(PREV_SUM_AMT)); //작년합계
    	result.put("SUM_AMT", StringUtil.commaMask(SUM_AMT)); //올해합계
    	result.put("PREV_EXP_SUM_AMT", StringUtil.commaMask(PREV_EXP_SUM_AMT)); //작년책정예산-합계
    	result.put("EXP_SUM_AMT", StringUtil.commaMask(EXP_SUM_AMT)); //올해책정예산-합계
 
		
		return result;
	}

	//년간결산저장
	public Map saveYearSum(HashMap map) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		Map result = new HashMap();
		
		//년간결산 존재유무 조회
		Map yearSum = settleMapper.getYearSum(map);
		
		map.put("CRE_ID", UserInfo.getUSER_ID());
		map.put("UPD_ID", UserInfo.getUSER_ID());
		map.put("END_YN", "N");		
		
		if(yearSum == null){
			//insert
			settleMapper.insertYearSum(map);
		}else{
			String endYn = (String)yearSum.get("END_YN");
			if("Y".equals(endYn)){
				result.put("SUCCESS", "이미 마감처리되었습니다.");
				return result;
			}else{
				//delete
				settleMapper.deleteYearSum(map);
				
				//insert
				settleMapper.insertYearSum(map);
			}
		}
		
		result.put("SUCCESS", "저장되었습니다.");
		return result;
	}
	
	//년마감처리
	public Map saveYearEndYn(HashMap map) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		Map result = new HashMap();
		
		//전년마감처리여부 조회
		Map nextAmt = settleMapper.getNextAmtYear(map);
		if(nextAmt != null){
			String endYn = (String)nextAmt.get("END_YN");
			String calYear = (String)nextAmt.get("CAL_YEAR");
			if(!"Y".equals(endYn)){
				result.put("SUCCESS", "마감처리 되지 않은 달이 있습니다.("+calYear+")"); 
				return result;
			}
		}
		
		//년간결산 존재유무 조회
		Map yearSum = settleMapper.getYearSum(map);
		if(yearSum == null){
			result.put("SUCCESS", "저장을 먼저 하세요."); 
			return result;
		}else{
			String endYn = (String)yearSum.get("END_YN");
			if("Y".equals(endYn)){
				result.put("SUCCESS", "이미 마감처리되었습니다."); 
				return result;
			}
			
			map.put("UPD_ID", UserInfo.getUSER_ID());
			map.put("END_YN", "Y"); //마감여부
			
			settleMapper.updateYearEndYn(map);
		}
		
		result.put("SUCCESS", "마감되었습니다.");
		result.put("END_YN", "Y"); //마감여부
		return result;
	}	
	
	@Override
	public Map listYearSumExcel(HashMap map) throws Exception {
		return listYearSum(map);
	}	
}
