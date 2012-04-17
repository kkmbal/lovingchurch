package lc.common.grid.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridOutVO {
	private String page;
	private String total;
	private String records;
	private String resultMsg;
	private Map<String, String> userdata;
//	private List<GridRow> rows;
	private List<? extends Map>  rows;
	
	private String item;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	
	
	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
		if(resultMsg != null){
			if(userdata == null) userdata = new HashMap<String, String>();
			userdata.put("resultMsg", resultMsg);
		}		
	}

	public Map<String, String> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, String> userdata) {
		if(!(userdata instanceof HashMap<?, ?>)) throw new RuntimeException("type error"); 
		if(this.userdata != null) this.userdata.putAll(userdata);
		else this.userdata = userdata;
	}

//	public List<GridRow> getRows() {
//		return rows;
//	}
//
//	public void setRows(List<GridRow> rows) {
//		this.rows = rows;
//	}

	public String getItem() {
		return item;
	}

	public List<? extends Map> getRows() {
		return rows;
	}

	public void setRows(List<? extends Map> rows) {
//		int i = 1;
//		for(Map row : rows){
//			row.put("id", String.valueOf(i++));
//		}
		this.rows = rows;
	}
	
	public void setItem(String item) {
		this.item = item;
	}

	public void setPaging(GridInVO giVO, Object cnt) {
		if(cnt != null && "".equals(cnt.toString())){
			throw new RuntimeException("ROWCNT is null.");
		}
		String rows = giVO.getRows();
		String page = giVO.getPage();
		int totalCnt = Integer.parseInt(cnt.toString());
		//(총카운트+출력될행수-1)/출력될행수
		int totalPage = totalCnt%Integer.parseInt(rows)==0 ? totalCnt/Integer.parseInt(rows) : (int)(totalCnt/Integer.parseInt(rows)) + 1;
		
		this.setPage(page);
		this.setTotal(String.valueOf(totalPage));
		this.setRecords(String.valueOf(totalCnt));
	}
	
	
}
