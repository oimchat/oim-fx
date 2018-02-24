package com.startup;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
import com.im.base.common.db.MultiDataSource;

/**
 * 数据源
 *
 * @Author: XiaHui
 * @Date: 2015年12月21日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2015年12月21日
 */
@Configuration
//@PropertySource({"classpath:config/db.properties"})
@PropertySource({"file:config/setting/db.properties"})
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    // //////////////////////////////////////////写数据源
    @Bean(name = "writeDataSource")
    @Primary  
    public DataSource writeDataSource() {
        MultiDataSource dataSource = new MultiDataSource();
        Map<Object, Object> dataSourcesMap = new HashMap<Object, Object>();
        try {
            DruidDataSource database = new DruidDataSource();
            database.setDriverClassName(environment.getProperty("db.driverClassName"));
            database.setUrl(environment.getProperty("db.master.url"));
            database.setUsername(environment.getProperty("db.master.username"));
            database.setPassword(environment.getProperty("db.master.password"));
            database.setFilters(environment.getProperty("db.filters"));
            database.setMaxActive(environment.getProperty("db.maxActive", Integer.class));
            database.setInitialSize(environment.getProperty("db.initialSize", Integer.class));
            database.setMaxWait(environment.getProperty("db.maxWait", Long.class));
            database.setMinIdle(environment.getProperty("db.minIdle", Integer.class));
            database.setTimeBetweenEvictionRunsMillis(environment.getProperty("db.timeBetweenEvictionRunsMillis", Long.class));
            database.setMinEvictableIdleTimeMillis(environment.getProperty("db.minEvictableIdleTimeMillis", Long.class));
            database.setValidationQuery(environment.getProperty("db.validationQuery"));
            database.setTestWhileIdle(environment.getProperty("db.testWhileIdle", Boolean.class));
            database.setTestOnBorrow(environment.getProperty("db.testOnBorrow", Boolean.class));
            database.setTestOnReturn(environment.getProperty("db.testOnReturn", Boolean.class));
            database.setPoolPreparedStatements(false);
            //database.setRemoveAbandoned(environment.getProperty("db.removeAbandoned", Boolean.class));
            //database.setRemoveAbandonedTimeout(environment.getProperty("db.removeAbandonedTimeout", Integer.class));
            database.setLogAbandoned(environment.getProperty("db.logAbandoned", Boolean.class));
            dataSourcesMap.put("default", database);
            dataSource.setTargetDataSources(dataSourcesMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 读数据源
     *
     * @return
     */
    @Bean(name = "readDataSource")
    public DataSource readDataSource() {
        MultiDataSource dataSource = new MultiDataSource();
        Map<Object, Object> dataSourcesMap = new HashMap<Object, Object>();
        try {
            DruidDataSource database = new DruidDataSource();
            database.setDriverClassName(environment.getProperty("db.driverClassName"));
            database.setUrl(environment.getProperty("db.slave.url"));
            database.setUsername(environment.getProperty("db.slave.username"));
            database.setPassword(environment.getProperty("db.slave.password"));
            database.setFilters(environment.getProperty("db.filters"));
            database.setMaxActive(environment.getProperty("db.maxActive", Integer.class));
            database.setInitialSize(environment.getProperty("db.initialSize", Integer.class));
            database.setMaxWait(environment.getProperty("db.maxWait", Long.class));
            database.setMinIdle(environment.getProperty("db.minIdle", Integer.class));
            database.setTimeBetweenEvictionRunsMillis(environment.getProperty("db.timeBetweenEvictionRunsMillis", Long.class));
            database.setMinEvictableIdleTimeMillis(environment.getProperty("db.minEvictableIdleTimeMillis", Long.class));
            database.setValidationQuery(environment.getProperty("db.validationQuery"));
            database.setTestWhileIdle(environment.getProperty("db.testWhileIdle", Boolean.class));
            database.setTestOnBorrow(environment.getProperty("db.testOnBorrow", Boolean.class));
            database.setTestOnReturn(environment.getProperty("db.testOnReturn", Boolean.class));
            database.setPoolPreparedStatements(false);
            //database.setRemoveAbandoned(environment.getProperty("db.removeAbandoned", Boolean.class));
            //database.setRemoveAbandonedTimeout(environment.getProperty("db.removeAbandonedTimeout", Integer.class));
            database.setLogAbandoned(environment.getProperty("db.logAbandoned", Boolean.class));
            dataSourcesMap.put("default", database);
            dataSource.setTargetDataSources(dataSourcesMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
