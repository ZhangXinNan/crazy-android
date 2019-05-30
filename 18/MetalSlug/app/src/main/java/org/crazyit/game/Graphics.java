package org.crazyit.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Description: 定义画图的工具类<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class Graphics
{
	public static final int HCENTER = 1;
	public static final int LEFT = 4;
	public static final int RIGHT = 8;

	// 定义绘制直线的方法
	public static void drawLine(Canvas canvas, float startX,
		float startY, float endX, float endY, Paint paint)
	{
		paint.setStyle(Style.STROKE);
		canvas.drawLine(startX, startY, endX, endY, paint);
	}
	// 定义绘制矩形的方法
	public static void drawRect(Canvas canvas, float x,
		float y, float w, float h, Paint paint)
	{
		paint.setStyle(Style.STROKE);
		RectF rect = new RectF(x, y, x + w, y + h);
		canvas.drawRect(rect, paint);
	}
	// 定义绘制弧的方法
	public static void drawArc(Canvas canvas, float x,
		float y, float width, float height,
		float startAngle, float sweepAngle, Paint paint)
	{
		paint.setStyle(Style.STROKE);
		RectF rect = new RectF(x, y, x + width, y + height);
		canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
	}

	// 定义绘制字符串的方法
	public static void drawString(Canvas canvas, String text,
		float textSize, float x, float y, int anchor, Paint paint)
	{
		// 动态计算字符串的对齐方式
		if ((anchor & LEFT) != 0)
		{
			paint.setTextAlign(Align.LEFT);
		}
		else if ((anchor & RIGHT) != 0)
		{
			paint.setTextAlign(Align.RIGHT);
		}
		else if ((anchor & HCENTER) != 0)
		{
			paint.setTextAlign(Align.CENTER);
		}
		else
		{
			paint.setTextAlign(Align.CENTER);
		}
		paint.setTextSize(textSize);
		// 绘制字符串
		canvas.drawText(text, x, y, paint);
		// 将文本对齐方式恢复到默认情况。
		paint.setTextAlign(Align.CENTER);
	}

	/**
	 * 绘制包边字符串的方法
	 *
	 * @param c           绘制包边字符串的画布
	 * @param borderColor 绘制包边字符串的边框颜色
	 * @param textColor   绘制包边字符串的文本颜色
	 * @param text        指定要绘制的字符串
	 * @param x           绘制字符串的X坐标
	 * @param y           绘制字符串的Y坐标
	 * @param borderWidth 绘制包边字符串的边框宽度
	 * @param mPaint      绘制包边字符串的画笔
	 */
	public static void drawBorderString(Canvas c, int borderColor,
		int textColor, String text, int x, int y, int borderWidth, Paint mPaint)
	{
		mPaint.setAntiAlias(true);
		// 先使用STROKE风格绘制字符串的边框
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(borderWidth);
		// 设置绘制边框的颜色
		mPaint.setColor(Color.rgb((borderColor & 0xFF0000) >> 16,
			(borderColor & 0x00ff00) >> 8, (borderColor & 0x0000ff)));
		c.drawText(text, x, y, mPaint);
		// 先使用FILL风格绘制字符串
		mPaint.setStyle(Paint.Style.FILL);
		// 设置绘制文本的颜色
		mPaint.setColor(Color.rgb((textColor & 0xFF0000) >> 16,
				(textColor & 0x00ff00) >> 8, (textColor & 0x0000ff)));
		c.drawText(text, x, y, mPaint);
	}

	private static final Rect src = new Rect();
	private static final Rect dst = new Rect();

	// 定义Android翻转参数的常量
	public static final int TRANS_MIRROR = 2;
	public static final int TRANS_MIRROR_ROT180 = 1;
	public static final int TRANS_MIRROR_ROT270 = 4;
	public static final int TRANS_MIRROR_ROT90 = 7;
	public static final int TRANS_NONE = 0;
	public static final int TRANS_ROT180 = 3;
	public static final int TRANS_ROT270 = 6;
	public static final int TRANS_ROT90 = 5;

	public static final float INTERVAL_SCALE = 0.05f; // 每次缩放的梯度
	// 每次缩放的梯度是0.05，所以这里乘20以后转成整数
	public static final int TIMES_SCALE = 20;
	private static final float[] pts = new float[8];
	private static final Path path = new Path();
	private static final RectF srcRect = new RectF();

	// 用于从源位图中的srcX、srcY点开始、挖取宽width、高height的区域，并对该图片进行trans变换、
	// 缩放scale（当scale为20时表示不缩放）、并旋转degree角度后绘制到Canvas的drawX、drawY处。
	public synchronized static void drawMatrixImage(Canvas canvas, Bitmap src,
		int srcX, int srcY, int width, int height, int trans, int drawX,
		int drawY, int degree, int scale)
	{
		if (canvas == null)
		{
			return;
		}
		if (src == null || src.isRecycled())
		{
			return;
		}
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		if (srcX + width > srcWidth)
		{
			width = srcWidth - srcX;
		}
		if (srcY + height > srcHeight)
		{
			height = srcHeight - srcY;
		}

		if (width <= 0 || height <= 0)
		{
			return;
		}
		// 设置图片在横向、纵向上缩放因子
		int scaleX = scale;
		int scaleY = scale;
		int rotate = 0;
		// 根据程序所要进行的图像变换来计算scaleX、scaleY以及rotate旋转角
		switch (trans)
		{
			case TRANS_MIRROR_ROT180:
				scaleX = -scale;
				rotate = 180;
				break;
			case TRANS_MIRROR:
				scaleX = -scale;
				break;
			case TRANS_ROT180:
				rotate = 180;
				break;
			case TRANS_MIRROR_ROT270:
				scaleX = -scale;
				rotate = 270;
				break;
			case TRANS_ROT90:
				rotate = 90;
				break;
			case TRANS_ROT270:
				rotate = 270;
				break;
			case TRANS_MIRROR_ROT90:
				scaleX = -scale;
				rotate = 90;
				break;
			default:
				break;
		}
		// 如果rotate、degree为0，表明不涉及旋转，
		// 如果scaleX等于TIMES_SCALE，表明不涉及缩放
		if (rotate == 0 && degree == 0
			&& scaleX == TIMES_SCALE)
		{    // 即scale=1无缩放， rotate=0无旋转
			drawImage(canvas, src, drawX, drawY, srcX, srcY, width, height);
		}
		else
		{
			Matrix matrix = new Matrix();
			matrix.postScale(scaleX * INTERVAL_SCALE, scaleY * INTERVAL_SCALE);
			matrix.postRotate(rotate); // 对Matrix旋转rotate
			matrix.postRotate(degree); // 对Matrix旋转degree
			srcRect.set(srcX, srcY, srcX + width, srcY + height);
			matrix.mapRect(srcRect);
			matrix.postTranslate(drawX - srcRect.left, drawY - srcRect.top);
			pts[0] = srcX;
			pts[1] = srcY;
			pts[2] = srcX + width;
			pts[3] = srcY;
			pts[4] = srcX + width;
			pts[5] = srcY + height;
			pts[6] = srcX;
			pts[7] = srcY + height;
			matrix.mapPoints(pts);
			canvas.save();
			path.reset();
			path.moveTo(pts[0], pts[1]);
			path.lineTo(pts[2], pts[3]);
			path.lineTo(pts[4], pts[5]);
			path.lineTo(pts[6], pts[7]);
			path.close();
			canvas.clipPath(path);
			// 使用matrix变换矩阵绘制位图
			canvas.drawBitmap(src, matrix, null);
			canvas.restore();
		}
	}
	// 工具方法：绘制位图
	// 作用是将源位图image中左上角为srcX、srcY、宽width、高height的区域绘制到canvas上
	public synchronized static void drawImage(Canvas canvas, Bitmap image,
		int destX, int destY, int srcX, int srcY, int width, int height)
	{
		if (canvas == null)
		{
			return;
		}
		if (image == null || image.isRecycled())
		{
			return;
		}
		// 如果源位图的区域比需要绘制的目标区域更小
		if (srcX == 0 && srcY == 0 && image.getWidth() <= width
			&& image.getHeight() <= height)
		{
			canvas.drawBitmap(image, destX, destY, null);
			return;
		}
		src.left = srcX;
		src.right = srcX + width;
		src.top = srcY;
		src.bottom = srcY + height;
		dst.left = destX;
		dst.right = destX + width;
		dst.top = destY;
		dst.bottom = destY + height;
		// 将image的src区域挖取出来，绘制在canvas的dst区域上
		canvas.drawBitmap(image, src, dst, null);
	}


	// 缩放图片，得到指定宽高的新图片
	// 使用浮点数运算，减少除法计算时的误差
	public static Bitmap scale(Bitmap img, float newWidth, float newHeight)
	{
		if (img == null || img.isRecycled())
		{
			return null;
		}

		float height = img.getHeight();
		float width = img.getWidth();
		if (height == 0 || width == 0 || newWidth == 0 || newHeight == 0)
		{
			return null;
		}
		// 创建缩放图片所需的Matrix
		Matrix matrix = new Matrix();
		matrix.postScale(newWidth / width, newHeight / height);
		try
		{
			// 生成对img缩放之后的图片
			return Bitmap.createBitmap(img, 0, 0,
				(int) width, (int) height, matrix, true);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	// 对原有位图执行镜像变换
	public static Bitmap mirror(Bitmap img)
	{
		if (img == null || img.isRecycled())
		{
			return null;
		}
		// 创建对图片执行镜像变量的Matrix
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		try
		{
			// 生成对img执行镜像变换之后的图片
			return Bitmap.createBitmap(img, 0, 0, img.getWidth(),
				img.getHeight(), matrix, true);
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
