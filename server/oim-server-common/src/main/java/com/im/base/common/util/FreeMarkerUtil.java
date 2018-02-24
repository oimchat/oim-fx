package com.im.base.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

/**
 * @Author: XiaHui
 * @Date: 2015年12月30日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2015年12月30日
 */
public class FreeMarkerUtil {

	public final static Map<String, Configuration> map = new HashMap<String, Configuration>();

	// templatePath模板文件存放路径
	// templateName 模板文件名称
	// filename 生成的文件名称
	public static void analysisTemplate(String templatePath, String templateName, String fileName, Map<?, ?> root) {
		try {
			Version version = new Version("2.3.23");
			Configuration config = new Configuration(new Version("2.3.23"));
			// 设置要解析的模板所在的目录，并加载模板文件
			config.setDirectoryForTemplateLoading(new File(templatePath));
			// 设置包装器，并将对象包装为数据模型
			config.setObjectWrapper(new DefaultObjectWrapper(version));

			// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			// 否则会出现乱码
			Template template = config.getTemplate(templateName, "UTF-8");
			// 合并数据模型与模板
			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			template.process(root, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	public static String getTemplate(String templateClassPath, String templateName, Map<?, ?> dataMap) {

		String temp = "";
		//		ByteArrayOutputStream os = null;
		//		Writer out = null;
		try {
			Configuration config = map.get(templateClassPath);
			if (null == config) {
				Version version = new Version("2.3.23");
				config = new Configuration(new Version("2.3.23"));
				config.setDirectoryForTemplateLoading(new File(FreeMarkerUtil.class.getResource(templateClassPath).getFile()));// 设置要解析的模板所在的目录，并加载模板文件
				config.setObjectWrapper(new DefaultObjectWrapper(version));// 设置包装器，并将对象包装为数据模型
				map.put(templateClassPath, config);
			}
			Template template = config.getTemplate(templateName, "UTF-8");// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致

			temp = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap); // ，否则会出现乱码
			// 合并数据模型与模板
			//			os = new ByteArrayOutputStream();
			//			out = new OutputStreamWriter(os, "UTF-8");
			//			template.process(dataMap, out);
			//			temp = new String(os.toByteArray(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			//			if (null != os) {
			//				try {
			//					os.close();
			//				} catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//			}
			//			if (null != out) {
			//				try {
			//					out.close();
			//				} catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//			}
		}
		return temp;
	}
}
