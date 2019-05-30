package org.crazyit.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.reflect.Field;

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
	public static final int BLAST_WIDTH = 240;
	public static final int BLAST_HEIGHT = 240;
	private MyView myView;
	private AnimationDrawable anim;
	private MediaPlayer bomb;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 使用FrameLayout布局管理器，它允许组件自己控制位置
		FrameLayout frame = new FrameLayout(this);
		setContentView(frame);
		// 设置使用背景
		frame.setBackgroundResource(R.drawable.back);
		// 加载音效
		bomb = MediaPlayer.create(this, R.raw.bomb);
		myView = new MyView(this);
		// 设置myView用于显示blast动画
		myView.setBackgroundResource(R.drawable.blast);
		// 设置myView默认为隐藏
		myView.setVisibility(View.INVISIBLE);
		// 获取动画对象
		AnimationDrawable anim = (AnimationDrawable) myView.getBackground();
		frame.addView(myView);
		frame.setOnTouchListener((source , event) -> {
			// 只处理按下事件（避免每次产生两个动画效果）
			if (event.getAction() == MotionEvent.ACTION_DOWN)
			{
				// 先停止动画播放
				anim.stop();
				float x = event.getX();
				float y = event.getY();
				// 控制myView的显示位置
				myView.setLocation((int)y - BLAST_HEIGHT, (int)x - BLAST_WIDTH / 2);
				myView.setVisibility(View.VISIBLE);
				// 启动动画
				anim.start();
				// 播放音效
				bomb.start();
			}
			return false;
		});
	}
	// 定义一个自定义View，该自定义View用于播放“爆炸”效果
	class MyView extends ImageView
	{
		MyView(Context context){
			super(context);
		}
		// 定义一个方法，该方法用于控制MyView的显示位置
		public void setLocation(int top, int left)
		{
			this.setFrame(left, top, left + BLAST_WIDTH, top + BLAST_HEIGHT);
		}
		// 重写该方法，控制如果动画播放到最后一帧时，隐藏该View
		@Override
		public void onDraw(Canvas canvas) // ①
		{
			try {
				Field field = AnimationDrawable.class.getDeclaredField("mCurFrame");
				field.setAccessible(true);
				// 获取anim动画的当前帧
				int curFrame = field.getInt(anim);
				// 如果已经到了最后一帧
				if (curFrame == anim.getNumberOfFrames() - 1) {
					// 让该View隐藏
					setVisibility(View.INVISIBLE);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			super.onDraw(canvas);
		}
	}
}
