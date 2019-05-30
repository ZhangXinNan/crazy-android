package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;

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
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取计时器组件
		Chronometer ch = findViewById(R.id.test);
		// 获取“开始”按钮
		Button start = findViewById(R.id.start);
		start.setOnClickListener((view) ->
		{
			// 设置开始计时时间
			ch.setBase(SystemClock.elapsedRealtime());
			// 启动计时器
			ch.start();
			start.setEnabled(false);
		});
		// 为Chronometer绑定事件监听器
		ch.setOnChronometerTickListener((source) -> {
			// 如果从开始计时到现在超过了20s
			if (SystemClock.elapsedRealtime() - ch.getBase() > 20 * 1000) {
				ch.stop();
				start.setEnabled(true);
			}
		});
	}
}