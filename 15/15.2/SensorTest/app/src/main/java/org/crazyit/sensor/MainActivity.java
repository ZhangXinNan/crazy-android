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
	// 定义Sensor管理器
	private SensorManager mSensorManager;
	private TextView etOrientation;
	private TextView etGyro;
	private TextView etMagnetic;
	private TextView etGravity;
	private TextView etLinearAcc;
	private TextView etTemerature;
	private TextView etHumidity;
	private TextView etLight;
	private TextView etPressure;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取界面上的TextView组件
		etOrientation = findViewById(R.id.etOrientation);
		etGyro = findViewById(R.id.etGyro);
		etMagnetic = findViewById(R.id.etMagnetic);
		etGravity = findViewById(R.id.etGravity);
		etLinearAcc = findViewById(R.id.etLinearAcc);
		etTemerature = findViewById(R.id.etTemerature);
		etHumidity = findViewById(R.id.etHumidity);
		etLight = findViewById(R.id.etLight);
		etPressure = findViewById(R.id.etPressure);
		// 获取传感器管理服务
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  // ①
	}
	@Override
	public void onResume()
	{
		super.onResume();
		// 为系统的方向传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的陀螺仪传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的磁场传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的重力传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的线性加速度传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的温度传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的湿度传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的光传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_GAME);
		// 为系统的压力传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
				SensorManager.SENSOR_DELAY_GAME);
	}
	@Override
	public void onPause()
	{
		// 程序暂停时取消注册传感器监听器
		mSensorManager.unregisterListener(this);
		super.onPause();
	}
	// 以下是实现SensorEventListener接口必须实现的方法
	// 当传感器精度改变时回调该方法
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		float[] values = event.values;
		// 获取触发event的传感器类型
		int sensorType = event.sensor.getType();
		StringBuilder sb;
		// 判断是哪个传感器发生改变
		switch (sensorType)
		{
			// 方向传感器
			case Sensor.TYPE_ORIENTATION:
				sb = new StringBuilder();
				sb.append("绕Z轴转过的角度：");
				sb.append(values[0]);
				sb.append("\n绕X轴转过的角度：");
				sb.append(values[1]);
				sb.append("\n绕Y轴转过的角度：");
				sb.append(values[2]);
				etOrientation.setText(sb.toString());
				break;
			// 陀螺仪传感器
			case Sensor.TYPE_GYROSCOPE:
				sb = new StringBuilder();
				sb.append("绕X轴旋转的角速度：");
				sb.append(values[0]);
				sb.append("\n绕Y轴旋转的角速度：");
				sb.append(values[1]);
				sb.append("\n绕Z轴旋转的角速度：");
				sb.append(values[2]);
				etGyro.setText(sb.toString());
				break;
			// 磁场传感器
			case Sensor.TYPE_MAGNETIC_FIELD:
				sb = new StringBuilder();
				sb.append("X轴方向上的磁场强度：");
				sb.append(values[0]);
				sb.append("\nY轴方向上的磁场强度：");
				sb.append(values[1]);
				sb.append("\nZ轴方向上的磁场强度：");
				sb.append(values[2]);
				etMagnetic.setText(sb.toString());
				break;
			// 重力传感器
			case Sensor.TYPE_GRAVITY:
				sb = new StringBuilder();
				sb.append("X轴方向上的重力：");
				sb.append(values[0]);
				sb.append("\nY轴方向上的重力：");
				sb.append(values[1]);
				sb.append("\nZ轴方向上的重力：");
				sb.append(values[2]);
				etGravity.setText(sb.toString());
				break;
			// 线性加速度传感器
			case Sensor.TYPE_LINEAR_ACCELERATION:
				sb = new StringBuilder();
				sb.append("X轴方向上的线性加速度：");
				sb.append(values[0]);
				sb.append("\nY轴方向上的线性加速度：");
				sb.append(values[1]);
				sb.append("\nZ轴方向上的线性加速度：");
				sb.append(values[2]);
				etLinearAcc.setText(sb.toString());
				break;
			// 温度传感器
			case Sensor.TYPE_AMBIENT_TEMPERATURE:
				sb = new StringBuilder();
				sb.append("当前温度为：");
				sb.append(values[0]);
				etTemerature.setText(sb.toString());
				break;
			// 湿度传感器
			case Sensor.TYPE_RELATIVE_HUMIDITY:
				sb = new StringBuilder();
				sb.append("当前湿度为：");
				sb.append(values[0]);
				etHumidity.setText(sb.toString());
				break;
			// 光传感器
			case Sensor.TYPE_LIGHT:
				sb = new StringBuilder();
				sb.append("当前光的强度为：");
				sb.append(values[0]);
				etLight.setText(sb.toString());
				break;
			// 压力传感器
			case Sensor.TYPE_PRESSURE:
				sb = new StringBuilder();
				sb.append("当前压力为：");
				sb.append(values[0]);
				etPressure.setText(sb.toString());
				break;
		}
	}
}
