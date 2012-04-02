package lc.common.code.service;

import java.util.List;

import lc.common.code.domain.CommonCode;

public interface CommonCodeMapper {
	public List<CommonCode> listHighCommonCode(CommonCode code);
	public List<CommonCode> listSubCommonCode(CommonCode code);
	public CommonCode getCommonCode(CommonCode code);
	public List<CommonCode> listCommonCode(CommonCode code);
	public List<CommonCode> listSubCommonCodeAll(CommonCode code);
	public List<CommonCode> listHighCommonCodeAll(CommonCode code);
	public void insertCommonCode(CommonCode code);
	public void updateCommonCode(CommonCode code);
	public void deleteCommonCode(CommonCode code);
	public int getRowCount(CommonCode code);
}
