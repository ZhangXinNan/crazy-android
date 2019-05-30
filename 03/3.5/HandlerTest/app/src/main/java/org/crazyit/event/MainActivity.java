package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

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
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MainActivity extends Activity
{
	private ImageView show;
	static class MyHandler extends Handler
	{
		private WeakReference<MainActivity> activity;
		public MyHandler(WeakReference<MainActivity> activity){
			this.activity = activity;
		}
		// 定义周期性显示的图片ID
		private int[] imageIds = new int[]{R.drawable.java,
				R.drawable.javaee, R.drawable.ajax,
				R.drawable.android, R.drawable.swift};
		private int currentImageId = 0;
		@Override
		public void handleMessage(Message msg)
		{
			// 如果该消息是本程序所发送的
			if (msg.what == 0x1233)
			{
				// 动态地修改所显示的图片
				activity.get().show.setImageResource(
					imageIds[currentImageId++ % imageIds.length]);
			}
		}
	}
	MyHandler myHandler = new MyHandler(new WeakReference(this));
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		show = findViewById(R.id.show);
		// 定义一个计时器，让该计时器周期性地执行指定任务
		new Timer().schedule(new TimerTask()
		{
			@Override public void run()
			{
				// 发送空消息
				myHandler.sendEmptyMessage(0x1233);
			}
		}, 0, 1200);
	}
}
