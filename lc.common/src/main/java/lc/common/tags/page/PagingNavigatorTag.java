package lc.common.tags.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import lc.common.util.PagingHelper;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class PagingNavigatorTag extends BodyTagSupport {
	private String property;

	public void setProperty(String property) {
		this.property = property;
	}

	public int doStartTag() throws JspException{
    	return SKIP_BODY;
    }	
    
    public int doEndTag() throws JspException{
    	try{
	    	PagingTag pagingTag = (PagingTag)findAncestorWithClass(this, PagingTag.class);
	    	PageInfo pageInfo = pagingTag.getName();
	    	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	    	String action = request.getContextPath() + "/" + pagingTag.getAction();
	    	
	    	String paramUrl = getParameter(request);
			if(!StringUtils.isEmpty(paramUrl)) action = action + "?" + paramUrl;
			else action = action + "?";
				
	        JspWriter out = null;
	        String value = "";
            out = pageContext.getOut();
            //String outString = makeContent();
            
            if(pageInfo != null){
            	PagingHelper helper = new PagingHelper(request.getContextPath(), 
            			                               action, pageInfo.getROWCOUNT(), 
            			                               pageInfo.getCurrPage(), 
            			                               pageInfo.getListSize(),
            			                               pageInfo.getPageSize());
            	value = BeanUtils.getProperty(helper, property);
            }
            out.print(value);
        }catch(Exception e){
        	e.printStackTrace();
        	throw new JspException(e);
        //    logger.error(e);
        }
    	return EVAL_PAGE;
    }

	private String getParameter(HttpServletRequest request) {
		Map parameterMap = request.getParameterMap();
    	
		Iterator<String> iterator = parameterMap.keySet().iterator();
		String key = null;
		List<String> list = new ArrayList<String>();
		
		while(iterator.hasNext()){
			key = iterator.next();
			if(!"currPage".equals(key)){
				Object obj = parameterMap.get(key);
				if(obj instanceof String[]){
					list.add(key+"="+((String[])obj)[0]);
				}else{
					list.add(key+"="+(String)obj);
				}
			}
		}
		
		return StringUtils.join(list, "&");
	}
    
	public void release() {
		super.release();
	}
}
