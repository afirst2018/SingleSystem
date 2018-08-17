package com.chujiu.core.util;

import org.springframework.util.StringUtils;

/**
 * 
 * @ClassName: SqlTool 
 * @Description: sql工具类 
 * @author chujiu
 * @date 2016年5月16日 上午11:15:15 
 *
 */
public class SqlTool {

	/**
	 * 
	 * @Title: transfer 
	 * @Description: 替换模糊查询的通配符 
	 * @author chujiu
	 *
	 * @param keyword
	 * @return String
	 */
	public static String transfer(String keyword) {
		if(!StringUtils.isEmpty(keyword) && (keyword.contains("%") || keyword.contains("_"))){  
			keyword = keyword.replaceAll("\\\\", "\\\\\\\\")  
							 .replaceAll("\\%", "\\\\%")  
							 .replaceAll("\\_", "\\\\_");
		} 
		return keyword;
	}
}
