package lc.common.code.service;

import java.util.ArrayList;
import java.util.List;

import lc.common.code.domain.CommonCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commonCodeService")
public class CommonCodeServiceImpl implements CommonCodeService {

	//@Resource(name="commonCodeMapper")
	@Autowired(required=true)
	private CommonCodeMapper commonCodeMapper;
	
	public List<CommonCode> listHighCommonCode(CommonCode code) throws Exception {
		code.setType("high");
		List<CommonCode> resultList	= new ArrayList<CommonCode>();
		resultList = commonCodeMapper.listHighCommonCode(code);
		int rowCount = commonCodeMapper.getRowCount(code);
		for(CommonCode b : resultList){
			b.setROWCOUNT(rowCount);
		} 
		return resultList;
	}
	
	public List<CommonCode> listSubCommonCode(CommonCode code) throws Exception {
		code.setType("sub");
		List<CommonCode> resultList	= new ArrayList<CommonCode>();
		resultList = commonCodeMapper.listSubCommonCode(code);
		int rowCount = commonCodeMapper.getRowCount(code);
		for(CommonCode b : resultList){
			b.setROWCOUNT(rowCount);
		} 
		return resultList;
	}
	public CommonCode getCommonCode(CommonCode code) throws Exception {
		return commonCodeMapper.getCommonCode(code);
	}
	//@Transactional
	public List<CommonCode> listCommonCode(CommonCode code) throws Exception {
		return commonCodeMapper.listCommonCode(code);
	}
	public List<CommonCode> listHighCommonCodeAll(CommonCode code) throws Exception {
		return commonCodeMapper.listHighCommonCodeAll(code);
	}
	public List<CommonCode> listSubCommonCodeAll(CommonCode code) throws Exception {
		return commonCodeMapper.listSubCommonCodeAll(code);
	}

	public void insertCommonCode(CommonCode code) throws Exception {
		commonCodeMapper.insertCommonCode(code);
	}

	public void updateCommonCode(CommonCode code) throws Exception {
		commonCodeMapper.updateCommonCode(code);
	}

	public void deleteCommonCode(CommonCode code) throws Exception {
		commonCodeMapper.deleteCommonCode(code);
	}
}
