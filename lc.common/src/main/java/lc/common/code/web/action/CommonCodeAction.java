package lc.common.code.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import lc.common.code.domain.CommonCode;
import lc.common.code.service.CommonCodeService;
import lc.common.web.action.BasicActionSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ModelDriven;

@Scope("prototype")
@Service("commonCodeAction")
public class CommonCodeAction extends BasicActionSupport implements ModelDriven<CommonCode>{
	private Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="commonCodeService")
	private CommonCodeService commonCodeService;
	
	private CommonCode code;
	private List<CommonCode> codeList;
	private String type;

	public CommonCode getCode() {
		return code;
	}

	public void setCode(CommonCode code) {
		this.code = code;
	}
	
	public List<CommonCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CommonCode> codeList) {
		this.codeList = codeList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CommonCodeAction(){
		code = new CommonCode();
	}
	
	public String listHigh() throws Exception{
		//리스트
		setBoardLimit(code);
		codeList = commonCodeService.listHighCommonCode(code);

		if(codeList != null && codeList.size() > 0){
			code.setROWCOUNT(((CommonCode)codeList.get(0)).getROWCOUNT());
		}
		return SUCCESS;
	}
	
	public String listSub() throws Exception{
		//리스트
		setBoardLimit(code);
		codeList = commonCodeService.listSubCommonCode(code);
		
		if(codeList != null && codeList.size() > 0){
			code.setROWCOUNT(((CommonCode)codeList.get(0)).getROWCOUNT());
		}
		return SUCCESS;
	}
	
	//Select box 용
    private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }
    
	public String select() throws Exception{
		List<CommonCode> codeList = commonCodeService.listCommonCode(code);
		StringBuilder sb = new StringBuilder();
		if(codeList != null && codeList.size() > 0){
			//sb.append("<select name>");
			for(CommonCode code : codeList){
				sb.append("<option value='").append(code.getCD_VAL()).append("'>").append(code.getCD_NM()).append("</option>");
			}
			//sb.append("</select>");
		}
		logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sb);
		//inputStream = new StringBufferInputStream(sb.toString());
		inputStream = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
		return SUCCESS;
	}	
	
	public String listCode() throws Exception{
		codeList = commonCodeService.listSubCommonCode(code);
		return "select";
	}
	
	public String view() throws Exception{
		code = commonCodeService.getCommonCode(code);
		return "view";
	}
	
	public String update() throws Exception{
		code = commonCodeService.getCommonCode(code);
		return "insert";
	}
	
	public String insert() throws Exception{
		code = new CommonCode();
		return "insert";
	}
	
	public String save() throws Exception{
		
		if(type.equals("insert")){
			//신규저장
			commonCodeService.insertCommonCode(code);
		}else if(type.equals("update")){
			//수정
			commonCodeService.updateCommonCode(code);
		}else if(type.equals("delete")){
			//삭제
			commonCodeService.deleteCommonCode(code);
		}
		return "save";
	}
	
	//@Override
	public CommonCode getModel() {
		return code;
	}
}
