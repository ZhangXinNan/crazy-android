package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;
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
	// 定义5个记录当前时间的变量
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatePicker datePicker = findViewById(R.id.datePicker);
		TimePicker timePicker = findViewById(R.id.timePicker);
		// 获取当前的年、月、日、小时、分钟
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR);
		minute = c.get(Calendar.MINUTE);
		// 初始化DatePicker组件，初始化时指定监听器
		datePicker.init(year, month, day,  (view, year, month, day) -> {
			MainActivity.this.year = year;
			MainActivity.this.month = month;
			MainActivity.this.day = day;
			// 显示当前日期、时间
			showDate(year, month, day, hour, minute);
		});
		// 为TimePicker指定监听器
		timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
			MainActivity.this.hour = hourOfDay;
			MainActivity.this.minute = minute;
			// 显示当前日期、时间
			showDate(year, month, day, hour, minute);
		});
	}
	// 使用Toast显示当前日期、时间的方法
	private void showDate(int year, int month, int day, int hour, int minute)
	{
		String msg = "您的购买日期为：" + year + "年" + (month + 1) +
				"月" + day + "日  " + hour + "时" + minute + "分";
		Toast.makeText(this, msg, Toast.LENGTH_SHORT)
				.show();
	}
}
