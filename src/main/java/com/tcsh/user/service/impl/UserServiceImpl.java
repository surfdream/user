package com.tcsh.user.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.tcsh.common.emial.EmailSender;
import com.tcsh.common.emial.MessageTemplate;
import com.tcsh.user.dao.UserDao;
import com.tcsh.user.entity.UserBaseEntity;
import com.tcsh.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	private UserDao userDao;

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
		if(null == userBase)
			return null;
		long now = System.currentTimeMillis();
		return null;
	}

	@Override
	public UserBaseEntity login(String username, String password, String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPasswordValid(Integer id, String password) {
		UserBaseEntity userBase = getById(id);
		
		return false;
	}

	@Override
	public UserBaseEntity getById(Integer id) {
		return userDao.get(UserBaseEntity.class, id);
	}

	@Override
	public UserBaseEntity getByUsername(String useremail) {
		return userDao.getUniqueByKey(UserBaseEntity.class, "userEmail", useremail);
	}

}
