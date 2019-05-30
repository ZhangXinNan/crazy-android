package org.crazyit.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
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
public class BindService extends Service
{
	private int count;
	private boolean quit;
	// 定义onBinder方法所返回的对象
	private MyBinder binder = new MyBinder();

	// 通过继承Binder来实现IBinder类
	class MyBinder extends Binder  // ①
	{
		// 获取Service的运行状态：count
		public int getCount()
		{
			return BindService.this.count;
		}
	}

	// Service被绑定时回调该方法
	@Override
	public IBinder onBind(Intent intent)
	{
		System.out.println("Service is Binded");
		// 返回IBinder对象
		return binder;
	}

	// Service被创建时回调该方法
	@Override
	public void onCreate()
	{
		super.onCreate();
		System.out.println("Service is Created");
		// 启动一条线程，动态地修改count状态值
		new Thread(() ->
		{
			while (!quit) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				BindService.this.count++;
			}
		}).start();
	}
	// Service被启动时回调该方法
	@Override public int onStartCommand(Intent intent, int flags, int startId)
	{
		System.out.println("Service is Started");
		return START_STICKY;
	}

	// Service被断开连接时回调该方法
	@Override
	public boolean onUnbind(Intent intent)
	{
		System.out.println("Service is Unbinded");
		return true;
	}

	// Service被关闭之前回调该方法
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		this.quit = true;
		System.out.println("Service is Destroyed");
	}
	// Service被重新绑定时回调该方法
	@Override
	public void onRebind(Intent intent)
	{
		super.onRebind(intent);
		this.quit = true;
		System.out.println("---Service is ReBinded---");
	}
}
