package com.chujiu.core.globalPathSetting;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created on   2016年6月22日
 * Title:       初九数据科技后台管理系统_[公共]
 * Description: [设定静态资源URL]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
public class StaticPathContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties p = new Properties();
		String file = "conf/config.properties";
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
			//sce.getServletContext().getResourceAsStream("/WEB-INF/classes/conf/config.properties");
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("staticPath", p.getProperty("static.resource.url", ""));
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
