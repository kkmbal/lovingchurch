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
import lc.common.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("settleService")
public class SettleServiceImpl implements SettleService {
	@Resource(name="lcSessionContext")
	private LcSessionContext lcSessionContext;
	
	@Autowired(required=true)
	private SettleMapper	settleMapper;
	
	
	//주간결산 수입내역조회
	public GridOutVO listIn(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.put("INOUT_CD", "01"); //입금
		
//		Map weekSum = settleMapper.getWeekSum(userdata);
//		List<Map> list = null;
//		if(weekSum == null){
//			list = settleMapper.listIn(userdata);
//		}else{
//			list = settleMapper.listInWeekSumDetl(userdata);
//		}
		
		List<Map> list = listIn(new HashMap(userdata));
		
		//List<Map> list = settleMapper.listIn(userdata);
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
	
	public List<Map> listIn(HashMap map) throws Exception{
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
		
//		Map weekSum = settleMapper.getWeekSum(userdata);
//		List<Map> list = null;
//		if(weekSum == null){
//			list = settleMapper.listOut(userdata);
//		}else{
//			list = settleMapper.listOutWeekSumDetl(userdata);
//		}
		List<Map> list = listOut(new HashMap(userdata));
		
		//List<Map> list = settleMapper.listOut(userdata);
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
	
	public List<Map> listOut(HashMap map) throws Exception{
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
	public Map getInoutSum(HashMap map) throws Exception{
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
		
		//전주마감처리여부 조회
		Map nextAmt = settleMapper.getNextAmt(map);
		if(nextAmt != null){
			String endYn = (String)nextAmt.get("END_YN");
			String calYmd = (String)nextAmt.get("CAL_YMD");
			if(!"Y".equals(endYn)){
				result.put("SUCCESS", "마감처리 되지 않은 날짜가 있습니다.("+calYmd+")"); //전주 미마감.
				return result;
			}
		}
		
		
		
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
				result.put("SUCCESS", "이미 마감처리되었습니다."); //전주 미마감.
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
	public Map saveEndYn(HashMap map) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		Map result = new HashMap();
		
		//전주마감처리여부 조회
		Map nextAmt = settleMapper.getNextAmt(map);
		if(nextAmt != null){
			String endYn = (String)nextAmt.get("END_YN");
			String calYmd = (String)nextAmt.get("CAL_YMD");
			if(!"Y".equals(endYn)){
				result.put("SUCCESS", "마감처리 되지 않은 날짜가 있습니다.("+calYmd+")"); //전주 미마감.
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
				result.put("SUCCESS", "이미 마감처리되었습니다."); //전주 미마감.
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
	public Map getEndYn(HashMap map) throws Exception{
		return settleMapper.getWeekSum(map);
	}
		
}
