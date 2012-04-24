package lc.common.code.service.grid;

import java.util.List;
import java.util.Map;

public interface GridCommonCodeMapper {
	//그룹코드리스트조회
	public List<Map> listGroup(Map map) throws Exception;
	//코드추가
	public void insertCode(Map map) throws Exception;
	//코드수정
	public void updateCode(Map map) throws Exception;
	//코드삭제
	public void deleteCode(Map map) throws Exception;
	//상세코드리스트조회
	public List<Map> listDetailCode(Map map) throws Exception;
	//상세코드유무조회
	public int getForDeleteCode(Map data) throws Exception;	
	//하위코드조회
	public List<Map> getCommSubCd(Map map) throws Exception;
	
	public int listGroupCount(Map map) throws Exception;
}
