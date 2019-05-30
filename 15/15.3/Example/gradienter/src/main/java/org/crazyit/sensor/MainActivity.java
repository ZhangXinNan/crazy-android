package org.crazyit.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

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
	public final static int MAX_ANGLE = 30;
	// 定义水平仪的仪表盘
	private MyView show;
	// 定义Sensor管理器
	private SensorManager mSensorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取水平仪的主组件
		show = findViewById(R.id.show);
		// 获取传感器管理服务
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		// 为系统的方向传感器注册监听器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onPause()
	{
		// 取消注册
		mSensorManager.unregisterListener(this);
		super.onPause();
	}

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
		switch (sensorType)
		{
			case Sensor.TYPE_ORIENTATION:
				// 获取与Y轴的夹角
				float yAngle = values[1];
				// 获取与Z轴的夹角
				float zAngle = values[2];
				// 气泡位于中间时（水平仪完全水平），气泡的X、Y坐标
				float x = (show.back.getWidth() - show.bubble.getWidth()) / 2;
				float y = (show.back.getHeight() - show.bubble.getHeight()) / 2;
				// 如果与Z轴的倾斜角还在最大角度之内
				if (Math.abs(zAngle) <= MAX_ANGLE)
				{
					// 根据与Z轴的倾斜角度计算X坐标的变化值
					// （倾斜角度越大，X坐标变化越大）
					int deltaX = (int) ((show.back.getWidth() - show.bubble
							.getWidth()) / 2 * zAngle / MAX_ANGLE);
					x += deltaX;
				}
				// 如果与Z轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
				else if (zAngle > MAX_ANGLE)
				{
					x = 0;
				}
				// 如果与Z轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
				else
				{
					x = show.back.getWidth() - show.bubble.getWidth();
				}
				// 如果与Y轴的倾斜角还在最大角度之内
				if (Math.abs(yAngle) <= MAX_ANGLE)
				{
					// 根据与Y轴的倾斜角度计算Y坐标的变化值
					// （倾斜角度越大，Y坐标变化越大）
					int deltaY = (int) ((show.back.getHeight() - show.bubble
							.getHeight()) / 2 * yAngle / MAX_ANGLE);
					y += deltaY;
				}
				// 如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
				else if (yAngle > MAX_ANGLE)
				{
					y = show.back.getHeight() - show.bubble.getHeight();
				}
				// 如果与Y轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最上边
				else
				{
					y = 0;
				}
				// 如果计算出来的X、Y坐标还位于水平仪的仪表盘内，更新水平仪的气泡坐标
				if (isContain(x, y))
				{
					show.bubbleX = x;
					show.bubbleY = y;
				}
				// 通知系统重绘MyView组件
				show.postInvalidate();
				break;
		}
	}

	// 计算x、y点的气泡是否处于水平仪的仪表盘内
	private boolean isContain(float x, float y)
	{
		// 计算气泡的圆心坐标X、Y
		float bubbleCx = x + show.bubble.getWidth() / 2;
		float bubbleCy = y + show.bubble.getHeight() / 2;
		// 计算水平仪仪表盘的圆心坐标X、Y
		float backCx = show.back.getWidth() / 2;
		float backCy = show.back.getHeight() / 2;
		// 计算气泡的圆心与水平仪仪表盘的圆心之间的距离
		double distance = Math.sqrt((bubbleCx - backCx) * (bubbleCx - backCx) +
				(bubbleCy - backCy) * (bubbleCy - backCy));
		// 若两个圆心的距离小于它们的半径差，即可认为处于该点的气泡依然位于仪表盘内
		return distance < (show.back.getWidth() - show.bubble.getWidth()) / 2;
	}
}
