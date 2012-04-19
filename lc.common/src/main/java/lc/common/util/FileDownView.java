package lc.common.util;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lc.common.excel.domain.JExcelInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 파일다운로드 View
 */
public class FileDownView extends AbstractView{
	Log logger = LogFactory.getLog(getClass());
	
    public FileDownView(){
        super.setContentType("application/octet-stream");
    }

    
    /**
     * 파일 다운로드 실행
     * @param request
     * @param response
     */
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	
//    	UpFile file = (UpFile)MapUtils.getObject(model, "file", new UpFile());
//    	String filePath = MapUtils.getString(model, "path", "");
//    	String inline = MapUtils.getString(model, "inline", "");
//    	if("".equals(inline)){
//    		inline = "attachment";
//    	}
//    	
//    	if(logger.isDebugEnabled()){
//    		logger.debug(file);
//    		logger.debug(filePath);
//    	}
    	List<JExcelInfo> listData = (List<JExcelInfo>)model.get("data");
    	byte[] fileByte = JExcelUtil.excelDown("Book1", listData);
    	
		//byte[] fileByte = file.getCts(); //파일내용(BLOB type)
		
		if(logger.isDebugEnabled()){
			logger.debug(fileByte.length);
		}
		
		response.setContentType(super.getContentType());
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		response.setHeader("Content-Disposition", "inline"+";filename="+new String("abc.xls".getBytes("euc-kr"),"8859_1")+";");
		OutputStream out = response.getOutputStream();

		try{
			FileCopyUtils.copy(fileByte,out);
		}catch(java.io.IOException e){
			logger.error(e.toString(), e);
		}finally{
			if(out != null) try{ out.close(); }catch(Exception e){}
		}
		out.flush();
    		
    }

//    protected void renderMergedOutputModel2(Map model, HttpServletRequest request,
//    		HttpServletResponse response) throws Exception{
//    	File file = (File)model.get("file");
//    	
//    	if(logger.isDebugEnabled()){
//    		logger.debug(file.getName());
//    		logger.debug(file.getAbsolutePath());
//    	}
//    	
//    	if(!file.exists()) return;
//    	
//    	response.setContentType(super.getContentType());
//    	response.setContentLength((int)file.length());
//    	response.setHeader("Content-Transfer-Encoding", "binary");
//    	response.setHeader("Pragma", "no-cache;");
//    	response.setHeader("Expires", "-1;");
//    	response.setHeader("Content-Disposition", "attachment;fileName="+file.getName()+";");
//    	
//    	OutputStream out = response.getOutputStream();
//    	FileInputStream fis = null;
//    	try{
//    		fis = new FileInputStream(file);
//    		FileCopyUtils.copy(fis,out);
//    	}catch(java.io.IOException e){
//    		logger.error(e.toString(), e);
//    	}finally{
//    		if(fis != null) try{ fis.close(); }catch(Exception e){}
//    		if(out != null) try{ out.close(); }catch(Exception e){}
//    	}
//    	out.flush();
//    }
//    
//    protected void renderMergedOutputModel3(Map model, HttpServletRequest request,
//    		HttpServletResponse response) throws Exception{
//    	
//    	List fileList = (List)MapUtils.getObject(model, "file", new ArrayList());
//    	String filePath = MapUtils.getString(model, "path", "");
//    	
//    	logger.debug(fileList);
//    	logger.debug(filePath);
//    	
//    	Iterator listItr = fileList.iterator(); 
//    	while(listItr.hasNext()){
//    		UpFile file = (UpFile)listItr.next();
//    		byte[] fileByte = file.getCts();
////			File tempFile = new File(filePath);
////			FileCopyUtils.copy(fileByte, tempFile);
//    		
//    		logger.debug(fileByte.length);
//    		
//    		response.setContentType(super.getContentType());
//    		response.setContentLength(fileByte.length);
//    		response.setHeader("Content-Transfer-Encoding", "binary;");
//    		response.setHeader("Pragma", "no-cache;");
//    		response.setHeader("Expires", "-1;");
//    		response.setHeader("Content-Disposition", "attachment;filename="+file.getFilenm()+";");
////			response.setHeader("Content-Location", "http://127.0.0.1:8080/admin-project-web/upload/file4785210090080229909aaa.txt");
////			response.setHeader("Referer", "/admin-project-web/upload/file4785210090080229909aaa.txt");
//    		
////			this.setBeanName("redirect:/upload/file4785210090080229909aaa.txt");
//    		//response.sendRedirect("/admin-project-web/upload/file4785210090080229909aaa.txt");
//    		OutputStream out = response.getOutputStream();
////	        FileInputStream fis = null;
//    		try{
//    			FileCopyUtils.copy(fileByte,out);
//    		}catch(java.io.IOException e){
//    			logger.error(e.toString(), e);
//    		}finally{
////	            if(fis != null) try{ fis.close(); }catch(Exception e){}
//    			if(out != null) try{ out.close(); }catch(Exception e){}
//    		}
//    		out.flush();
//    		
//    	}
//    }
}
