package com.tcsh.common.security;

/**
 * 用户名没有找到异常
 */
public class UsernameNotFoundException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public UsernameNotFoundException() {
	}

	public UsernameNotFoundException(String msg) {
		super(msg);
	}
}
