package lc.common.login.domain;

import java.io.Serializable;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String USER_ID;
	private String PASSWORD;
	private String USER_NM;
	private String USE_YN;
	private String AUTH_CD;
	private String ADMIN_YN;
	
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getUSER_NM() {
		return USER_NM;
	}
	public void setUSER_NM(String uSER_NM) {
		USER_NM = uSER_NM;
	}
	public String getUSE_YN() {
		return USE_YN;
	}
	public void setUSE_YN(String uSE_YN) {
		USE_YN = uSE_YN;
	}
	public String getADMIN_YN() {
		return ADMIN_YN;
	}
	public void setADMIN_YN(String aDMIN_YN) {
		ADMIN_YN = aDMIN_YN;
	}
	public String getAUTH_CD() {
		return AUTH_CD;
	}
	public void setAUTH_CD(String aUTH_CD) {
		AUTH_CD = aUTH_CD;
	}
	
	

	
}
