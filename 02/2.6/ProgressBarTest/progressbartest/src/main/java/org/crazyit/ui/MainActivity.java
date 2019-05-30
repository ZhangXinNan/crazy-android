package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

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
	// 该程序模拟填充长度为100的数组
	private int[] data = new int[100];
	private int hasData = 0;
	// 记录ProgressBar的完成进度
	int status = 0;
	private ProgressBar bar;
	private ProgressBar bar2;
	static class MyHandler extends Handler
	{
		private WeakReference<MainActivity> activity;
		MyHandler(WeakReference<MainActivity> activity){
			this.activity = activity;
		}
		@Override public void handleMessage(Message msg)
		{
			// 表明消息是由该程序发送的
			if (msg.what == 0x111)
			{
				activity.get().bar.setProgress(activity.get().status);
				activity.get().bar2.setProgress(activity.get().status);
			}
		}
	}
	// 创建一个负责更新的进度的Handler
	MyHandler mHandler = new MyHandler(new WeakReference<>(this));

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bar = findViewById(R.id.bar);
		bar2 = findViewById(R.id.bar2);
		// 启动线程来执行任务
		new Thread()
		{
			@Override public void run()
			{
				while (status < 100)
				{
					// 获取耗时操作的完成百分比
					status = doWork();
					// 发送消息
					mHandler.sendEmptyMessage(0x111);
				}
			}
		}.start();
	}
	// 模拟一个耗时的操作
	public int doWork()
	{
		// 为数组元素赋值
		data[hasData++] = (int)(Math.random() * 100);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return hasData;
	}
}
