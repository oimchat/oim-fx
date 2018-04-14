package com.oim.test;

import java.util.Random;

/**
 * @author: XiaHui
 * @date: 2017年7月13日 下午2:31:20
 */
public class RandomTest {

	public static void main(String[] args) {
		Random random = new Random();
		int sex = random.nextInt(3) + 1;
		System.out.println(sex);
	}
}
