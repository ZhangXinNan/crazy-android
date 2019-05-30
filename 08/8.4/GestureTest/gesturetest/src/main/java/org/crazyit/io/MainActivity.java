package org.crazyit.io;

import android.app.Activity;
import android.os.Bundle;
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
public class MainActivity extends Activity implements GestureDetector.OnGestureListener
{
	// 定义手势检测器变量
	private GestureDetector detector;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建手势检测器
		detector = new GestureDetector(this, this);
	}
	// 将该Activity上的触碰事件交给GestureDetector处理
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		return detector.onTouchEvent(me);
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		Toast.makeText(this, "onDown",
				Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
		Toast.makeText(this, "onShowPress",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		Toast.makeText(this, "onSingleTapUp",
				Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		Toast.makeText(this, "onScroll",
				Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{
		Toast.makeText(this, "onLongPress",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		Toast.makeText(this, "onFling",
				Toast.LENGTH_SHORT).show();
		return false;
	}
}
