package org.crazyit.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

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
public class AidlService extends Service
{
	private CatBinder catBinder;
	private Timer timer = new Timer();
	private String[] colors = new String[]{"红色", "黄色", "黑色"};
	private double[] weights = new double[]{2.3, 3.1, 1.58};
	private String color;
	private double weight;
	// 继承Stub，也就是实现了ICat接口，并实现了IBinder接口
	class CatBinder extends ICat.Stub
	{
		@Override
		public String getColor()
		{
			return AidlService.this.color;
		}

		@Override
		public double getWeight()
		{
			return AidlService.this.weight;
		}
	}

	@Override public void onCreate()
	{
		super.onCreate();
		catBinder = new CatBinder();
		timer.schedule(new TimerTask()
		{
			@Override public void run()
			{
				// 随机改变Service组件内color、weight属性的值
				int rand = (int) (Math.random() * 3);
				color = colors[rand];
				weight = weights[rand];
			}
		},0, 800);
	}

	@Override public IBinder onBind(Intent intent)
	{
		/* 返回catBinder对象
		 * 在绑定本地Service的情况下，该catBinder对象会直接
		 * 传给客户端的ServiceConnection对象
		 * 的onServiceConnected方法的第二个参数
		 * 在绑定远程Service的情况下，只将catBinder对象的代理
		 * 传给客户端的ServiceConnection对象
		 * 的onServiceConnected方法的第二个参数
		 */
		return catBinder; // ①
	}
	@Override public void onDestroy()
	{
		timer.cancel();
	}
}
