package lc.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownView extends AbstractView{
    public FileDownView(){
        super.setContentType("application/octet-stream");
    }
    
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	
    	String filePath = MapUtils.getString(model, "filePath", "");
    	String fileName = MapUtils.getString(model, "fileName", "");
    	
    	Map beans = new HashMap();
    	XLSTransformer transformer = new XLSTransformer();
    	System.out.println(filePath);
        transformer.transformXLS(filePath + "/" + fileName, beans, filePath + "/" + "0718_result.xls");
        
    	/*
    	UpFile file = (UpFile)MapUtils.getObject(model, "file", new UpFile());
    	String filePath = MapUtils.getString(model, "path", "");
    	String inline = MapUtils.getString(model, "inline", "");
    	if("".equals(inline)){
    		inline = "attachment";
    	}
    	
    	if(logger.isDebugEnabled()){
    		logger.debug(file);
    		logger.debug(filePath);
    	}
    	
		byte[] fileByte = file.getCts(); //파일내용(BLOB type)
		
		if(logger.isDebugEnabled()){
			logger.debug(fileByte.length);
		}
		
		response.setContentType(super.getContentType());
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		response.setHeader("Content-Disposition", "attachment"+";filename="+new String(file.getFilenm().getBytes("euc-kr"),"8859_1")+";");
		OutputStream out = response.getOutputStream();

		try{
			FileCopyUtils.copy(fileByte,out);
		}catch(java.io.IOException e){
			logger.error(e.toString(), e);
		}finally{
			if(out != null) try{ out.close(); }catch(Exception e){}
		}
		out.flush();
    	*/	
    }
}
