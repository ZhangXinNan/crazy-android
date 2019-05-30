package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
	private EditText address;
	private EditText content;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取页面中收件人地址、短信内容
		address = findViewById(R.id.address);
		content = findViewById(R.id.content);
		Button bn = findViewById(R.id.send);
		// 使用外部类的实例作为事件监听器
		bn.setOnLongClickListener(new SendSmsListener(this,
				address.getText().toString(), content.getText().toString()));
	}
}
