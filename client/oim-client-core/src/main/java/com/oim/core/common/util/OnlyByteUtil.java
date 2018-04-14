package com.oim.core.common.util;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月24日 下午12:16:13
 * @version 0.0.1
 */
public class OnlyByteUtil {

	public static String bytesToString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();

		if (null != bytes && bytes.length > 0) {
			int length = bytes.length;
			int end = length - 1;
			for (int i = 0; i < length; i++) {
				sb.append(bytes[i]);
				if (i < end) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}
	public static byte[] stringToBytes(String value) {
		byte[] bytes=null;
		if(null!=value&&!"".equals(value)){
			String[] array= value.split(",");
			int length=array.length;
			bytes=new byte[length];
			for (int i = 0; i < length; i++) {
				bytes[i]=Byte.parseByte(array[i]);
			}
		}
		return bytes;
	}
	
	public static int bytesToInt(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < bytes.length; i++) {
			value += (bytes[i] & 0XFF) << (8 * (3 - i));
		}
		return value;
	}

	public static byte[] intToBytes(int value) {
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			bytes[i] = (byte) (value >> (24 - i * 8));
		}
		return bytes;
	}
	
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}
	
	public static void main(String[] s){
		String m="123456";
		byte[] bytes=m.getBytes();
		String bs=bytesToString(bytes);
		System.out.println(bs);
		System.out.println(stringToBytes(bs)[0]);
	}
}
