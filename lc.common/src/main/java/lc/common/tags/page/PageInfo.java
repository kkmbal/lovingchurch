package lc.common.tags.page;

import lc.common.util.PagingHelper;

public abstract class PageInfo {
	private int ROWCOUNT;
	private int pageSize;
	private int currPage;
	private int listSize;
	
	//페이징 관련
	private String spaces;
	private int fromLimit;
	private int toLimit;
	
	//검색관련
	private String searchKeyword;
	private String searchValue;
	
	
	public String getSpaces() {
		return spaces;
	}
	public void setSpaces(String spaces) {
		this.spaces = spaces;
	}
	public int getFromLimit() {
		return fromLimit;
	}
	public void setFromLimit(int fromLimit) {
		this.fromLimit = fromLimit;
	}
	public int getToLimit() {
		return toLimit;
	}
	public void setToLimit(int toLimit) {
		this.toLimit = toLimit;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public int getROWCOUNT() {
		return ROWCOUNT;
	}
	public void setROWCOUNT(int rOWCOUNT) {
		ROWCOUNT = rOWCOUNT;
	}
	public int getPageSize() {
		return pageSize <= 0 ? PagingHelper._pageSize : pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getListSize() {
		return listSize <= 0 ? PagingHelper._listSize : listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
}
