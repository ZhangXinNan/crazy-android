package org.crazyit.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
		// 为bn按钮添加一个监听器
		bn.setOnClickListener(view -> {
			// 创建Intent
			Intent intent = new Intent();
			// 为Intent设置Action属性
			intent.setAction(Intent.ACTION_VIEW);
			// 设置Data属性
			intent.setData(Uri.parse("http://www.crazyit.org"));
			startActivity(intent);
		});
		Button edit = findViewById(R.id.edit);
		// 为edit按钮添加一个监听器
		edit.setOnClickListener(view -> {
			// 创建Intent
			Intent intent = new Intent();
			// 为Intent设置Action属性（动作为：编辑）
			intent.setAction(Intent.ACTION_EDIT);
			// 设置Data属性
			intent.setData(Uri.parse("content://com.android.contacts/contacts/1"));
			startActivity(intent);
		});
		Button call = findViewById(R.id.call);
		// 为call按钮添加一个监听器
		call.setOnClickListener(view -> {
			// 创建Intent
			Intent intent = new Intent();
			// 为Intent设置Action属性（动作为：拨号）
			intent.setAction(Intent.ACTION_DIAL);
			// 设置Data属性
			intent.setData(Uri.parse("tel:13800138000"));
			startActivity(intent);
		});
	}
}
