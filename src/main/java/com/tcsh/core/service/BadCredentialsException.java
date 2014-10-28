package com.tcsh.core.service;

import com.tcsh.common.security.AuthenticationException;

/**
 * 认证信息错误异常。如：密码错误。
 */
public class BadCredentialsException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public BadCredentialsException() {
	}

	public BadCredentialsException(String msg) {
		super(msg);
	}
}
