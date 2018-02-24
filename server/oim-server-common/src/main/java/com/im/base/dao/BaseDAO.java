/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.im.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.only.query.hibernate.QueryWrapper;
import com.only.query.hibernate.ResultType;

/**
 * 通用的DAO
 * 
 * @author XiaHui
 */
@Repository
public class BaseDAO {

	@Resource
	protected ReadDAO readDAO;

	@Resource
	protected WriteDAO writeDAO;

	/**
	 * 根据主键id获取实体对象
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return readDAO.get(entityClass, id);
	}

	/**
	 * 新增数据
	 * 
	 * @param t
	 * @return
	 */
	public <T> Serializable save(T t) {
		return writeDAO.save(t);
	}

	/**
	 * 修改对象
	 * 
	 * @param t
	 */
	public <T> void update(T t) {
		this.writeDAO.update(t);
	}

	/**
	 * 根据有无主键执行新增或者修改
	 * 
	 * @param t
	 */
	public <T> void saveOrUpdate(T t) {
		this.writeDAO.saveOrUpdate(t);
	}

	/**
	 * 删除数据
	 * 
	 * @param t
	 */
	public <T> void delete(T t) {
		this.writeDAO.delete(t);
	}

	/**
	 * 根据类名删除数据
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void delete(String entityName, Object entity) {
		this.writeDAO.delete(entityName, entity);
	}

	/**
	 * 批量删除实体数据
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection<?> entities) {
		this.writeDAO.deleteAll(entities);
	}

	/**
	 * 根据类和id删除对象
	 * 
	 * @param entityClass
	 * @param id
	 */
	public void deleteById(Class<?> entityClass, Serializable id) {
		Object o = this.get(entityClass, id);
		this.delete(o);
	}

	public <T> void saveList(final List<T> list) {
		writeDAO.saveList(list);
	}

	public <T> void updateList(final List<T> list) {
		writeDAO.updateList(list);
	}

	/**
	 * 批量根据有无主键执行新增或者修改
	 * 
	 * @param list
	 */
	public <T> void saveOrUpdateList(final List<T> list) {
		writeDAO.saveOrUpdateList(list);
	}

	public List<?> find(String queryString, Object... values) {
		return this.readDAO.find(queryString, values);
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public int executeSQL(String sql) {
		return executeSQL(sql, null);
	}

	/**
	 * 执行带传入数据的sql
	 * 
	 * @param sql
	 * @param queryWrapper
	 * @return
	 */
	public int executeSQL(String sql, QueryWrapper queryWrapper) {
		return writeDAO.executeSQL(sql, queryWrapper);
	}

	/**
	 * 执行sql查询，返回单个对象
	 * 
	 * @param sql
	 * @param queryWrapper
	 * @param resultClass
	 * @param returnTypeList
	 * @return
	 */
	public <T> T queryUniqueResult(String sql, QueryWrapper queryWrapper, Class<T> resultClass, List<ResultType> returnTypeList) {
		return this.readDAO.queryUniqueResult(sql, queryWrapper, resultClass, returnTypeList);
	}

	/**
	 * 执行sql返回list对象
	 * 
	 * @param sql
	 * @param queryWrapper
	 * @param resultClass
	 * @param returnTypeList
	 * @return
	 */
	public <T> List<T> queryList(String sql, QueryWrapper queryWrapper, Class<T> resultClass, List<ResultType> returnTypeList) {
		return this.readDAO.queryList(sql, queryWrapper, resultClass, returnTypeList);
	}

	/**
	 * 执行sql返回分页后的对象list
	 * 
	 * @param sql
	 * @param queryWrapper
	 * @param resultClass
	 * @param returnTypeList
	 * @return
	 */
	public <T> List<T> queryPageList(String sql, QueryWrapper queryWrapper, Class<T> resultClass, List<ResultType> returnTypeList) {
		return readDAO.queryPageList(sql, queryWrapper, resultClass, returnTypeList);
	}

	/**
	 * 根据xml配置的name执行sql
	 * 
	 * @param name
	 * @param queryWrapper
	 * @return
	 */
	public int executeSQLByName(String name, QueryWrapper queryWrapper) {
		return writeDAO.executeSQLByName(name, queryWrapper);
	}

	/**
	 * 根据xml配置的name执行sql，返回单个对象
	 * 
	 * @param name
	 * @param queryWrapper
	 * @param resultClass
	 * @return
	 */
	public <T> T queryUniqueResultByName(String name, QueryWrapper queryWrapper, Class<T> resultClass) {
		return readDAO.queryUniqueResultByName(name, queryWrapper, resultClass);
	}

	/**
	 * 根据xml配置的name执行sql，返回list对象
	 * 
	 * @param name
	 * @param queryWrapper
	 * @param resultClass
	 * @return
	 */
	public <T> List<T> queryListByName(String name, QueryWrapper queryWrapper, Class<T> resultClass) {
		return readDAO.queryListByName(name, queryWrapper, resultClass);
	}

	/**
	 * 根据xml配置的name执行sql，返回分页后的list对象
	 * 
	 * @param name
	 * @param queryWrapper
	 * @param resultClass
	 * @return
	 */
	public <T> List<T> queryPageListByName(String name, QueryWrapper queryWrapper, Class<T> resultClass) {
		return readDAO.queryPageListByName(name, queryWrapper, resultClass);
	}

}