package lc.common.code.service;

import java.util.List;

import lc.common.code.domain.CommonCode;

public interface CommonCodeService {
	public List<CommonCode> listHighCommonCode(CommonCode code) throws Exception;
	public List<CommonCode> listSubCommonCode(CommonCode code) throws Exception;
	public CommonCode getCommonCode(CommonCode code) throws Exception;
	public List<CommonCode> listCommonCode(CommonCode code) throws Exception;
	public List<CommonCode> listSubCommonCodeAll(CommonCode code) throws Exception;
	public List<CommonCode> listHighCommonCodeAll(CommonCode code) throws Exception;
	public void insertCommonCode(CommonCode code) throws Exception;
	public void updateCommonCode(CommonCode code) throws Exception;
	public void deleteCommonCode(CommonCode code) throws Exception;
}
