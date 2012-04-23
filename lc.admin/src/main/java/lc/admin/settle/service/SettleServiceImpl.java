package lc.admin.settle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.grid.domain.GridInVO;
import lc.common.grid.domain.GridOutVO;
import lc.common.login.web.controller.LcSessionContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("settleService")
public class SettleServiceImpl implements SettleService {

	@Autowired(required=true)
	private SettleMapper	settleMapper;
	
	
	//주간결산 수입내역조회
	public GridOutVO listIn(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.put("INOUT_CD", "01"); //입금
		
		List<Map> list = settleMapper.listIn(userdata);
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
	
	//주간결산 지출내역조회
	public GridOutVO listOut(GridInVO giVO) throws Exception{
		Map<String, String> userdata = giVO.getUserdata();
		userdata.put("INOUT_CD", "02"); //출금
		
		List<Map> list = settleMapper.listOut(userdata);
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
	
	//주간결산 합계조회
	public Map getInoutSum(HashMap map) throws Exception{
		return settleMapper.getInoutSum(map);
	}
	
	// ----------------------------------------------------------------------------------
	
		
}
