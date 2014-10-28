package com.tcsh.user.dao.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.tcsh.core.dao.impl.HibernateBaseDaoImpl;
import com.tcsh.user.dao.AuthenticationDao;

@Repository("authenticationDao")
public class AuthenticationDaoImpl extends HibernateBaseDaoImpl implements
		AuthenticationDao {

	@Override
	public int deleteExpire(Timestamp timestamp) {
		String hql = "delete Authentication bean where bean.updateTime <= :d";
		return getSession().createQuery(hql).setTimestamp("d", timestamp)
				.executeUpdate();
	}

}
