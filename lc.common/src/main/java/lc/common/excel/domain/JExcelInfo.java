package lc.common.excel.domain;


public class JExcelInfo {
	private int colIdx;
	private int rowIdx;
	private String content;
	
	public JExcelInfo(){}
	
	public JExcelInfo(int colIdx, int rowIdx, String content){
		this.colIdx = colIdx;
		this.rowIdx = rowIdx;
		this.content = content;
	}

	public JExcelInfo(String content){
		this.content = content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	
}
