package com.tcsh.user.dao;

import java.sql.Timestamp;

import com.tcsh.core.dao.BaseDao;

public interface AuthenticationDao extends BaseDao {

	int deleteExpire(Timestamp timestamp);

}
