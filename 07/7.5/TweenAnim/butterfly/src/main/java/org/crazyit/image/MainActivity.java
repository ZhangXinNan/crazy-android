package org.crazyit.image;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.TranslateAnimation;
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
	// 记录蝴蝶ImageView当前的位置
	private float curX;
	private float curY = 30f;
	// 记录蝴蝶ImageView下一个位置的坐标
	private float nextX;
	private float nextY;
	private ImageView imageView;
	private float screenWidth;
	static class MyHandler extends Handler
	{
		private WeakReference<MainActivity> activity;
		MyHandler(WeakReference<MainActivity> activity)
		{
			this.activity = activity;
		}
		@Override
		public void handleMessage(Message msg)
		{
			if (activity.get() != null && msg.what == 0x123)
			{
				MainActivity act = activity.get();
				// 横向上一直向右飞
				if (act.nextX > act.screenWidth)
				{
					act.nextX = 0f;
					act.curX = act.nextX;
				}
				else
				{
					act.nextX += 8f;
				}
				// 纵向上可以随机上下
				act.nextY = act.curY + (float)(Math.random() * 10 - 5);
				// 设置显示蝴蝶的ImageView发生位移改变
				TranslateAnimation anim = new TranslateAnimation(act.curX, act.nextX, act.curY, act.nextY);
				act.curX = act.nextX;
				act.curY = act.nextY;
				anim.setDuration(200);
				// 开始位移动画
				act.imageView.startAnimation(anim);  // ①
			}
		}
	}
	private Handler handler = new MyHandler(new WeakReference<>(this));

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Point p = new Point();
		// 获取屏幕宽度
		getWindowManager().getDefaultDisplay().getSize(p);
		screenWidth = p.x;
		// 获取显示蝴蝶的ImageView组件
		imageView = findViewById(R.id.butterfly);
		AnimationDrawable butterfly = (AnimationDrawable) imageView.getBackground();
		imageView.setOnClickListener(view -> {
			// 开始播放蝴蝶振翅的逐帧动画
			butterfly.start();  // ②
			// 通过定时器控制每0.2秒运行一次TranslateAnimation动画
			new Timer().schedule(new TimerTask()
			{
				@Override public void run()
				{
					handler.sendEmptyMessage(0x123);
				}
			}, 0, 200);
		});
	}
}
