package com.oim.core.business.manager;

import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author XiaHui
 * @date 2017-11-10 10:31:00
 */
public class ContentHandler extends AbstractComponent{

	public ContentHandler(AppContext appContext) {
		super(appContext);
	}

	
	public StringBuilder getImageTag(String id, String name, String value, String path) {
		
		StringBuilder image = new StringBuilder();
		image.append("	<img ");
		if (null != id && !"".equals(id)) {
			image.append(" id=\"");
			image.append(id);
			image.append("\"");
		}
		if (null != name && !"".equals(name)) {
			image.append(" name=\"");
			image.append(name);
			image.append("\"");
		}
		if (null != value && !"".equals(value)) {
			image.append(" value=\"");
			image.append(value);
			image.append("\"");
		}
		image.append(" src=\"");
		image.append(path);
		image.append("\" ");
		image.append("	style=\" max-width: 100%; height: auto;\" ");
		image.append(" />");
		return image;
	}
	
	
	public String handlePath(String path) {
		String p = null;
		if (null != path && !"".equals(path)) {
			p = path.replace("\\", "/");
		}
		return p;
	}



	public String toFileSource(String path) {
		StringBuilder source = new StringBuilder();
		if (null != path && !"".equals(path)) {
			String p = handlePath(path);
			source.append("file:");
			if (!p.startsWith("/")) {
				source.append("/");
			}
			source.append(handlePath(path));
		}
		return source.toString();
	}
}
