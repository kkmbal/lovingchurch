package lc.admin.member.service;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
	//조회
	public List<Map> listMember(Map map) throws Exception;
	//ROWCNT조회
	public int listMemberCount(Map map) throws Exception;
	//코드추가
	public void insertMember(Map map) throws Exception;
	//코드수정
	public void updateMember(Map map) throws Exception;
	//코드삭제
	public void deleteMember(Map map) throws Exception;
}
