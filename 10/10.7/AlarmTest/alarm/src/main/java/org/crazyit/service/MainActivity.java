package org.crazyit.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

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
	private AlarmManager aManager;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取AlarmManager对象
		aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// 获取程序界面上的按钮
		Button setTimeBn = findViewById(R.id.setTime);
		// 为“设置闹铃”按钮绑定监听器
		setTimeBn.setOnClickListener(view -> {
			Calendar currentTime = Calendar.getInstance();
			// 创建一个TimePickerDialog实例，并把它显示出来
			new TimePickerDialog(MainActivity.this, 0, // 绑定监听器
				(dialog, hourOfDay, minute) -> {
					// 指定启动AlarmActivity组件
					Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
					// 创建PendingIntent对象
					PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
					Calendar c = Calendar.getInstance();
					// 根据用户选择时间来设置Calendar对象
					c.set(Calendar.HOUR_OF_DAY, hourOfDay);
					c.set(Calendar.MINUTE, minute);
					// 设置AlarmManager将在Calendar对应的时间启动指定组件
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 6.0及以上
						aManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
								c.getTimeInMillis(), pi);
					} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4及以上
						aManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
					}
					else {
						aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
					}
					// 显示闹铃设置成功的提示信息
					Toast.makeText(MainActivity.this, "闹铃设置成功啦",
							Toast.LENGTH_SHORT).show();
				},currentTime.get(Calendar.HOUR_OF_DAY),
				currentTime.get(Calendar.MINUTE), false).show();
		});
	}
}

