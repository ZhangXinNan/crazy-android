package org.crazyit.service;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

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
	private Vibrator vibrator;
	private GestureDetector detector;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取系统的Vibrator服务
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
		{
			@Override
			public void onLongPress(MotionEvent e)
			{
				Toast.makeText(MainActivity.this, "手机振动",
					Toast.LENGTH_SHORT).show();
				// 控制手机振动2秒，振动幅度为180
				vibrator.vibrate(VibrationEffect.createOneShot(2000, 180));
			}
		});
	}
	// 重写onTouchEvent方法，当用户触碰触摸屏时触发该方法
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return detector.onTouchEvent(event);
	}
}
