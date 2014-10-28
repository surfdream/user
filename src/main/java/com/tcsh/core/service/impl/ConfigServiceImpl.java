package com.tcsh.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcsh.core.dao.ConfigDao;
import com.tcsh.core.entity.Config;
import com.tcsh.core.entity.Config.ConfigLogin;
import com.tcsh.core.service.ConfigService;

@Service("configService")
@Transactional
public class ConfigServiceImpl implements ConfigService {

	@Resource(name = "configDao")
	private ConfigDao configDao;

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> getMap() {
		List<Config> list = configDao.getListAllEntity(Config.class);
		Map<String, String> map = new HashMap<String, String>(list.size());
		for (Config config : list) {
			map.put(config.getCfgKey(), config.getCfgValue());
		}
		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigLogin getConfigLogin() {
		return ConfigLogin.create(getMap());
	}

	@Override
	@Transactional(readOnly = true)
	public String getValue(String key) {
		Config entity = configDao.get(Config.class, key);
		if(null != entity)
			return entity.getCfgValue();
		return null;
	}

	@Override
	public void updateOrSave(Map<String, String> map) {
		if (null != map && map.size() > 0) {
			for (Entry<String, String> entry : map.entrySet()) {
				updateOrSave(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public Config updateOrSave(String key, String value) {
		Config config = configDao.get(Config.class, key);
		if(null != config){
			config.setCfgValue(value);
		}else{
			config = new Config(key);
			config.setCfgValue(value);
			configDao.save(config);
		}
		return config;
	}

	@Override
	public void deleteById(String key) {
		configDao.delete(Config.class, key);
	}

	@Override
	public void deleteByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			deleteById(ids[i]);
		}
	}

}
