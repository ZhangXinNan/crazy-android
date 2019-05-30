package org.crazyit.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
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
public class SecondActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		// 创建一个TextView来显示该Activity和它所在的Task ID
		TextView tv = new TextView(this);
		tv.setText("Activity为：" + this.toString() + "\n" + "，Task ID为:" + this.getTaskId());
		Button button = new Button(this);
		button.setText("启动MainActivity");
		// 添加TextView和Button
		layout.addView(button);
		layout.addView(tv);
		// 为button添加事件监听器，当单击该按钮时启动MainActivity
		button.setOnClickListener(view -> {
			Intent intent = new Intent(SecondActivity.this, MainActivity.class);
			startActivity(intent);
		});
	}
}
