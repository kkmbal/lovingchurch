package lc.common.excel.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JExcelExportInfo {
	private String fileName;
	private List<JExcelInfo> data;
	private List<JExcelListInfo> list; //세로
	private List<JExcelListInfo> list2; //세로
	
	public JExcelExportInfo(){
		this.data = new ArrayList<JExcelInfo>();
		this.list = new ArrayList<JExcelListInfo>();
		this.list2 = new ArrayList<JExcelListInfo>();
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<JExcelInfo> getData() {
		return data;
	}
	public void addData(int colIdx, int rowIdx, String content) {
		JExcelInfo data = new JExcelInfo(colIdx, rowIdx, content);
		this.data.add(data);
	}
	public List<JExcelListInfo> getList() {
		return list;
	}
	public void addList(int colIdx, int rowIdx, List<List<JExcelInfo>> listJExcelInfo) {
		JExcelListInfo list = new JExcelListInfo(colIdx, rowIdx, listJExcelInfo);
		this.list.add(list);
	}
	public void addList(int colIdx, int rowIdx, List<Map> listMap, String[] order) {
		List<List<JExcelInfo>> listJExcelInfo = new ArrayList<List<JExcelInfo>>();
    	List<JExcelInfo> iList = null;
		for(Map<String, ?> map : listMap){
			iList = new ArrayList<JExcelInfo>();
			for(String str : order){
				if(map.get(str) != null){
					iList.add(new JExcelInfo(map.get(str).toString()));
				}else{
					iList.add(new JExcelInfo(""));
				}
			}
			listJExcelInfo.add(iList);
		}
		JExcelListInfo list = new JExcelListInfo(colIdx, rowIdx, listJExcelInfo);
		this.list.add(list);
	}
	public List<JExcelListInfo> getList2() {
		return list2;
	}
	public void addList2(int colIdx, int rowIdx, List<List<JExcelInfo>> listJExcelInfo) {
		JExcelListInfo list2 = new JExcelListInfo(colIdx, rowIdx, listJExcelInfo);
		this.list2.add(list2);
	}
	public void addList2(int colIdx, int rowIdx, List<Map> listMap, String[] order) {
		List<List<JExcelInfo>> listJExcelInfo = new ArrayList<List<JExcelInfo>>();
    	List<JExcelInfo> iList = null;
		for(Map<String, ?> map : listMap){
			iList = new ArrayList<JExcelInfo>();
			for(String str : order){
				if(map.get(str) != null){
					iList.add(new JExcelInfo(map.get(str).toString()));
				}else{
					iList.add(new JExcelInfo(""));
				}
			}
			listJExcelInfo.add(iList);
		}
		JExcelListInfo list2 = new JExcelListInfo(colIdx, rowIdx, listJExcelInfo);
		this.list2.add(list2);
	}

	
	
}
