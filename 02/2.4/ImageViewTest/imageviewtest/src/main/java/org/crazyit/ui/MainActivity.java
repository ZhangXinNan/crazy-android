package org.crazyit.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
	// 定义一个访问图片的数组
	private int[] images = new int[]{R.drawable.lijiang, R.drawable.qiao,
			R.drawable.shuangta, R.drawable.shui, R.drawable.xiangbi};
	// 定义默认显示的图片
	private int currentImg = 2;
	// 定义图片的初始透明度
	private int alpha = 255;

	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button plus = findViewById(R.id.plus);
		Button minus = findViewById(R.id.minus);
		ImageView image1 = findViewById(R.id.image1);
		ImageView image2 = findViewById(R.id.image2);
		Button next = findViewById(R.id.next);
		// 定义查看下一张图片的监听器
		next.setOnClickListener(source -> {
			// 控制ImageView显示下一张图片
			image1.setImageResource(images[++currentImg % images.length]);
		});
		// 定义改变图片透明度的方法
		View.OnClickListener listener =  v -> {
			if (v == plus)
			{
				alpha += 20;
			}
			if (v == minus)
			{
				alpha -= 20;
			}
			if (alpha >= 255)
			{
				alpha = 255;
			}
			if (alpha <= 0)
			{
				alpha = 0;
			}
			// 改变图片的透明度
			image1.setImageAlpha(alpha);
		};
		// 为两个按钮添加监听器
		plus.setOnClickListener(listener);
		minus.setOnClickListener(listener);
		image1.setOnTouchListener((view, event) -> {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) image1.getDrawable();
			// 获取第一个图片显示框中的位图
			Bitmap bitmap = bitmapDrawable.getBitmap();
			// bitmap图片实际大小与第一个ImageView的缩放比例
			double scale = 1.0 * bitmap.getHeight() / image1.getHeight();
			// 获取需要显示的图片的开始点
			long x = Math.round(event.getX() * scale);
			long y = Math.round(event.getY() * scale);
			if (x + 120 > bitmap.getWidth())
			{
				x = bitmap.getWidth() - 120;
			}
			if (y + 120 > bitmap.getHeight())
			{
				y = bitmap.getHeight() - 120;
			}
			// 显示图片的指定区域
			image2.setImageBitmap(Bitmap.createBitmap(bitmap, (int)x, (int)y, 120, 120));
			image2.setImageAlpha(alpha);
			return false;
		});
	}
}
