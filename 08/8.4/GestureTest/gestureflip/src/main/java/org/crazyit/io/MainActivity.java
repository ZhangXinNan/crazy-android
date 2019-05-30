package org.crazyit.io;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

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
	//  ViewFlipper实例
	private ViewFlipper flipper;
	// 定义手势检测器变量
	private GestureDetector detector;
	// 定义一个动画数组，用于为ViewFlipper指定切换动画效果
	private Animation[] animations = new Animation[4];
	// 定义手势动作两点之间的最小距离
	private float flipDistance = 0f;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flipDistance = getResources().getDimension(R.dimen.flip_distance);
		// 创建手势检测器
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
		{
			@Override public boolean onFling(MotionEvent event1, MotionEvent event2,
				float velocityX, float velocityY)
			{
				// 如果第一个触点事件的X坐标大于第二个触点事件的X坐标超过flipDistance
				// 也就是手势从右向左滑
				if (event1.getX() - event2.getX() > flipDistance)
				{
					// 为flipper设置切换的动画效果
					flipper.setInAnimation(animations[0]);
					flipper.setOutAnimation(animations[1]);
					flipper.showPrevious();
					return true;
				}
				// 如果第二个触点事件的X坐标大于第一个触点事件的X坐标超过flipDistance
				// 也就是手势从左向右滑
				else if (event2.getX() - event1.getX() > flipDistance)
				{
					// 为flipper设置切换的动画效果
					flipper.setInAnimation(animations[2]);
					flipper.setOutAnimation(animations[3]);
					flipper.showNext();
					return true;
				}
				return false;
			}
		});
		// 获得ViewFlipper实例
		flipper = this.findViewById(R.id.flipper);
		// 为ViewFlipper添加6个ImageView组件
		flipper.addView(addImageView(R.drawable.java));
		flipper.addView(addImageView(R.drawable.javaee));
		flipper.addView(addImageView(R.drawable.ajax));
		flipper.addView(addImageView(R.drawable.android));
		flipper.addView(addImageView(R.drawable.html));
		flipper.addView(addImageView(R.drawable.swift));
		// 初始化Animation数组
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
		animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
		animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}
	// 定义添加ImageView的工具方法
	private View addImageView(int resId)
	{
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(resId);
		imageView.setScaleType(ImageView.ScaleType.CENTER);
		return imageView;
	}
	@Override public boolean onTouchEvent(MotionEvent event)
	{
		// 将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(event);
	}
}
