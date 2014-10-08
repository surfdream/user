package com.tcsh.user.service;

import com.tcsh.common.emial.EmailSender;
import com.tcsh.common.emial.MessageTemplate;
import com.tcsh.user.entity.UserBaseEntity;

/**
 * 
 * @author sunny
 *
 */
public interface UserService {

	/**
	 * 忘记密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param email
	 *            发送者邮件信息
	 * @param tpl
	 *            邮件模板。内容模板可用变量${uid}、${username}、${resetKey}、${resetPwd}。
	 * @return
	 */
	public UserBaseEntity passwordForgotten(Integer userId, EmailSender email,
			MessageTemplate tpl);
	
	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	public UserBaseEntity resetPassword(Integer userId);
	
	/**
	 * 根据用户登录邮箱获取该用户登录错误次数
	 * 
	 * @param useremail
	 * @return
	 */
	public Integer errorRemaining(String useremail);
	
	/**
	 * 用户登录
	 * @param useremail
	 * @param password
	 * @param ip
	 * @return
	 */
	public UserBaseEntity login(String useremail, String password, String ip);
	
	/**
	 * 密码是否正确
	 * 
	 * @param id
	 *            用户ID
	 * @param password
	 *            未加密密码
	 * @return
	 */
	public boolean isPasswordValid(Integer id, String password);
	
	/**
	 * 根据用户id 获取用户基本信息
	 * @param id
	 * @return
	 */
	public UserBaseEntity getById(Integer id);
	
	/**
	 * 根据用户的登录名获取用户基本信息
	 * @param useremail
	 * @return
	 */
	public UserBaseEntity getByUsername(String useremail);
}
