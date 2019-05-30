package org.crazyit.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

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
public class MyView extends View
{
	// 初始的图片资源
	private Bitmap bitmap;
	// Matrix 实例
	private Matrix bmMatrix = new Matrix();
	// 设置倾斜度
	float sx;
	// 位图宽和高
	private int bmWidth;
	private int bmHeight;
	// 缩放比例
	float scale = 1.0f;
	// 判断缩放还是旋转
	boolean isScale;

	public MyView(Context context, AttributeSet set)
	{
		super(context, set);
		// 获得位图
		bitmap = ((BitmapDrawable)context.getResources().getDrawable(
				R.drawable.a, context.getTheme())).getBitmap();
		// 获得位图宽
		bmWidth = bitmap.getWidth();
		// 获得位图高
		bmHeight = bitmap.getHeight();
		// 使当前视图获得焦点
		this.setFocusable(true);
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 重置Matrix
		bmMatrix.reset();
		if (!isScale)
		{
			// 倾斜Matrix
			bmMatrix.setSkew(sx, 0f);
		}
		else
		{
			// 缩放Matrix
			bmMatrix.setScale(scale, scale);
		}
		// 根据原始位图和Matrix创建新图片
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap,
				0, 0, bmWidth, bmHeight, bmMatrix, true);
		// 绘制新位图
		canvas.drawBitmap(bitmap2, bmMatrix, null);
	}
}
