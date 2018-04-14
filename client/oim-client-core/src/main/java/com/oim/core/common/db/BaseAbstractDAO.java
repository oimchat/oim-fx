package com.oim.core.common.db;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.only.query.hibernate.QueryContext;
import com.only.query.hibernate.QueryWrapper;
import com.only.query.hibernate.ResultToBean;
import com.only.query.hibernate.ResultType;
import com.only.query.page.DefaultPage;
import com.only.query.page.QueryPage;

/**
 * 描述：
 *
 * @author 夏辉
 * @date 2014年3月29日 下午2:08:59 version 0.0.1
 */
public abstract class BaseAbstractDAO {

	protected SessionBox sessionBox;
	protected QueryContext queryContext;

	public BaseAbstractDAO(SessionBox sessionBox) {
		this.sessionBox = sessionBox;
	}

	public void setQueryContext(QueryContext queryContext) {
		this.queryContext = queryContext;
	}

	/**
	 * 根据主键id获取实体对象
	 *
	 * @param <T>
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		Session session = sessionBox.getCurrentSession();
		return session.get(entityClass, id);
	}

	/**
	 * 新增数据
	 *
	 * @param <T>
	 * @param t
	 * @return
	 */
	public <T> T save(T t) {
		Session session = sessionBox.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(t);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return t;
	}

