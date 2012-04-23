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
    	
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
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
    	l.add(new JExcelInfo(0, 1, DateUtil.getCurrentDate("yyyy년 MM월 dd일")));
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
    	
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("CAL_YMD", DateUtil.getCurrentDate()); //현재날짜
    	
    	//금주수입내역
    	param.put("INOUT_CD", "01"); //입금
    	List<Map> listInExcel = settleMapper.listIn(param);
    	
    	//금주지출내역
    	param.put("INOUT_CD", "02"); //출금
    	List<Map> listOutExcel = settleMapper.listOut(param);
    	
    	//금주수입계
    	param.put("INOUT_CD", "01"); //입금
    	Map inSumExcel = settleMapper.getInoutSum(param);
    	String inSum = StringUtil.commaMask(inSumExcel.get("INOUT_AMT").toString());
    	
    	//금주지출계
    	param.put("INOUT_CD", "02"); //출금
    	Map outSumExcel = settleMapper.getInoutSum(param);
    	String outSum = StringUtil.commaMask(outSumExcel.get("INOUT_AMT").toString());
    	
    	
    	JExcelExportInfo export = new JExcelExportInfo();
    	
    	// #DATA타입
    	List<JExcelInfo> l = new ArrayList<JExcelInfo>();
    	l.add(new JExcelInfo(0, 0, DateUtil.getCurrentDate("yyyy년 MM월 dd일")));
    	l.add(new JExcelInfo(1, 7, inSum)); //금주수입계
    	l.add(new JExcelInfo(1, 13, inSum)); //금주수입계
    	l.add(new JExcelInfo(3, 4, outSum)); //금주지출계
    	l.add(new JExcelInfo(3, 7, outSum)); //금주지출계
    	l.add(new JExcelInfo(3, 9, outSum)); //금주지출계
    	l.add(new JExcelInfo(3, 13, outSum)); //금주지출계
    	export.setData(l);
    	
    	// #LIST타입
    	List<JExcelListInfo> ll = new ArrayList<JExcelListInfo>();
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
    	ll.add(new JExcelListInfo(2, 11, iiList));
    	
    	export.setList(ll);
    	export.setFileName("week_sum");
    	
    	Map modelMap = new HashMap();
    	modelMap.put("export", export);
    	return new ModelAndView(new FileDownView(), modelMap);
    }     
}
