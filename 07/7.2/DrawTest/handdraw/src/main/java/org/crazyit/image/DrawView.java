package org.crazyit.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View
{
	// 定义记录前一个拖动事件发生点的坐标
	private float preX;
	private float preY;
	private Path path = new Path();
	Paint paint = new Paint(Paint.DITHER_FLAG);
	private Bitmap cacheBitmap;
	// 定义cacheBitmap上的Canvas对象
	private Canvas cacheCanvas = new Canvas();
	private Paint bmpPaint = new Paint();

	DrawView(Context context, int width, int height){
		super(context);
		// 创建一个与该View具有相同大小的图片缓冲区
		cacheBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 设置cacheCanvas将会绘制到内存中的cacheBitmap上
		cacheCanvas.setBitmap(cacheBitmap);
		// 设置画笔的颜色
		paint.setColor(Color.RED);
		// 设置画笔风格
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		// 反锯齿
		paint.setAntiAlias(true);
		paint.setDither(true);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// 获取拖动事件的发生位置
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				// 从前一个点绘制到当前点之后，把当前点定义成下次绘制的前一个点
				path.moveTo(x, y);
				preX = x;
				preY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				// 从前一个点绘制到当前点之后，把当前点定义成下次绘制的前一个点
				path.lineTo(x, y);
				preX = x;
				preY = y;
				break;
			case MotionEvent.ACTION_UP:
				cacheCanvas.drawPath(path, paint); // ①
				path.reset();
				break;
		}
		invalidate();
		// 返回true表明处理方法已经处理该事件
		return true;
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		// 将cacheBitmap绘制到该View组件上
		canvas.drawBitmap(cacheBitmap, 0f, 0f, bmpPaint); // ②
		// 沿着path绘制
		canvas.drawPath(path, paint);
	}
}
