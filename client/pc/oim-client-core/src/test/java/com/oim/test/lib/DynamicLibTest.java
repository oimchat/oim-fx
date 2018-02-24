package com.oim.test.lib;

import java.util.Set;

import com.oim.core.common.util.ExtClasspathLoader;
import com.only.common.spring.util.ClassScaner;

/**
 * @author XiaHui
 * @date 2017-11-13 14:01:06
 */
public class DynamicLibTest {

	public static void main(String[] args) {
		//SystemUtil.addLibPath("lib");
		ExtClasspathLoader.loadClasspath();
		ClassScaner cs = new ClassScaner();
		Set<Class<?>> classSet = cs.doScan("com.only.lib");
		for(Class<?> c:classSet) {
			System.out.println(c);
		}
		
		try {
			Object o=Class.forName("com.only.lib.OnlyData");
			System.out.println(o);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
