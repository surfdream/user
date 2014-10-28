package com.tcsh.common.security;

/**
 * 登录异常
 */
public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Object extraInformation;

	public AuthenticationException() {

	}

	public AuthenticationException(String msg) {
		super(msg);
	}

	public AuthenticationException(String msg, Object extraInformation) {
		super(msg);
		this.extraInformation = extraInformation;
	}

	/**
	 * Any additional information about the exception. Generally a
	 * <code>UserDetails</code> object.
	 * 
	 * @return extra information or <code>null</code>
	 */
	public Object getExtraInformation() {
		return extraInformation;
	}

	public void clearExtraInformation() {
		this.extraInformation = null;
	}
}
