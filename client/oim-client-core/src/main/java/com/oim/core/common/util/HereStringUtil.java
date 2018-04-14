/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author XiaHui
 */
public class HereStringUtil {

	private static NumberFormat numberFormat = new DecimalFormat("0000000000000000");
	static Pattern numberPattern = Pattern.compile("[0-9]*");

	public static String value(String value) {
		return (value == null) ? "" : value;
	}

	public static String numberToString(int number) {
		return numberFormat.format(number);
	}

	public static boolean isIntegerNumber(String text) {
		Matcher matcher = numberPattern.matcher(text);
		return matcher.matches() && !"".equals(text);
	}

	public static String exceptionToString(Exception e) {
		StackTraceElement[] array = e.getStackTrace();
		StringBuilder exception = new StringBuilder();
		for (StackTraceElement stackTraceElement : array) {
			exception.append(stackTraceElement);
			exception.append("\n");
		}
		return exception.toString();
	}

	public static String exceptionToString(Throwable e) {
		StackTraceElement[] array = e.getStackTrace();
		StringBuilder exception = new StringBuilder();
		if (null != array) {
			for (StackTraceElement stackTraceElement : array) {
				exception.append(stackTraceElement);
				exception.append("\n");
			}
		}
		return exception.toString();
	}

	public static String bytesToStringValue(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(bytes[i]);
			if (i < bytes.length) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public static byte[] stringValueToBytes(String value) {
		String[] arry = value.split(",");
		byte[] bytes = new byte[arry.length];

		for (int i = 0; i < arry.length; i++) {
			bytes[i] = Byte.valueOf(arry[i]);
		}
		return bytes;
	}
}
