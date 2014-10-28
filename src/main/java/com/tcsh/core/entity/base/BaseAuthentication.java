package com.tcsh.core.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseAuthentication implements Serializable {
	private static final long serialVersionUID = 1L;

	private String authenticationId;
	private int userid;
	private String username;
	private String email;
	private Date loginTime;
	private String loginIp;
	private Date updateTime;

	// constructors
	public BaseAuthentication() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseAuthentication(String authenticationId) {
		this.setAuthenticationId(authenticationId);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseAuthentication(String authenticationId, Integer uid, String username,
			Date loginTime, String loginIp, Date updateTime) {

		this.setAuthenticationId(authenticationId);
		this.setUserid(uid);
		this.setUsername(username);
		this.setLoginTime(loginTime);
		this.setLoginIp(loginIp);
		this.setUpdateTime(updateTime);
		initialize();
	}

	protected void initialize() {
	}

	@Id
	@Column(name = "authentication_id", unique = true, nullable = false, length = 32)
	public String getAuthenticationId() {
		return this.authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	@Column(name = "userid", nullable = false)
	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Column(name = "username", nullable = false, length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "email", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_time", nullable = false, length = 35)
	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "login_ip", nullable = false, length = 50)
	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false, length = 35)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
