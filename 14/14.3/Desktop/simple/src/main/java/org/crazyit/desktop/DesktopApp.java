package org.crazyit.desktop;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

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
public class DesktopApp extends AppWidgetProvider
{
	@Override
	public void onUpdate(Context context,
						 AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		// 加载指定界面布局文件，创建RemoteViews对象
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.my_widget);  // ①
		// 为show ImageView设置图片
		remoteViews.setImageViewResource(R.id.show, R.drawable.logo);   // ②
		// 将AppWidgetProvider的子类实例包装成ComponentName对象
		ComponentName componentName = new ComponentName(context,
				DesktopApp.class);  // ③
		// 调用AppWidgetManager将remoteViews添加到ComponentName中
		appWidgetManager.updateAppWidget(componentName, remoteViews);  // ④
	}
}
