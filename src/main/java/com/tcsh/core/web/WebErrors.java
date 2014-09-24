package com.tcsh.core.web;

public class WebErrors {

	public boolean ifOutOfLength(String s, String field, int minLength,
			int maxLength) {
		if (s == null) {
//			addErrorCode("error.required", field);
			return true;
		}
		int len = s.length();
		if (len < minLength || len > maxLength) {
//			addErrorCode("error.outOfLength", field, minLength, maxLength);
			return true;
		}
		return false;
	}
	
}
