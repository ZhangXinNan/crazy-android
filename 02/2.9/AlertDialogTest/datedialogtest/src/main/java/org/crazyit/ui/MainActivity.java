package org.crazyit.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button dateBn = findViewById(R.id.date_bn);
		Button timeBn = findViewById(R.id.time_bn);
		TextView show = findViewById(R.id.show);
		// 为“设置日期”按钮绑定监听器
		dateBn.setOnClickListener(view -> {
			Calendar c = Calendar.getInstance();
			// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
			new DatePickerDialog(MainActivity.this,
				// 绑定监听器
				(dp, year, month, dayOfMonth) -> {
					show.setText("您选择了：" + year + "年" + (month + 1) +
						"月" + dayOfMonth + "日");
				}, c.get(Calendar.YEAR),
					c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH)) // 设置初始日期
				.show();
		});
		// 为“设置时间”按钮绑定监听器
		timeBn.setOnClickListener(view -> {
			Calendar c = Calendar.getInstance();
			// 创建一个TimePickerDialog实例，并把它显示出来
			new TimePickerDialog(MainActivity.this,
					// 绑定监听器
					(tp, hourOfDay, minute) -> {
					show.setText("您选择了：" + hourOfDay +
							"时" + minute + "分");
			}, c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE),  // 设置初始时间
				true) // true表示采用24小时制
			.show();
		});
	}
}
