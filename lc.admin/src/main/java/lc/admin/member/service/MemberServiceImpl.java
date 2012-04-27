package lc.admin.member.service;

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
import lc.common.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired(required=true)
	private MemberMapper	memberMapper;
	
	
	@Resource(name="lcSessionContext")
	private LcSessionContext lcSessionContext;
	
	
	//조회
	public GridOutVO listMember(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.putAll(GridUtil.getPager(giVO)); 
		List<Map> list = memberMapper.listMember(userdata);
		int rowCnt = memberMapper.listMemberCount(userdata);
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
	//저장
	public GridOutVO saveMember(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getSavedata();
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				switch(GridUtil.getOperation(data)){
					case INSERT:
						try{
							String sec = SecurityUtil.encrypt(String.valueOf(Math.random()));
							data.put("USER_ID", sec);
							data.put("PASSWORD", sec);
						}catch(Exception e){
							String sec = "love"+String.valueOf(Math.random());
							data.put("USER_ID", sec);
							data.put("PASSWORD", sec);
						}
						data.put("CRE_ID", UserInfo.getUSER_ID());
						data.put("UPD_ID", UserInfo.getUSER_ID());
						data.put("USE_YN", "Y");
						
						memberMapper.insertMember(data);
						break;
					case UPDATE:
						data.put("UPD_ID", UserInfo.getUSER_ID());
						
						memberMapper.updateMember(data);
						break;
				}
			}
		}
		
		return listMember(giVO);
	}
	
	//삭제
	public GridOutVO deleteMember(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getDeldata();
		
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				memberMapper.deleteMember(data);
			}
		}
		
		return listMember(giVO);
	}
	
	public List<Map> listAllMember(HashMap map) throws Exception{
		return memberMapper.listAllMember(map);
	}
			
}
