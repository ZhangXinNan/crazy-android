package org.crazyit.image;

import android.app.Activity;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

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
		setContentView(R.layout.activity_main);
		// 获取TextView对象
		TextView textView = findViewById(R.id.tv);
		// 获取ImageView对象
		ImageView imageView = findViewById(R.id.image);
		// ①、创建ImageDecoder.Source对象
		ImageDecoder.Source source = ImageDecoder.createSource(getResources(), R.drawable.fat_po);
		try {
			// ②，执行decodeDrawable()方法获取Drawable对象
			Drawable drawable = ImageDecoder.decodeDrawable(source, (decoder, info, s) -> {
				// 通过info参数获取被解码的图片信息
				textView.setText("图片原始宽度" + info.getSize().getWidth()
					+ "\n" + "图片原始高度" + info.getSize().getHeight());
				// 设置图片解码之后的缩放大小
				decoder.setTargetSize(600, 580);
			});
			imageView.setImageDrawable(drawable);
			// 如果drawable是AnimatedImageDrawable的实例，执行动画
			if (drawable instanceof AnimatedImageDrawable) {
				((AnimatedImageDrawable) drawable).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
