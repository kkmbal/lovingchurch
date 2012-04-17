package lc.common.board.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.LcConstants;
import lc.common.LcConstants.LOC;
import lc.common.board.domain.Board;
import lc.common.board.domain.BoardFile;
import lc.common.board.service.BoardService;
import lc.common.exception.LcException;
import lc.common.login.domain.UserInfo;
import lc.common.web.action.BasicActionSupport;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * Struts2 Action
 * 1.POJO 객체
 * 2.com.opensymphony.xwork2.Action 인터페이스 구현
 * 3.com.opensymphony.xwork2.ActionSupport 클래스 상속
 * +알파 : ModelDriven 구현하면 board.TITLE => TITLE 으로 쓸수 있음.
 */

//@Scope("prototype")
//@Service("boardAction")
public abstract class BoardAction  extends BasicActionSupport implements ModelDriven<Board>{
	//private Logger logger = LogManager.getLogger(getClass());
	protected Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	private String BOARD_CAT_CD;
	private String BOARD_SITE;
	private String BOARD_LOC;
	//protected String BOARD_PART_CD;

	private Board board;
	private Board boardPrev;
	private Board boardNext;
	
	private List<Board> boardList;
	private List<Board> boardLocateList;
	private List<BoardFile> boardFileList;
	
	//file upload 관련 (multiple)
    private List<File> upload = new ArrayList<File>();;
    private List<String> contentType = new ArrayList<String>();
    private List<String> fileName = new ArrayList<String>();
	
	
	public BoardAction(){
		board = new Board();
	}
	
	private  void saveCategorySeq(){
		this.BOARD_CAT_CD = getBoardCatCd();
		this.BOARD_SITE = getBoardSite();
		this.BOARD_LOC = getBoardLoc();
		board.setBOARD_CAT_CD(this.BOARD_CAT_CD);
		board.setVIEW_SITE(this.BOARD_SITE);
		board.setVIEW_LOC(this.BOARD_LOC);
	}
	
	protected abstract String getBoardCatCd();
	protected abstract String getBoardSite();
	protected abstract String getBoardLoc();
	
	public String list() throws LcException {
		try{
			logger.debug("list.....");
			saveCategorySeq();
			
			//게시물리스트
			setBoardLimit(board);
			boardList = boardService.listBoard(board);
	
			
			if(boardList != null && boardList.size() > 0){
				board.setROWCOUNT(((Board)boardList.get(0)).getROWCOUNT());
			}
			int idx = 0;
			for(Board b : boardList){
				StringBuilder sb = new StringBuilder();
				for(int i = 0 ; i< b.getRELEVEL();i++){
					sb.append("&nbsp;&nbsp;");
				}
				b.setSpaces(sb.toString());
				
				if(b.getCONTENT() != null){
					b.setCONTENT(b.getCONTENT().replaceAll("\n", "</br>"));
				}
				/*
				if(b.getTITLE() != null && b.getTITLE().length() > 10){
					b.setTITLE2(StringUtils.substring(b.getTITLE(), 0, 10) + "...");
				}
				*/
				if(b.getTITLE() != null){
					//b.setTITLE2(com.mvoipcenter.common.util.StringUtil.cutTailHan( b.getTITLE(), 30));
				}
				
				//b.setBOARD_NO((board.getFromLimit()+1) * idx++);
				b.setBOARD_NO(board.getROWCOUNT() - board.getFromLimit() - idx++);
			}
			
			//특정위치에 보이는 리스트
			Board locBoard = new Board();
			locBoard.setBOARD_CAT_CD(board.getBOARD_CAT_CD());
			locBoard.setBOARD_PART_CD(board.getBOARD_PART_CD());
			locBoard.setToLimit(5);
			locBoard.setVIEW_SITE(board.getVIEW_SITE());
			locBoard.setVIEW_LOC(LOC.list_top.toString());
			boardLocateList = boardService.listMainBoard(locBoard);
			for(Board b : boardLocateList){
				if(b.getCONTENT() != null){
					b.setCONTENT(b.getCONTENT().replaceAll("\n", "</br>"));
				}				
			}
			
			return "list";
		}catch(Exception e){
			logger.error("list error", e);
			throw new LcException(this, e);
		}
	}
	
