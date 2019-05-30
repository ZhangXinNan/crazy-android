package org.crazyit.image;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

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
	private String[] images;
	private int currentImg;
	private ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		image = findViewById(R.id.image);
		try {
			// 获取/assets/目录下的所有文件
			images = getAssets().list("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获取next按钮
		Button next = findViewById(R.id.next);
		// 为next按钮绑定事件监听器，该监听器将会查看下一张图片
		next.setOnClickListener(view -> {
			// 如果发生数组越界
			if (currentImg >= images.length)
			{
				currentImg = 0;
			}
			// 找到下一个图片文件
			while (!images[currentImg].endsWith(".png") &&
					!images[currentImg].endsWith(".jpg") &&
					!images[currentImg].endsWith(".gif"))
			{
				currentImg++;
				// 如果已发生数组越界
				if (currentImg >= images.length)
				{
					currentImg = 0;
				}
			}
			InputStream assetFile = null;
			try {
				// 打开指定资源对应的输入流
				assetFile = getAssets().open(images[currentImg++]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
			// 如果图片还未回收，先强制回收该图片
			if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()) // ①
			{
				bitmapDrawable.getBitmap().recycle();
			}
			// 改变ImageView显示的图片
			image.setImageBitmap(BitmapFactory.decodeStream(assetFile)); // ②
		});
	}
}
