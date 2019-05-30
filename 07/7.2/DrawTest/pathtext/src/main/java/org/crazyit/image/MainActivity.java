package org.crazyit.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
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
public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new PathTextView(this));
	}
	private static class PathTextView extends View
	{
		private String drawStr = getResources().getString(R.string.draw_string);
		private Path[] paths = new Path[3];
		private Paint paint = new Paint();
		public PathTextView(Context context)
		{
			super(context);
			paths[0] = new Path();
			paths[0].moveTo(0f, 0f);
			for (int i = 1; i <= 20; i++)
			{
				// 生成20个点，随机生成它们的Y坐标，并将它们连成一条Path
				paths[0].lineTo(i * 30f, (float) (Math.random() * 30));
			}
			paths[1] = new Path();
			RectF rectF = new RectF(0f, 0f, 600f, 360f);
			paths[1].addOval(rectF, Path.Direction.CCW);
			paths[2] = new Path();
			paths[2].addArc(rectF, 60f, 180f);
			// 初始化画笔
			paint.setAntiAlias(true);
			paint.setColor(Color.CYAN);
			paint.setStrokeWidth(1f);
		}
		@Override
		public void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);
			canvas.translate(40f, 40f);
			// 设置从右边开始绘制（右对齐）
			paint.setTextAlign(Paint.Align.RIGHT);
			paint.setTextSize(30f);
			// 绘制路径
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawPath(paths[0], paint);
			paint.setTextSize(40f);
			// 沿着路径绘制一段文本
			paint.setStyle(Paint.Style.FILL);
			canvas.drawTextOnPath(drawStr, paths[0], -8f, 20f, paint);
			// 对Canvas进行坐标变换：画布下移60
			canvas.translate(0f, 60f);
			// 绘制路径
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawPath(paths[1], paint);
			// 沿着路径绘制一段文本
			paint.setStyle(Paint.Style.FILL);
			canvas.drawTextOnPath(drawStr, paths[1], -20f, 20f, paint);
			// 对Canvas进行坐标变换：画布下移360
			canvas.translate(0f, 360f);
			// 绘制路径
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawPath(paths[2], paint);
			// 沿着路径绘制一段文本
			paint.setStyle(Paint.Style.FILL);
			canvas.drawTextOnPath(drawStr, paths[2], -10f, 20f, paint);
		}
	}
}
