package com.tcsh.core.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "base_config", schema = "public")
public abstract class BaseConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cfgKey;
	private String cfgValue;

	@Id
	@Column(name = "cfg_key", unique = true, nullable = false, length = 50)
	public String getCfgKey() {
		return cfgKey;
	}

	public void setCfgKey(String cfgKey) {
		this.cfgKey = cfgKey;
	}

	@Column(name = "cfg_value", length = 255)
	public String getCfgValue() {
		return cfgValue;
	}

	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}

}
