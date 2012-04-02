package lc.common.login.domain;

public class UserInfo {
	private String LOGIN_ID;
	private String PASSWORD;
	private String CUSTOMERID;
	private String CUSTOMERNM;
	private String CUST_ST_CD;
	
	private String ADMIN_NM;
	private String USE_YN;
	private String ADMIN_YN;
	
	
	public String getADMIN_YN() {
		return ADMIN_YN;
	}
	public void setADMIN_YN(String aDMIN_YN) {
		ADMIN_YN = aDMIN_YN;
	}
	public String getADMIN_NM() {
		return ADMIN_NM;
	}
	public void setADMIN_NM(String aDMINNM) {
		ADMIN_NM = aDMINNM;
	}
	public String getUSE_YN() {
		return USE_YN;
	}
	public void setUSE_YN(String uSEYN) {
		USE_YN = uSEYN;
	}
	public String getCUSTOMERNM() {
		return CUSTOMERNM;
	}
	public void setCUSTOMERNM(String cUSTOMERNM) {
		CUSTOMERNM = cUSTOMERNM;
	}
	public String getLOGIN_ID() {
		return LOGIN_ID;
	}
	public void setLOGIN_ID(String lOGINID) {
		LOGIN_ID = lOGINID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getCUSTOMERID() {
		return CUSTOMERID;
	}
	public void setCUSTOMERID(String cUSTOMERID) {
		CUSTOMERID = cUSTOMERID;
	}
	public String getCUST_ST_CD() {
		return CUST_ST_CD;
	}
	public void setCUST_ST_CD(String cUSTSTCD) {
		CUST_ST_CD = cUSTSTCD;
	}
	
	
}
