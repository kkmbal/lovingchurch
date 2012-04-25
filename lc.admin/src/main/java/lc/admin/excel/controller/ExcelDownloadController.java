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
    	List<JExcelInfo> l = new ArrayList<JExcelInfo>();
    	//l.add(new JExcelInfo(0, 1, DateUtil.getCurrentDate("yyyy년 MM월 dd일")));
    	l.add(new JExcelInfo(0, 1, DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YMD), "yyyy년 MM월 dd일")));
    	l.add(new JExcelInfo(2, 3, StringUtil.commaMask(sum)));
    	export.setData(l);
    	
    	// #LIST타입
    	List<JExcelListInfo> ll = new ArrayList<JExcelListInfo>();
    	List<List<JExcelInfo>> iiList = new ArrayList<List<JExcelInfo>>();
    	List<JExcelInfo> iList = null;
    	
    	// 주일헌금(01)
    	// 십일조(02)
    	// 선교헌금(04)
    	// 감사헌금(03)
    	// 건축헌금(05)
    	for(Map map : listDonation){
    		iList = new ArrayList<JExcelInfo>();
    		iList.add(new JExcelInfo(map.get("CD_NM").toString()));
    		iList.add(new JExcelInfo(map.get("USER_NM").toString()));
    		iList.add(new JExcelInfo(StringUtil.commaMask(map.get("INOUT_AMT").toString())));
    		iiList.add(iList);
    		if("합계".equals(map.get("USER_NM").toString())){
    			iList = new ArrayList<JExcelInfo>();
    			iList.add(new JExcelInfo(" "));
    			iiList.add(iList);
    		}
    	}
    	
    	
//    	iList.add(new JExcelInfo("1"));
//    	iList.add(new JExcelInfo("22222"));
//    	iList.add(new JExcelInfo("aaaaa"));
//    	iiList.add(iList);
    	
