package lc.common.board.domain;

import java.util.List;

import lc.common.tags.page.PageInfo;

public class Board extends PageInfo{
	private int BOARD_NO;
	private String BOARD_CAT_CD;
	private String BOARD_PART_CD;
	private String BOARD_PART_CD_NM;
	private String BOARD_SEQ;
	private String TITLE;
	private String TITLE2;
	private String CONTENT;
	private String PASSWORD;
	private String HIT_CNT;
	private String FILE_SEQ;
	private String CRE_ID;
	private String CRE_DATE;
	private String CRE_DATE_TIME;
	private String UPD_ID;
	private String UPD_DATE;
	private String UPD_DATE_TIME;
	private String REF;
	private String VIEW_SITE;
	private String VIEW_SITE1;
	private String VIEW_SITE2;
	private String VIEW_LOC;
	private String VIEW_LOC1;
	private String VIEW_LOC2;
	private int RESTEP;
	private int RELEVEL;
	
	private String BOARD_SEQ_DEL;

	
	//첨부파일관련
	private List<BoardFile> boardFile;
	private int FILE_CNT;
	
	
	
	public String getBOARD_SEQ_DEL() {
		return BOARD_SEQ_DEL;
	}
	public void setBOARD_SEQ_DEL(String bOARD_SEQ_DEL) {
		BOARD_SEQ_DEL = bOARD_SEQ_DEL;
	}
	public int getBOARD_NO() {
		return BOARD_NO;
	}
	public void setBOARD_NO(int bOARD_NO) {
		BOARD_NO = bOARD_NO;
	}
	public String getTITLE2() {
		return TITLE2;
	}
	public void setTITLE2(String tITLE2) {
		TITLE2 = tITLE2;
	}
	public String getVIEW_SITE1() {
		return VIEW_SITE1;
	}
	public void setVIEW_SITE1(String vIEW_SITE1) {
		VIEW_SITE1 = vIEW_SITE1;
	}
	public String getVIEW_SITE2() {
		return VIEW_SITE2;
	}
	public void setVIEW_SITE2(String vIEW_SITE2) {
		VIEW_SITE2 = vIEW_SITE2;
	}
	public String getVIEW_LOC1() {
		return VIEW_LOC1;
	}
	public void setVIEW_LOC1(String vIEW_LOC1) {
		VIEW_LOC1 = vIEW_LOC1;
	}
	public String getVIEW_LOC2() {
		return VIEW_LOC2;
	}
	public void setVIEW_LOC2(String vIEW_LOC2) {
		VIEW_LOC2 = vIEW_LOC2;
	}
	public String getCRE_DATE_TIME() {
		return CRE_DATE_TIME;
	}
	public void setCRE_DATE_TIME(String cRE_DATE_TIME) {
		CRE_DATE_TIME = cRE_DATE_TIME;
	}
	public String getUPD_DATE_TIME() {
		return UPD_DATE_TIME;
	}
	public void setUPD_DATE_TIME(String uPD_DATE_TIME) {
		UPD_DATE_TIME = uPD_DATE_TIME;
	}
	public int getFILE_CNT() {
		return FILE_CNT;
	}
	public void setFILE_CNT(int fILE_CNT) {
		FILE_CNT = fILE_CNT;
	}
	public String getBOARD_PART_CD_NM() {
		return BOARD_PART_CD_NM;
	}
	public void setBOARD_PART_CD_NM(String bOARDPARTCDNM) {
		BOARD_PART_CD_NM = bOARDPARTCDNM;
	}
	public String getBOARD_PART_CD() {
		return BOARD_PART_CD;
	}
	public void setBOARD_PART_CD(String bOARDPARTCD) {
		BOARD_PART_CD = bOARDPARTCD;
	}
	public String getVIEW_SITE() {
		return VIEW_SITE;
	}
	public void setVIEW_SITE(String vIEWSITE) {
		VIEW_SITE = vIEWSITE;
	}
	public String getVIEW_LOC() {
		return VIEW_LOC;
	}
	public void setVIEW_LOC(String vIEWLOC) {
		VIEW_LOC = vIEWLOC;
	}
	public List<BoardFile> getBoardFile() {
		return boardFile;
	}
	public void setBoardFile(List<BoardFile> boardFile) {
		this.boardFile = boardFile;
	}
	public String getBOARD_CAT_CD() {
		return BOARD_CAT_CD;
	}
	public void setBOARD_CAT_CD(String cAT_SEQ) {
		BOARD_CAT_CD = cAT_SEQ;
	}
	public String getBOARD_SEQ() {
		return BOARD_SEQ;
	}
	public void setBOARD_SEQ(String bOARD_SEQ) {
		BOARD_SEQ = bOARD_SEQ;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getHIT_CNT() {
		return HIT_CNT;
	}
	public void setHIT_CNT(String hIT_CNT) {
		HIT_CNT = hIT_CNT;
	}
	public String getFILE_SEQ() {
		return FILE_SEQ;
	}
	public void setFILE_SEQ(String fILE_SEQ) {
		FILE_SEQ = fILE_SEQ;
	}
	public String getCRE_ID() {
		return CRE_ID;
	}
	public void setCRE_ID(String cRE_ID) {
		CRE_ID = cRE_ID;
	}
	public String getCRE_DATE() {
		return CRE_DATE;
	}
	public void setCRE_DATE(String cRE_DATE) {
		CRE_DATE = cRE_DATE;
	}
	public String getUPD_ID() {
		return UPD_ID;
	}
	public void setUPD_ID(String uPD_ID) {
		UPD_ID = uPD_ID;
	}
	public String getUPD_DATE() {
		return UPD_DATE;
	}
	public void setUPD_DATE(String uPD_DATE) {
		UPD_DATE = uPD_DATE;
	}
	public String getREF() {
		return REF;
	}
	public void setREF(String rEF) {
		REF = rEF;
	}
	public int getRESTEP() {
		return RESTEP;
	}
	public void setRESTEP(int rESTEP) {
		RESTEP = rESTEP;
	}
	public int getRELEVEL() {
		return RELEVEL;
	}
	public void setRELEVEL(int rELEVEL) {
		RELEVEL = rELEVEL;
	}
	
	
	
}