	/**
	 * 修改对象
	 *
	 * @param <T>
	 * @param t
	 */
	public <T> void update(T t) {
		Session session = sessionBox.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(t);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * 根据有无主键执行新增或者修改
	 *
	 * @param <T>
	 * @param t
	 */
	public <T> T saveOrUpdate(T t) {
		Session session = sessionBox.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.saveOrUpdate(t);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return t;
	}

	/**
	 * 删除数据
	 *
	 * @param <T>
	 * @param t
	 */
	public <T> void delete(T t) {
		Session session = sessionBox.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(t);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * 根据类名删除数据
	 *
	 * @param entityName
	 * @param entity
	 */
	public void delete(String entityName, Object entity) {
		Session session = sessionBox.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(entityName, entity);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * 批量删除实体数据
	 *
	 * @param entities
	 */
	public void deleteAll(Collection<?> entities) {
		Session session = sessionBox.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			for (Object entity : entities) {
				session.delete(entity);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
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
		if (null != list) {
			Session session = sessionBox.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try {
				int batch = 0;
				for (Object o : list) {
					session.save(o);
					batch++;
					if (batch == 50) {// 每50条批量提交一次。
						session.flush();
						session.clear();
						batch = 0;
					}
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			}
		}
	}

	public <T> void updateList(final List<T> list) {
		if (null != list) {
			Session session = sessionBox.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try {
				int batch = 0;
				for (Object o : list) {
					session.update(o);
					batch++;
					if (batch == 50) {// 每50条批量提交一次。
						session.flush();
						session.clear();
						batch = 0;
					}
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			}
		}
	}

	/**
	 * 批量根据有无主键执行新增或者修改
	 *
	 * @param <T>
	 * @param list
	 */
	public <T> void saveOrUpdateList(final List<T> list) {
		if (null != list) {
			Session session = sessionBox.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try {
				int batch = 0;
				for (Object o : list) {
					session.saveOrUpdate(o);
					batch++;
					if (batch == 50) {// 每50条批量提交一次。
						session.flush();
						session.clear();
						batch = 0;
					}
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			}
		}
	}

	/**
	 * 根据查询语句 包装total语句
	 *
	 * @param sql
	 *            泛指可能是sql也可能是hql
	 * @return
	 */
	public String wrapTotalSql(String sql) {
		// 后续可以考虑包装一起
		List<String> keyList = new ArrayList<String>(); // sql关键字 用于检查order
		// by是不是在最后
		keyList.add(")");
		keyList.add("where");
		keyList.add("from");
		int orderByIndex = sql.lastIndexOf("order by"); // 后续考虑order by 放后面
		StringBuilder sb = new StringBuilder("select count(1) count from (");
		if (orderByIndex == -1) {
			return sb.append(sql).append(") totalTable").toString();
		}
		boolean boolOrderLast = true; // order by 是否在最后
		for (String key : keyList) {
			int keyIndex = sql.lastIndexOf(key);
			// 解决order by 后面带有()在查询总数sql中存在后在sqlserver2008报错的问题 简单处理，后面有特殊场景在做处理
			// 修改时间2015年1月9日10:37:09 ys
			if (keyIndex > orderByIndex) {
				if (")".equals(key)) {
					// (所在位置
					int keyIndex2 = sql.lastIndexOf("(", keyIndex);
					// (也在order by 后面
					if (keyIndex2 > orderByIndex) {
						continue;
					}
				}
				boolOrderLast = false;
				break;
			}
		}
		if (boolOrderLast) { // 如果order by 为是最后的话 说明该语句排序了 需要把排序去除
			sql = sql.substring(0, orderByIndex);
		}
		return sb.append(sql).append(") totalTable").toString();
	}

	/**
	 * 设置sql or hql查询条件
	 *
	 * @param query
	 * @param queryWrapper
	 */
	@SuppressWarnings("rawtypes")
	protected void setParameter(Query query, QueryWrapper queryWrapper) {
		// TODO Auto-generated method stub
		if (queryWrapper != null) {
			Map<String, Object> map = queryWrapper.getParameterMap();
			String[] manes = query.getNamedParameters();
			for (String name : manes) {
				Object value = map.get(name);
				if (value instanceof Collection) {
					query.setParameterList(name, (Collection) value);
				} else if (value instanceof Object[]) {
					query.setParameterList(name, (Object[]) value);
				} else {
					query.setParameter(name, value);
				}
			}
		}
	}

	protected void setScalar(Query query, List<ResultType> returnTypeList) {
		if (returnTypeList != null && returnTypeList.size() > 0) {
			if (query instanceof SQLQuery) {
				SQLQuery tmpQuery = (SQLQuery) query;
				for (ResultType returnType : returnTypeList) {
					if (returnType.getReturnType() != null) {
						tmpQuery.addScalar(returnType.getColumnName(), returnType.getReturnType());
					} else {
						tmpQuery.addScalar(returnType.getColumnName());
					}
				}
			}
		}
	}

	protected int getCount(Query query) {
		Object count = query.uniqueResult();
		if (count instanceof Long) {
			return ((Long) count).intValue();
		} else if (count instanceof BigInteger) {
			return ((BigInteger) count).intValue();
		} else {
			return ((Integer) count);
		}
	}

	protected Query createSQLQuery(Session session, String sql) {
		Query query = session.createSQLQuery(sql);
		query.setCacheable(false);
		return query;
	}

	protected Query createHQLQuery(Session session, String hql) {
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		return query;
	}

	public boolean isPrimitive(Class<?> o) {
		return (o.isPrimitive()) || (o == Integer.class) || (o == Long.class) || (o == Float.class)
				|| (o == Double.class) || (o == Byte.class) || (o == Character.class) || (o == Boolean.class)
				|| (o == Short.class);
	}

	public boolean isString(Class<?> o) {
		return (o == String.class);
	}

	public int execute(String queryString, QueryWrapper queryWrapper) {
		Session session = sessionBox.getCurrentSession();
		Query query = createSQLQuery(session, queryString);
		setParameter(query, queryWrapper);
		int count = query.executeUpdate();
		return count;
	}

	@SuppressWarnings("unchecked")
	public <T> T queryUniqueResult(String queryString, QueryWrapper queryWrapper, Class<T> resultClass, List<ResultType> returnTypeList) {
		Session session = sessionBox.getCurrentSession();
		Query query = createSQLQuery(session, queryString);
		setParameter(query, queryWrapper);
		setScalar(query, returnTypeList);

		if (resultClass != null && !this.isPrimitive(resultClass) && !this.isString(resultClass)) {
			if (null == returnTypeList || returnTypeList.isEmpty()) {
				query.setResultTransformer(new ResultToBean(resultClass));
			} else {
				query.setResultTransformer(Transformers.aliasToBean(resultClass));
			}
		}
		if (null == resultClass) {
			query.setResultTransformer(new ResultToBean(resultClass));
		}
		Object value = query.uniqueResult();
		if (value instanceof BigInteger) {
			if (Long.class == resultClass) {
				value = ((BigInteger) value).longValue();
			}
		}
		return (T) value;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(String queryString, QueryWrapper queryWrapper, Class<T> resultClass, List<ResultType> returnTypeList) {

		Session session = sessionBox.getCurrentSession();
		List<T> list = null;

		Query query = createSQLQuery(session, queryString);
		setParameter(query, queryWrapper);
		if (null != returnTypeList) {
			setScalar(query, returnTypeList);
		}
		if (resultClass != null && !this.isPrimitive(resultClass) && !this.isString(resultClass)) {
			if (null == returnTypeList || returnTypeList.isEmpty()) {
				query.setResultTransformer(new ResultToBean(resultClass));
			} else {
				query.setResultTransformer(Transformers.aliasToBean(resultClass));
			}
		}

		if (null == resultClass) {
			query.setResultTransformer(new ResultToBean(resultClass));
		}

		list = query.list();
		if (null == list) {
			list = new ArrayList<>();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryPageList(String queryString, QueryWrapper queryWrapper, Class<T> resultClass, List<ResultType> returnTypeList) {
		Session session = sessionBox.getCurrentSession();
		List<T> list = null;
		String queryCountSQL = wrapTotalSql(queryString);
		Query queryCount = createSQLQuery(session, queryCountSQL);
		setParameter(queryCount, queryWrapper);
		QueryPage page = queryWrapper.getPage();
		if (page == null) {
			page = new DefaultPage();
		}
		int totalCount = getCount(queryCount);
		if (0 < totalCount) {
			page.setTotalCount(totalCount);
			Query query = createSQLQuery(session, queryString);
			query.setFirstResult(page.getStartResult()); // 从第0条开始
			query.setMaxResults(page.getPageSize()); // 取出10条
			setParameter(query, queryWrapper);
			if (null != returnTypeList) {
				setScalar(query, returnTypeList);
			}
			if (resultClass != null && !this.isPrimitive(resultClass) && !this.isString(resultClass)) {
				if (null == returnTypeList || returnTypeList.isEmpty()) {
					query.setResultTransformer(new ResultToBean(resultClass));
				} else {
					query.setResultTransformer(Transformers.aliasToBean(resultClass));
				}
			}
			if (null == resultClass) {
				query.setResultTransformer(new ResultToBean(resultClass));
			}
			list = query.list();
		}
		if (null == list) {
			list = new ArrayList<T>();
		}
		page.setResultList(list);
		return list;
	}
}
