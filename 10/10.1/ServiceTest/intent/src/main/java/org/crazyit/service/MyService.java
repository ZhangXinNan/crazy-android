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
public class MyService extends Service
{
	@Override public IBinder onBind(Intent intent)
	{
		return null;
	}
	@Override public int onStartCommand(Intent intent, int flags, int startId)
	{
		// 该方法内执行耗时任务可能导致ANR（Application Not Responding）异常
		long endTime = System.currentTimeMillis() + 20 * 1000;
		System.out.println("onStart");
		while (System.currentTimeMillis() < endTime)
		{
			synchronized(this) {
				try {
					wait(endTime - System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("---耗时任务执行完成---");
		return START_STICKY;
	}
}
