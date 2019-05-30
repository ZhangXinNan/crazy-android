package org.crazyit.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		Button simple = findViewById(R.id.simple);
		// 为按钮的单击事件绑定事件监听器
		simple.setOnClickListener(view -> {
			// 创建一个Toast提示信息
			Toast toast = Toast.makeText(MainActivity.this,
			"简单的提示信息", Toast.LENGTH_SHORT); // 设置该Toast提示信息的持续时间
			toast.show();
		});
		Button bn = findViewById(R.id.bn);
		// 为按钮的单击事件绑定事件监听器
		bn.setOnClickListener(view -> {
			// 创建一个Toast提示信息
			Toast toast = new Toast(MainActivity.this);
			// 设置Toast的显示位置
			toast.setGravity(Gravity.CENTER, 0, 0);
			// 创建一个ImageView
			ImageView image = new ImageView(MainActivity.this);
			image.setImageResource(R.drawable.tools);
			// 创建一个LinearLayout容器
			LinearLayout ll = new LinearLayout(MainActivity.this);
			// 向LinearLayout中添加图片、原有的View
			ll.addView(image);
			// 创建一个TextView
			TextView textView = new TextView(MainActivity.this);
			textView.setText("带图片的提示信息");
			// 设置文本框内字号的大小和字体颜色
			textView.setTextSize(24f);
			textView.setTextColor(Color.MAGENTA);
			ll.addView(textView);
			// 设置Toast显示自定义View
			toast.setView(ll);
			// 设置Toast的显示时间
			toast.setDuration(Toast.LENGTH_LONG);
			toast.show();
		});
	}
}
