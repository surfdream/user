package com.tcsh.core.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.tcsh.core.dao.BaseDao;

public class HibernateBaseDaoImpl implements BaseDao {

	public Log log = LogFactory.getLog(HibernateBaseDaoImpl.class);

	@Resource(name = "hibernateTemplate")
	public HibernateTemplate hibernateTemplate;
	
	public Session getSession(){
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	@Override
	public <T> void save(T... entity) {
		for (int i = 0; i < entity.length; i++) {
			hibernateTemplate.save(entity[i]);
		}
	}

	@Override
	public <T> Serializable add(T t) {
		return hibernateTemplate.save(t);
	}

	@Override
	public <T> T get(Class<T> clazz, Serializable id) {
		return hibernateTemplate.get(clazz, id);
	}

	@Override
	public <T> void delete(Class<T> clazz, Serializable id) {
		try {
			hibernateTemplate.delete(hibernateTemplate.get(clazz, id));
			if (log.isInfoEnabled()) {
				log.info("删除实体类成功," + clazz.getClass().getName());
			}
		} catch (RuntimeException e) {
			log.error("删除实体异常", e);
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getUniqueByKey(Class<T> clazz, String columnName, Object value) {
		List<T> list = getSession().createCriteria(clazz).add(Restrictions.eq(columnName, value)).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getUniqueResult(Class<T> clazz,
			Map<String, Object> nameValuePairs) {
		Criteria criteria = getSession().createCriteria(clazz);
		for (Map.Entry<String, Object> entry : nameValuePairs.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getListByKey(Class<T> clazz, String columnName,
			Object value) {
		return getSession().createCriteria(clazz).add(Restrictions.eq(columnName, value)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getListResult(Class<T> clazz,
			Map<String, Object> nameValuePairs) {
		Criteria criteria = getSession().createCriteria(clazz);
		for (Map.Entry<String, Object> entry : nameValuePairs.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return criteria.list();
	}

	@Override
	public <T> void update(T... entity) {
		for (int i = 0; i < entity.length; i++) {
			hibernateTemplate.saveOrUpdate(entity[i]);
		}
	}

	@Override
	public <T> List<T> getListAllEntity(Class<T> clazz) {
		return hibernateTemplate.loadAll(clazz);
	}

}
