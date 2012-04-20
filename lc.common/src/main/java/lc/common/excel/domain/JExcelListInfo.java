package lc.common.excel.domain;

import java.util.List;

public class JExcelListInfo {
	private int colIdx;
	private int rowIdx;
	private List<List<JExcelInfo>> listJExcelInfo; //가로
	
	//public JExcelListInfo(){}
	
	public JExcelListInfo(int colIdx, int rowIdx, List<List<JExcelInfo>> listJExcelInfo){
		this.colIdx = colIdx;
		this.rowIdx = rowIdx;
		this.listJExcelInfo = listJExcelInfo;
	}

	public int getColIdx() {
		return colIdx;
	}

	public void setColIdx(int colIdx) {
		this.colIdx = colIdx;
	}

	public int getRowIdx() {
		return rowIdx;
	}

	public void setRowIdx(int rowIdx) {
		this.rowIdx = rowIdx;
	}

	public List<List<JExcelInfo>> getListJExcelInfo() {
		return listJExcelInfo;
	}

	public void setListJExcelInfo(List<List<JExcelInfo>> listJExcelInfo) {
		this.listJExcelInfo = listJExcelInfo;
	}
	
	
}
