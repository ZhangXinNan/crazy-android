package org.crazyit.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

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
public class MainActivity extends Activity implements SensorEventListener
{
	// 定义系统的Sensor管理器
	private SensorManager sensorManager;
	private TextView etTxt1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面上的文本框组件
		etTxt1 = findViewById(R.id.txt1);
		// 获取系统的传感器管理服务
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  // ①
	}
	@Override
	public void onResume()
	{
		super.onResume();
		// 为系统的加速度传感器注册监听器
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(
				Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME); // ②
	}
	@Override
	public void onPause()
	{
		super.onPause();
		// 取消注册
		sensorManager.unregisterListener(this);
	}
	// 以下是实现SensorEventListener接口必须实现的方法
	// 当传感器的值发生改变时回调该方法
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		float[] values = event.values;
		String sb = "X方向上的加速度：" +
				values[0] +
				"\nY方向上的加速度：" +
				values[1] +
				"\nZ方向上的加速度：" +
				values[2];
		etTxt1.setText(sb);
	}
	// 当传感器精度改变时回调该方法
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
}
