package org.crazyit.service;

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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面中的start和stop两个按钮
		Button startBn = findViewById(R.id.start);
		Button stopBn = findViewById(R.id.stop);
		// 创建启动Service的Intent
		Intent intent = new Intent(this, FirstService.class);
		startBn.setOnClickListener(view -> startService(intent)/*启动指定Service*/);
		stopBn.setOnClickListener(view -> stopService(intent)/*停止指定Service*/);
	}
}
