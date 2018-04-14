package com.demo.string;

/**
 * @author: XiaHui
 * @date: 2017年7月31日 上午11:27:04
 */
public class Html {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		String test = "<div>sss</div>234<strong>324</strong>324<em>32<a href=\"#\">4te</a>st1</em>2<img src=\"test.jpg\" />3";
		//System.out.println(test);
		System.out.println(test.replaceAll("<(?!img)[^>]*>", ""));

		test = "<div>sss</div>234<strong>324</strong>324<em>32<a href=\"#\">4te</a>st1</em>2<img src=\"test.jpg\" />3";
		//System.out.println(test);
		System.out.println(test.replaceAll("<(img|IMG)[^<>]*>", ""));
		
		test = "<div>sss</div>234<strong>324</strong>324<em>32<a href=\"#\">4te</a>st1</em>2<img src=\"test.jpg\" />3";
		//System.out.println(test);
		System.out.println(test.replaceAll("<(?!(img|IMG))[^>]*>", ""));
		

	}

}
