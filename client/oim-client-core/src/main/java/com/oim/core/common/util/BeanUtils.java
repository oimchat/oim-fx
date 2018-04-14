package com.oim.core.common.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

public class BeanUtils {
	/**
	 * 拷贝属性
	 * props 需要拷贝的属性(即属性为空是否需要覆盖，有就覆盖（用数据中的值来覆盖），没有就不覆盖)
	 * 这里还可以优化 对于循环拷贝我们可以把反射的信息缓存起来放线程中
	 */
	public static Object copyProperties(Object toBean,Object fromBean,List<String> fieldNameList){
		Class<?>[] classes = new Class[0];
		Object[] objects = new Object[0];
		try {			
			Class<? extends Object> formBeanClass = fromBean.getClass();			
			Method[] beanMethods = toBean.getClass().getMethods();			
 
			String methodName  = null;
			String getMethodName = null;
			Class<?>[] paramsType = null;
			Class<?> paramType = null;
			//这里为什么用method去循环,而不用fields去循环?呵呵,这个就是提升性能的一个点
			for(Method method: beanMethods){
				methodName = method.getName();
				if(methodName.startsWith("set")){
					paramsType = method.getParameterTypes();
					String _prop=methodName.replace("set","");
					String prop=_prop.replaceFirst(_prop.substring(0,1), _prop.substring(0,1).toLowerCase());
					
					if(paramsType.length != 1){
						continue;
					}
					paramType = paramsType[0];
					if(boolean.class.getSimpleName().equalsIgnoreCase(paramType.getName())){
						getMethodName = "is"+methodName.substring(3);
					}else{
						getMethodName = "get"+methodName.substring(3);
					}
 
					try{
						Method formBeanGetMethod = formBeanClass.getMethod(getMethodName, classes);
						if( paramType.equals( formBeanGetMethod.getReturnType() )){
							Object value = formBeanGetMethod.invoke(fromBean,objects);
							if (value != null){
								method.invoke(toBean,new Object[]{value});
							}else {
								if(fieldNameList!=null&&(fieldNameList.size()>0&&fieldNameList.contains(prop))) { // 当值为空时，直接覆盖数据
									method.invoke(toBean,new Object[]{value}); 
								}
							}
						}
					}catch (NoSuchMethodException e) {
						
					}
				}				
			}		
			return toBean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object copyProperties(Object toBean,Object fromBean){
		 return copyProperties(toBean, fromBean, null);
	}
	/**
	 * 
	 * @Methodname: parseQuerySqlByParenthesis
	 * @Discription: 解析sql语句通过配对的（）,不支持嵌套（）
	 * @param parentSql
	 * @return Map<String,String>  key为临时替代符，value为sql片段
	 *
	 */
	public static Map<String,String> parseQuerySqlByParenthesis(String parentSql) {
		Map<String,String> map=new HashMap<String, String>();
		Map<Integer,Integer> index = getMatchedParenthesis(parentSql);
		Iterator<Entry<Integer,Integer>> it=index.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Integer,Integer> entry=it.next(); 
			int start=entry.getKey();
			int end=entry.getValue();
			map.put("parenthesis("+start+")",parentSql.substring(start,end));
		}
		return map;
	}
	
	public static Map<Integer,Integer> getMatchedParenthesis(String brackets) {
		int string_size = brackets.length();
		Stack<Character> match = new Stack<Character>();
		Stack<Integer> matchLeftIndex = new Stack<Integer>();
		int times = 0;
		Map<Integer, Integer> index = new HashMap<Integer, Integer>();
		char symbol;
		for (int i = 0; i < string_size; i++) {
			symbol = brackets.charAt(i);
			if (symbol == '(') {
				if ((!match.isEmpty()) && match.peek() == symbol)
					times++;
				else {
					match.push(symbol);
					matchLeftIndex.push(i);
				}
			}
			if (symbol == ')') {
				if (times == 0) {
					if (match.isEmpty()) {
						throw new RuntimeException("括号不匹配");
					} else {
						if (symbol == ')' && match.peek() == '(') {
							match.pop();
							index.put(matchLeftIndex.pop(), i);
						} else {
							throw new RuntimeException("括号不匹配");
						}
					}
				} else
					times--;
			}

		}
		if (!match.empty())
			throw new RuntimeException("括号不匹配");

		return index;
	}
	
	public static String removeTabs(String str) {
		return str.replaceAll("[\\s*\t\n\r]", "");
	}
	
	public static String replaceTabs(String str) {
		return str.replaceAll("[\t\n\r]", " ");
	}
	
	public static boolean isEmpty(String obj) {
		return null==obj||"".equals(removeTabs(obj));
	}
	
	public static boolean isNotEmpty(String obj) {
		return null!=obj&&!"".equals(removeTabs(obj));
	}

}
