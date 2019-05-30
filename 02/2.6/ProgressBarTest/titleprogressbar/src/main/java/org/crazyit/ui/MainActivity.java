package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
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
		// 设置窗口特征：启用显示进度的进度条
		requestWindowFeature(Window.FEATURE_PROGRESS); // ①
		// 设置窗口特征：启用不显示进度的进度条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // ②
		setContentView(R.layout.activity_main);
		Button bn1 = findViewById(R.id.bn1);
		Button bn2 = findViewById(R.id.bn2);
		bn1.setOnClickListener(view -> {
			// 显示不带进度的进度条
			setProgressBarIndeterminateVisibility(true);
			// 显示带进度的进度条
			setProgressBarVisibility(true);
			// 设置进度条的进度
			setProgress(4500);
		});
		bn2.setOnClickListener(view -> {
			// 隐藏不带进度的进度条
			setProgressBarIndeterminateVisibility(false);
			// 隐藏带进度的进度条
			setProgressBarVisibility(false);
		});
	}
}
