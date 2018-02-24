package com.oim.core.business.module;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.core.common.AppConstant;
import com.oim.core.common.db.CommonDAO;
import com.oim.core.common.db.SessionBox;
import com.only.common.util.OnlyClassUtil;
import com.only.query.hibernate.QueryContext;
import com.only.query.hibernate.QueryItemException;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

public class DAOFactory extends AbstractComponent {

	SessionBox sessionBox;
	QueryContext bean = new QueryContext();
	private Map<Class<?>, Object> map = new ConcurrentHashMap<Class<?>, Object>();

	public DAOFactory(AppContext appContext) {
		super(appContext);
	}

	public void initialize() {
		bean.setConfigLocation("classpath:config/query/*.xml");
		try {
			bean.load();
		} catch (QueryItemException e) {
			e.printStackTrace();
		}
		String path = AppConstant.getUserAppPath();
		String dbPath = path + "/db/oim";
		//;MVCC=TRUE为取消文件锁定，允许多程序访问
		// String url="jdbc:h2:file:./db/oim";
//		String url = "jdbc:h2:file:" + dbPath;
		String url = "jdbc:sqlite:" + dbPath+".db";
		sessionBox = new SessionBox(url);
	}

	public <T extends CommonDAO> T getDAO(Class<T> classType) {
		return getObject(classType, false, false);
	}

	@SuppressWarnings("unchecked")
	private <T> T getObject(Class<T> classType, boolean createNew, boolean cover) {
		Object object = null;

		if (null != classType) {

			boolean has = map.containsKey(classType);

			object = map.get(classType);
			if (!has || createNew) {

				boolean canInstance = OnlyClassUtil.isCanInstance(classType);

				if (canInstance) {
					Class<?>[] types = { SessionBox.class };

					try {
						Constructor<?> constructor = classType.getConstructor(types);
						if (null != constructor) {
							Object[] objects = { sessionBox };
							object = constructor.newInstance(objects);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (null == object) {
							object = classType.newInstance();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (object instanceof CommonDAO) {
						((CommonDAO) object).setQueryContext(bean);
					}
					if (!has || cover) {
						map.put(classType, object);
					}
				}
			}
		}
		return (T) object;
	}
}
