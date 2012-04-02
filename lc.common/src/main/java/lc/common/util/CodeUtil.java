package lc.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.common.code.domain.CommonCode;
import lc.common.code.service.CommonCodeService;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CodeUtil {
	private static CodeUtil codeUtil;
	private static Map<String, List<String>> codeMap;
	
	private CodeUtil(){}
	
	public static CodeUtil instance(){
		if(codeUtil == null){
			codeUtil = new CodeUtil();
			codeUtil.init();
		}
		return codeUtil;
	}
	
	private void init(){
		try{
			codeMap = new HashMap<String, List<String>>();
			
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			CommonCodeService commonCodeService = (CommonCodeService) webApplicationContext.getBean("commonCodeService");
			
			List<CommonCode> highCodeList = commonCodeService.listHighCommonCodeAll(new CommonCode());
			List<CommonCode> subCodeList = commonCodeService.listSubCommonCodeAll(new CommonCode());
			
			for(CommonCode code : highCodeList){
				codeMap.put(code.getCD_VAL(), new ArrayList<String>());
			}
			
			for(CommonCode code : subCodeList){
				for(CommonCode subCode : subCodeList){
					if(code.getCD_VAL().equals(subCode.getHICD_VAL())){
						codeMap.get(code.getCD_VAL()).add(subCode.getCD_VAL());
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static List<String> listCodeMap(String hicd){
		List<String> codeList = codeMap.get(hicd);
		return codeList == null ? new ArrayList<String>() : codeList;
	}
	
	public static String getCodeMap(String hicd, String cd){
		String returnCd = "";
		List<String> codeList = codeMap.get(hicd);
		if(codeList != null && codeList.size() > 0){
			returnCd = codeList.get(codeList.indexOf(cd));
		}
		return returnCd;
	}
	
	public static CommonCode getCode(String hicd, String cd) throws Exception{
		CommonCode code = new CommonCode();
		code.setHICD_VAL(hicd);
		code.setCD_VAL(cd);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		CommonCodeService commonCodeService = (CommonCodeService) webApplicationContext.getBean("commonCodeService");
		CommonCode resultCode = commonCodeService.getCommonCode(code);
		return resultCode == null ? code : resultCode;
	}
	
	public static List<CommonCode> listCode(String hicd) throws Exception{
		CommonCode code = new CommonCode();
		code.setHICD_VAL(hicd);
		
		List<CommonCode> codeList = new ArrayList<CommonCode>();
		
		code.setCD_VAL(null);
		if(!StringUtils.isEmpty(code.getHICD_VAL())){
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			CommonCodeService commonCodeService = (CommonCodeService) webApplicationContext.getBean("commonCodeService");
			codeList = commonCodeService.listCommonCode(code);
		}
		return codeList;
	}
}
