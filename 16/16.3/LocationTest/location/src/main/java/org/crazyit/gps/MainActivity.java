package org.crazyit.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
public class MainActivity extends Activity
{
	// 定义LocationManager对象
	private LocationManager locManager;
	// 定义程序界面中的TextView组件
	private TextView show;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面上的EditText组件
		show = findViewById(R.id.show);
		requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0x123);
	}
	@SuppressLint("MissingPermission")
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		// 如果用户允许使用GPS定位信息
		if(requestCode == 0x123	&& grantResults.length == 1
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			// 创建LocationManager对象
			locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			// 从GPS获取最近的定位信息
			Location location =
					locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			// 使用location来更新EditText的显示
			updateView(location);
			// 设置每3秒获取一次GPS定位信息
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					3000, 8f, new LocationListener() // ①
			{
				@Override
				public void onLocationChanged(Location location)
				{
					// 当GPS定位信息发生改变时，更新位置
					updateView(location);
				}

				@Override
				public void onStatusChanged(String provider, int status, Bundle extras)
				{}

				@Override
				public void onProviderEnabled(String provider)
				{
					// 当GPS LocationProvider可用时，更新位置
					updateView(locManager.getLastKnownLocation(provider));
				}

				@Override
				public void onProviderDisabled(String provider)
				{
					updateView(null);
				}
			});
		}
	}
	// 更新EditText中显示的内容
	public void updateView(Location newLocation)
	{
		if (newLocation != null)
		{
			String sb = "实时的位置信息：\n" +
					"经度：" +
					newLocation.getLongitude() +
					"\n纬度：" +
					newLocation.getLatitude() +
					"\n高度：" +
					newLocation.getAltitude() +
					"\n速度：" +
					newLocation.getSpeed() +
					"\n方向：" +
					newLocation.getBearing();
			show.setText(sb);
		}
		else
		{
			// 如果传入的Location对象为空，则清空EditText
			show.setText("");
		}
	}
}
