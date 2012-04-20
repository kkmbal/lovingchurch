package lc.common.excel.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import lc.common.board.service.BoardService;
import lc.common.excel.domain.JExcelInfo;
import lc.common.util.JExcelUtil;
import lc.common.web.action.BasicActionSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service("excelDownloadAction")
public class ExcelDownloadAction  extends BasicActionSupport implements ServletContextAware{
	//private Logger logger = LogManager.getLogger(getClass());
	protected Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	private String fileName;
	
	private byte[] outFileByte;
	
	private ServletContext servletContext; 
	
	public ExcelDownloadAction(){
	}
	
	protected void saveCategorySeq(){}
	
    private String inputPath;
    
    public void setInputPath(String value) {
        inputPath = value;
    }
    
    public InputStream getInputStream() throws Exception {
	    return new ByteArrayInputStream(getOutFileByte());
    }

    public String downloadBook5() throws Exception {
    	
    	List<JExcelInfo> listData = new ArrayList<JExcelInfo>();
    	JExcelInfo info = new JExcelInfo();
    	
    	info.setRowIdx(6);
    	info.setColIdx(1);
    	info.setContent("00시 ~ 01시");
    	listData.add(info);

    	info = new JExcelInfo();
    	info.setRowIdx(6);
    	info.setColIdx(2);
    	info.setContent("100");
    	listData.add(info);
    	
    	info = new JExcelInfo();
    	info.setRowIdx(6);
    	info.setColIdx(3);
    	info.setContent("9");
    	listData.add(info);
    	
    	info = new JExcelInfo();
    	info.setRowIdx(6);
    	info.setColIdx(4);
    	info.setContent("20");
    	listData.add(info);
    	
    	info = new JExcelInfo();
    	info.setRowIdx(6);
    	info.setColIdx(5);
    	info.setContent("5");
    	listData.add(info);
    	
    	//setOutFileByte(JExcelUtil.excelDown(fileName, listData));
    	
        return SUCCESS;
    }
    
    
    
    
    
    public String execute() throws Exception {
        return SUCCESS;
    }

	public String getFileName() {
		return fileName.endsWith(".xls")?fileName:fileName + ".xls";
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
    public ServletContext getServletContext() {
    	return servletContext;     
    }     
    
    public void setServletContext(ServletContext servletContext) {         
    	this.servletContext = servletContext;     
    }

	public byte[] getOutFileByte() {
		return outFileByte;
	}

	public void setOutFileByte(byte[] outFileByte) {
		this.outFileByte = outFileByte;
	}
    
    
}

