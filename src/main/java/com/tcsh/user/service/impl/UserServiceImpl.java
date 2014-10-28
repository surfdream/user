package com.tcsh.user.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcsh.common.emial.EmailSender;
import com.tcsh.common.emial.MessageTemplate;
import com.tcsh.common.security.UsernameNotFoundException;
import com.tcsh.common.security.encoder.PwdEncoder;
import com.tcsh.core.entity.Config.ConfigLogin;
import com.tcsh.core.service.BadCredentialsException;
import com.tcsh.core.service.ConfigService;
import com.tcsh.user.dao.UserDao;
import com.tcsh.user.entity.UserBaseEntity;
import com.tcsh.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	private UserDao userDao;

	@Resource(name = "configService")
	private ConfigService configService;
	
	private PwdEncoder pwdEncoder;
	
	@Autowired
	public void setPwdEncoder(PwdEncoder pwdEncoder) {
		this.pwdEncoder = pwdEncoder;
	}

	@Override
	public UserBaseEntity passwordForgotten(Integer userId, EmailSender email,
			MessageTemplate tpl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBaseEntity resetPassword(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer errorRemaining(String username) {
		if (StringUtils.isBlank(username))
			return null;
		UserBaseEntity userBase = getByUsername(username);
		if (userBase == null)
			return null;
		long now = System.currentTimeMillis();
		ConfigLogin configLogin = configService.getConfigLogin();
		int maxErrorTimes = configLogin.getErrorTimes();
		int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
		Integer errorCount = userBase.getErrorCount();
		Date errorTime = userBase.getErrorTime();
		if (errorCount <= 0 || errorTime == null
				|| errorTime.getTime() + maxErrorInterval < now) {
			return maxErrorTimes;
		}
		return maxErrorTimes - errorCount;
	}

	@Override
	public UserBaseEntity login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException {
		UserBaseEntity userBase = getByUsername(username);
		if (userBase == null) {
			throw new UsernameNotFoundException("username not found: "
					+ username);
		}
		if (!pwdEncoder.isPasswordValid(userBase.getUserPassword(), password)) {
			updateLoginError(userBase.getId(), ip);
			throw new BadCredentialsException("password invalid");
		}
		return null;
	}
	
	public void updateLoginError(Integer userId, String ip) {
		UserBaseEntity user = getById(userId);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		ConfigLogin configLogin = configService.getConfigLogin();
		int errorInterval = configLogin.getErrorInterval();
		Timestamp errorTime = user.getErrorTime();

		user.setErrorIp(ip);
		if (errorTime == null
				|| errorTime.getTime() + errorInterval * 60 * 1000 < now
						.getTime()) {
			user.setErrorTime(errorTime);
			user.setErrorCount(1);
		} else {
			user.setErrorCount(user.getErrorCount() + 1);
		}
	}

	@Override
	public boolean isPasswordValid(Integer id, String password) {
		UserBaseEntity userBase = getById(id);
		return pwdEncoder.isPasswordValid(userBase.getUserPassword(), password);
	}

	@Override
	public UserBaseEntity getById(Integer id) {
		return userDao.get(UserBaseEntity.class, id);
	}

	@Override
	public UserBaseEntity getByUsername(String useremail) {
		return userDao.getUniqueByKey(UserBaseEntity.class, "userEmail",
				useremail);
	}

}
