package com.startup;

import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * Hibernate 相关配置
 * @author XiaHui
 * @date 2017-11-25 11:28:54
 *
 */
@Configuration
@PropertySource({"file:config/hibernate/hibernate.properties"})
//@PropertySource({"classpath:setting/hibernate.properties"})
public class HibernateConfig {

    @Autowired
    private Environment environment;
    @Autowired
    DataSourceConfig dataSourceConfig;

    /**
     * Hibernate事务管理
     *
     * @return
     */
    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager bean = new HibernateTransactionManager();
        try {
            bean.setSessionFactory(writeSessionFactory().getObject());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 持久层会话工厂类
     *
     * @return
     */
    @Bean
    @Resource(name = "writeDataSource")
    public FactoryBean<SessionFactory> writeSessionFactory() {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSourceConfig.writeDataSource());
        bean.setPackagesToScan(new String[]{"com.im.server.general.common.bean","com.im.server.general.*.bean", "com.im.server.*.common.bean", "com.im.*.bean"});

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.query.substitutions", environment.getProperty("hibernate.query.substitutions"));
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", environment.getProperty("hibernate.jdbc.fetch_size"));
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", environment.getProperty("hibernate.jdbc.batch_size"));
        hibernateProperties.setProperty("hibernate.cache.region.factory_class", environment.getProperty("hibernate.cache.region.factory_class"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", environment.getProperty("hibernate.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", environment.getProperty("hibernate.cache.use_query_cache"));
        hibernateProperties.setProperty("hibernate.transaction.coordinator_class", environment.getProperty("hibernate.transaction.coordinator_class"));
        hibernateProperties.setProperty("hibernate.cache.provider_configuration_file_resource_path", environment.getProperty("hibernate.cache.provider_configuration_file_resource_path"));
        hibernateProperties.setProperty("hibernate.search.default.directory_provider", environment.getProperty("hibernate.search.default.directory_provider"));
        bean.setHibernateProperties(hibernateProperties);
        return bean;
    }

    @Bean
    @Resource(name = "readDataSource")
    public FactoryBean<SessionFactory> readSessionFactory() {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSourceConfig.readDataSource());
        bean.setPackagesToScan(new String[]{"com.im.server.general.common.bean","com.im.server.general.*.bean", "com.im.server.*.common.bean", "com.im.*.bean"});

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.query.substitutions", environment.getProperty("hibernate.query.substitutions"));
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", environment.getProperty("hibernate.jdbc.fetch_size"));
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", environment.getProperty("hibernate.jdbc.batch_size"));
        hibernateProperties.setProperty("hibernate.cache.region.factory_class", environment.getProperty("hibernate.cache.region.factory_class"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", environment.getProperty("hibernate.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", environment.getProperty("hibernate.cache.use_query_cache"));
        hibernateProperties.setProperty("hibernate.transaction.coordinator_class", environment.getProperty("hibernate.transaction.coordinator_class"));
        hibernateProperties.setProperty("hibernate.cache.provider_configuration_file_resource_path", environment.getProperty("hibernate.cache.provider_configuration_file_resource_path"));
        hibernateProperties.setProperty("hibernate.search.default.directory_provider", environment.getProperty("hibernate.search.default.directory_provider"));
        bean.setHibernateProperties(hibernateProperties);
        return bean;
    }
}
