package lc.common.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lc.common.excel.domain.JExcelInfo;
import lc.common.util.FileDownView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 파일다운로드 처리
 */
@Controller
public class FileDownloadController {
	Log logger = LogFactory.getLog(getClass());
    private String viewName;
    private String errorView;
    private String inline;
    
    /**
     * 파일 다운로드 처리
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/excel.do")
    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
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
    	
    	Map modelMap = new HashMap();
    	modelMap.put("data", listData);
    	return new ModelAndView(new FileDownView(), modelMap);
    }    
}
