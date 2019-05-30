package org.crazyit.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View
{
	Paint paint = new Paint();
	public MyView(Context context, AttributeSet set)
	{
		super(context, set);
		paint.setColor(Color.RED);
	}
	@Override public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 使用指定Paint对象画矩形
		canvas.drawRect(0f, 0f, getWidth(), getHeight(), paint);
	}
}
