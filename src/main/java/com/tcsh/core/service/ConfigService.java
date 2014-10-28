package com.tcsh.core.service;

import java.util.Map;

import com.tcsh.core.entity.Config;
import com.tcsh.core.entity.Config.ConfigLogin;

public interface ConfigService {

	/**
	 * 获取数据库中的所有的配置信息,并以map集合形式返回
	 * 
	 * @return
	 */
	public Map<String, String> getMap();

	/**
	 * 获取登录的实体类
	 * @return
	 */
	public ConfigLogin getConfigLogin();

	public String getValue(String key);

	public void updateOrSave(Map<String, String> map);

	public Config updateOrSave(String key, String value);

	public void deleteById(String id);

	public void deleteByIds(String[] ids);
}
