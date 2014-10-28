package com.tcsh.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.tcsh.core.dao.impl.HibernateBaseDaoImpl;
import com.tcsh.user.dao.UserDao;

@Repository(value = "userDao")
public class UserDaoImpl extends HibernateBaseDaoImpl implements UserDao {

}
