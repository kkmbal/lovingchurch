package lc.admin.inout.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import lc.admin.settle.service.SettleService;
import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;
import lc.common.login.domain.UserInfo;
import lc.common.login.web.controller.LcSessionContext;
import lc.common.util.GridUtil;
import lc.common.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("inoutService")
public class InoutServiceImpl implements InoutService {

	@Autowired(required=true)
	private InoutMapper	inoutMapper;
	
	
	@Resource(name="lcSessionContext")
	private LcSessionContext lcSessionContext;
	
	@Resource(name="settleService")
	private SettleService settleService;
	
	
	//헌금조회
	public GridOutVO listDonation(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.putAll(GridUtil.getPager(giVO)); 
		userdata.put("INOUT_CD", "01"); //입금
		
		List<Map> list = inoutMapper.listDonation(userdata);
		int rowCnt = inoutMapper.listDonationCount(userdata);
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
	//헌금저장
	public GridOutVO saveDonation(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getSavedata();
		Map<String, String> userdata = giVO.getUserdata();
		String calYmd = userdata.get("CAL_YMD");
		
		//마감여부조회
		if("Y".equals(settleService.getEndYnForPeriod(new HashMap(userdata)))){
			GridOutVO listDonation = listDonation(giVO);
			listDonation.setResultMsg("이미 마감되었습니다.");
			return listDonation;
		}
		
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				Iterator<String> iterator = ((Map<String, String>)((HashMap<String, String>)data).clone()).keySet().iterator();
				
				data.put("INOUT_CD", "01"); //입금
				
				while(iterator.hasNext()){
					String cd = iterator.next();
					if(cd.startsWith("DONA_CD_")){
						data.put("INOUT_ITEM_CD", cd.replaceAll("DONA_CD_", "")); //헌금코드
						data.put("INOUT_AMT", data.get(cd)); //금액
						data.put("CAL_YMD", calYmd); //날짜
						
						Map donation = inoutMapper.getDonation(data);
						
						if(donation == null){
							//insert
							String seq = inoutMapper.getMaxInoutSeq(data);
							data.put("INOUT_SEQ_NO", seq);
							data.put("CRE_ID", UserInfo.getUSER_ID());
							data.put("UPD_ID", UserInfo.getUSER_ID());
							if(!"0".equals(data.get("INOUT_AMT"))){ // 0원은 insert 제외
								inoutMapper.insertInout(data);
							}
						}else{
							//update
							data.put("UPD_ID", UserInfo.getUSER_ID());
							
							inoutMapper.updateDonation(data);
						}
						
					}
				}
			}
			
		}
		
		return listDonation(giVO);
	}
	
	
	// ----------------------------------------------------------------------------------
	
	//수입조회
	public GridOutVO listIn(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.putAll(GridUtil.getPager(giVO)); 
		
		userdata.put("INOUT_CD", "01"); //입금
		
		List<Map> list = inoutMapper.listInout(userdata);
		int rowCnt = inoutMapper.listInoutCount(userdata);
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
	//수입저장
	public GridOutVO saveIn(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getSavedata();
		Map<String, String> userdata = giVO.getUserdata();
		String calYmd = userdata.get("CAL_YMD");
		
		//마감여부조회
		if("Y".equals(settleService.getEndYnForPeriod(new HashMap(userdata)))){
			GridOutVO listIn = listIn(giVO);
			listIn.setResultMsg("이미 마감되었습니다.");
			return listIn;
		}
		
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				
				data.put("INOUT_CD", "01"); //입금
				data.put("USER_KEY", null); //사용자키
				data.put("CAL_YMD", calYmd); //날짜
				
				switch(GridUtil.getOperation(data)){
					case INSERT:
						String seq = inoutMapper.getMaxInoutSeq(data);
						data.put("INOUT_SEQ_NO", seq);
						data.put("CRE_ID", UserInfo.getUSER_ID());
						data.put("UPD_ID", UserInfo.getUSER_ID());
						
						
						inoutMapper.insertInout(data);
						break;
					case UPDATE:
						data.put("UPD_ID", UserInfo.getUSER_ID());
						
						inoutMapper.updateInout(data);
						break;
				}
			}
		}
		
		return listIn(giVO);
	}
	
	//수입삭제
	public GridOutVO deleteIn(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getDeldata();
		
		if(savedata != null && savedata.size() > 0){
			//마감여부조회
			if("Y".equals(settleService.getEndYnForPeriod(new HashMap(savedata.get(0))))){
				GridOutVO listIn = listIn(giVO);
				listIn.setResultMsg("이미 마감되었습니다.");
				return listIn;
			}
			
			for(Map<String, String> data : savedata){
				inoutMapper.deleteInout(data);
			}
		}
		
		return listIn(giVO);
	}
	
	// ----------------------------------------------------------------------------------
	
	//지출조회
	public GridOutVO listOut(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.putAll(GridUtil.getPager(giVO)); 
		
		userdata.put("INOUT_CD", "02"); //출금
		
		List<Map> list = inoutMapper.listInout(userdata);
		int rowCnt = inoutMapper.listInoutCount(userdata);
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
	//지출저장
	public GridOutVO saveOut(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getSavedata();
		Map<String, String> userdata = giVO.getUserdata();
		String calYmd = userdata.get("CAL_YMD");
		
		//마감여부조회
		if("Y".equals(settleService.getEndYnForPeriod(new HashMap(userdata)))){
			GridOutVO listOut = listOut(giVO);
			listOut.setResultMsg("이미 마감되었습니다.");
			return listOut;
		}
		
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				
				data.put("INOUT_CD", "02"); //출금
				data.put("USER_KEY", null); //사용자키
				data.put("CAL_YMD", calYmd); //날짜
				
				switch(GridUtil.getOperation(data)){
					case INSERT:
						String seq = inoutMapper.getMaxInoutSeq(data);
						data.put("INOUT_SEQ_NO", seq);
						data.put("CRE_ID", UserInfo.getUSER_ID());
						data.put("UPD_ID", UserInfo.getUSER_ID());
						
						
						inoutMapper.insertInout(data);
						break;
					case UPDATE:
						data.put("UPD_ID", UserInfo.getUSER_ID());
						
						inoutMapper.updateInout(data);
						break;
				}
			}
		}
		
		return listOut(giVO);
	}
	
	//지출삭제
	public GridOutVO deleteOut(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getDeldata();
		
		if(savedata != null && savedata.size() > 0){
			//마감여부조회
			if("Y".equals(settleService.getEndYnForPeriod(new HashMap(savedata.get(0))))){
				GridOutVO listOut = listOut(giVO);
				listOut.setResultMsg("이미 마감되었습니다.");
				return listOut;
			}
			
			for(Map<String, String> data : savedata){
				inoutMapper.deleteInout(data);
			}
		}
		
		return listOut(giVO);
	}	
}
