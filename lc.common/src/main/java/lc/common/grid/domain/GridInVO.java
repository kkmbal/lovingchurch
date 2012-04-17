package lc.common.grid.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.util.StringUtil;

public class GridInVO {
	private String page; //requested page
	private String rows; //grid rows
	private String sidx; //sort column
	private String sord; //sort direction
	private String nodeid; //tree
	private String parentid; //tree
	private String n_level; //tree
	private Map<String, String> userdata;
	private List<Map<String, String>> savedata;
	private List<Map<String, String>> deldata;
	
	
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getN_level() {
		return n_level;
	}
	public void setN_level(String nLevel) {
		n_level = nLevel;
	}
	public List<Map<String, String>> getDeldata() {
		return deldata;
	}
	public void setDeldata(List<Map<String, String>> deldata) {
		this.deldata = deldata;
	}
	public Map<String, String> getUserdata() {
		return userdata == null ? new HashMap<String, String>() : userdata;
	}
	public void setUserdata(Map<String, String> userdata) {
		this.userdata = userdata;
	}
	public List<Map<String, String>> getSavedata() {
		return savedata;
	}
	public void setSavedata(List<Map<String, String>> savedata) {
		this.savedata = savedata;
	}
	public String getPage() {
		return (StringUtil.isEmpty(page))?"1":page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return (StringUtil.isEmpty(rows))?"10":rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}

	
}
