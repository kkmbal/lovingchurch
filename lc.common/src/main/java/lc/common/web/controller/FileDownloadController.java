package lc.common.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lc.common.excel.domain.JExcelExportInfo;
import lc.common.excel.domain.JExcelInfo;
import lc.common.excel.domain.JExcelListInfo;
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
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	List<JExcelInfo> l = new ArrayList<JExcelInfo>();
    	l.add(new JExcelInfo(4, 1, "00시 ~ 01시"));
    	l.add(new JExcelInfo(3, 2, "9"));
    	l.add(new JExcelInfo(4, 2, "20"));
    	l.add(new JExcelInfo(2, 2, "22222"));
    	l.add(new JExcelInfo(3, 3, "11111"));
    	//export.setData(l);
    	
    	List<JExcelListInfo> ll = new ArrayList<JExcelListInfo>();
    	List<List<JExcelInfo>> iiList = new ArrayList<List<JExcelInfo>>();
    	List<JExcelInfo> iList = null;
    	
    	iList = new ArrayList<JExcelInfo>();
    	iList.add(new JExcelInfo("1"));
    	iList.add(new JExcelInfo("22222"));
    	iList.add(new JExcelInfo("aaaaa"));
    	iiList.add(iList);
    	
    	iList = new ArrayList<JExcelInfo>();
    	iList.add(new JExcelInfo("2"));
    	iList.add(new JExcelInfo("44444"));
    	iList.add(new JExcelInfo("bbbbb"));
    	iiList.add(iList);
    	
    	iList = new ArrayList<JExcelInfo>();
    	iList.add(new JExcelInfo("3"));
    	iList.add(new JExcelInfo("66666"));
    	iList.add(new JExcelInfo("ccccc"));
    	iiList.add(iList);
    	
    	ll.add(new JExcelListInfo(2, 4, iiList));
    	ll.add(new JExcelListInfo(2, 7, iiList));
    	
    	//export.setList(ll);
    	
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }    
}
