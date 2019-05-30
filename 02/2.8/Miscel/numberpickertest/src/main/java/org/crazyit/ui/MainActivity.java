package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.NumberPicker;
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
	// 定义最低价格、最高价格的初始值
	private int minPrice = 25;
	private int maxPrice = 75;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		NumberPicker np1 = findViewById(R.id.np1);
		// 设置np1的最小值和最大值
		np1.setMinValue(10);
		np1.setMaxValue(50);
		// 设置np1的当前值
		np1.setValue(minPrice);
		np1.setOnValueChangedListener((picker, oldVal, newVal) -> {
			// 当NumberPicker的值发生改变时，将会激发该方法
			minPrice = newVal;
			showSelectedPrice();
		});
		NumberPicker np2 = findViewById(R.id.np2);
		// 设置np2的最小值和最大值
		np2.setMinValue(60);
		np2.setMaxValue(100);
		// 设置np2的当前值
		np2.setValue(maxPrice);
		np2.setOnValueChangedListener((picker, oldVal, newVal) -> {
			// 当NumberPicker的值发生改变时，将会激发该方法
			maxPrice = newVal;
			showSelectedPrice();
		});
	}
	private void showSelectedPrice()
	{
		Toast.makeText(this, "您选择最低价格为：" + minPrice +
				", 最高价格为：" + maxPrice, Toast.LENGTH_SHORT).show();
	}
}
