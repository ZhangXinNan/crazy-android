package org.crazyit.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

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
	private TextView show;
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
				// 设置show组件显示服务器响应
				mainActivity.get().show.setText(msg.obj.toString());
			}
		}
	}
	private Handler handler = new MyHandler(new WeakReference<>(this));
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button getBn = findViewById(R.id.get);
		Button postBn = findViewById(R.id.post);
		show = findViewById(R.id.show);
		getBn.setOnClickListener(view -> new Thread(() ->
			{
				String str = GetPostUtil.sendGet(
						"http://192.168.1.88:8888/abc/a.jsp", null);
				// 发送消息通知UI线程更新UI组件
				Message msg = new Message();
				msg.what = 0x123;
				msg.obj = str;
				handler.sendMessage(msg);
			}).start());
		postBn.setOnClickListener(view -> new Thread(() ->
			{
				String str = GetPostUtil.sendPost(
						"http://192.168.1.88:8888/abc/login.jsp", "name=crazyit.org&pass=leegang"
				);
				// 发送消息通知UI线程更新UI组件
				Message msg = new Message();
				msg.what = 0x123;
				msg.obj = str;
				handler.sendMessage(msg);
			}).start());
	}
}
