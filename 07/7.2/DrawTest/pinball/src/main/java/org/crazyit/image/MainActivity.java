package org.crazyit.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.Random;
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
	// 屏幕的宽度
	private float tableWidth;
	// 屏幕的高度
	private float tableHeight;
	// 球拍的垂直位置
	private float racketY;
	// 下面定义球拍的高度和宽度
	private float racketHeight;
	private float racketWidth;
	// 小球的大小
	private float ballSize;
	// 小球纵向的运行速度
	private int ySpeed = 15;
	private Random rand = new Random();
	// 返回一个-0.5~0.5的比率，用于控制小球的运行方向
	private double xyRate = rand.nextDouble() - 0.5;
	// 小球横向的运行速度
	private int xSpeed = (int) (ySpeed * xyRate * 2.0);
	// ballX和ballY代表小球的坐标
	private float ballX = rand.nextInt(200) + 20f;
	private float ballY = rand.nextInt(10) + 60f;
	// racketX代表球拍的水平位置
	private float racketX = rand.nextInt(200);
	// 游戏是否结束的旗标
	private boolean isLose;
	private GameView gameView;
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
			if (msg.what == 0x123)
			{
				activity.get().gameView.invalidate();
			}
		}
	}
	private Handler handler = new MyHandler(new WeakReference<>(this));
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 去掉窗口标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		racketHeight = getResources().getDimension(R.dimen.racket_height);
		racketWidth = getResources().getDimension(R.dimen.racket_width);
		ballSize = getResources().getDimension(R.dimen.ball_size);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 创建GameView组件
		gameView = new GameView(this);
		setContentView(gameView);
		// 获取窗口管理器
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// 获得屏幕宽和高
		tableWidth = metrics.widthPixels;
		tableHeight = metrics.heightPixels;
		racketY = tableHeight - 80;
		gameView.setOnTouchListener((source, event) -> { // ②
			if (event.getX() <= tableWidth / 10)
			{
				// 控制挡板左移
				if (racketX > 0) racketX -= 10;
			}
			if (event.getX() >= tableWidth * 9 / 10)
			{
				// 控制挡板右移
				if (racketX < tableWidth - racketWidth) racketX += 10;
			}
			return true;
		});
		Timer timer = new Timer();
		timer.schedule(new TimerTask()  // ①
		{
			@Override
			public void run()
			{
				// 如果小球碰到左边边框
				if (ballX < ballSize || ballX >= tableWidth - ballSize)
				{
					xSpeed = -xSpeed;
				}
				// 如果小球高度超出了球拍位置，且横向不在球拍范围之内，游戏结束
				if (ballY >= racketY - ballSize &&
						(ballX < racketX || ballX > racketX + racketWidth))
				{
					timer.cancel();
					// 设置游戏是否结束的旗标为true
					isLose = true;
				}
				// 如果小球位于球拍之内，且到达球拍位置，小球反弹
				else if (ballY < ballSize || (ballY >= racketY - ballSize &&
						ballX > racketX && ballX <= racketX + racketWidth))
				{
					ySpeed = -ySpeed;
				}
				// 小球坐标增加
				ballY += ySpeed;
				ballX += xSpeed;
				// 发送消息，通知系统重绘组件
				handler.sendEmptyMessage(0x123);
			}
		}, 0, 100);
	}
	class GameView extends View
	{
		private Paint paint = new Paint();
		private RadialGradient mShader = new RadialGradient(-ballSize/ 2,
				-ballSize/ 2, ballSize, Color.WHITE, Color.RED, Shader.TileMode.CLAMP);
		public GameView(Context context)
		{
			super(context);
			setFocusable(true);
			paint.setStyle(Paint.Style.FILL);
			// 设置去锯齿
			paint.setAntiAlias(true);
		}

		// 重写View的onDraw方法，实现绘画
		@Override
		public void onDraw(Canvas canvas)
		{
			// 如果游戏已经结束
			if (isLose)
			{
				paint.setColor(Color.RED);
				paint.setTextSize(40f);
				canvas.drawText("游戏已结束", tableWidth / 2 - 100, 200f, paint);
			}
			// 如果游戏还未结束
			else
			{
				canvas.save(); // 保存坐标系统
				// 将坐标系统平移到小球圆心处来绘制小球
				canvas.translate(ballX, ballY);
				// 设置渐变，并绘制小球
				paint.setShader(mShader);
				canvas.drawCircle(0f, 0f, ballSize, paint);
				canvas.restore(); // 恢复原来的坐标系统
				paint.setShader(null);
				// 设置颜色，并绘制球拍
				paint.setColor(Color.rgb(80, 80, 200));
				canvas.drawRect(racketX, racketY, racketX + racketWidth,
						racketY + racketHeight, paint);
			}
		}
	}
}
