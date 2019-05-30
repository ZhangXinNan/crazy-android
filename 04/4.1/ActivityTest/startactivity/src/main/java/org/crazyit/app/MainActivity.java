package org.crazyit.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
		// 获取应用程序中的bn按钮
		Button bn = findViewById(R.id.bn);
		// 为bn按钮绑定事件监听器
		bn.setOnClickListener( view -> {
			// 创建需要启动的Activity对应的Intent
			Intent intent = new Intent(MainActivity.this, SecondActivity.class);
			// 启动intent对应的Activity
			startActivity(intent);
		});
	}
}