//    	iList = new ArrayList<JExcelInfo>();
//    	iList.add(new JExcelInfo("2"));
//    	iList.add(new JExcelInfo("44444"));
//    	iList.add(new JExcelInfo("bbbbb"));
//    	iiList.add(iList);
//    	
//    	iList = new ArrayList<JExcelInfo>();
//    	iList.add(new JExcelInfo("3"));
//    	iList.add(new JExcelInfo("66666"));
//    	iList.add(new JExcelInfo("ccccc"));
//    	iiList.add(iList);
    	
    	ll.add(new JExcelListInfo(0, 6, iiList));
    	//ll.add(new JExcelListInfo(2, 7, iiList));
    	
    	export.setList(ll);
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
    	//param.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
    	param.put("CAL_YMD", CAL_YMD);
    	
    	//금주수입내역
    	param.put("INOUT_CD", "01"); //입금
    	List<Map> listInExcel = settleService.listInExcel(param);
    	
    	//금주지출내역
    	param.put("INOUT_CD", "02"); //출금
    	List<Map> listOutExcel = settleService.listOutExcel(param);
    	
    	//금주수입,지출계
    	HashMap param2 = new HashMap();
    	//param2.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
    	param2.put("CAL_YMD", CAL_YMD);
    	Map inoutSumExcel = settleService.getWeekSum(param2);
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	List<JExcelInfo> l = new ArrayList<JExcelInfo>();
    	
    	//l.add(new JExcelInfo(0, 0, DateUtil.getCurrentDate("yyyy년 MM월 dd일")));
    	l.add(new JExcelInfo(0, 0, DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YMD), "yyyy년 MM월 dd일")));
    	l.add(new JExcelInfo(1, 4, inoutSumExcel.get("prevThisSum").toString())); //전주+금주
    	l.add(new JExcelInfo(1, 5, inoutSumExcel.get("thisEnd").toString())); //금주마감
    	l.add(new JExcelInfo(1, 7, inoutSumExcel.get("thisIn").toString())); //금주수입계
    	l.add(new JExcelInfo(1, 8, inoutSumExcel.get("prevEnd").toString())); //전주이월계
    	l.add(new JExcelInfo(1, 9, inoutSumExcel.get("thisInSum").toString())); //수입합계
    	l.add(new JExcelInfo(1, 13, inoutSumExcel.get("thisIn").toString())); //금주수입계
    	l.add(new JExcelInfo(3, 4, inoutSumExcel.get("thisOut").toString())); //금주지출계
    	l.add(new JExcelInfo(3, 7, inoutSumExcel.get("thisOut").toString())); //금주지출계
    	l.add(new JExcelInfo(3, 9, inoutSumExcel.get("thisOutSum").toString())); //지출합계
    	l.add(new JExcelInfo(3, 13, inoutSumExcel.get("thisOutSum").toString())); //지출합계
    	export.setData(l);
    	
    	// #LIST타입
    	List<JExcelListInfo> ll = new ArrayList<JExcelListInfo>();
    	List<JExcelListInfo> ll2 = new ArrayList<JExcelListInfo>();
    	List<List<JExcelInfo>> iiList = new ArrayList<List<JExcelInfo>>();
    	List<JExcelInfo> iList = null;
    	
    	//수입내역
    	for(Map map : listInExcel){
    		iList = new ArrayList<JExcelInfo>();
    		iList.add(new JExcelInfo(map.get("CD_NM").toString()));
    		iList.add(new JExcelInfo(StringUtil.commaMask(map.get("INOUT_AMT").toString())));
    		iiList.add(iList);
    	}
    	ll.add(new JExcelListInfo(0, 11, iiList));
    	
    	iiList = new ArrayList<List<JExcelInfo>>();
    	//지출내역
    	for(Map map : listOutExcel){
    		iList = new ArrayList<JExcelInfo>();
    		iList.add(new JExcelInfo(map.get("CD_NM").toString()));
    		iList.add(new JExcelInfo(StringUtil.commaMask(map.get("INOUT_AMT").toString())));
    		iiList.add(iList);
    	}
    	ll2.add(new JExcelListInfo(2, 11, iiList));
    	
    	export.setList(ll);
    	export.setList2(ll2);
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
    	//String CAL_YMD = request.getParameter("CAL_YMD"); //조회날짜
    	String CAL_YMD = CAL_YM + "01";
    	
    	HashMap<String, String> param = new HashMap<String, String>();
    	//param.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
    	param.put("CAL_YM", CAL_YM);
    	
    	//금주수입내역
    	param.put("INOUT_CD", "01"); //입금
    	List<Map> listInExcel = settleService.listInMonthExcel(param);
    	
    	//금주지출내역
    	param.put("INOUT_CD", "02"); //출금
    	List<Map> listOutExcel = settleService.listOutMonthExcel(param);
    	
    	//금주수입,지출계
    	HashMap param2 = new HashMap();
    	//param2.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
    	param2.put("CAL_YM", CAL_YM);
    	//param2.put("CAL_YMD", CAL_YMD);
    	Map inoutSumExcel = settleService.getMonthSum(param2);
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	List<JExcelInfo> l = new ArrayList<JExcelInfo>();
    	
    	//l.add(new JExcelInfo(0, 0, DateUtil.getCurrentDate("yyyy년 MM월 dd일")));
    	l.add(new JExcelInfo(0, 0, DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YMD), "yyyy년 MM월")));
    	l.add(new JExcelInfo(1, 4, inoutSumExcel.get("prevThisSum").toString())); //전달+금달
    	l.add(new JExcelInfo(1, 5, inoutSumExcel.get("thisEnd").toString())); //금달마감
    	l.add(new JExcelInfo(1, 7, inoutSumExcel.get("thisIn").toString())); //금달수입계
    	l.add(new JExcelInfo(1, 8, inoutSumExcel.get("prevEnd").toString())); //전달이월계
    	l.add(new JExcelInfo(1, 9, inoutSumExcel.get("thisInSum").toString())); //수입합계
    	l.add(new JExcelInfo(1, 13, inoutSumExcel.get("thisIn").toString())); //금달수입계
    	l.add(new JExcelInfo(3, 4, inoutSumExcel.get("thisOut").toString())); //금달지출계
    	l.add(new JExcelInfo(3, 7, inoutSumExcel.get("thisOut").toString())); //금달지출계
    	l.add(new JExcelInfo(3, 9, inoutSumExcel.get("thisOutSum").toString())); //지출합계
    	l.add(new JExcelInfo(3, 13, inoutSumExcel.get("thisOutSum").toString())); //지출합계
    	export.setData(l);
    	
    	// #LIST타입
    	List<JExcelListInfo> ll = new ArrayList<JExcelListInfo>();
    	List<List<JExcelInfo>> iiList = new ArrayList<List<JExcelInfo>>();
    	List<JExcelInfo> iList = null;
    	
    	//수입내역
    	for(Map map : listInExcel){
    		iList = new ArrayList<JExcelInfo>();
    		iList.add(new JExcelInfo(map.get("CAL_YMD").toString()));
    		iList.add(new JExcelInfo(StringUtil.commaMask(map.get("INOUT_AMT").toString())));
    		iiList.add(iList);
    	}
    	ll.add(new JExcelListInfo(0, 11, iiList));
    	
    	iiList = new ArrayList<List<JExcelInfo>>();
    	//지출내역
    	for(Map map : listOutExcel){
    		iList = new ArrayList<JExcelInfo>();
    		iList.add(new JExcelInfo(map.get("CAL_YMD").toString()));
    		iList.add(new JExcelInfo(StringUtil.commaMask(map.get("INOUT_AMT").toString())));
    		iiList.add(iList);
    	}
    	ll.add(new JExcelListInfo(2, 11, iiList));
    	
    	export.setList(ll);
    	export.setFileName("month_sum");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }     
    
    public static void main(String[] args){
    	String CAL_YM = "201203";
    	System.out.println(">>>>>>>"+DateUtil.convertStringToDate(CAL_YM));
    	System.out.println(">>>>>>>"+DateUtil.convertDateToString(DateUtil.convertStringToDate(CAL_YM), "yyyy년 MM월"));
    }
}
