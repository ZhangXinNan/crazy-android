package org.crazyit.desktop;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
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
		Button addBn = findViewById(R.id.add);
		ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
		addBn.setOnClickListener(view -> {
			if (shortcutManager.isRequestPinShortcutSupported())
			{
				// 设置该快捷方式启动的Intent
				Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
				myIntent.setAction("android.intent.action.VIEW");
				// 如果ID为fk-shortcut的快捷方式已经存在，则可省略设置Intent、Icon等属性
				ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(
					MainActivity.this, "my-shortcut")
                    .setShortLabel("Pinned快捷")
					.setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
					.setIntent(myIntent).build();  // ①
				// 请求添加Pinned快捷方式
				shortcutManager.requestPinShortcut(pinShortcutInfo, null);  // ②
			}
		});
	}
}
