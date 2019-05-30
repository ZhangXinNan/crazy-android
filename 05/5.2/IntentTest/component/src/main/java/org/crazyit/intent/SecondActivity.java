package org.crazyit.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
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
		setContentView(R.layout.activity_second);
		TextView show = findViewById(R.id.show);
		// 获取该Activity对应的Intent的Component属性
		ComponentName comp = getIntent().getComponent();
		// 显示该ComponentName对象的包名、类名
		show.setText("组件包名为：" + comp.getPackageName() +
			"\n组件类名为：" + comp.getClassName());
	}
}
