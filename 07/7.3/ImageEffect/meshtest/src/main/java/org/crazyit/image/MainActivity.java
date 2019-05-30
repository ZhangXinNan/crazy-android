package org.crazyit.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
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
public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this, R.drawable.jinta));
	}
	private class MyView extends View
	{
		// 定义两个常量，这两个常量指定该图片横向、纵向上都被划分为20格
		private final static int WIDTH = 20;
		private final static int HEIGHT = 20;
		// 记录该图片上包含441个顶点
		private final static int COUNT = (WIDTH + 1) * (HEIGHT + 1);
		// 定义一个数组，保存Bitmap上的21 * 21个点的坐标
		private float[] verts = new float[COUNT * 2];
		// 定义一个数组，记录Bitmap上的21 * 21个点经过扭曲后的坐标
		// 对图片进行扭曲的关键就是修改该数组里元素的值
		private float[] orig = new float[COUNT * 2];
		private Bitmap bitmap;

		MyView(Context context, int drawableId)
		{
			super(context);
			setFocusable(true);
			// 根据指定资源加载图片
			bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
			// 获取图片宽度、高度
			float bitmapWidth = bitmap.getWidth();
			float bitmapHeight = bitmap.getHeight();
			int index = 0;
			for (int y = 0; y <= HEIGHT; y++) {
				float fy = bitmapHeight * y / HEIGHT;
				for (int x = 0; x <= WIDTH; x++) {
					float fx = bitmapWidth * x / WIDTH;
					// 初始化orig、verts数组。初始化后，orig、verts
					// 两个数组均匀地保存了21 * 21个点的x,y坐标
					verts[index * 2] = fx;
					orig[index * 2] = verts[index * 2];
					verts[index * 2 + 1] = fy;
					orig[index * 2 + 1] = verts[index * 2 + 1];
					index += 1;
				}
			}
			// 设置背景色
			setBackgroundColor(Color.WHITE);
		}

		@Override
		public void onDraw(Canvas canvas)
		{
			// 对bitmap按verts数组进行扭曲
			// 从第一个点（由第5个参数0控制）开始扭曲
			canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
		}

		// 工具方法，用于根据触摸事件的位置计算verts数组里各元素的值
		private void warp(float cx, float cy)
		{
			for (int i = 0; i < COUNT * 2; i += 2) {
				float dx = cx - orig[i];
				float dy = cy - orig[i + 1];
				float dd = dx * dx + dy * dy;
				// 计算每个坐标点与当前点（cx, cy）之间的距离
				float d = (float) Math.sqrt(dd);
				// 计算扭曲度，距离当前点（cx, cy）越远，扭曲度越小
				float pull = 200000 / (dd * d);
				// 对verts数组（保存bitmap上21 * 21个点经过扭曲后的坐标）重新赋值
				if (pull >= 1) {
					verts[i] = cx;
					verts[i + 1] = cy;
				} else {
					// 控制各顶点向触摸事件发生点偏移
					verts[i] = orig[i] + dx * pull;
					verts[i + 1] = orig[i + 1] + dy * pull;
				}
			}
			// 通知View组件重绘
			invalidate();
		}

		@Override
		public boolean onTouchEvent(MotionEvent event)
		{
			// 调用warp方法根据触摸屏事件的坐标点来扭曲verts数组
			warp(event.getX(), event.getY());
			return true;
		}
	}
}
