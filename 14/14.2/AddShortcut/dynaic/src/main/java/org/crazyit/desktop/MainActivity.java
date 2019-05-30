package org.crazyit.desktop;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
	public static final String ID_PREFIX = "FK";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button addBn = findViewById(R.id.add);
		Button deleteBn = findViewById(R.id.delete);
		// 获取快捷方式管理器：ShortcutManager
		ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
		// 为按钮的单击事件添加监听器
		addBn.setOnClickListener(view -> {
			// 获取所有动态添加的快捷方式
			List<ShortcutInfo> infList = shortcutManager.getDynamicShortcuts();
			// 如果还没有添加动态的快捷方式
			if (infList == null || infList.isEmpty())
			{
				List<ShortcutInfo> addList = new ArrayList<>();
				// 采用循环添加4个动态快捷方式
				for (int i = 1; i < 5; i++)
				{
					// 为快捷方式创建Intent
					Intent intent = new Intent(MainActivity.this, DynamicActivity.class);
					// 必须设置action属性
					intent.setAction(Intent.ACTION_VIEW);
					intent.putExtra("msg", "第" + i + "条消息");
					ShortcutInfo shortcut = new ShortcutInfo.Builder(this, ID_PREFIX + i)
							.setShortLabel("快捷方式" + i)
							.setLongLabel("详细描述" + i)
							.setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
							.setIntent(intent).build();
					addList.add(shortcut);
				}
				// 添加多个快捷方式
				shortcutManager.addDynamicShortcuts(addList);
				Toast.makeText(this, "快捷方式添加成功",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(this, "快捷方式已经存在",
						Toast.LENGTH_SHORT).show();
			}
		});
		// 为按钮的单击事件添加监听器
		deleteBn.setOnClickListener(view -> {
			// 获取所有动态添加的快捷方式
			List<ShortcutInfo> infList = shortcutManager.getDynamicShortcuts();
			// 如果还没有添加动态的快捷方式
			if (infList == null || infList.isEmpty())
			{
				Toast.makeText(this, "还没有添加快捷方式",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				// 删除所有的快捷方式
				shortcutManager.removeAllDynamicShortcuts();
				Toast.makeText(this, "删除快捷方式成功",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
