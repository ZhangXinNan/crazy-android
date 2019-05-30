package org.crazyit.desktop;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
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
public class StackWidgetProvider extends AppWidgetProvider
{
	public static final String TOAST_ACTION = "org.crazyit.desktop.TOAST_ACTION";
	public static final String EXTRA_ITEM = "org.crazyit.desktop.EXTRA_ITEM";

	@Override
	public void onUpdate(Context context,
						 AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		// 创建RemoteViews对象，加载/res/layout目录下的widget_layout.xml文件
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		Intent intent = new Intent(context, StackWidgetService.class);
		// 使用intent更新rv中的stack_view组件（StackView）
		rv.setRemoteAdapter(R.id.stack_view, intent);  // ①
		// 设置当StackWidgetService提供的列表项为空时，直接显示empty_view组件
		rv.setEmptyView(R.id.stack_view, R.id.empty_view);
		// 创建启动StackWidgetProvider组件（作为BroadcastReceiver）的Intent
		Intent toastIntent = new Intent(context, StackWidgetProvider.class);
		// 为该Intent设置Action属性
		toastIntent.setAction(StackWidgetProvider.TOAST_ACTION);
		// 将Intent包装成PendingIntent
		PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context,
				0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		// 将PendingIntent与stack_view进行关联
		rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
		// 使用AppWidgetManager通过RemoteViews更新AppWidgetProvider
		appWidgetManager.updateAppWidget(new ComponentName(context,
				StackWidgetProvider.class), rv);  // ②
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	// 重写该方法，将该组件当成BroadcastReceiver使用
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (TOAST_ACTION.equals(intent.getAction()))
		{
			// 获取Intent中的数据
			int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
			// 显示Toast提示
			Toast.makeText(context, "点击第" + viewIndex + "个列表项",
					Toast.LENGTH_SHORT).show();
		}
		super.onReceive(context, intent);
	}
}
