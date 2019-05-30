package org.crazyit.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	private static final String TAG = "--CrazyIt--";
	private Button finishBn;
	private Button startActivityBn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 输出日志
		Log.d(TAG, "-------onCreate------");
		finishBn = findViewById(R.id.finish);
		startActivityBn = findViewById(R.id.startActivity);
		// 为startActivity按钮绑定事件监听器
		startActivityBn.setOnClickListener(view ->{
			Intent intent = new Intent(MainActivity.this, SecondActivity.class);
			startActivity(intent);
		});
		// 为finish按钮绑定事件监听器
		finishBn.setOnClickListener(view -> MainActivity.this.finish()/*结束该Activity*/);
	}
	@Override public void onStart()
	{
		super.onStart();
		// 输出日志
		Log.d(TAG, "-------onStart------");
	}
	@Override public void onRestart()
	{
		super.onRestart();
		// 输出日志
		Log.d(TAG, "-------onRestart------");
	}
	@Override public void onResume()
	{
		super.onResume();
		// 输出日志
		Log.d(TAG, "-------onResume------");
	}
	@Override public void onPause()
	{
		super.onPause();
		// 输出日志
		Log.d(TAG, "-------onPause------");
	}
	@Override public void onStop()
	{
		super.onStop();
		// 输出日志
		Log.d(TAG, "-------onStop------");
	}
	@Override public void onDestroy()
	{
		super.onDestroy();
		// 输出日志
		Log.d(TAG, "-------onDestroy------");
	}
}
