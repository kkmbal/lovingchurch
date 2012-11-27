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
    	long AMT1 = 0;
    	long AMT2 = 0;
    	long AMT3 = 0;
    	long AMT4 = 0;
    	long AMT5 = 0;
    	long AMT6 = 0;
    	long AMT7 = 0;
    	long SUMM = 0;
    	
    	for(Map map : yearSumExcel){
    		if(map.get("AMT1") != null){
    			map.put("S_AMT1", StringUtil.commaMask(map.get("AMT1").toString()));
    			AMT1 += Long.parseLong(map.get("AMT1").toString());
    		}
    		if(map.get("AMT2") != null){
    			map.put("S_AMT2", StringUtil.commaMask(map.get("AMT2").toString()));
    			AMT2 += Long.parseLong(map.get("AMT2").toString());
    		}
    		if(map.get("AMT3") != null){
    			map.put("S_AMT3", StringUtil.commaMask(map.get("AMT3").toString()));
    			AMT3 += Long.parseLong(map.get("AMT3").toString());
    		}
    		if(map.get("AMT4") != null){
    			map.put("S_AMT4", StringUtil.commaMask(map.get("AMT4").toString()));
    			AMT4 += Long.parseLong(map.get("AMT4").toString());
    		}
    		if(map.get("AMT5") != null){
    			map.put("S_AMT5", StringUtil.commaMask(map.get("AMT5").toString()));
    			AMT5 += Long.parseLong(map.get("AMT5").toString());
    		}
    		if(map.get("AMT6") != null){
    			map.put("S_AMT6", StringUtil.commaMask(map.get("AMT6").toString()));
    			AMT6 += Long.parseLong(map.get("AMT6").toString());
    		}
    		if(map.get("AMT7") != null){
    			map.put("S_AMT7", StringUtil.commaMask(map.get("AMT7").toString()));
    			AMT7 += Long.parseLong(map.get("AMT7").toString());
    		}
    		if(map.get("SUMM") != null){
    			map.put("S_SUMM", StringUtil.commaMask(map.get("SUMM").toString()));
    			SUMM += Long.parseLong(map.get("SUMM").toString());
    		}
    	}    	
    	
    	// #DATA타입
    	export.addData(5, 1, "<"+YEAR+"년 수입예산>");
    	export.addData(2, 6, StringUtil.commaMask(AMT1));
    	export.addData(3, 6, StringUtil.commaMask(AMT2));
    	export.addData(4, 6, StringUtil.commaMask(AMT3));
    	export.addData(5, 6, StringUtil.commaMask(AMT4));
    	export.addData(6, 6, StringUtil.commaMask(AMT5));
    	export.addData(7, 6, StringUtil.commaMask(AMT6));
    	export.addData(8, 6, StringUtil.commaMask(AMT7));
    	export.addData(9, 6, StringUtil.commaMask(SUMM));
    	export.addData(5, 8, "전년이월금 : ");
    	export.addData(5, 9, "합계 : "+StringUtil.commaMask(SUMM));
    	
    	// #LIST타입
    	export.addList(1, 4, yearSumExcel, new String[]{"CAL_YMD", "S_AMT1", "S_AMT2", "S_AMT3", "S_AMT4", "S_AMT5", "S_AMT6", "S_AMT7", "S_SUMM"});
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
    	
    	long sum = 0;
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	int idx = 1;
    	for(Map map : yearSumExcel){
    		if(map.get("INOUT_AMT") != null){
    			map.put("S_INOUT_AMT", StringUtil.commaMask(map.get("INOUT_AMT").toString()));
    			sum += Long.parseLong(map.get("INOUT_AMT").toString());
    		}
    		map.put("idx", idx+"");
    		idx++;
    	}
    	
    	
    	
    	// #DATA타입
    	export.addData(3, 1, "<"+YEAR+"년 지출결산>");
    	export.addData(3, 6, "합계 : "+StringUtil.commaMask(sum));
    	
    	// #LIST타입
    	export.addList(1, 4, yearSumExcel, new String[]{"idx", "CD_NM", "DESC", "S_INOUT_AMT"});
    	export.setFileName("out_year_list");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }     
    
    //예산결산 엑셀
    @RequestMapping("/excel_year_sum.do")
    public ModelAndView excelYearSum(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String YEAR = request.getParameter("YEAR"); //조회날짜
    	if(YEAR == null || "".equals(YEAR)) return null;
    	
    	int prevYear = Integer.parseInt(YEAR)-1;
    	
    	HashMap param = new HashMap<String, String>();
    	param.put("YEAR", YEAR); //현재날짜
    	
    	//지출연간리스트
    	Map sumMap = settleService.listYearSumExcel(param);
    	
    	long sum = 0;
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	export.addData(2, 1, prevYear+"년 수입결산 / "+YEAR+"년 수입예산");
    	export.addData(2, 5, (sumMap.get("PREV_SUM1_AMT").toString())); //작년주정현금
    	export.addData(3, 5, (sumMap.get("SUM1_AMT").toString())); //올해주정현금
    	export.addData(5, 5, (sumMap.get("PREV_EXP_SUM1_AMT").toString())); //작년책정예산-주정헌금
    	export.addData(6, 5, (sumMap.get("EXP_SUM1_AMT").toString())); //올해책정예산-주정헌금
    	export.addData(7, 5, (sumMap.get("REMARK1").toString())); //비고-주정현금
    	
    	export.addData(2, 6, (sumMap.get("PREV_SUM2_AMT").toString())); //작년십일조현금
    	export.addData(3, 6, (sumMap.get("SUM2_AMT").toString())); //올해십일조현금
    	export.addData(5, 6, (sumMap.get("PREV_EXP_SUM2_AMT").toString())); //작년책정예산-십일조헌금
    	export.addData(6, 6, (sumMap.get("EXP_SUM2_AMT").toString())); //올해책정예산-십일조헌금
    	export.addData(7, 6, (sumMap.get("REMARK2").toString())); //비고-십일조현금
    	
    	export.addData(2, 7, (sumMap.get("PREV_SUM3_AMT").toString())); //작년감사현금
    	export.addData(3, 7, (sumMap.get("SUM3_AMT").toString())); //올해감사현금
    	export.addData(5, 7, (sumMap.get("PREV_EXP_SUM3_AMT").toString())); //작년책정예산-감사헌금
    	export.addData(6, 7, (sumMap.get("EXP_SUM3_AMT").toString())); //올해책정예산-감사헌금
    	export.addData(7, 7, (sumMap.get("REMARK3").toString())); //비고-감사현금
    	
    	export.addData(2, 8, (sumMap.get("PREV_SUM4_AMT").toString())); //작년선교현금
    	export.addData(3, 8, (sumMap.get("SUM4_AMT").toString())); //올해선교현금
    	export.addData(5, 8, (sumMap.get("PREV_EXP_SUM4_AMT").toString())); //작년책정예산-선교헌금
    	export.addData(6, 8, (sumMap.get("EXP_SUM4_AMT").toString())); //올해책정예산-선교헌금
    	export.addData(7, 8, (sumMap.get("REMARK4").toString())); //비고-주정현금
    	
    	export.addData(2, 9, (sumMap.get("PREV_SUM5_AMT").toString())); //작년건축현금
    	export.addData(3, 9, (sumMap.get("SUM5_AMT").toString())); //올해건축현금
    	export.addData(5, 9, (sumMap.get("PREV_EXP_SUM5_AMT").toString())); //작년책정예산-건축헌금
    	export.addData(6, 9, (sumMap.get("EXP_SUM5_AMT").toString())); //올해책정예산-건축헌금
    	export.addData(7, 9, (sumMap.get("REMARK5").toString())); //비고-건축현금
    	
    	export.addData(2, 10, (sumMap.get("PREV_SUM6_AMT").toString())); //작년절기현금
    	export.addData(3, 10, (sumMap.get("SUM6_AMT").toString())); //올해절기현금
    	export.addData(5, 10, (sumMap.get("PREV_EXP_SUM6_AMT").toString())); //작년책정예산-절기헌금
    	export.addData(6, 10, (sumMap.get("EXP_SUM6_AMT").toString())); //올해책정예산-절기헌금
    	export.addData(7, 10, (sumMap.get("REMARK6").toString())); //비고-절기현금
    	
    	export.addData(2, 11, (sumMap.get("PREV_SUM7_AMT").toString())); //작년기타현금
    	export.addData(3, 11, (sumMap.get("SUM7_AMT").toString())); //올해기타현금
    	export.addData(5, 11, (sumMap.get("PREV_EXP_SUM7_AMT").toString())); //작년책정예산-기타헌금
    	export.addData(6, 11, (sumMap.get("EXP_SUM7_AMT").toString())); //올해책정예산-기타헌금
    	export.addData(7, 11, (sumMap.get("REMARK7").toString())); //비고-기타현금
    	
   
    	export.addData(2, 12, sumMap.get("PREV_SUM_AMT").toString()); //작년합계
    	export.addData(3, 12, sumMap.get("SUM_AMT").toString()); //올해합계
    	export.addData(5, 12, sumMap.get("PREV_EXP_SUM_AMT").toString()); //작년책정예산-합계
    	export.addData(6, 12, sumMap.get("EXP_SUM_AMT").toString()); //올해책정예산-합계
    	
    	export.setFileName("sum_year_list");
    	
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
