package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
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
public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CalendarView cv = findViewById(R.id.calendarView);
		// 为CalendarView组件的日期改变事件添加事件监听器
		cv.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
			// 使用Toast显示用户选择的日期
			Toast.makeText(MainActivity.this,"你生日是" + year
				+ "年" + (month + 1) + "月" + dayOfMonth + "日"
				, Toast.LENGTH_SHORT).show();
		});
	}
}
