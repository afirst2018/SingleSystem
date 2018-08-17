package com.chujiu.core.globalPathSetting;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 * @ClassName: CllFreeMarkerView
 * @Description: 设定全局的路径变量，便于前台引用js，css，image等
 * @author suliang
 * @date 2016年5月16日 下午2:04:37
 *
 */
public class CustomFreeMarkerView extends FreeMarkerView {
	private static final String CONTEXT_PATH = "webRootPath";
	private static final String BASE_PATH = "basePath";
	private static final String STATIC_PATH = "staticPath";

	@Override
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		String webRootPath = request.getContextPath();
		String port = "";
		if (80 == request.getServerPort() || 443 == request.getServerPort()) {
			port = "";
		} else {
			port = ":" + Integer.toString(request.getServerPort());
		}
		String basePath = request.getScheme() + "://" + request.getServerName() + port + webRootPath;
		model.put(CONTEXT_PATH, webRootPath);
		model.put(BASE_PATH, basePath);
		model.put(STATIC_PATH, request.getSession().getServletContext().getAttribute(STATIC_PATH));
		super.exposeHelpers(model, request);
	}

}
