package com.oim.common.util;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年6月6日 上午9:25:18
 * @version 0.0.1
 */
public class SystemUtil {

	public static void addPath(String path) {
		try {
			Field field = ClassLoader.class.getDeclaredField("usr_paths");
			field.setAccessible(true);
			String[] paths = (String[]) field.get(null);
			for (int i = 0; i < paths.length; i++) {
				if (path.equals(paths[i])) {
					return;
				}
			}
			String[] tmp = new String[paths.length + 1];
			System.arraycopy(paths, 0, tmp, 0, paths.length);
			tmp[paths.length] = path;
			field.set(null, tmp);
			paths = (String[]) field.get(null);
			System.setProperty("java.library.path", StringUtils.join(paths, ";"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String a[]){
		//String libPath = System.getProperty("java.library.path");
		//System.out.println(libPath);
		addLibPath("dll");
		
	}
	
	public static void addLibPath(String path){
		if(null!=path&&!"".equals(path)){
			try {
				Field field = ClassLoader.class.getDeclaredField("usr_paths");
				field.setAccessible(true);
				String[] paths = (String[]) field.get(null);
				for (int i = 0; i < paths.length; i++) {
					if (path.equals(paths[i])) {
						return;
					}
				}
				String[] tmp = new String[paths.length + 1];
				System.arraycopy(paths, 0, tmp, 0, paths.length);
				tmp[paths.length] = path;
				field.set(null, tmp);
				paths = (String[]) field.get(null);
				
				StringBuilder addPath=new StringBuilder();
				int lastAddPathIndex=path.lastIndexOf(";");
				if(-1!=lastAddPathIndex&&(lastAddPathIndex==(path.length()-1))){
					addPath.append(path);
				}else{
					addPath.append(path);
					addPath.append(";");
				}
				
				StringBuilder sb=new StringBuilder();
				String libPath = System.getProperty("java.library.path");
				if(null==libPath||"".equals(libPath)){
					sb.append(addPath);
				}else{
					sb.append(libPath);
					int lastIndex=sb.lastIndexOf(";");
					if(-1!=lastIndex){
						sb.insert(lastIndex+1, addPath);
					}else{
						sb.insert(0, addPath);
					}
				}
				
				System.setProperty("java.library.path", sb.toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
}
