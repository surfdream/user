package com.tcsh.user.service.impl;

import javax.annotation.Resource;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBaseEntity login(String username, String password, String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPasswordValid(Integer id, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserBaseEntity getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
