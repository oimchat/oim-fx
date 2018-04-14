package com.oim.core.common.function;

import com.oim.common.util.HttpUrlUtil;
import com.oim.core.js.ChatInterface;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author: XiaHui
 * @date: 2017年10月17日 下午2:28:36
 */
public class ChatInterfaceImpl extends AbstractComponent implements ChatInterface{

	public ChatInterfaceImpl(AppContext appContext) {
		super(appContext);
	}

	@Override
	public void showImage(String source) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void openUrl(String url) {
		HttpUrlUtil.open(url);
	}

	@Override
	public void download(String url) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void prompt(String text) {
		// TODO 自动生成的方法存根
		
	}

}
