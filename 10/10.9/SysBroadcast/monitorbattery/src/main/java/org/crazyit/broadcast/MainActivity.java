package org.crazyit.broadcast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		IntentFilter batteryfilter = new IntentFilter();
		// 设置该Intent的Action属性
		batteryfilter.addAction(Intent.ACTION_BATTERY_CHANGED);
		// 注册BatteryReceiver
		registerReceiver(new BatteryReceiver(), batteryfilter);

		BatteryManager bm = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
		// 获取电池的状态
		int st = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS);
		// 获取电池的剩下电量（剩下的百分比）
		int a = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
		// 获取电池的剩下的电量（以纳瓦时为单位）
//		int a = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
		// 获取电池的平均电流（以毫安为单位），正值表示正在充电，负值表示正在放电
		int b = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
		// 获取电池的瞬时电流（以毫安为单位），正值表示正在充电，负值表示正在放电
		int c = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
	}
}
