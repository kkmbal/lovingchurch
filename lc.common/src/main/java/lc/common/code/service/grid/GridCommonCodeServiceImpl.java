package lc.common.code.service.grid;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gridCommonCodeService")
public class GridCommonCodeServiceImpl implements GridCommonCodeService {

	@Autowired(required=true)
	private GridCommonCodeMapper	gridCommonCodeMapper;
	
	
	@Resource(name="lcSessionContext")
	private LcSessionContext lcSessionContext;
	
	
	//그룹코드리스트조회
	public GridOutVO listGroup(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.putAll(GridUtil.getPager(giVO)); 
		List<Map> list = gridCommonCodeMapper.listGroup(userdata);
		int rowCnt = gridCommonCodeMapper.listGroupCount(userdata);
		
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
	//그룹코드저장
	public GridOutVO saveGroupCode(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getSavedata();
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				switch(GridUtil.getOperation(data)){
					case INSERT:
						//Long codeKey = null;//sequenceMapper.getSequence(Sequence.COMNCD_SEQ).getNextId();
						//data.put("COMN_CD_KEY", codeKey.toString());
						data.put("CREATR_KEY", UserInfo.getUSER_ID());
						data.put("ALTRPSN_KEY", UserInfo.getUSER_ID());
						data.put("USE_YN", "Y");
						data.put("GRP_CD", "GRP_CD");
						
						gridCommonCodeMapper.insertCode(data);
						break;
					case UPDATE:
						data.put("ALTRPSN_KEY", UserInfo.getUSER_ID());
						
						gridCommonCodeMapper.updateCode(data);
						break;
				}
			}
		}
		
		return listGroup(giVO);
	}
	//그룹코드삭제
	public GridOutVO deleteGroupCode(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getDeldata();
		String resultMsg = null;
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				int count = gridCommonCodeMapper.getForDeleteCode(data);
				if(count > 0){
					resultMsg = "사용중인 상세코드가 존재합니다.";
					break;
				}else{
					data.put("ALTRPSN_KEY", UserInfo.getUSER_ID());
					data.put("USE_YN", "N");
					
					gridCommonCodeMapper.deleteCode(data);
				}
			}
		}
		
		GridOutVO listGroup = listGroup(giVO);
		if(resultMsg != null){
			listGroup.setResultMsg(resultMsg);
		}
		return listGroup;
	}
	//상세코드리스트조회
	public GridOutVO listDetailCode(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		List<Map> list = gridCommonCodeMapper.listDetailCode(userdata);
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
	//상세코드저장
	public GridOutVO saveDetailCode(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getSavedata();
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				switch(GridUtil.getOperation(data)){
					case INSERT:
						//Long codeKey = null;//sequenceMapper.getSequence(Sequence.COMNCD_SEQ).getNextId();
						//data.put("COMN_CD_KEY", codeKey.toString());
						data.put("CREATR_KEY", UserInfo.getUSER_ID());
						data.put("ALTRPSN_KEY", UserInfo.getUSER_ID());
						data.put("USE_YN", "Y");
						
						gridCommonCodeMapper.insertCode(data);
						break;
					case UPDATE:
						data.put("ALTRPSN_KEY", UserInfo.getUSER_ID());
						
						gridCommonCodeMapper.updateCode(data);
						break;
				}
			}
		}
		
		return listDetailCode(giVO);
	}
	//상세코드삭제
	public GridOutVO deleteDetailCode(GridInVO giVO) throws Exception{
		UserInfo UserInfo = lcSessionContext.getUserInfo();
		
		List<Map<String, String>> savedata = giVO.getDeldata();
		
		if(savedata != null && savedata.size() > 0){
			for(Map<String, String> data : savedata){
				data.put("ALTRPSN_KEY", UserInfo.getUSER_ID());
				data.put("USE_YN", "N");
				
				gridCommonCodeMapper.deleteCode(data);
			}
		}
		
		return listDetailCode(giVO);
	}
	
	//하위코드조회
	public List<Map> getCommSubCd(HashMap map) throws Exception{
		return gridCommonCodeMapper.getCommSubCd(map);
	}
}
