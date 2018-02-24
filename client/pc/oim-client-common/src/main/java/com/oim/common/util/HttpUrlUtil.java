package com.oim.common.util;

import java.awt.Desktop;
import java.lang.reflect.Method;

/**
 * @author: XiaHui
 * @date: 2017年7月25日 上午9:15:49
 */
public class HttpUrlUtil {
	/**
	 * 正则表达式说明 (http://)?? 表示一次或者多次 (\\w)+ 表示单词字符：[a-zA-Z_0-9] 出现至少一次 (\\.\\w+)+
	 * 表示 .单词字符出现至少一次 ([/\\w\\.\\?=%&-:@#$]*+)*+ 表示
	 * /单词字符.?=%&-:@#$出现0到多次的形式的字符串出现0到多次
	 */
	// (?is)(?<!')(http://[/\\.\\w]+)
	//static String http = "(http://)??(\\w)+(\\.\\w+)+([/\\w\\.\\?=%&-:@#$]*+)*+";
	static String http = "(http[s]?://)??(\\w)+(\\.\\w+)+([/\\w\\.\\?=%&-:@#$]*+)*+";
	static String https = "(https://)??(\\w)+(\\.\\w+)+([/\\w\\.\\?=%&-:@#$]*+)*+";

	/**
	 * 将字符串中符合url格式的字符串选出，全部添加超链接的a标签
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceUrl(String text, String value) {
		String temp = null;
		if (null != text) {
			//text = text.replace("http://", "");
			text = text.replace("&amp;", "&");
			temp = text.replaceAll("(" + http + ")", value);
		}
		return temp;
	}

	public static String replaceUrlToLink(String text) {
		//return replaceUrl(text, "<a href=\"http://$1\">http://$1</a>");
		return replaceUrl(text, "<a href=\"$1\">$1</a>");
	}

	public static String replaceUrlToLink(String text, String attribute) {
		return replaceUrl(text, "<a " + attribute + " href=\"$1\">$1</a>");
	}
	
	public static String replaceUrlToDisableLink(String text, String attribute) {
		return replaceUrl(text, "<a " + attribute + " href=\"javascript:void(0);\">$1</a>");
	}

	public static boolean open(String url) {
		boolean mark = true;
		try {
			java.net.URI uri = new java.net.URI(url);
			java.awt.Desktop d = java.awt.Desktop.getDesktop();
			if (Desktop.isDesktopSupported() && d.isSupported(java.awt.Desktop.Action.BROWSE)) {
				java.awt.Desktop.getDesktop().browse(uri);
			} else {
				browse(url);
			}
		} catch (Exception e) {
			mark = false;
		}
		return mark;
	}

	private static void browse(String url) throws Exception {
		// 获取操作系统的名字
		String osName = System.getProperty("os.name", "");
		if (osName.startsWith("Mac OS")) {// 苹果的打开方式
			Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
			Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
			openURL.invoke(null, new Object[] { url });
		} else if (osName.startsWith("Windows")) {// windows的打开方式。
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		} else {// Unix or Linux的打开方式
			String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
			String browser = null;
			for (int count = 0; count < browsers.length && browser == null; count++)
				// 执行代码，在brower有值后跳出，
				// 这里是如果进程创建成功了，==0是表示正常结束。
				if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) {
					browser = browsers[count];
				}
			if (browser == null) {
				throw new Exception("Could not find web browser");
			} else {// 这个值在上面已经成功的得到了一个进程。
				Runtime.getRuntime().exec(new String[] { browser, url });
			}
		}
	}

	public static void main(String[] arg) {
		String text = "你打开这个地址http://www.onlyoim.com/index.html?userId=10001&name=账号t   图片https://www.onlyoim.com/1.jpg";
		//System.out.println(replaceUrlToLink(text));
		//text = "http://www.onlyoim.com/index.html?userId=10001&name=账号";
		//System.out.println(replaceUrlToLink(text));
		String b="打开www.baidu.com和http://www.c.c?ss=da&nd=拿到  然后http://www.c.c?ss=da&nd= https://www.c.c:800/d.do?ss=da&nd=";
		
		//text = text.replace("http://", "");
		
		http = "(http[s]?://)??(\\w)+(\\.\\w+)+([/\\w\\.\\?=%&-:@#$]*+)*+";
		text = b.replaceAll("(" + http + ")", "<a href=\"$1\">$1</a>");
		System.out.println(text);
		//System.out.println(open("www.baidu.com?u=1"));
	}
}
