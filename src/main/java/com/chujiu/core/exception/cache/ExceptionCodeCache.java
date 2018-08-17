package com.chujiu.core.exception.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: ExceptionCodeCache 
 * @Description: 异常信息缓存 
 * @author chujiu
 * @date 2016年5月16日 下午2:02:20 
 *
 */
public class ExceptionCodeCache {
	private static Map<String, ExceptionInfo> exceptionMap = new HashMap<String, ExceptionInfo>();

	/**
	 * 加载异常配置信息到缓存
	 * @param key 异常码
	 * @param value 异常详细信息
	 */
	public static void addExceptionCache(String key, ExceptionInfo value) {
		if (!exceptionMap.containsKey(key)) {
			exceptionMap.put(key, value);
		}
	}

	/**
	 * 获取指定的异常配置信息
	 * @param key 要获取的信息所对应的异常码
	 * @return 异常配置信息
	 */
	public static ExceptionInfo getExceptionCache(String key) {
		ExceptionInfo exp = exceptionMap.get(key);
		if(exp == null){
			exp = new ExceptionInfo();
			exp.setCode(key);
			exp.setMessage("未知的异常信息,异常码="+key);
		}
		return exp;
	}
}
