package com.tcsh.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.tcsh.user.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl extends HibernateBaseDaoImpl implements UserDao {

}
