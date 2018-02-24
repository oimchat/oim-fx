package com.only.json.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

/**
 * @author guyong
 * 
 */
public class CustomizedHandlerAdapter extends RequestMappingHandlerAdapter {

	public CustomizedHandlerAdapter() {
		super();
		getMessageConverters().add(new ResourceHttpMessageConverter());
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		List<HandlerMethodReturnValueHandler> handlers = getReturnValueHandlers();
		Set<Class<?>> excluded = new HashSet<Class<?>>(2);
		excluded.add(MapMethodProcessor.class);
		excluded.add(ViewNameMethodReturnValueHandler.class);

		List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<HandlerMethodReturnValueHandler>();
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (!excluded.contains(handler.getClass())) {
				newHandlers.add(handler);
			}
		}
		setReturnValueHandlers(newHandlers);
	}
}
