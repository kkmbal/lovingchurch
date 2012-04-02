package lc.common.tags.page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class PagingTag extends BodyTagSupport {
	private PageInfo name;
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setName(PageInfo name) {
		this.name = name;
	}
	
	public PageInfo getName(){
		return this.name;
	}


	public int doStartTag() throws JspException{
		//evaluateExpressions("name", nameEl, Board.class);
    	return EVAL_BODY_INCLUDE;
    }	
    
    public int doEndTag() throws JspException{
//        JspWriter out = null;
//        try{
//            out = pageContext.getOut();
//            //String outString = makeContent();
//            out.print(name.getROWCOUNT());
//        }catch(Exception e){
//        	throw new JspException(e);
//        //    logger.error(e);
//        }
    	return EVAL_PAGE;
    }
    
//    private void evaluateExpressions(String attrName, String attrValue, Class returnClass) throws JspException{
//    	Object source = ExpressionEvaluatorManager.evaluate(attrName, attrValue, returnClass, this, pageContext);
//    	this.name = (Board)source;
//    }
    
	public void release() {
		super.release();
	}
}
