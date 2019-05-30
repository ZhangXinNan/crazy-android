package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.QuickContactBadge;

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
		// 获取QuickContactBadge组件
		QuickContactBadge badge = findViewById(R.id.badge);
		// 将QuickContactBadge组件与特定电话号码对应的联系人建立关联
		badge.assignContactFromPhone("020-88888888", false);

	}
}
