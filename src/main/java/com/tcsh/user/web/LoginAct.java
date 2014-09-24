package com.tcsh.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tcsh.user.service.UserService;

@Controller
public class LoginAct {

	@Resource(name = "userService")
	private UserService userService;

	private static final Log log = LogFactory.getLog(LoginAct.class);

	public static final String COOKIE_ERROR_REMAINING = "_error_remaining";

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String submit(String useremail, String password, String captcha,
			String message, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		//对数据校验
		//.......
		
		Integer errorRemaining = userService.errorRemaining(useremail);
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		if (!StringUtils.isBlank(captcha) || (errorRemaining != null && errorRemaining < 0)) {
//			try {
//				if (!imageCaptchaService.validateResponseForID(session.getSessionId(request, response), captcha)) {
//					errors.addErrorCode("error.invalidCaptcha");
//					return errors;
//				}
//			} catch (CaptchaServiceException e) {
//				errors.addErrorCode("error.exceptionCaptcha");
//				log.warn("", e);
//				return errors;
//			}
		}
		return "";
	}
}
