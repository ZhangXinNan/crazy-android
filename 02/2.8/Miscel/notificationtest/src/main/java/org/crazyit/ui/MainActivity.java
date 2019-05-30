package org.crazyit.ui;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
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
	public static final int NOTIFICATION_ID = 0x123;
	public static final String CHANNEL_ID = "crazyit";
	private NotificationManager nm;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取系统的NotificationManager服务
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 设置通知Channel的名字
		String name = "测试Channel";
		// 创建通知
		NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
				name, NotificationManager.IMPORTANCE_HIGH);
		// 设置通知Channel的描述信息
		channel.setDescription("测试Channel的描述信息");
		// 设置通知出现时的闪光灯
		channel.enableLights(true);
		channel.setLightColor(Color.RED);
		// 设置通知出现时振动
		channel.enableVibration(true);
		channel.setVibrationPattern(new long[]{0 , 50 , 100, 150});
		channel.setSound(Uri.parse("android.resource://org.crazyit.ui/" + R.raw.msg), null);
		// 最后在NotificationManager上创建该通知Channel
		nm.createNotificationChannel(channel);
	}
	// 为发送通知的按钮的点击事件定义事件处理方法
	public void send(View source)
	{
		// 创建一个启动其他Activity的Intent
		Intent intent = new Intent(MainActivity.this, OtherActivity.class);
		PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
		Person p = new Person.Builder()
				.setName("孙悟空")
				.setIcon(Icon.createWithResource(this, R.drawable.sun))
				.build();
		// 设置通知参与者
		Notification.MessagingStyle messagingStyle = new Notification.MessagingStyle(p);
		// 设置消息标题
		messagingStyle.setConversationTitle("一条新通知");
		// 创建一条消息
		Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message(
				"恭喜您，您加薪了，工资增加20%!", System.currentTimeMillis(), p);
		// 设置额外的数据
//		message.setData("image/jpeg", Uri.parse("file:///mnt/sdcard/list.png"));  // ②
		// 添加一条消息
		messagingStyle.addMessage(message);
		Notification notify = new Notification.Builder(this, CHANNEL_ID)
			// 设置打开该通知，该通知自动消失
			.setAutoCancel(true)
			// 设置通知的图标
			.setSmallIcon(R.drawable.notify)
			// 设置MessagingStyle
			.setStyle(messagingStyle)
			// 设置通知将要启动程序的Intent
			.setContentIntent(pi)  // ①
			.build();
		// 发送通知
		nm.notify(NOTIFICATION_ID, notify);
	}
	// 为删除通知的按钮的点击事件定义事件处理方法
	public void del(View v)
	{
		// 取消通知
		nm.cancel(NOTIFICATION_ID);
	}
}
