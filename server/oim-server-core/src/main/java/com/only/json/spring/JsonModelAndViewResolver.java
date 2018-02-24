package com.only.json.spring;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import com.only.json.JSON;

/**
 * @author guyong
 * 
 */
public class JsonModelAndViewResolver implements ModelAndViewResolver {

	public static final String RETURN_VALUE_KEY = "JSON_RETURN_VALUE_KEY";
	public static final String CURRENT_SERIALIZER_KEY = "CURRENT_SERIABLIZER_KEY";

	private Map<String, JSONView> cache = null;

	public JsonModelAndViewResolver() {
		cache = new ConcurrentHashMap<String, JSONView>();
	}

	protected String getCacheKey(Method method, Class<?> type) {
		return type.getName() + "." + method.getName();
	}

	public ModelAndView resolveModelAndView(Method handlerMethod, Class<?> handlerType, Object returnValue, ExtendedModelMap implicitModel, NativeWebRequest webRequest) {
		String key = getCacheKey(handlerMethod, handlerType);
		JSONView view = cache.get(key);
		if (view == null) {
			JSON json = AnnotationUtils.getAnnotation(handlerMethod, JSON.class);
			if (json == null) {
				return ModelAndViewResolver.UNRESOLVED;
			}
			String[] exp = json.value();
			view = new JSONView(exp);
			cache.put(key, view);
		}

		webRequest.setAttribute(CURRENT_SERIALIZER_KEY, view.getSerializer(), RequestAttributes.SCOPE_REQUEST);

		ModelAndView mav = new ModelAndView(view);
		mav.addAllObjects(implicitModel);
		mav.addObject(RETURN_VALUE_KEY, returnValue);
		return mav;
	}

}
