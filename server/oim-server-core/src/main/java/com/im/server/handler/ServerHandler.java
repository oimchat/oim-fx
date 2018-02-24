package com.im.server.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.im.base.common.task.ExecuteTask;
import com.im.base.common.task.QueueTaskThread;
import com.only.common.action.ActionDispatch;
import com.only.common.resolver.BeanBox;
import com.only.common.resolver.gson.DefineValuesResolver;
import com.only.common.result.Info;
import com.only.general.annotation.action.MethodMapping;
import com.only.net.session.SocketSession;
import com.onlyxiahui.im.message.AbstractMessage;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.ClientHead;
import com.onlyxiahui.im.message.server.ResultBodyMessage;
import com.onlyxiahui.im.message.server.ResultMessage;
import com.onlyxiahui.im.message.server.ServerHead;

import net.sf.json.util.JSONUtils;

@Component
public class ServerHandler {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	private ApplicationContext applicationContext = null;
	@Resource
	private SessionHandler sessionHandler;
	private final Gson gson = new Gson();
	private final JsonParser jsonParser = new JsonParser();
	private final ActionDispatch cd = new ActionDispatch("com.im.server.general.business.action");
	private DefineValuesResolver pvr = new DefineValuesResolver();

	@Resource
	QueueTaskThread queueTaskThread;

	public ServerHandler() {
		init();
	}

	private void init() {
	}

	public void onMessage(String message, SocketSession socketSession) {
		queueTaskThread.add(new ExecuteTask() {

			@Override
			public void execute() {
				doMessage(message, socketSession);
			}
		});
	}

	public void doMessage(String message, SocketSession socketSession) {
		Object result = null;
		if (JSONUtils.mayBeJSON(message)) {
			try {
				JsonObject jo = jsonParser.parse(message).getAsJsonObject();

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
							boolean isAuth = interceptor(filter, method, socketSession);
							Parameter[] parameters = method.getParameters();
							BeanBox beanBox = getBeanBox();
							beanBox.register(Head.class, head);
							beanBox.register(SocketSession.class, socketSession);
							Object[] data = pvr.resolver(bodyObject, parameters, beanBox);

							try {
								if (isAuth) {
									result = method.invoke(filter, data);
									if (null == result) {
										resultHead.setTime(System.currentTimeMillis());
										ResultMessage m = new ResultMessage();
										m.setHead(resultHead);
										write(socketSession, m);
									} else if (result instanceof AbstractMessage) {
										AbstractMessage m = (AbstractMessage) result;
										if (null == m.getHead()) {
											resultHead.setTime(System.currentTimeMillis());
											m.setHead(resultHead);
										}
										write(socketSession, m);
									} else if (result instanceof Info) {
										resultHead.setTime(System.currentTimeMillis());
										ResultBodyMessage m = new ResultBodyMessage();
										m.setHead(resultHead);
										m.setInfo((Info) result);
										write(socketSession, m);
									} else {
										resultHead.setTime(System.currentTimeMillis());
										ResultBodyMessage m = new ResultBodyMessage();
										m.setHead(resultHead);
										m.setBody(result);
										write(socketSession, m);
									}
								} else {
									resultHead.setResultCode(Head.code_fail);
									resultHead.setResultMessage("您没有权限访问！");
									resultHead.setTime(System.currentTimeMillis());

									ResultMessage m = new ResultMessage();
									m.setHead(resultHead);
									write(socketSession, m);
								}
							} catch (IllegalAccessException e) {
								logger.error("", e);
								// resultHead.setAction("");
								// resultHead.setMethod("");
								resultHead.setResultCode(Head.code_fail);
								resultHead.setResultMessage("系统异常！");
								resultHead.setTime(System.currentTimeMillis());

								ResultMessage m = new ResultMessage();
								m.setHead(head);
								write(socketSession, m);
							} catch (IllegalArgumentException e) {
								logger.error("", e);
								resultHead.setResultCode(Head.code_fail);
								resultHead.setResultMessage("系统异常！");
								resultHead.setTime(System.currentTimeMillis());

								ResultMessage m = new ResultMessage();
								m.setHead(head);
								write(socketSession, m);
							} catch (InvocationTargetException e) {
								logger.error("", e);
								resultHead.setResultCode(Head.code_fail);
								resultHead.setResultMessage("系统异常！");
								resultHead.setTime(System.currentTimeMillis());

								ResultMessage m = new ResultMessage();
								m.setHead(head);
								write(socketSession, m);
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
					write(socketSession, m);
				}
			} catch (Exception e) {
				logger.error("", e);
				ServerHead head = new ServerHead();
				head.setAction("");
				head.setMethod("");
				head.setResultCode(Head.code_fail);
				head.setResultMessage("请求协议异常！");
				head.setTime(System.currentTimeMillis());

				ResultMessage m = new ResultMessage();
				m.setHead(head);
				write(socketSession, m);
			}
		}
	}

	private BeanBox getBeanBox() {
		BeanBox beanBox = new BeanBox() {

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
		return beanBox;
	}

	private boolean interceptor(Object filter, Method method, SocketSession socketSession) {
		boolean isAuth = false;
		if (!isIntercept(method)) {
			isAuth = true;
		} else {
			isAuth = socketSession.isAuth();
		}
		return isAuth;
	}

	private boolean isIntercept(Method method) {
		boolean isIntercept = true;
		MethodMapping mm = method.getAnnotation(MethodMapping.class);
		if (null != mm) {
			isIntercept = mm.isIntercept();
		}
		return isIntercept;
	}

	private void write(SocketSession socketSession, Object object) {
		if (null != object) {
			socketSession.write(object);
		}
	}

	public void onClose(SocketSession socketSession) {
		sessionHandler.removeSession(socketSession);
	}

	public void scan(String path) {
		cd.scan(path);
	}

	public void coverAction(Class<?> classType) {
		cd.cover(classType);
	}
}
