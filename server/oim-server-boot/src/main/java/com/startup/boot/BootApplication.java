package com.startup.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.only.common.util.OnlyPropertiesUtil;
import com.startup.BeanConfig;
import com.startup.DataSourceConfig;
import com.startup.HibernateConfig;
import com.startup.WebConfig;

/**
 * 
 * @author: XiaHui
 * @date 2017年10月19日 下午4:10:57
 */
@EnableCaching
// @EnableConfigurationProperties(SiteConfig.class)
// @EnableAutoConfiguration注解加上，有异常不会找默认error页面了，而是直接输出字符串
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@SpringBootApplication()

// @SpringBootApplication(exclude = {
// DataSourceAutoConfiguration.class,
// DataSourceTransactionManagerAutoConfiguration.class,
// HibernateJpaAutoConfiguration.class })

// @ComponentScan(basePackages = "com.only.im", excludeFilters =
// {@ComponentScan.Filter(value = {Controller.class})})
@ComponentScan(basePackages = "com.im")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({ DataSourceConfig.class, HibernateConfig.class, BeanConfig.class, WebConfig.class }) // 导入其他配置
@EnableTransactionManagement
public class BootApplication {
	
	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				String serverPort = OnlyPropertiesUtil.getProperty("config/setting/config.properties", "server.port");
				int port=Integer.parseInt(serverPort);
				// container.setSessionTimeout(1, TimeUnit.MINUTES);
				container.setPort(port);
			}
		};
	}
}
