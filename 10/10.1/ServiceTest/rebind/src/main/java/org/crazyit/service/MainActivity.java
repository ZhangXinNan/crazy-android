package org.crazyit.service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
	// 保持所启动的Service的IBinder对象
	private BindService.MyBinder binder;
	// 定义启动Service的Intent
	private Intent targetIt;
	// 定义一个ServiceConnection对象
	private ServiceConnection conn = new ServiceConnection()
	{
		// 当该Activity与Service连接成功时回调该方法
		@Override public void onServiceConnected(ComponentName name, IBinder service)
		{
			System.out.println("--Service Connected--");
			// 获取Service的onBind方法所返回的MyBinder对象
			binder = (BindService.MyBinder)service; // ①
		}

		// 当该Activity与Service断开连接时回调该方法
		@Override public void onServiceDisconnected(ComponentName name)
		{
			System.out.println("--Service Disconnected--");
		}
	};
	public void start(View source)
	{
		// 启动targetIt对应的Service
		startService(targetIt);
	}

	public void bind(View source)
	{
		// 绑定targetIt对应的Service
		bindService(targetIt, conn, Service.BIND_AUTO_CREATE);
	}

	public void unBind(View source)
	{
		// 解除绑定Serivce
		unbindService(conn);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建启动Service的Intent
		targetIt = new Intent(this, BindService.class);
	}
}
