package lc.common.code.domain;

import lc.common.tags.page.PageInfo;

public class CommonCode extends PageInfo{
	private String CD_VAL;	    
	private String HICD_VAL	;
	private String CD_NM;		
	private String ORD_NO;	    
	private String USE_YN;	    
	private String ETC1_VAL;	
	private String ETC2_VAL;
	
	//페이징 관련
	private int fromLimit;
	private int toLimit;
	
	//검색관련
	private String searchKeyword;
	private String searchValue;
	
	//상위코드=high, 하위코드=sub
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFromLimit() {
		return fromLimit;
	}
	public void setFromLimit(int fromLimit) {
		this.fromLimit = fromLimit;
	}
	public int getToLimit() {
		return toLimit;
	}
	public void setToLimit(int toLimit) {
		this.toLimit = toLimit;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getCD_VAL() {
		return CD_VAL;
	}
	public void setCD_VAL(String cDVAL) {
		CD_VAL = cDVAL;
	}
	public String getHICD_VAL() {
		return HICD_VAL;
	}
	public void setHICD_VAL(String hICDVAL) {
		HICD_VAL = hICDVAL;
	}
	public String getCD_NM() {
		return CD_NM;
	}
	public void setCD_NM(String cDNM) {
		CD_NM = cDNM;
	}
	public String getORD_NO() {
		return ORD_NO;
	}
	public void setORD_NO(String oRDNO) {
		ORD_NO = oRDNO;
	}
	public String getUSE_YN() {
		return USE_YN;
	}
	public void setUSE_YN(String uSEYN) {
		USE_YN = uSEYN;
	}
	public String getETC1_VAL() {
		return ETC1_VAL;
	}
	public void setETC1_VAL(String eTC1VAL) {
		ETC1_VAL = eTC1VAL;
	}
	public String getETC2_VAL() {
		return ETC2_VAL;
	}
	public void setETC2_VAL(String eTC2VAL) {
		ETC2_VAL = eTC2VAL;
	}
	
	
}
