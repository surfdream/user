package com.tcsh.core.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcsh.common.security.UsernameNotFoundException;
import com.tcsh.common.web.session.SessionProvider;
import com.tcsh.core.entity.Authentication;
import com.tcsh.core.service.AuthenticationService;
import com.tcsh.core.service.BadCredentialsException;
import com.tcsh.user.dao.AuthenticationDao;
import com.tcsh.user.entity.UserBaseEntity;
import com.tcsh.user.service.UserService;

@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Resource(name = "authenticationDao")
	private AuthenticationDao authenticationDao;
	
	@Resource(name = "userService")
	private UserService userService;
	
	// 过期时间
	private int timeout = 30 * 60 * 1000; // 30分钟

	// 间隔时间
	private int interval = 4 * 60 * 60 * 1000; // 4小时

	// 刷新时间。
	private long refreshTime = getNextRefreshTime(System.currentTimeMillis(),
			this.interval);

	@Override
	public Integer retrieveUserIdFromSession(SessionProvider session,
			HttpServletRequest request) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId == null) {
			return null;
		}
		Authentication auth = retrieve(authId);
		if (auth == null) {
			return null;
		}
		return auth.getUserid();
	}

	@Override
	public void storeAuthIdToSession(SessionProvider session,
			HttpServletRequest request, HttpServletResponse response,
			String authId) {
		session.setAttribute(request, response, AUTH_KEY, authId);
	}

	@Override
	public Authentication retrieve(String authId) {
		long current = System.currentTimeMillis();
		// 是否刷新数据库
		if (refreshTime < current) {
			refreshTime = getNextRefreshTime(current, interval);
			int count = authenticationDao.deleteExpire(new Timestamp(current - timeout));
			log.info("refresh Authentication, delete count: {}", count);
		}
		Authentication auth = findById(authId);
		if (auth != null && auth.getUpdateTime().getTime() + timeout > current) {
			auth.setUpdateTime(new Timestamp(current));
			return auth;
		} else {
			return null;
		}
	}

	@Override
	public Authentication login(String useremail, String password, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) throws UsernameNotFoundException,
			BadCredentialsException {
		UserBaseEntity userBase = userService.login(useremail, password, ip);
		Authentication auth = new Authentication();
		auth.setUserid(userBase.getId());
		auth.setUsername(userBase.getNickname());
		auth.setEmail(userBase.getUserEmail());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, AUTH_KEY, auth.getAuthenticationId());
		return auth;
	}

	@Override
	public Authentication activeLogin(UserBaseEntity user, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) {
		return null;
	}

	@Override
	public Authentication findById(String id) {
		return authenticationDao.get(Authentication.class, id);
	}

	@Override
	public Authentication save(Authentication bean) {
		bean.setAuthenticationId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
		bean.init();
		authenticationDao.save(bean);
		return bean;
	}

	@Override
	public void deleteById(String id) {
		authenticationDao.delete(Authentication.class, id);
	}

	@Override
	public void deleteByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			deleteById(ids[i]);
		}
	}
	
	/**
	 * 获得下一个刷新时间。
	 * 
	 * @param current
	 * @param interval
	 * @return 随机间隔时间
	 */
	private long getNextRefreshTime(long current, int interval) {
		return current + interval;
		// 为了防止多个应用同时刷新，间隔时间=interval+RandomUtils.nextInt(interval/4);
		// return current + interval + RandomUtils.nextInt(interval / 4);
	}

}
