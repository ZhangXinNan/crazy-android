package org.crazyit.image;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

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
	private SurfaceHolder holder;
	private Paint paint = new Paint();
	int HEIGHT = 400;
	int screenWidth;
	int X_OFFSET = 5;
	int cx = X_OFFSET;
	// 实际的Y轴的位置
	int centerY = HEIGHT / 2;
	Timer timer = new Timer();
	TimerTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenWidth = metrics.widthPixels;
		SurfaceView surface = findViewById(R.id.show);
		// 初始化SurfaceHolder对象
		holder = surface.getHolder();
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(getResources().getDimension(R.dimen.stroke_width));
		Button sin = findViewById(R.id.sin);
		Button cos = findViewById(R.id.cos);
		View.OnClickListener listener = source -> {
			drawBack(holder);
			cx = X_OFFSET;
			if (task != null)
			{
				task.cancel();
			}
			task = new TimerTask()
			{
				@Override
				public void run()
				{
					paint.setColor(Color.GREEN);
					int cy = source.getId() == R.id.sin ? centerY
							- (int)(100 * Math.sin((cx - 5) * 2
							* Math.PI / 150))
							: centerY - (int)(100 * Math.cos ((cx - 5)
							* 2 * Math.PI / 150));
					Canvas canvas = holder.lockCanvas(new Rect(cx, cy,
						cx + (int)paint.getStrokeWidth(), cy + (int)paint.getStrokeWidth()));
					canvas.drawPoint(cx, cy, paint);
					cx += 2;
					if (cx > screenWidth)
					{
						task.cancel();
						task = null;
					}
					holder.unlockCanvasAndPost(canvas);
				}
			};
			timer.schedule(task, 0, 10);
		};
		sin.setOnClickListener(listener);
		cos.setOnClickListener(listener);
		holder.addCallback(new SurfaceHolder.Callback()
		{
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
				drawBack(holder);
			}
			@Override
			public void surfaceCreated(SurfaceHolder myHolder)
			{
			}
			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
				timer.cancel();
			}
		});
	}
	private void drawBack(SurfaceHolder holder)
	{
		Canvas canvas = holder.lockCanvas();
		// 绘制白色背景
		canvas.drawColor(Color.WHITE);
		paint.setColor(Color.BLACK);
		// 绘制坐标轴
		canvas.drawLine(X_OFFSET, centerY,
				screenWidth, centerY, paint);
		canvas.drawLine(X_OFFSET, 40f, X_OFFSET, HEIGHT, paint);
		holder.unlockCanvasAndPost(canvas);
		holder.lockCanvas(new Rect(0, 0, 0, 0));
		holder.unlockCanvasAndPost(canvas);
	}
}
