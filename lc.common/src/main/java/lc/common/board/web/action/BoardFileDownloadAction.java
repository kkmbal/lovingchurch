package lc.common.board.web.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import lc.common.board.domain.BoardFile;
import lc.common.board.service.BoardService;
import lc.common.web.action.BasicActionSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ModelDriven;

/**
 * Struts2 Action
 * 1.POJO 객체
 * 2.com.opensymphony.xwork2.Action 인터페이스 구현
 * 3.com.opensymphony.xwork2.ActionSupport 클래스 상속
 * +알파 : ModelDriven 구현하면 board.TITLE => TITLE 으로 쓸수 있음.
 */

@Scope("prototype")
@Service("boardDownloadAction")
public class BoardFileDownloadAction  extends BasicActionSupport implements ServletContextAware, ModelDriven<BoardFile>{
	//private Logger logger = LogManager.getLogger(getClass());
	protected Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	private BoardFile boardFile;
	
	private String fileName;
	
	private ServletContext servletContext; 
	
	public BoardFileDownloadAction(){
		boardFile = new BoardFile();
	}
	
	protected void saveCategorySeq(){}
	
    private String inputPath;
    
    public void setInputPath(String value) {
        inputPath = value;
    }

    public InputStream getInputStream() throws Exception {
        //return ServletActionContext.getServletContext().getResourceAsStream(inputPath);
    	return new ByteArrayInputStream(boardFile.getFILE_CONTENT());
    }
    
    public InputStream getInputStream2() throws Exception {
    	//return getServletContext().getResourceAsStream(boardFile.getFILE_PATH());
    	BufferedInputStream bf = new BufferedInputStream(new FileInputStream(boardFile.getFILE_PATH()));
    	return new ByteArrayInputStream(FileCopyUtils.copyToByteArray(bf));
    }

    public String execute() throws Exception {
    	saveCategorySeq();
    	
    	boardFile = boardService.getBoardFile(boardFile);
        return SUCCESS;
    }

	public String getFileName() {
		logger.debug("★★★"+boardFile.getFILE_NAME());
		logger.debug("★★★★★★★★★★★★★★★★★★★★★★★"+fileName);
		try{
			this.fileName = new String(boardFile.getFILE_NAME().getBytes("euc-kr"),"8859_1");
		}catch(Exception e){}
		return fileName;
	}

	public BoardFile getBoardFile() {
		return boardFile;
	}

	public void setBoardFile(BoardFile boardFile) {
		this.boardFile = boardFile;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BoardFile getModel() {
		return boardFile;
	}
	
    public ServletContext getServletContext() {
    	return servletContext;     
    }     
    
    public void setServletContext(ServletContext servletContext) {         
    	this.servletContext = servletContext;     
    }
}
