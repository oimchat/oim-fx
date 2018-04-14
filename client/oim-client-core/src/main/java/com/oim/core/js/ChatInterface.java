package com.oim.core.js;
/**
 * @author: XiaHui
 * @date: 2017年10月17日 上午9:38:55
 */
public interface ChatInterface extends WebInterface{

	void openUrl(String url);
	
	void download(String url);
	
	void prompt(String text);
}
