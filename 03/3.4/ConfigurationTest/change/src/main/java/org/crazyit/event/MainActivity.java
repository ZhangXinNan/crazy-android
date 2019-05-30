package org.crazyit.event;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
		// 为按钮绑定事件监听器
		bn.setOnClickListener(view -> {
			Configuration config = getResources().getConfiguration();
			// 如果当前是横屏
			if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
				// 设为竖屏
				MainActivity.this.setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			// 如果当前是竖屏
			if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
				// 设为横屏
				MainActivity.this.setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		});
	}
	// 重写该方法，用于监听系统设置的更改，主要是监控屏幕方向的更改
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		String screen = newConfig.orientation ==
			Configuration.ORIENTATION_LANDSCAPE? "横向屏幕": "竖向屏幕";
		Toast.makeText(this, "系统的屏幕方向发生改变"
			+ "\n修改后的屏幕方向为：" + screen, Toast.LENGTH_LONG).show();
	}
}
