package com.oim.fx.common;

/**
 * @author XiaHui
 * @date 2017年9月28日 下午2:17:48
 */
public interface DataConverter<T, E> {

	E converter(T data);
}
