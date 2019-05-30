package org.crazyit.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
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
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MainActivity extends Activity
{
	private TextView response;
	private OkHttpClient okHttpClient;

	static class MyHandler extends Handler
	{
		private WeakReference<MainActivity> mainActivity;

		MyHandler(WeakReference<MainActivity> mainActivity)
		{
			this.mainActivity = mainActivity;
		}

		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				// 使用response文本框显示服务器响应信息
				mainActivity.get().response.setText(msg.obj.toString());
			}
		}
	}

	private Handler handler = new MyHandler(new WeakReference<>(this));

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		response = findViewById(R.id.response);
		// 创建默认的OkHttpClient对象
//        okHttpClient = OkHttpClient()
		final Map<String, List<Cookie>> cookieStore = new HashMap<>();
		okHttpClient = new OkHttpClient.Builder()
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
	}

	public void accessSecret(View source)
	{
		new Thread(() ->
		{
			String url = "http://192.168.1.88:8888/foo/secret.jsp";
			// 创建请求
			Request request = new Request.Builder().url(url).build();  // ①
			Call call = okHttpClient.newCall(request);
			try
			{
				Response response = call.execute();  // ②
				Message msg = new Message();
				msg.what = 0x123;
				msg.obj = response.body().string().trim();
				handler.sendMessage(msg);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}).start();
	}

	public void showLogin(View source)
	{
		// 加载登录界面
		View loginDialog = getLayoutInflater().inflate(R.layout.login, null);
		// 使用对话框供用户登录系统
		new AlertDialog.Builder(MainActivity.this)
			.setTitle("登录系统").setView(loginDialog)
			.setPositiveButton("登录", (dialog, which) ->
			{
				// 获取用户输入的用户名、密码
				String name = ((EditText) loginDialog.findViewById(R.id.name))
						.getText().toString();
				String pass = ((EditText) loginDialog.findViewById(R.id.pass))
						.getText().toString();
				String url = "http://192.168.1.88:8888/foo/login.jsp";
				FormBody body = new FormBody.Builder().add("name", name)
						.add("pass", pass).build();  //③
				Request request = new Request.Builder().url(url)
						.post(body).build();  //④
				Call call = okHttpClient.newCall(request);
				call.enqueue(new Callback()  // ⑤
				{
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e)
					{
						e.printStackTrace();
					}

					@Override
					public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
					{
						Looper.prepare();
						Toast.makeText(MainActivity.this,
								response.body().string().trim(), Toast.LENGTH_SHORT).show();
						Looper.loop();
					}
				});
			}).setNegativeButton("取消", null).show();
	}
}
