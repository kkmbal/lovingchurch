package lc.common.exception;

import com.opensymphony.xwork2.ValidationAware;

public class LcException extends Exception{
	private String m_code;

	private String m_message;

	//private Throwable m_throwable;

	public LcException() {
		super();
	}

	public LcException(String message) {
		super(message);

		this.m_message = message;
	}

	public LcException(String code, String message) {
		super(message);

		this.m_code = code;
		this.m_message = message;
	}

	public LcException(Throwable throwable) {
		super(throwable);
		this.m_message = throwable.getMessage();
	}
	
	public LcException(ValidationAware aware, Throwable throwable) {
		super(throwable);
		this.m_message = throwable.getMessage();
		
		aware.addActionError(m_message);
	}
	
	public String getCode() {
		return m_code;
	}

	public void setCode(String code) {
		this.m_code = code;
	}

	public String getMessage() {
		return m_message;
	}

	public void setMessage(String message) {
		this.m_message = message;
	}
}
