package com.im.server.general.manage.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.im.server.general.common.service.UserService;
import com.only.common.action.ActionDispatch;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.common.resolver.BeanBox;
import com.only.common.resolver.gson.DefineValuesResolver;
import com.only.common.result.Info;
import com.onlyxiahui.im.message.AbstractMessage;
import com.onlyxiahui.im.message.Data;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.ClientHead;
import com.onlyxiahui.im.message.data.Auth;
import com.onlyxiahui.im.message.server.ResultBodyMessage;
import com.onlyxiahui.im.message.server.ResultMessage;
import com.onlyxiahui.im.message.server.ServerHead;

import net.sf.json.util.JSONUtils;

@Controller
public class WebHandler {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	private ApplicationContext applicationContext = null;
	private final Gson gson = new Gson();
	private final ActionDispatch cd = new ActionDispatch("com.im.server.*.manage.controller");
	@Resource
	private UserService userService;
	private DefineValuesResolver pvr = new DefineValuesResolver();
	private BeanBox beanBox;

	public WebHandler() {
		init();
	}

	private void init() {
		beanBox = new BeanBox() {

			Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

			@Override
			public void register(Class<?> requiredType, Object object) {
				map.put(requiredType, object);
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T getBean(Class<T> requiredType) {
				Object value = null;
				Set<Class<?>> keySet = map.keySet();
				for (Class<?> type : keySet) {
					if (requiredType == type || type.isAssignableFrom(requiredType)) {
						value = map.get(type);
						break;
					}
				}
				if (null == value) {
					value = applicationContext.getBean(requiredType);
				}
				return (T) value;
			}
		};
	}

	public Object onMessage(String message, HttpServletRequest request, HttpServletResponse response) {
		Data data = null;
		if (JSONUtils.mayBeJSON(message)) {

			String json = request.getParameter("auth");
			Auth auth = OnlyJsonUtil.jsonToObject(json, Auth.class);

			JsonObject jo = new JsonParser().parse(message).getAsJsonObject();

			boolean hasHead = jo.has("head");
			boolean hasBody = jo.has("body");

			if (hasHead && hasBody) {
				JsonElement headElement = jo.get("head");
				JsonObject bodyObject = jo.get("body").getAsJsonObject();

				ClientHead head = gson.fromJson(headElement, ClientHead.class);
				ServerHead resultHead = gson.fromJson(headElement, ServerHead.class);

				String classCode = head.getAction();
				String methodCode = head.getMethod();
				String path = classCode + "/" + methodCode;
				
				Class<?> classType = cd.getClass(path);
				Method method = cd.getMethod(path);
				
		
				if (null != classType && null != method) {
					Object filter = applicationContext.getBean(classType);
					if ((null != filter)) {
						Parameter[] parameters = method.getParameters();
						beanBox.register(Auth.class, auth);
						beanBox.register(Head.class, head);
						beanBox.register(HttpServletRequest.class, request);
						beanBox.register(HttpServletResponse.class, response);
						Object[] datas = pvr.resolver(bodyObject, parameters, beanBox);

						try {

							Object result = method.invoke(filter, datas);

							if (null == result) {
								resultHead.setTime(System.currentTimeMillis());
								ResultMessage m = new ResultMessage();
								m.setHead(resultHead);
								data = m;
							} else if (result instanceof AbstractMessage) {
								AbstractMessage m = (AbstractMessage) result;
								if (null == m.getHead()) {
									resultHead.setTime(System.currentTimeMillis());
									m.setHead(resultHead);
								}
								data = m;
							} else if (result instanceof Info) {
								resultHead.setTime(System.currentTimeMillis());
								ResultBodyMessage m = new ResultBodyMessage();
								m.setHead(resultHead);
								m.setInfo((Info) result);
								data = m;
							} else {
								resultHead.setTime(System.currentTimeMillis());
								ResultBodyMessage m = new ResultBodyMessage();
								m.setHead(resultHead);
								m.setBody(result);
								data = m;
							}
						} catch (IllegalAccessException e) {
							logger.error("", e);
						} catch (IllegalArgumentException e) {
							logger.error("", e);
						} catch (InvocationTargetException e) {
							logger.error("", e);
						}
					}
				}
			} else {
				ServerHead head = new ServerHead();
				head.setAction("");
				head.setMethod("");
				head.setResultCode(Head.code_fail);
				head.setResultMessage("参数错误！");
				head.setTime(System.currentTimeMillis());

				ResultMessage m = new ResultMessage();
				m.setHead(head);
				data = m;
			}

		} else {
			ServerHead head = new ServerHead();
			head.setAction("");
			head.setMethod("");
			head.setResultCode(Head.code_fail);
			head.setResultMessage("参数错误！");
			head.setTime(System.currentTimeMillis());

			ResultMessage m = new ResultMessage();
			m.setHead(head);
			data = m;
		}
		return data;
	}
}
