package lc.common;

@SuppressWarnings("serial")
public class LcException extends Exception {
	public LcException(){
	}
	
	public LcException(String msg){
		super(msg);
	}
	
	public LcException(Throwable t){
		super(t);
	}
}
