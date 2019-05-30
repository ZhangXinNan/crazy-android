package org.crazyit.io;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

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
	// 定义手势检测器变量
	private GestureDetector detector;
	private ImageView imageView;
	// 初始的图片资源
	private Bitmap bitmap;
	// 定义图片的宽、高
	private int width;
	private int height;
	// 记录当前的缩放比
	private float currentScale = 1.0f;
	// 控制图片缩放的Matrix对象
	private Matrix matrix;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建手势检测器
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent event1, MotionEvent event2,
					float velocityX, float velocityY)  // ②
			{
				float vx = velocityX > 4000 ? 4000f : velocityX;
				vx = velocityX < -4000 ? -4000f : velocityX;
				// 根据手势的速度来计算缩放比，如果vx>0，则放大图片；否则缩小图片
				currentScale += currentScale * vx / 4000.0f;
				// 保证currentScale不会等于0
				currentScale = currentScale > 0.01 ? currentScale : 0.01f;
				// 重置Matrix
				matrix.reset();
				// 缩放Matrix
				matrix.setScale(currentScale, currentScale, 160f, 200f);
				BitmapDrawable tmp = (BitmapDrawable) imageView.getDrawable();
				// 如果图片还未回收，先强制回收该图片
				if (!tmp.getBitmap().isRecycled()) // ①
				{
					tmp.getBitmap().recycle();
				}
				// 根据原始位图和Matrix创建新图片
				Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
				// 显示新的位图
				imageView.setImageBitmap(bitmap2);
				return true;
			}
		});
		imageView = findViewById(R.id.show);
		matrix = new Matrix();
		// 获取被缩放的源图片
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
		// 获得位图宽
		width = bitmap.getWidth();
		// 获得位图高
		height = bitmap.getHeight();
		// 设置ImageView初始化时显示的图片
		imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flower));
	}
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		// 将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(me);
	}
}
