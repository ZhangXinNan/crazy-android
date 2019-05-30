package org.crazyit.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
public class MainActivity extends FragmentActivity
{
	Button startActivity, addFragment, backFragment, replaceFragment ,finish;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startActivity = findViewById(R.id.startActivity);
		addFragment = findViewById(R.id.addFragment);
		backFragment = findViewById(R.id.backFragment);
		replaceFragment = findViewById(R.id.replaceFragment);
		finish = findViewById(R.id.finish);
		// 为startActivity按钮绑定事件监听器
		startActivity.setOnClickListener(source -> {
			Intent intent = new Intent(MainActivity.this
					, SecondActivity.class);
			startActivity(intent);
		});
		// 为addFragment按钮绑定事件监听器
		addFragment.setOnClickListener(source -> {
			LifecycleFragment fragment = new LifecycleFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment)
					.commit();
		});
		// 为backFragment按钮绑定事件监听器
		backFragment.setOnClickListener(source -> {
			SecondFragment fragment = new SecondFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment)
					.addToBackStack("aa")// 将替换前的Fragment添加到Back栈
					.commit();
		});
		// 为replaceFragment按钮绑定事件监听器
		replaceFragment.setOnClickListener(source -> {
			SecondFragment fragment = new SecondFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment)
					.commit();
		});
		// 为finish按钮绑定事件监听器
		finish.setOnClickListener(source->MainActivity.this.finish()/* 结束该Activity*/);
	}
}
