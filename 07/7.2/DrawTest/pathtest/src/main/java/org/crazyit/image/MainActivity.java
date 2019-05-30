package org.crazyit.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
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
		setContentView(new MyView(this));
	}
	private static class MyView extends View
	{
		private float phase;
		private PathEffect[] effects = new PathEffect[7];
		private int[] colors;
		private Paint paint = new Paint();
		// 定义创建并初始化Path
		private Path path = new Path();
		public MyView(Context context)
		{
			super(context);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(4);
			path.moveTo(0f, 0f);
			for (int i = 1; i <= 40; i++)
			{
				// 生成40个点，随机生成它们的Y坐标，并将它们连成一条Path
				path.lineTo(i * 25f, (float) (Math.random() * 90));
			}
			// 初始化7种颜色
			colors = new int[]{Color.BLACK, Color.BLUE, Color.CYAN,
					Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};
			// -----------下面开始初始化7种路径效果----------
			// 不使用路径效果
			effects[0] = null;
			// 使用CornerPathEffect路径效果
			effects[1] = new CornerPathEffect(10f);
			// 初始化DiscretePathEffect
			effects[2] = new DiscretePathEffect(3.0f, 5.0f);
		}

		@Override
		public void onDraw(Canvas canvas)
		{
			// 将背景填充成白色
			canvas.drawColor(Color.WHITE);
			// 将画布移动到(8, 8)处开始绘制
			canvas.translate(8f, 8f);
			// 依次使用7种不同的路径效果、7种不同的颜色来绘制路径
			for (int i = 0; i < effects.length; i++) {
				paint.setPathEffect(effects[i]);
				paint.setColor(colors[i]);
				canvas.drawPath(path, paint);
				canvas.translate(0f, 90f);
			}
			// 初始化DashPathEffect
			effects[3] = new DashPathEffect(new float[]{20f, 10f, 5f, 10f}, phase);
			// 初始化PathDashPathEffect
			Path p = new Path();
			p.addRect(0f, 0f, 8f, 8f, Path.Direction.CCW);
			effects[4] = new PathDashPathEffect(p, 12f, phase,
					PathDashPathEffect.Style.ROTATE);
			// 初始化ComposePathEffect
			effects[5] = new ComposePathEffect(effects[2], effects[4]);
			effects[6] = new SumPathEffect(effects[4], effects[3]);
			// 改变phase值，形成动画效果
			phase += 1f;
			invalidate();
		}
	}
}