	public String view() throws LcException{
		try{
			saveCategorySeq();
			
			Map session = ActionContext.getContext().getSession();
			UserInfo userInfo = (UserInfo)MapUtils.getObject(session, "userInfo", new UserInfo());
			board.setUPD_ID(userInfo.getUSER_ID());
			
			boardPrev = new Board();
			boardNext = new Board();
			
			board.setBOARD_PART_CD(null);
			boardPrev = boardService.getBoardPrev(board);
			if(boardPrev != null && boardPrev.getTITLE() != null) {
				//boardPrev.setTITLE2( com.mvoipcenter.common.util.StringUtil.cutTailHan(boardPrev.getTITLE(), 45));
			}
			boardNext = boardService.getBoardNext(board);
			if(boardNext != null && boardNext.getTITLE() != null) {
				//boardNext.setTITLE2( com.mvoipcenter.common.util.StringUtil.cutTailHan(boardNext.getTITLE(), 45));
			}		
			
			//VIEW_SITE 를 현재 게시물의 정보를 다가져와서 비교함에 따라 이전/다음글이 안나올수 있으므로 이전글, 다음글 정보뒤로 이동
			board = boardService.getBoard(board);
			
			if(board != null){
				if(board.getCONTENT() != null){
					board.setCONTENT(board.getCONTENT().replaceAll("\n", "</br>"));
				}
				if(board.getVIEW_LOC() != null){
					board.setVIEW_LOC(board.getVIEW_LOC().replaceAll(LOC.main.toString(), "메인"));
					board.setVIEW_LOC(board.getVIEW_LOC().replaceAll(LOC.list_top.toString(), "리스트상단"));
				}
				
				//첨부파일리스트
				BoardFile boardFile = new BoardFile();
				boardFile.setBOARD_CAT_CD(board.getBOARD_CAT_CD());
				boardFile.setBOARD_SEQ(board.getBOARD_SEQ());
				boardFileList = boardService.listBoardFile(boardFile);
			}
		}catch(Exception e){
			logger.error("view error", e);
			throw new LcException(this, e);
		}
		
		return "view";
	}
	
	public String viewEdit() throws LcException{
		try{
			saveCategorySeq();
			
			board = boardService.getBoard(board);
			
			if(board != null){
				//첨부파일리스트
				BoardFile boardFile = new BoardFile();
				boardFile.setBOARD_CAT_CD(board.getBOARD_CAT_CD());
				boardFile.setBOARD_SEQ(board.getBOARD_SEQ());
				boardFileList = boardService.listBoardFile(boardFile);
			}
		}catch(Exception e){
			logger.error("edit error", e);
			throw new LcException(this, e);
		}
		return "viewEdit";
	}
	
	public String viewInsert() throws LcException{
		return "viewInsert";
	}
	
	public String insert() throws LcException{
		try{
			saveCategorySeq();
			
			Map session = ActionContext.getContext().getSession();
			UserInfo userInfo = (UserInfo)MapUtils.getObject(session, "userInfo", new UserInfo());
			
			Map<String, String> parameterMap = this.getParameterMap();
			board.setVIEW_SITE(parameterMap.get("VIEW_SITE"));
			board.setVIEW_LOC(parameterMap.get("VIEW_LOC"));
			
			board.setCRE_ID(userInfo.getUSER_ID());
			board.setUPD_ID(userInfo.getUSER_ID());
			
			fileHandle();
			
			//신규저장
			boardService.insertBoard(board);
		}catch(Exception e){
			logger.error("save error", e);
			throw new LcException(this, e);
		}
		return "save";
	}
	
	public String update() throws LcException{
		try{
			saveCategorySeq();
			
			Map session = ActionContext.getContext().getSession();
			UserInfo userInfo = (UserInfo)MapUtils.getObject(session, "userInfo", new UserInfo());
			
			Map<String, String> parameterMap = this.getParameterMap();
			board.setVIEW_SITE(parameterMap.get("VIEW_SITE"));
			board.setVIEW_LOC(parameterMap.get("VIEW_LOC"));
			
			
			board.setCRE_ID(userInfo.getUSER_ID());
			board.setUPD_ID(userInfo.getUSER_ID());
			
			fileHandle();
			
			//글수정
			boardService.updateBoard(board);
		}catch(Exception e){
			logger.error("save error", e);
			throw new LcException(this, e);
		}
		return "save";
	}
	
	public String reply() throws LcException{
		try{
			saveCategorySeq();
			
			Map session = ActionContext.getContext().getSession();
			UserInfo userInfo = (UserInfo)MapUtils.getObject(session, "userInfo", new UserInfo());
			
			board.setCRE_ID(userInfo.getUSER_ID());
			board.setUPD_ID(userInfo.getUSER_ID());
			
			fileHandle();
			
			//답변저장
			boardService.replyBoard(board);
		}catch(Exception e){
			logger.error("save error", e);
			throw new LcException(this, e);
		}
		return "save";
	}
	
	public String delete() throws LcException{
		try{
			saveCategorySeq();
			
			Map session = ActionContext.getContext().getSession();
			UserInfo userInfo = (UserInfo)MapUtils.getObject(session, "userInfo", new UserInfo());
			
			board.setCRE_ID(userInfo.getUSER_ID());
			board.setUPD_ID(userInfo.getUSER_ID());
			
			fileHandle();
			
			//글삭제
			boardService.deleteBoard(board);
		}catch(Exception e){
			logger.error("save error", e);
			throw new LcException(this, e);
		}
		return "save";
	}
	
