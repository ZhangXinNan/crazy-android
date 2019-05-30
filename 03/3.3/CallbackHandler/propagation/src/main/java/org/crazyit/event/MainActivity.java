package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bn = findViewById(R.id.bn);
		bn.setOnTouchListener((view, event) ->
		{
			// 只处理按下键的事件
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Log.v("--Listener--", "the TouchDown in Listener");
			}
			// 返回false，表明该事件会向外传播
			return true; // ①
		});
	}
	// 重写onTouchEvent方法，该方法可监听它所包含的所有组件上的触碰事件
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		super.onTouchEvent(event);
		Log.v("--Activity--", "the onTouchEvent in Activity");
		// 返回false，表明该事件会向外传播
		return false;
	}
}
