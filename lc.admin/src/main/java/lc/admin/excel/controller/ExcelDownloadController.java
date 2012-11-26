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
import lc.admin.settle.service.SettleMapper;
import lc.admin.settle.service.SettleService;
import lc.common.excel.domain.JExcelExportInfo;
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
    
//	@Resource(name="inoutService")
//	private InoutService inoutService;
    
	@Resource(name="settleService")
	private SettleService settleService;
	
	@Autowired(required=true)
	private InoutMapper	inoutMapper;
	
	@Autowired(required=true)
	private SettleMapper settleMapper;	
	
    /**
     * 수입내역
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/excel_in_list.do")
    public ModelAndView excelInList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String CAL_YMD = request.getParameter("CAL_YMD"); //조회날짜
    	
    	Map<String, String> param = new HashMap<String, String>();
    	//param.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
    	param.put("CAL_YMD", CAL_YMD); 
    	param.put("INOUT_CD", "01"); //입금
    	
    	//입금총액
    	Map donationSumExcel = inoutMapper.getDonationSumExcel(param);
    	
    	//헌금내역
    	List<Map> listDonation = inoutMapper.listDonationExcel(param);
    	
    	String sum = null;
    	if(donationSumExcel != null){
    		sum = donationSumExcel.get("INOUT_AMT").toString();
    	}
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	export.addData(0, 1, DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YMD), "yyyy년 MM월 dd일"));
    	export.addData(2, 3, StringUtil.commaMask(sum));
    	
    	// #LIST타입
    	for(Map map : listDonation){
    		if(map.get("INOUT_AMT") != null){
    			map.put("INOUT_AMT", StringUtil.commaMask(map.get("INOUT_AMT").toString()));
    		}
    	}
    	export.addList(0, 6, listDonation, new String[]{"CD_NM", "USER_NM", "INOUT_AMT"});
    	export.setFileName("in_list");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    } 
    
    /**
     * 주간결산
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/excel_week_sum.do")
    public ModelAndView excelWeekSum(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String CAL_YMD = request.getParameter("CAL_YMD"); //조회날짜
    	
    	HashMap<String, String> param = new HashMap<String, String>();
    	param.put("CAL_YMD", CAL_YMD);
    	
    	//금주수입내역
    	param.put("INOUT_CD", "01"); //입금
    	List<Map> listInExcel = settleService.listInExcel(param);
    	
    	//금주지출내역
    	param.put("INOUT_CD", "02"); //출금
    	List<Map> listOutExcel = settleService.listOutExcel(param);
    	
    	//금주수입,지출계
    	HashMap param2 = new HashMap();
    	param2.put("CAL_YMD", CAL_YMD);
    	Map inoutSumExcel = settleService.getWeekSum(param2);
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	export.addData(0, 0, DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YMD), "yyyy년 MM월 dd일"));
    	export.addData(1, 4, inoutSumExcel.get("prevThisSum").toString()); //전주+금주
    	export.addData(1, 5, inoutSumExcel.get("thisEnd").toString()); //금주마감
    	export.addData(1, 7, inoutSumExcel.get("thisIn").toString()); //금주수입계
    	export.addData(1, 8, inoutSumExcel.get("prevEnd").toString()); //전주이월계
    	export.addData(1, 9, inoutSumExcel.get("thisInSum").toString()); //수입합계
    	export.addData(1, 13, inoutSumExcel.get("thisIn").toString()); //금주수입계
    	export.addData(3, 4, inoutSumExcel.get("thisOut").toString()); //금주지출계
    	export.addData(3, 7, inoutSumExcel.get("thisOut").toString()); //금주지출계
    	export.addData(3, 9, inoutSumExcel.get("thisOutSum").toString()); //지출합계
    	export.addData(3, 13, inoutSumExcel.get("thisOutSum").toString()); //지출합계
    	
    	// #LIST타입
    	for(Map map : listInExcel){
    		if(map.get("INOUT_AMT") != null){
    			map.put("INOUT_AMT", StringUtil.commaMask(map.get("INOUT_AMT").toString()));
    		}
    	}
    	// #LIST2타입
    	for(Map map : listOutExcel){
    		if(map.get("INOUT_AMT") != null){
    			map.put("INOUT_AMT", StringUtil.commaMask(map.get("INOUT_AMT").toString()));
    		}
    		map.put("CD_NM2", map.get("CD_NM"));
    		map.put("INOUT_AMT2", map.get("INOUT_AMT"));
    		map.remove("CD_NM");
    		map.remove("INOUT_AMT");
    	}
    	List<Map> newList = new ArrayList<Map>();
    	if(listInExcel.size() >= listOutExcel.size()){
    		for(int i=0;i<listOutExcel.size();i++){
    			((Map)listInExcel.get(i)).putAll(listOutExcel.get(i));
    		}
    		newList.addAll(listInExcel);
    	}else{
    		for(int i=0;i<listInExcel.size();i++){
    			((Map)listOutExcel.get(i)).putAll(listInExcel.get(i));
    		}
    		newList.addAll(listOutExcel);
    	}
    	
    	
    	
    	
    	export.addList(0, 11, newList, new String[]{"CD_NM", "INOUT_AMT", "CD_NM2", "INOUT_AMT2"});
    	

    	export.setFileName("week_sum");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }  
    
    /**
     * 월간결산
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/excel_month_sum.do")
    public ModelAndView excelMonSum(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String CAL_YM = request.getParameter("CAL_YM"); //조회날짜
    	String CAL_YMD = CAL_YM + "01";
    	
    	HashMap<String, String> param = new HashMap<String, String>();
    	param.put("CAL_YM", CAL_YM);
    	
    	//금주수입내역
    	param.put("INOUT_CD", "01"); //입금
    	List<Map> listInExcel = settleService.listInMonthExcel(param);
    	
    	//금주지출내역
    	param.put("INOUT_CD", "02"); //출금
    	List<Map> listOutExcel = settleService.listOutMonthExcel(param);
    	
    	//금주수입,지출계
    	HashMap param2 = new HashMap();
    	param2.put("CAL_YM", CAL_YM);
    	Map inoutSumExcel = settleService.getMonthSum(param2);
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	export.addData(0, 0, DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YMD), "yyyy년 MM월"));
    	export.addData(1, 4, inoutSumExcel.get("prevThisSum").toString()); //전달+금달
    	export.addData(1, 5, inoutSumExcel.get("thisEnd").toString()); //금달마감
    	export.addData(1, 7, inoutSumExcel.get("thisIn").toString()); //금달수입계
    	export.addData(1, 8, inoutSumExcel.get("prevEnd").toString()); //전달이월계
    	export.addData(1, 9, inoutSumExcel.get("thisInSum").toString()); //수입합계
    	export.addData(1, 13, inoutSumExcel.get("thisIn").toString()); //금달수입계
    	export.addData(3, 4, inoutSumExcel.get("thisOut").toString()); //금달지출계
    	export.addData(3, 7, inoutSumExcel.get("thisOut").toString()); //금달지출계
    	export.addData(3, 9, inoutSumExcel.get("thisOutSum").toString()); //지출합계
    	export.addData(3, 13, inoutSumExcel.get("thisOutSum").toString()); //지출합계
    	
    	// #LIST타입
    	for(Map map : listInExcel){
    		if(map.get("INOUT_AMT") != null){
    			map.put("INOUT_AMT", StringUtil.commaMask(map.get("INOUT_AMT").toString()));
    		}
    	}
    	// #LIST2타입
    	for(Map map : listOutExcel){
    		if(map.get("INOUT_AMT") != null){
    			map.put("INOUT_AMT", StringUtil.commaMask(map.get("INOUT_AMT").toString()));
    		}
    		map.put("CAL_YMD2", map.get("CAL_YMD"));
    		map.put("INOUT_AMT2", map.get("INOUT_AMT"));
    		map.remove("CAL_YMD");
    		map.remove("INOUT_AMT");
    	}
    	List<Map> newList = new ArrayList<Map>();
    	if(listInExcel.size() >= listOutExcel.size()){
    		for(int i=0;i<listOutExcel.size();i++){
    			((Map)listInExcel.get(i)).putAll(listOutExcel.get(i));
    		}
    		newList.addAll(listInExcel);
    	}else{
    		for(int i=0;i<listInExcel.size();i++){
    			((Map)listOutExcel.get(i)).putAll(listInExcel.get(i));
    		}
    		newList.addAll(listOutExcel);
    	}
    	
    	export.addList(0, 11, newList, new String[]{"CAL_YMD", "INOUT_AMT", "CAL_YMD2", "INOUT_AMT2"});
    	
    	
    	export.setFileName("month_sum");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }     
    
    //수입예산 엑셀
    @RequestMapping("/excel_in_year_sum.do")
    public ModelAndView excelInYearSum(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String YEAR = request.getParameter("YEAR"); //조회날짜
    	
    	HashMap param = new HashMap<String, String>();
    	param.put("YEAR", YEAR); //현재날짜
    	
    	//수입연간리스트
    	List<Map> yearSumExcel = settleMapper.listInYear(param);
    	
    	String sum = null;
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	export.addData(5, 1, "<"+YEAR+"년 수입예산>");
    	export.addData(5, 6, "전년이월금 : ");
    	export.addData(5, 7, "합계 : ");
    	
    	// #LIST타입
    	for(Map map : yearSumExcel){
    		if(map.get("AMT1") != null){
    			map.put("AMT1", StringUtil.commaMask(map.get("AMT1").toString()));
    		}
    		if(map.get("AMT2") != null){
    			map.put("AMT2", StringUtil.commaMask(map.get("AMT2").toString()));
    		}
    		if(map.get("AMT3") != null){
    			map.put("AMT3", StringUtil.commaMask(map.get("AMT3").toString()));
    		}
    		if(map.get("AMT4") != null){
    			map.put("AMT4", StringUtil.commaMask(map.get("AMT4").toString()));
    		}
    		if(map.get("AMT5") != null){
    			map.put("AMT5", StringUtil.commaMask(map.get("AMT5").toString()));
    		}
    		if(map.get("AMT6") != null){
    			map.put("AMT6", StringUtil.commaMask(map.get("AMT6").toString()));
    		}
    		if(map.get("AMT7") != null){
    			map.put("AMT7", StringUtil.commaMask(map.get("AMT7").toString()));
    		}
    		if(map.get("SUMM") != null){
    			map.put("SUMM", StringUtil.commaMask(map.get("SUMM").toString()));
    		}
    	}
    	export.addList(1, 4, yearSumExcel, new String[]{"CAL_YMD", "AMT1", "AMT2", "AMT3", "AMT4", "AMT5", "AMT6", "AMT7", "SUMM"});
    	export.setFileName("in_year_list");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    } 
    
    
    //지출예산 엑셀
    @RequestMapping("/excel_out_year_sum.do")
    public ModelAndView excelOutYearSum(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String YEAR = request.getParameter("YEAR"); //조회날짜
    	
    	HashMap param = new HashMap<String, String>();
    	param.put("YEAR", YEAR); //현재날짜
    	
    	//지출연간리스트
    	List<Map> yearSumExcel = settleMapper.listOutYear(param);
    	
    	String sum = null;
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	export.addData(3, 1, "<"+YEAR+"년 지출결산>");
    	export.addData(3, 6, "합계 : ");
    	
    	// #LIST타입
    	int idx = 1;
    	for(Map map : yearSumExcel){
    		if(map.get("INOUT_AMT") != null){
    			map.put("INOUT_AMT", StringUtil.commaMask(map.get("INOUT_AMT").toString()));
    		}
    		map.put("idx", idx+"");
    		idx++;
    	}
    	export.addList(1, 4, yearSumExcel, new String[]{"CD_NM", "DESC", "INOUT_AMT"});
    	export.setFileName("out_year_list");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }     
    
    
    
    public static void main(String[] args){
    	//String CAL_YM = "201203";
    	//System.out.println(">>>>>>>"+DateUtil.convertStringToDate(CAL_YM));
    	//System.out.println(">>>>>>>"+DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YM), "yyyy년 MM월"));
    	System.out.println(Math.random());
    }
}
