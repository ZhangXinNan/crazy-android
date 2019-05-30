package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MainActivity extends Activity
{
	// 定义一个访问图片的数组
	private int[] images = new int []{R.drawable.java, R.drawable.javaee,
			R.drawable.swift, R.drawable.ajax, R.drawable.html};
	private int currentImg = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取LinearLayout布局容器
		LinearLayout main = findViewById(R.id.root);
		// 程序创建ImageView组件
		ImageView image = new ImageView(this);
		// 将ImageView组件添加到LinearLayout布局容器中
		main.addView(image);
		// 初始化时显示第一张图片
		image.setImageResource(images[0]);
		image.setOnClickListener(view -> {
			// 改变ImageView里显示的图片
			image.setImageResource(images[++currentImg % images.length]);
		});
	}
}

