package com.startup;

//import org.apache.log4j.Logger;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;

/**
 * 缓存配置
 *
 * @author XiaHui
 * @date 2015年12月25日 下午11:40:29
 * @version 0.0.1
 */
//@Configuration
//@EnableCaching
//// <!-- 启用缓存注解 --> <cache:annotation-driven cache-manager="cacheManager" />
//public class CachingConfig {
//
//    private static final Logger logger = Logger.getLogger(CachingConfig.class);
//
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        return ehCacheManagerFactoryBean;
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        logger.info("EhCacheCacheManager");
//        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
//        cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
//        return cacheManager;
//    }
//}
