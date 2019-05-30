package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
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
	private boolean isShow = false;
	private ConstraintLayout content;
	private FloatingActionButton fab;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fab = findViewById(R.id.fab);
		content = findViewById(R.id.content);
		// 定义事件监听器
		View.OnClickListener listener = view -> {
			switch (view.getId()) {
				// 如果是fab悬浮按钮被点击，切换两个小的悬浮按钮的显示状态
				case R.id.fab:
					isShow = !isShow;
					content.setVisibility(isShow ? View.VISIBLE : View.GONE);
					break;
				// 如果是被展开的悬浮按钮，折叠两个小的悬浮按钮
				case R.id.mini_fab01: case R.id.mini_fab02:
					content.setVisibility(View.GONE);
					isShow = false;
					break;
			}
		};
		// 为悬浮按钮绑定事件处理监听器
		fab.setOnClickListener(listener);
	}
}
