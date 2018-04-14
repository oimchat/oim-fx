package com.oim.core.common.db;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author XiaHui
 * @date 2017年8月27日 下午1:32:17
 */
public class SessionBox {

	private ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private SessionFactory sessionFactory;

	public SessionBox(String url) {
		load(url);
	}

	private void load(String url) {
		File configFile = new File(SessionBox.class.getResource("/config/hibernate/hibernate.cfg.xml").getPath());
		Configuration configuration = new Configuration();
		configuration.configure(configFile);
		configuration.setProperty("hibernate.connection.url", url);
		configuration.setProperty("hibernate.connection.username", "oim");
		configuration.setProperty("hibernate.connection.password", "only@oim.h2");
		// configuration.setProperty("hibernate.connection.driver_class","org.h2.Driver");
		// configuration.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
		//
		// configuration.setProperty("hibernate.show_sql","false");
		// configuration.setProperty("hibernate.hbm2ddl.auto","update");
		// configuration.setProperty("hibernate.query.substitutions","true 1,
		// false 0");
		// configuration.setProperty("hibernate.jdbc.fetch_size","50");
		// configuration.setProperty("hibernate.jdbc.batch_size","50");
		// configuration.setProperty("hibernate.transaction.coordinator_class","jdbc");
		// configuration.setProperty("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");
		// configuration.setProperty("hibernate.cache.use_second_level_cache","false");
		// configuration.setProperty("hibernate.cache.use_query_cache","true");
		// configuration.setProperty("hibernate.cache.provider_configuration_file_resource_path","ehcache.xml");
		// configuration.setProperty("hibernate.search.default.directory_provider","org.hibernate.search.store.impl.FSDirectoryProvider");
		try {
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 提供返回本地线程绑的session的方法
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// 提供方法返回sessionFactory
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session getCurrentSession() {
		Session session = threadLocal.get();
		if (session == null) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}
		return session;
	}

	public void closeCurrentSession() {
		Session s = getCurrentSession();
		if (s != null && s.isOpen()) {
			s.close();
			threadLocal.set(null);
		}
	}

	public <T> void save(T t) {
		Session session = this.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(t);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}
}
