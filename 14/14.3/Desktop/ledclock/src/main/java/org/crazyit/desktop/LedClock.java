package org.crazyit.desktop;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class LedClock extends AppWidgetProvider
{
	private Timer timer = new Timer();
	private AppWidgetManager appWidgetManager;
	private Context context;
	// 将0~9的液晶数字图片定义成数组
	private int[] digits = new int[]{R.drawable.su01, R.drawable.su02,
			R.drawable.su03, R.drawable.su04, R.drawable.su05,
			R.drawable.su06, R.drawable.su07, R.drawable.su08,
			R.drawable.su09, R.drawable.su10};
	// 将显示小时、分钟、秒钟的ImageView定义成数组
	private int[] digitViews = new int[]{R.id.img01, R.id.img02, R.id.img04,
			R.id.img05, R.id.img07, R.id.img08};

	static class MyHandler extends Handler
	{
		private WeakReference<LedClock> ledClock;
		public MyHandler(WeakReference<LedClock> ledClock)
		{
			this.ledClock = ledClock;
		}
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				RemoteViews views = new RemoteViews(ledClock.get().context.getPackageName(), R.layout.clock);
				// 定义SimpleDateFormat对象
				SimpleDateFormat df = new SimpleDateFormat("HHmmss");
				// 将当前时间格式化成HHmmss的形式
				String timeStr = df.format(new Date());
				for (int i = 0; i < timeStr.length(); i++)
				{
					// 将第i个数字字符转换为对应的数字
					int num = timeStr.charAt(i) - 48;
					// 将第i个图片设为对应的液晶数字图片
					views.setImageViewResource(ledClock.get().digitViews[i],
						ledClock.get().digits[num]);
				}
				// 将AppWidgetProvider子类实例包装成ComponentName对象
				ComponentName componentName = new ComponentName(ledClock.get().context,
					LedClock.class);
				// 调用AppWidgetManager将remoteViews添加到ComponentName中
				ledClock.get().appWidgetManager.updateAppWidget(
					componentName, views);
			}
			super.handleMessage(msg);
		}
	}

	private Handler handler = new MyHandler(new WeakReference<>(this));
	@Override
	public void onUpdate(Context context,
						 AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		System.out.println("--onUpdate--");
		this.appWidgetManager = appWidgetManager;
		this.context = context;
		// 定义计时器
		timer = new Timer();
		// 启动周期性调度
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				// 发送空消息，通知界面更新
				handler.sendEmptyMessage(0x123);
			}
		},0, 1000);
	}
}
