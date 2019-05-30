package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

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
public class MainActivity extends Activity
{
	int[] names = new int[] {R.id.view01, R.id.view02,
			   R.id.view03, R.id.view04, R.id.view05, R.id.view06};
	TextView[] views = new TextView[names.length];

	class MyHandler extends Handler
	{
		private WeakReference<MainActivity> activity;
		public MyHandler(WeakReference<MainActivity> activity)
		{
			this.activity = activity;
		}
		private int currentColor = 0;
		// 定义一个颜色数组
		int[] colors = new int[]{R.color.color1, R.color.color2,
			R.color.color3, R.color.color4, R.color.color5, R.color.color6};
		@Override public void handleMessage(Message msg)
		{
			// 表明消息来自于本程序所发送的
			if (msg.what == 0x123) {
				for (int i = 0, len = activity.get().names.length; i < len; i++)
				{
					activity.get().views[i].setBackgroundResource(
						colors[(i + currentColor) % colors.length]);
				}
				currentColor++;
			}
			super.handleMessage(msg);
		}
	}

	private Handler handler = new MyHandler(new WeakReference(this));
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		for (int i = 0 ; i < names.length; i++) {
			views[i] = findViewById(names[i]);
		}
		// 定义一个线程周期性地改变currentColor变量值
		new Timer().schedule(new TimerTask()
		{
			@Override public void run()
			{
				// 发送一条空消息通知系统改变6个TextView组件的背景色
				handler.sendEmptyMessage(0x123);
			}
		},0, 200);
	}
}
