package org.crazyit.event;

import android.content.Context;
import android.util.AttributeSet;
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
public class MyButton extends Button
{
	public MyButton(Context context, AttributeSet set)
	{
		super(context, set);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)

	{
		super.onTouchEvent(event);
		Log.v("-crazyit.org-", "the onTouchEvent in MyButton");
		// 返回false，表明该事件会向外传播
		return false;  // ①
	}
}
