package org.crazyit.auction.client.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class HttpUtil
{
	public static final String BASE_URL = "http://192.168.1.88:8888/auction/api/";
	private static Map<String, List<Cookie>> cookieStore = new HashMap<>();
	// 创建线程池
	private static ExecutorService threadPool = Executors.newFixedThreadPool(30);
	// 创建默认的OkHttpClient对象
	private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.cookieJar(new CookieJar()
		{
			@Override
			public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list)
			{
				cookieStore.put(httpUrl.host(), list);
			}

			@Override
			public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl)
			{
				List<Cookie> cookies = cookieStore.get(httpUrl.host());
				return cookies == null ? new ArrayList<>() : cookies;
			}
		}).build();
	/**
	 *
	 * @param url 发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception 该方法可能引发的异常
	 */
	public static String getRequest(String url) throws Exception
	{
		FutureTask<String> task = new FutureTask<>(() -> {
			System.out.println("=============" + url);
			// 创建请求对象
			Request request = new Request.Builder()
				.url(url)
				.build();
			Call call = okHttpClient.newCall(request);
			// 发送GET请求
			Response response = call.execute();
			// 如果服务器成功地返回响应
			if (response.isSuccessful() && response.body() != null)
			{
				// 获取服务器响应字符串
				return response.body().string().trim();
			}
			else
			{
				return null;
			}
		});
		threadPool.submit(task); // 提交任务
		return task.get();
	}

	/**
	 * @param url 发送请求的URL
	 * @param rawParams 请求参数
	 * @return 服务器响应字符串
	 * @throws Exception 该方法可能引发的异常
	 */
	public static String postRequest(String url, Map<String, String> rawParams) throws Exception
	{
		FutureTask<String> task = new FutureTask<>(() -> {
			// 构建包含请求参数的表单体
			FormBody.Builder builder = new FormBody.Builder();
			rawParams.forEach(builder::add);
			FormBody body = builder.build();
			// 创建请求对象
			Request request = new Request.Builder()
					.url(url)
					.post(body).build();
			Call call = okHttpClient.newCall(request);
			// 发送POST请求
			Response response = call.execute();
			// 如果服务器成功地返回响应
			if (response.isSuccessful() && response.body() != null)
			{
				// 获取服务器响应字符串
				return response.body().string().trim();
			}
			else
			{
				return null;
			}
		});
		threadPool.submit(task); // 提交任务
		return task.get();
	}
}
