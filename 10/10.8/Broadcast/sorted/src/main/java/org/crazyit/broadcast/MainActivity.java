package org.crazyit.broadcast;

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
		// 获取程序中的send按钮
		Button sendBn = findViewById(R.id.send);
		sendBn.setOnClickListener(view -> {
			// 创建Intent对象
			Intent intent = new Intent();
			intent.setAction("org.crazyit.action.CRAZY_BROADCAST");
			intent.setPackage("org.crazyit.broadcast");
			intent.putExtra("msg", "简单的消息");
			// 发送有序广播
			sendOrderedBroadcast(intent, null);
		});
	}
}
