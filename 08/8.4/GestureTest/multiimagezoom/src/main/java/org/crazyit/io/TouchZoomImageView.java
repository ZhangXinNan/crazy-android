package org.crazyit.io;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
public class TouchZoomImageView extends View
{
	// 定义手势检测器变量
	private GestureDetector detector;
	// 保存该组件所绘制的位图的变量
	private Bitmap mBitmap;
	// 定义对位图进行变换的Matrix
	private Matrix matrix = new Matrix();
	private float prevDist;
	private float totalScaleRadio = 1.0f;
	private float totalTranslateX = 0.0f;
	private float totalTranslateY = 0.0f;
	// 为TouchZoomImageView定义不同场景下的构造器
	public TouchZoomImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initDetector();
	}
	public TouchZoomImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initDetector();
	}
	public TouchZoomImageView(Context context)
	{
		super(context);
		initDetector();
	}
	public void initDetector()
	{
		detector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener()
		{
			@Override public boolean onFling(MotionEvent event1, MotionEvent event2,
				float velocityX, float velocityY)
			{
				// 根据手势的滑动距离来计算图片的位移距离
				float translateX = event2.getX() - event1.getX();
				float translateY = event2.getY() - event1.getY();
				totalTranslateX += translateX;
				totalTranslateY += translateY;
				postInvalidate();
				return false;
			}
		});
	}
	// 根据传入的位图资源ID来设置位图
	public void setImage(int resourceId) {
		// 根据位图资源ID来解析图片
		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				resourceId);
		setImage(bm);
	}
	public void setImage(Bitmap bm) {
		this.mBitmap = bm;
		// 当传递过位图来之后，对位图进行初始化操作
		postInvalidate();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// 获取当前触摸点的个数，如果触碰点大于2，说明是缩放行为
		if (event.getPointerCount() >= 2) {
			// 使用getActionMasked来处理多点触碰事件
			switch (event.getActionMasked()) {  // ①
				// 处理手指按下的事件
				case MotionEvent.ACTION_POINTER_DOWN:
					// 计算两个手指的距离
					prevDist = calSpace(event);
					break;
				// 处理手指移动的事件
				case MotionEvent.ACTION_MOVE:

					float curDist = calSpace(event);
					// 计算出当前的缩放值
					float scaleRatio = curDist / prevDist;
					totalScaleRadio *= scaleRatio;
					// 调用onDraw方法，重新绘制界面
					postInvalidate();
					// 准备处理下一次缩放行为
					prevDist = curDist;
					break;
			}
		}
		// 对于单点触碰，触碰事件交给GestureDetector处理
		else
		{
			detector.onTouchEvent(event);  // ②
		}
		return true;
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		matrix.reset();
		// 处理缩放
		matrix.postScale(totalScaleRadio, totalScaleRadio);
		// 处理位移
		matrix.postTranslate(totalTranslateX, totalTranslateY);
		canvas.drawBitmap(mBitmap, matrix, null);
	}
	// 计算两个手指之间的距离
	private float calSpace(MotionEvent event)
	{
		// 获取两个点之间X坐标的差值
		float x = event.getX(0) - event.getX(1);
		// 获取两个点之间Y坐标的差值
		float y = event.getY(0) - event.getY(1);
		// 计算两点距离
		return (float)Math.sqrt(x * x + y * y);
	}
}
