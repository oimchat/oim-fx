package com.im.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.only.general.annotation.action.MethodMapping;

public class SessionInterceptor implements HandlerInterceptor {
	
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String basePath = request.getContextPath();
		HttpSession session = request.getSession();
		basePath=(null==basePath||"".equals(basePath))?"/":basePath;
		session.setAttribute("basePath", basePath);

		String servletPath = request.getServletPath();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if (logger.isInfoEnabled()) {
			logger.info("request url: " + servletPath);
		}
		boolean mark = true;
		return mark;
	}

	/**
	 * 判断请求来源同步还是异步. 异步请求返回true； 同步请求返回false
	 *
	 * @param request
	 * @return
	 */
	public boolean isSysnc(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		boolean isAjax = (null != requestType && "XMLHttpRequest".equals(requestType));
		return isAjax;
	}

	public boolean isIntercept(Object handler) {
		boolean isIntercept = true;
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Method method = hm.getMethod();
			MethodMapping mm = method.getAnnotation(MethodMapping.class);
			if (null != mm) {
				isIntercept = mm.isIntercept();
			}
		}
		return isIntercept;
	}
}
