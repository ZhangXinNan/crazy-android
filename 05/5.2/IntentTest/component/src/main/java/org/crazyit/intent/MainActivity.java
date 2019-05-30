package org.crazyit.intent;

import android.app.Activity;
import android.content.ComponentName;
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
		Button bn = findViewById(R.id.bn);
		// 为bn按钮绑定事件监听器
		bn.setOnClickListener(view -> {
//			// 创建一个ComponentName对象
//			ComponentName comp = new ComponentName(MainActivity.this, SecondActivity.class);
//			Intent intent = new Intent();
//			// 为Intent设置Component属性
//			intent.setComponent(comp);
			// 根据指定组件类来创建Intent
			Intent intent = new Intent(MainActivity.this
					, SecondActivity.class);

			startActivity(intent);
		});
	}
}
