package com.tcsh.user.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao {

	/**
	 * 批量保存记录
	 * @param entity
	 */
	public <T> void save(T... entity);
	
	/**
	 * 添加一条记录，并返回该记录生成的id
	 * @param t
	 * @return
	 */
	public <T> Serializable add(T t);

	/**
	 * 根据id返回唯一的一条记录
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> clazz, Serializable id);

	/**
	 * 根据唯一主键删除一条记录
	 * @param clazz
	 * @param id
	 */
	public <T> void delete(Class<T> clazz, Serializable id);
	
	/**
	 * 批量更新类实例
	 * @param entity
	 */
	public <T> void update(T... entity);
	
	/**
	 * 获取所有的实体对象
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getListAllEntity(Class<T> clazz);

	/**
	 * 根据指定的条件，获取唯一的一条记录，如果有多个记录符合这个条件，则返回第一个满足条件的记录，没有满足条件，则返回null
	 * @param clazz	类名
	 * @param columnName 属性
	 * @param value	属性值
	 * @return class or null
	 */
	public <T> T getUniqueByKey(Class<T> clazz, String columnName, Object value);

	/**
	 * 根据指定的多个条件，获取唯一的一条记录，如果有多个记录符合这个条件，则返回第一个满足条件的记录，没有满足条件，则返回null
	 * @param clazz
	 * @param nameValuePairs
	 * @return class or null
	 */
	public <T> T getUniqueResult(Class<T> clazz, Map<String, Object> nameValuePairs);
	
	/**
	 * 获取所有满足条件的记录
	 * @param clazz
	 * @param columnName
	 * @param value
	 * @return List or null
	 */
	public <T> List<T> getListByKey(Class<T> clazz, String columnName, Object value);
	
	/**
	 * 获取所有满足多个条件的记录
	 * @param clazz
	 * @param nameValuePairs
	 * @return
	 */
	public <T> List<T> getListResult(Class<T> clazz, Map<String, Object> nameValuePairs);
}
