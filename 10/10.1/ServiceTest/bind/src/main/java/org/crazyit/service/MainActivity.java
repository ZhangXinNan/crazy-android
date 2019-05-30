package org.crazyit.service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

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
	// 保持所启动的Service的IBinder对象
	private BindService.MyBinder binder;
	// 定义一个ServiceConnection对象
	private ServiceConnection conn = new ServiceConnection()
	{
		// 当该Activity与Service连接成功时回调该方法
		@Override public void onServiceConnected(ComponentName name, IBinder service)
		{
			System.out.println("--Service Connected--");
			// 获取Service的onBind方法所返回的MyBinder对象
			binder = (BindService.MyBinder)service;   // ①
		}
		// 当该Activity与Service断开连接时回调该方法
		@Override public void onServiceDisconnected(ComponentName name)
		{
			System.out.println("--Service Disconnected--");
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面中的start、stop、getServiceStatus按钮
		Button bindBn = findViewById(R.id.bind);
		Button unbindBn = findViewById(R.id.unbind);
		Button getServiceStatusBn = findViewById(R.id.getServiceStatus);
		// 创建启动Service的Intent
		Intent intent = new Intent(this, BindService.class);
		bindBn.setOnClickListener(view ->
			// 绑定指定Service
			bindService(intent, conn, Service.BIND_AUTO_CREATE));
		unbindBn.setOnClickListener(view ->
			// 解除绑定Service
			unbindService(conn));
		getServiceStatusBn.setOnClickListener(view ->
			// 获取并显示Service的count值
			Toast.makeText(MainActivity.this, "Service的count值为：" +
				binder.getCount(), Toast.LENGTH_SHORT).show()); // ②
	}
}
