package lc.common.util;

public class PagingHelper { 
	private int totalPage;
	private int currPage;
	private int startPage;
	private int endPage;
	private int prevPage;
	private int nextPage;
	private int prevPageGroup;
	private int nextPageGroup;
	public static final int _pageSize = 10; //가로
	public static final int  _listSize = 10; //세로
	private String action;
	private String contextPath;
	private int searchListSize;
	private int searchPageSize;
	
	public PagingHelper(String contextPath, String action, int rowCount, int currPage){
		this.action = action;
		this.contextPath = contextPath;
		setPaging(rowCount, currPage);
	}
	
	public PagingHelper(String contextPath, String action, int rowCount, int currPage, int listSize, int pageSize){
		this.action = action;
		this.contextPath = contextPath;
		this.searchListSize = listSize;
		this.searchPageSize = pageSize;
		setPaging(rowCount, currPage);
	}

	/**
	 * startPage = 1 부터 시작함.
	 * endPage = 필요한 것보다 1더 크게 설정함.
	 */
	private void setPaging(int rowCount, int currPage) {
		//System.out.println("currPage====="+currPage);
		this.currPage = currPage;
		this.totalPage = rowCount%getSearchListSize()==0 ? rowCount/getSearchListSize() : (int)(rowCount/getSearchListSize()) + 1;
		this.startPage = currPage%getSearchPageSize()==0 ? (int)(currPage/getSearchPageSize()-1)*getSearchPageSize() + 1 : (int)(currPage/getSearchPageSize())*getSearchPageSize() + 1; 
		this.endPage = startPage + getSearchPageSize();
		
		if(startPage < 0) {
			startPage = 1;
			endPage = 1;
		}
		
		if(endPage > totalPage) this.endPage = totalPage + 1;
		//this.nextPage = currPage < totalPage ? currPage+1 : 0;
		//this.prevPage = currPage > 1 ? currPage - 1 : 0;
		
		
		this.nextPageGroup = endPage <= totalPage ? endPage : 0;
		this.prevPageGroup = startPage > 0 ? startPage - 1 : 0;
		
//		System.out.println("===================");
//		System.out.println("rowCount====="+rowCount);
//		System.out.println("listSize====="+listSize);
//		System.out.println("totalPage====="+totalPage);
//		System.out.println("startPage====="+startPage);
//		System.out.println("endPage====="+endPage);
//		System.out.println("prevPageGroup====="+prevPageGroup);
//		System.out.println("nextPageGroup====="+nextPageGroup);
	}
	
	public String getPaging(){
		StringBuilder sb = new StringBuilder();
		for(int i = startPage ; i < endPage ; i++){
			if(i == currPage){
				sb.append("<strong>").append(i).append("</strong>");
			}else{
//				sb.append("<a href='/mvoip_m/listBoard.do?board.currPage="+i+"'>"+i+"</a>");
				sb.append("<a href='"+action+"&currPage="+i+"'>"+i+"</a>");
			}
			sb.append(" ");
		}
		return sb.toString();
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public String getPrevPage() {
		return prevPage > 0 ? "&lt;" : "";
	}

	public String getNextPage() {
		return nextPage > 0 ? "&gt;" : "";
	}

	public int __getPageSize() {
		return _pageSize;
	}

	public String getPrevPageGroup() {
		//return prevPageGroup > 0 ? "<a href='"+action+"?currPage="+prevPageGroup+"'>"+"&lt;"+"</a>" : "";
		return prevPageGroup > 0 ? "<a class=\"direction\" href='"+action+"&currPage="+prevPageGroup+"'><img src=\""+contextPath+"/img/icn/pre.gif\" alt=\"이전\" /></a>" : "";
	}

	public String getNextPageGroup() {
		//return nextPageGroup > 0 ? "<a href='"+action+"?currPage="+nextPageGroup+"'>"+"&gt;"+"</a>" : "";
		return nextPageGroup > 0 ? "<a class=\"direction\" href='"+action+"&currPage="+nextPageGroup+"'><img src=\""+contextPath+"/img/icn/next.gif\" alt=\"다음\" /></a>" : "";
	}

	public String getFirstPage(){
		//return prevPageGroup > 0 ?"<a href='"+action+"?currPage=1'>처음</a>" : "";
		return prevPageGroup > 0 ? "<a class=\"direction\" href='"+action+"&currPage=1'><img src=\""+contextPath+"/img/icn/first.gif\" alt=\"처음\" /></a>" : "";
	}
	
	public String getLastPage(){
		//return nextPageGroup > 0 ? "<a href='"+action+"?currPage="+totalPage+"'>마지막</a>" : "";
		return nextPageGroup > 0 ? "<a class=\"direction\" href='"+action+"&currPage="+totalPage+"'><img src=\""+contextPath+"/img/icn/end.gif\" alt=\"마지막\" /></a>" : "";
	}

	public int getSearchListSize() {
		return searchListSize <= 0 ? this._listSize : searchListSize;
	}

	public void setSearchListSize(int searchListSize) {
		this.searchListSize = searchListSize;
	}

	public int getSearchPageSize() {
		return searchPageSize <= 0 ? this._pageSize : searchPageSize;
	}

	public void setSearchPageSize(int searchPageSize) {
		this.searchPageSize = searchPageSize;
	}
	
	
}
