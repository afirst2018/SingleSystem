package com.chujiu.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * [HttpClient工具类]
 * @version: 1.0
 */
public class HttpClientUitl {

	/**
	 * 模拟post请求，进行远程访问并获取返回值
	 * @param mapInfo
	 * @param URL
	 * @return
	 */
	public static String javahttpPost(Map<String, Object> mapInfo, String URL) {
		HttpClient client = HttpClientBuilder.create().build();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
		try {
			String getStr = "";
			HttpPost httpost = new HttpPost(URL);
			httpost.setConfig(defaultRequestConfig);
			// 参数设置
			// 遍历map
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (mapInfo != null) {
				Iterator<String> iter = mapInfo.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					Object value = mapInfo.get(key);
					nvps.add(new BasicNameValuePair(key, value.toString()));
				}
				httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				HttpResponse response = client.execute(httpost);
				int status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_OK) {
					getStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				}
			}
			httpost.releaseConnection();
			return getStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			HttpClientUtils.closeQuietly(client);
		}
	}

	/**
	 * 模拟get请求
	 * @param URL
	 * @return
	 */
	public static String javahttpGet(String URL) {
		HttpClient client = HttpClientBuilder.create().build();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
		try {
			HttpGet httpget = new HttpGet(URL + "&t=" + System.currentTimeMillis());
			httpget.setConfig(defaultRequestConfig);
			httpget.addHeader("Content-type", "text/html; charset=utf-8");
			HttpResponse response = client.execute(httpget);
			String getStr = "";
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				getStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			} else {
				System.out.println(EntityUtils.toString(response.getEntity(),"UTF-8"));
			}
			httpget.releaseConnection();
			return getStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			HttpClientUtils.closeQuietly(client);
		}
	}


	/**
	 * 通过url抓取html页面内容
	 * @param URL
	 * @return
	 */
	public static String getHtmlContentByUrl(String URL) {
		HttpClient client = HttpClientBuilder.create().build();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
		try {
			HttpGet httpget = new HttpGet(URL);
			httpget.setConfig(defaultRequestConfig);
			HttpResponse response = client.execute(httpget);
			String getStr = "";
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				getStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			httpget.releaseConnection();
			return getStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			HttpClientUtils.closeQuietly(client);
		}
	}
}
