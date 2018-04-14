package com.demo.string;

/**
 * @author: XiaHui
 * @date: 2017年7月25日 上午9:04:14
 */
public class HttpFindReplace {
	// try this：
	public static void main(String[] args) {
		String str = "看这个地址www.pp.cc/sasd?h=d&b=d哈哈撒hashhttps://www.pp.cc/sasd?h=d&b=d";
		System.out.println(str2Url(str));
		//String str = "http://www.xxx.com/1噶为欧冠奇偶位i二姐http://www.xxx.com/1/2乌黑欧冠我几乎http://www.xxx.com/1/2/3的稿费欧文和岗位i偶尔给http://www.xxx.com/1.jpg";
		str = str.replaceAll("(?is)(http://[/\\.\\w]+\\.jpg)", "<img src='$1'/>");
		str = str.replaceAll("(?is)(?<!')(http://[/\\.\\w]+)", "<a href='$1'>$1</a>");
		System.out.println(str);
		
	}
	
	/**
	 * 将字符串中符合url格式的字符串选出，全部添加超链接的a标签
	 * @param str
	 * @return
	 */
	public synchronized static String str2Url(String str){
		/**
		 * 正则表达式说明
		 * (http://)?? 			表示一次或者多次
		 * (\\w)+				表示单词字符：[a-zA-Z_0-9] 出现至少一次
		 * (\\.\\w+)+		 	表示 .单词字符出现至少一次
		 * ([/\\w\\.\\?=%&-:@#$]*+)*+		表示 /单词字符.?=%&-:@#$出现0到多次的形式的字符串出现0到多次
		 */
		
		String reg = "(http://)??(\\w)+(\\.\\w+)+([/\\w\\.\\?=%&-:@#$]*+)*+";
		//str = str.replace("http://", "");
		String outStr = str.replaceAll("("+reg+")", "<a href='http://$1'>http://$1</a>");
		return outStr;
	}
	
	/**
	 * 将字符串中符合url格式的字符串选出，全部添加超链接的a标签
	 * @param str
	 * @return
	 */
	public synchronized static String sting2Url(String str){
		/**
		 * 正则表达式说明
		 * (http://)?? 			表示一次或者多次
		 * (\\w)+				表示单词字符：[a-zA-Z_0-9] 出现至少一次
		 * (\\.\\w+)+		 	表示 .单词字符出现至少一次
		 * ([/\\w\\.\\?=%&-:@#$]*+)*+		表示 /单词字符.?=%&-:@#$出现0到多次的形式的字符串出现0到多次
		 */
		//(?is)(?<!')(http://[/\\.\\w]+)
		String reg = "(http://)??(\\w)+(\\.\\w+)+([/\\w\\.\\?=%&-:@#$]*+)*+";
		str = str.replace("http://", "");
		String outStr = str.replaceAll("("+reg+")", "<a href='http://$1'>http://$1</a>");
		return outStr;
	}
}
