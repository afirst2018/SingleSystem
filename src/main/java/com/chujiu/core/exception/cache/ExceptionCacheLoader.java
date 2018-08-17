package com.chujiu.core.exception.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: ExceptionCacheLoader 
 * @Description: 异常缓存加载器类 
 * @author chujiu
 * @date 2016年5月16日 上午11:52:29 
 *
 */
@Component
public class ExceptionCacheLoader {

	private final static Logger log = LoggerFactory.getLogger(ExceptionCacheLoader.class);
	/**
	 * 
	 * @Title: init 
	 * @Description: 初始化缓存
	 * @author suliang
	 */
	@PostConstruct
	public void init() {
		FileInputStream fr = null;
		File file = null;
		try {
			file = new File(java.net.URLDecoder.decode(getClass().getResource("/conf/exception.properties").getFile(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("不支持字符编码", e);
		}
		try {
			fr = new FileInputStream(file);
			Properties props = new Properties();
			props.load(fr);
			Set<Entry<Object, Object>> entrySet = props.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				if (!entry.getKey().toString().startsWith("#")) {
					ExceptionInfo ei = new ExceptionInfo();
					ei.setCode(((String) entry.getKey()).trim());
					ei.setMessage(((String) entry.getValue()).trim());
					ExceptionCodeCache.addExceptionCache(ei.getCode(), ei);
				}
			}
		} catch (FileNotFoundException e) {
			log.error("找不到配置文件", e);
		} catch (IOException e) {
			log.error("初始化配置错误", e);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					log.error("初始化配置错误", e);
				}
			}
		}
	}
}
