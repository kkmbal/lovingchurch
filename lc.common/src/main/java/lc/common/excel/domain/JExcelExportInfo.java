package lc.common.excel.domain;

import java.util.List;

public class JExcelExportInfo {
	private String fileName;
	private List<JExcelInfo> data;
	private List<JExcelListInfo> list; //세로
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
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
