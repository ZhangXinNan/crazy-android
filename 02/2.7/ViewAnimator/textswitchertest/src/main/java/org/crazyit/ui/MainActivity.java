package org.crazyit.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextSwitcher;
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
	private TextSwitcher textSwitcher;
	private String[] strs = new String[]{"疯狂Java讲义", "轻量级Java EE企业应用实战", "疯狂Android讲义", "疯狂前端开发讲义"};
	private int curStr;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textSwitcher = findViewById(R.id.textSwitcher);
		textSwitcher.setFactory(() -> {
			TextView tv = new TextView(MainActivity.this);
			tv.setTextSize(40f);
			tv.setTextColor(Color.MAGENTA);
			return tv;
		});
		// 调用next方法显示下一个字符串
		next(null);
	}
	// 事件处理函数，控制显示下一个字符串
	public void next(View source)
	{
		textSwitcher.setText(strs[curStr++ % strs.length]);  // ①
	}
}