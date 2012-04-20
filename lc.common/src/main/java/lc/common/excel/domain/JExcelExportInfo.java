package lc.common.excel.domain;

import java.util.List;

public class JExcelExportInfo {
	private List<JExcelInfo> data;
	private List<JExcelListInfo> list; //세로
	
	public List<JExcelInfo> getData() {
		return data;
	}
	public void setData(List<JExcelInfo> data) {
		this.data = data;
	}
	public List<JExcelListInfo> getList() {
		return list;
	}
	public void setList(List<JExcelListInfo> list) {
		this.list = list;
	}
	

	
	
}
