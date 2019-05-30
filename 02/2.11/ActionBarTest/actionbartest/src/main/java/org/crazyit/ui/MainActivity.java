package org.crazyit.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取该Activity的ActionBar
		// 只有当应用主题没有关闭ActionBar时，该代码才能返回ActionBar
		actionBar = getActionBar();
	}
	// 为“显示ActionBar”按钮定义事件处理方法
	public void showActionBar(View source)
	{
		// 显示ActionBar
		actionBar.show();
	}
	// 为“隐藏ActionBar”按钮定义事件处理方法
	public void hideActionBar(View source)
	{
		// 隐藏ActionBar
		actionBar.hide();
	}
}
