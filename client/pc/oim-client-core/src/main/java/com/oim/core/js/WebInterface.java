package com.oim.core.js;
/**
 * @author: XiaHui
 * @date: 2017-10-17 9:38:55
 */
public interface WebInterface {

	void openUrl(String url);
	
	void download(String url);
	
	void prompt(String text);
	
	void showImage(String source);
}
