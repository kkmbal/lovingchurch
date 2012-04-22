package lc.admin.excel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lc.admin.inout.service.InoutMapper;
import lc.admin.inout.service.InoutService;
import lc.common.excel.domain.JExcelExportInfo;
import lc.common.excel.domain.JExcelInfo;
import lc.common.excel.domain.JExcelListInfo;
import lc.common.util.DateUtil;
import lc.common.util.FileDownView;
import lc.common.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 파일다운로드 처리
 */
@Controller
public class ExcelDownloadController {
	Log logger = LogFactory.getLog(getClass());
    
	@Resource(name="inoutService")
	private InoutService inoutService;
	
	@Autowired(required=true)
	private InoutMapper	inoutMapper;
	
    /**
     * 수입내역
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/excel_in_list.do")
    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
    	param.put("INOUT_CD", "01"); //입금
    	
    	Map donationSumExcel = inoutMapper.getDonationSumExcel(param);
    	String sum = null;
    	if(donationSumExcel != null){
    		sum = donationSumExcel.get("INOUT_AMT").toString();
    	}
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	List<JExcelInfo> l = new ArrayList<JExcelInfo>();
    	l.add(new JExcelInfo(0, 1, DateUtil.getCurrentDate("yyyy년 MM월 dd일")));
    	l.add(new JExcelInfo(2, 3, StringUtil.commaMask(sum)));
    	export.setData(l);
    	
    	
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
    	
    	export.setList(ll);
    	export.setFileName("in_list");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }    
}
