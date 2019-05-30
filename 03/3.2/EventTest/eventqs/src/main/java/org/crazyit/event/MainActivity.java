package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
		// 为按钮绑定事件监听器
		bn.setOnClickListener(new MyClickListener()); // ①
	}
	// 定义一个单击事件的监听器
	class MyClickListener implements View.OnClickListener
	{
		// 实现监听器类必须实现的方法，该方法将会作为事件处理器
		@Override
		public void onClick(View v)
		{
			TextView txt = findViewById(R.id.txt);
			txt.setText("bn按钮被单击了！");
		}
	}
}
