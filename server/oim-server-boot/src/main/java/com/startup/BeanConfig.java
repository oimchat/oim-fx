package com.startup;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.only.common.spring.util.SpringUtil;
import com.only.query.hibernate.QueryContext;
import com.only.query.hibernate.QueryItemException;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: XiaHui
 * @Date: 2015年12月17日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2015年12月17日
 */
@Configuration
// @PropertySource({ "classpath:config/config.properties" })
// @ImportResource("classpath:spring/task-beans.xml")
@PropertySource({ "file:config/setting/config.properties" })
@ImportResource("file:config/spring/task-beans.xml")
public class BeanConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean
	public QueryContext queryContext() {
		QueryContext bean = new QueryContext();
		// bean.setConfigLocation("classpath:query/*.xml");
		bean.setConfigLocation("file:config/query/*.xml");
		try {
			bean.load();
		} catch (QueryItemException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Bean
	public SpringUtil springUtil() {
		SpringUtil bean = new SpringUtil();
		return bean;
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig bean = new JedisPoolConfig();
		bean.setMaxIdle(300);
		bean.setMaxTotal(500);
		bean.setMaxWaitMillis(1000);
		bean.setTestOnBorrow(true);
		return bean;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory bean = new JedisConnectionFactory(jedisPoolConfig());
		bean.setUsePool(environment.getProperty("redis.usePool", Boolean.class));
		bean.setHostName(environment.getProperty("redis.hostName"));
		bean.setPort(environment.getProperty("redis.port", Integer.class));
		bean.setPassword(environment.getProperty("redis.password"));
		bean.setDatabase(environment.getProperty("redis.database", Integer.class));
		return bean;
	}

	@Bean
	public RedisTemplate<Serializable, Serializable> redisTemplate() {
		RedisTemplate<Serializable, Serializable> bean = new RedisTemplate<Serializable, Serializable>();
		bean.setConnectionFactory(jedisConnectionFactory());
		return bean;
	}
}
