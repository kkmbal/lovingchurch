package lc.common.web.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lc.common.tags.page.PageInfo;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BasicActionSupport extends ActionSupport{
	
	protected Map<String, String> getParameterMap() {
		Map parameterMap = (Map) ActionContext.getContext().getParameters();

		Iterator<String> iterator = parameterMap.keySet().iterator();
		String key = null;
		Map<String, String> request = new HashMap<String, String>();
		
		while(iterator.hasNext()){
			key = iterator.next();
			Object obj = parameterMap.get(key);
			if(obj instanceof String[]){
				//request.put(key, ((String[])obj)[0]);
				request.put(key, StringUtils.join((String[])obj, ','));
			}else if (obj instanceof String){
				request.put(key, (String)obj);
			}
		}
		return request;
	}
	
	protected void setBoardLimit(PageInfo pageInfo){
		if(pageInfo.getCurrPage() == 0) pageInfo.setCurrPage(1);
		pageInfo.setCurrPage(pageInfo.getCurrPage());
		pageInfo.setFromLimit((pageInfo.getCurrPage()-1)*pageInfo.getListSize());
		pageInfo.setToLimit(pageInfo.getListSize());
	}
	
	protected void setBillingLimit(PageInfo pageInfo){
		if(pageInfo.getCurrPage() == 0) pageInfo.setCurrPage(1);
		pageInfo.setCurrPage(pageInfo.getCurrPage());
		pageInfo.setFromLimit((pageInfo.getCurrPage()-1)*pageInfo.getListSize() + 1);
		pageInfo.setToLimit((pageInfo.getFromLimit()-1) + pageInfo.getListSize());
	}
}
