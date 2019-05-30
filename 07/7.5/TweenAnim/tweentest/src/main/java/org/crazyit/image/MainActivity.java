package org.crazyit.image;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
	private ImageView flower;
	private Animation reverse;
	class MyHandler extends  Handler
	{
		private WeakReference<MainActivity> activity;
		public MyHandler(WeakReference<MainActivity> activity)
		{
			this.activity = activity;
		}
		@Override
		public  void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				activity.get().flower.startAnimation(activity.get().reverse);
			}
		}
	}
	Handler handler = new MyHandler(new WeakReference<>(this));
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flower = findViewById(R.id.flower);
		// 加载第一份动画资源
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
		// 设置动画结束后保留结束状态
		anim.setFillAfter(true);
		// 加载第二份动画资源
		reverse = AnimationUtils.loadAnimation(this, R.anim.reverse);
		// 设置动画结束后保留结束状态
		reverse.setFillAfter(true);
		Button bn = findViewById(R.id.bn);
		bn.setOnClickListener(view -> {
			flower.startAnimation(anim);
			// 设置3.5秒后启动第二个动画
			new Timer().schedule(new TimerTask()
			{
				@Override public void run()
				{
					handler.sendEmptyMessage(0x123);
				}
			}, 3500);
		});
	}
}
