package org.crazyit.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
public class FirstService extends Service
{
	// Service被绑定时回调该方法
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	// Service被创建时回调该方法
	@Override
	public void onCreate()
	{
		super.onCreate();
		System.out.println("Service is Created");
	}

	// Service被启动时回调该方法
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		System.out.println("Service is Started");
		return START_STICKY;
	}

	// Service被关闭之前回调该方法
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		System.out.println("Service is Destroyed");
	}
}
