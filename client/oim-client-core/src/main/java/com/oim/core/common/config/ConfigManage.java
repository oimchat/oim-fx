/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.common.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.only.common.util.OnlyFileUtil;
import com.only.xml.XmlNodeDecoder;
import com.only.xml.XmlNodeEncoder;

/**
 * 这里是将一些配置转成xml或者将xml转回对象
 * 
 * @author Hero
 */
public class ConfigManage {

	private static Map<Class<?>, Object> objectMap = new ConcurrentHashMap<Class<?>, Object>();
	private static Map<String, Document> documentMap = new ConcurrentHashMap<String, Document>();
	private static Map<String, ResourceBundle> pesourceBundleMap = new ConcurrentHashMap<String, ResourceBundle>();
	private static XmlNodeDecoder xmlDecoder = new XmlNodeDecoder();
	private static XmlNodeEncoder xmlEncoder = new XmlNodeEncoder();

	public static Object get(Class<?> classType) {
		Object object = objectMap.get(classType);
		try {
			if (null == object) {
				object = classType.newInstance();
				objectMap.put(classType, object);
			}
		} catch (Exception ex) {
			Logger.getLogger(ConfigManage.class.getName()).log(Level.SEVERE, null, ex);
		}
		return object;
	}

	public static Object get(String path, Class<?> classType) {
		return get(path, classType, false);
	}

	public static Object get(String path, Class<?> classType, boolean newLoad) {
		Object object = (newLoad) ? null : objectMap.get(classType);
		try {
			if (null == object) {
				object = classType.newInstance();

				Document document = getDocument(path);
				if (null == document) {
					return object;
				}
				Element rootElement = document.getRootElement();
				if (null == rootElement) {
					return object;
				}
				String name = classType.getSimpleName();
				name = name.substring(0, 1).toLowerCase() + name.substring(1);
				Element nodeElement = rootElement.element(name);
				if (null == nodeElement) {
					return object;
				}
				object = xmlDecoder.xmlNodeToObject(nodeElement, classType);

				if (null == object) {
					return classType.newInstance();
				} else {
					objectMap.put(classType, object);
					return object;
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(ConfigManage.class.getName()).log(Level.SEVERE, null, ex);
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public synchronized static void addOrUpdate(String path, Object o) {
		Element rootElement = null;
		Document document = documentMap.get(path);
		if (null == document) {
			document = DocumentHelper.createDocument();
		} else {
			rootElement = document.getRootElement();
		}
		if (null == rootElement) {
			rootElement = DocumentHelper.createElement("root");
			document.add(rootElement);
		}
		String name = o.getClass().getSimpleName();
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		List<Element> nodeElementList = rootElement.elements(name);
		for (Element nodeElement : nodeElementList) {
			rootElement.remove(nodeElement);
		}
		Element nodeElement = xmlEncoder.objectToElementNode(o, name);

		rootElement.add(nodeElement);
		if (saveXml(path, document)) {
			objectMap.put(o.getClass(), o);
		}
	}

	public static boolean saveXml(String path, Document document) {
		boolean b = false;
		try {
			OnlyFileUtil.checkOrCreateFile(path);
			File xmlFile = new File(path);
			FileWriter fileWriter = new FileWriter(xmlFile);
			XMLWriter writer = new XMLWriter(fileWriter);
			writer.write(document);
			writer.close();
			b = true;
			documentMap.put(path, document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	public static String getProperty(String path, String name, boolean createNew) {
		ResourceBundle rb = getResourceBundle(path, createNew);
		if (null == rb) {
			return null;
		}
		if (rb.containsKey(name)) {
			return rb.getString(name);
		} else {
			return null;
		}
	}

	public static Document getDocument(String path) {
		try {
			Document document = documentMap.get(path);
			if (null == document) {
				if (OnlyFileUtil.isFileExists(path)) {
					SAXReader reader = new SAXReader();
					document = reader.read(new File(path));
					documentMap.put(path, document);
				}
			}
			return document;
		} catch (DocumentException ex) {
			return null;
		}
	}

	public static ResourceBundle getResourceBundle(String path, boolean createNew) {
		ResourceBundle rb = pesourceBundleMap.get(path);
		try {
			if (null == rb || createNew) {
				if (OnlyFileUtil.isFileExists(path)) {
					InputStream in = new BufferedInputStream(new FileInputStream(path));
					rb = new PropertyResourceBundle(in);
					pesourceBundleMap.put(path, rb);
				}
			}
		} catch (Exception e) {
		}
		return rb;

	}

	public static void main(String[] args) {
		//addOrUpdate(ConnectConfigData.path, new ConnectConfigData());
	}
}