	public String deleteMulti() throws LcException{
		try{
			saveCategorySeq();
			
			Map session = ActionContext.getContext().getSession();
			UserInfo userInfo = (UserInfo)MapUtils.getObject(session, "userInfo", new UserInfo());
			
			board.setCRE_ID(userInfo.getUSER_ID());
			board.setUPD_ID(userInfo.getUSER_ID());
			
			fileHandle();
			
			//글삭제
			boardService.deleteMulti(board);
		}catch(Exception e){
			logger.error("save error", e);
			throw new LcException(this, e);
		}
		return "save";
	}
	
	private void fileHandle() throws LcException{
        logger.debug("\n\n upload1");
        logger.debug("files:");
        
        try{
	        List<BoardFile> fileList = new ArrayList<BoardFile>();
	        
	        for (File u: upload) {
	        	if(u != null){
		        	BoardFile boardFile = new BoardFile();
		        	boardFile.setFILE_CONTENT(FileCopyUtils.copyToByteArray(u));
		        	boardFile.setFILE_PATH(u.toString());
		        	boardFile.setFILE_SIZE((int)u.length());
		        	fileList.add(boardFile);
	        	}
	        }
	        int idx = 0;
	        for (String n: fileName) {
	        	if(n != null){
	        		fileList.get(idx++).setFILE_NAME(n);
	        	}
	        }
	        logger.debug("content types:");
	        idx = 0;
	        for (String c: contentType) {
	        	if(c != null){
	        		fileList.get(idx++).setFILE_TYPE(c);
	        	}
	        }
	        if(fileList.size() > 0){
	        	board.setBoardFile(fileList);
	        }
		}catch(Exception e){
			logger.error("fileHandle error", e);
			throw new LcException(this, e);
		}
	}
	
	private void fileHandle2() throws LcException{
        logger.debug("\n\n upload1");
        logger.debug("files:");
        
        try{
        	//String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "board_file";
        	String rootPath = LcConstants.BOARD_FILE_PATH;
        	
	        List<BoardFile> fileList = new ArrayList<BoardFile>();
	        
	        for (File u: upload) {
	        	if(u != null){
		        	BoardFile boardFile = new BoardFile();
		        	boardFile.setFILE_CONTENT(null);
		        	boardFile.setFILE_SIZE((int)u.length());
		        	
		        	File newFile = new File(rootPath + File.separator + u.getName());
		        	u.renameTo(newFile);
		        	
		        	//boardFile.setFILE_PATH("/WEB-INF" + File.separator + "board_file" + File.separator + u.getName());
		        	boardFile.setFILE_PATH(rootPath + File.separator + u.getName());
		        	fileList.add(boardFile);
	        	}
	        }
	        int idx = 0;
	        for (String n: fileName) {
	        	if(n != null){
	        		fileList.get(idx++).setFILE_NAME(n);
	        	}
	        }
	        logger.debug("content types:");
	        idx = 0;
	        for (String c: contentType) {
	        	if(c != null){
	        		fileList.get(idx++).setFILE_TYPE(c);
	        	}
	        }
	        if(fileList.size() > 0){
	        	board.setBoardFile(fileList);
	        }
		}catch(Exception e){
			logger.error("fileHandle error", e);
			throw new LcException(this, e);
		}
	}	

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<Board> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}

	public List<BoardFile> getBoardFileList() {
		return boardFileList;
	}

	public void setBoardFileList(List<BoardFile> boardFileList) {
		this.boardFileList = boardFileList;
	}

	public Board getBoardPrev() {
		return boardPrev;
	}

	public void setBoardPrev(Board boardPrev) {
		this.boardPrev = boardPrev;
	}

	public Board getBoardNext() {
		return boardNext;
	}

	public void setBoardNext(Board boardNext) {
		this.boardNext = boardNext;
	}

	public String getBOARD_CAT_CD() {
		return BOARD_CAT_CD;
	}

	public void setBOARD_CAT_CD(String BOARD_CAT_CD) {
		this.BOARD_CAT_CD = BOARD_CAT_CD;
	}

	public String getBOARD_SITE() {
		return BOARD_SITE;
	}

	public void setBOARD_SITE(String bOARD_SITE) {
		BOARD_SITE = bOARD_SITE;
	}

	public String getBOARD_LOC() {
		return BOARD_LOC;
	}

	public void setBOARD_LOC(String bOARD_LOC) {
		BOARD_LOC = bOARD_LOC;
	}

	public List<Board> getBoardLocateList() {
		return boardLocateList;
	}

	public void setBoardLocateList(List<Board> boardLocateList) {
		this.boardLocateList = boardLocateList;
	}

	//@Override
	public Board getModel() {
		return board;
	}

	/**
	 * form 의  file type의 name=upload 일때 
	 */
	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public void setUploadContentType(List<String> contentType) {
		this.contentType = contentType;
	}

	public void setUploadFileName(List<String> fileName) {
		this.fileName = fileName;
	}
	public List<File> getUpload() {
		return this.upload;
	}
	
	public List<String> getUploadContentType() {
		return this.contentType;
	}
	
	public List<String> getUploadFileName() {
		return this.fileName;
	}

	
}
