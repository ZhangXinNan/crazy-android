package org.crazyit.intent;

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
	// 定义一个Action常量
	public static final String CRAZYIT_ACTION =
			"org.crazyit.intent.action.CRAZYIT_ACTION";
	// 定义一个Category常量
	public static final String CRAZYIT_CATEGORY =
		"org.crazyit.intent.category.CRAZYIT_CATEGORY";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bn = findViewById(R.id.bn);
		bn.setOnClickListener(view -> {
			Intent intent = new Intent();
			// 设置Action属性
			intent.setAction(MainActivity.CRAZYIT_ACTION);
			// 添加Category属性
			intent.addCategory(MainActivity.CRAZYIT_CATEGORY);
			startActivity(intent);
		});
	}
}
