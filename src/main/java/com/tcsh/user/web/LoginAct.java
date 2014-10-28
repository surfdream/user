package com.tcsh.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.tcsh.common.web.RequestUtils;
import com.tcsh.common.web.session.SessionProvider;
import com.tcsh.core.entity.Authentication;
import com.tcsh.core.service.AuthenticationService;
import com.tcsh.core.web.WebErrors;
import com.tcsh.user.service.UserService;

@Controller
public class LoginAct {

	@Resource(name = "userService")
	private UserService userService;
	
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@Autowired
	private SessionProvider session;
	
	@Resource(name = "authenticationService")
	private AuthenticationService authenticationService;

	private static final Log log = LogFactory.getLog(LoginAct.class);

	public static final String COOKIE_ERROR_REMAINING = "_error_remaining";

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String submit(String useremail, String password, String captcha,
			String message, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer errorRemaining = userService.errorRemaining(useremail);
		WebErrors errors = validateSubmit(useremail, password, captcha,
				errorRemaining, request, response);
		
		if (!errors.hasErrors()) {
			try{
				String ip = RequestUtils.getIpAddr(request);
				Authentication auth = authenticationService.login(useremail, password, ip,
						request, response, session);
				
			}catch(Exception e){
				
			}
		}
		return "";
	}

	private WebErrors validateSubmit(String useremail, String password,
			String captcha, Integer errorRemaining, HttpServletRequest request,
			HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNotEmail(useremail, "useremail", 50)) {
			return errors;
		}
		if (errors.ifOutOfLength(password, "password", 1, 32)) {
			return errors;
		}
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		if (!StringUtils.isBlank(captcha)
				|| (errorRemaining != null && errorRemaining < 0)) {
			if (errors.ifBlank(captcha, "captcha", 100)) {
				return errors;
			}
			try {
				if (!imageCaptchaService.validateResponseForID(
						session.getSessionId(request, response), captcha)) {
					errors.addErrorCode("error.invalidCaptcha");
					return errors;
				}
			} catch (CaptchaServiceException e) {
				errors.addErrorCode("error.exceptionCaptcha");
				log.warn("", e);
				return errors;
			}
		}
		return errors;
	}
	
	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		String authId = (String) session.getAttribute(request, com.tcsh.core.service.AuthenticationService.AUTH_KEY);
		if (authId != null) {
			authenticationService.deleteById(authId);
			session.logout(request, response);
		}
//		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);
//		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
//		String view = getView(processUrl, returnUrl, authId);
//		if (view != null) {
//			return view;
//		} else {
//			return "redirect:login.do";
//		}
		return null;
	}
}
