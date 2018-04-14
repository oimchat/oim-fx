package com.oim.test;

/**
 * @author: XiaHui
 * @date: 2017年8月17日 下午4:34:33
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUrl {
	public static void main(String[] args) {
		// 截取网页中的链接中的TITLE和URL，且URL必须以HTTP或HTTPS开头
		// String s = "<div><a href='http://www.baidu.com' title='tip'>aaa</a><a
		// href='https://www.baidu.com?param=1'
		// title='baiduSearch'>bbb</a><span><a
		// href='file:///d:/test/Arr.txt'>ccc</a></span></div>";
		// String s = "撒的发生的手动阀http://www.baidu.com阿斯顿发送到";
		// String s =
		// "开始大发送到https://www.cnblogs.com/akiradunn/p/5855073.html大发送到http://www.tngou.NET/blog/show/496大发送到结束http://www.baidu.com/daf";
		String s = "博客地址:http://blog.csdn.net/u013456370页面初始化赋值：http://blog.csdn.Net/u013456370/article/details/68067469";
		String b="打开www.baidu.com和http://www.c.c?ss=da&nd=拿到  然后http://www.c.c?ss=da&nd= https://www.c.c:800/手动?ss=da&nd=";
		
		// String mode =
		// "<a\\s*href=(?='?http|https)([^>]*)title=([^>]*)>(.*?)</a>";
		// String mode = "https?://\\w+\\.\\w+\\.\\w+";
		// String mode = "[a-zA-z]+://[^\\s]*+[a-zA-z]";
		String mode = "(http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&*=]*))";
		Pattern p = Pattern.compile(mode);
		Matcher m = p.matcher(b);
		String s1 = "";
		int i = 0;
		while (m.find()) {
			//System.out.println("find...");
			String url = m.group();
			System.out.println(url);
			// <a href="https://www.baidu.com" target="_blank">百度</a>
//			if (i > 0) {
//				s1 = s1.replaceAll(url, "<a href=\'" + url + "\'" + " target=\'_blank\'>" + url + "</a>");
//				System.out.println(i);
//			} else {
//				s1 = s.replaceAll(url, "<a href=\'" + url + "\'" + " target=\'_blank\'>" + url + "</a>");
//			}
//			i++;
//			System.out.println("src:" + url);
//			System.out.println(s1);
		}
		// System.out.println("最后替换的：" + s1);
	}
}