package lc.common.excel.domain;

import java.util.ArrayList;
import java.util.List;

public class JExcelExportInfo {
	private List<JExcelInfo> data;
	private List<JExcelListInfo> list; //세로
	
	public JExcelExportInfo(){
		data = new ArrayList<JExcelInfo>();
		list = new ArrayList<JExcelListInfo>();
	}
	public List<JExcelInfo> getData() {
		return data;
	}
	public void setData(JExcelInfo data) {
		this.data.add(data);
	}
	public List<JExcelListInfo> getList() {
		return list;
	}
	public void setList(JExcelListInfo list) {
		this.list.add(list);
	}
	
	
}
