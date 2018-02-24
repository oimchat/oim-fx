package com.oim.core.common.db;

import java.util.List;

import com.only.query.hibernate.QueryItem;
import com.only.query.hibernate.QueryWrapper;
import com.only.query.hibernate.ResultType;

/**
 * 描述：
 *
 * @author 夏辉
 * @date 2014年3月29日 下午2:08:59 version 0.0.1
 */
public abstract class AbstractDAO extends BaseAbstractDAO {

	public AbstractDAO(SessionBox sessionBox) {
		super(sessionBox);
	}

	/**
	 * **************************************************************************************
	 */
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
		return this.execute(sql, queryWrapper);
	}

	/**
	 * 根据xml配置的name执行sql
	 *
	 * @param name
	 * @param queryWrapper
	 * @return
	 */
	public int executeSQLByName(String name, QueryWrapper queryWrapper) {
		String sql = queryContext.getQueryContent(name, queryWrapper);
		return executeSQL(sql, queryWrapper);
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
		QueryItem queryItem = queryContext.getQueryItem(name);
		String sql = queryContext.getQueryContent(name, queryWrapper);
		List<ResultType> resultTypeList = queryItem.getResultTypeList();
		return this.queryUniqueResult(sql, queryWrapper, resultClass, resultTypeList);
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
		QueryItem queryItem = queryContext.getQueryItem(name);
		String sql = queryContext.getQueryContent(name, queryWrapper);
		List<ResultType> resultTypeList = queryItem.getResultTypeList();
		return queryList(sql, queryWrapper, resultClass, resultTypeList);
	}

	/**
	 * 根据xml配置的name执行sql，返回分页后的list对象
	 *
	 * @param <T>
	 *
	 * @param name
	 * @param queryWrapper
	 * @param resultClass
	 * @return
	 */
	public <T> List<T> queryPageListByName(String name, QueryWrapper queryWrapper, Class<T> resultClass) {
		QueryItem queryItem = queryContext.getQueryItem(name);
		String sql = queryContext.getQueryContent(name, queryWrapper);
		List<ResultType> resultTypeList = queryItem.getResultTypeList();
		return queryPageList(sql, queryWrapper, resultClass, resultTypeList);
	}
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
}
