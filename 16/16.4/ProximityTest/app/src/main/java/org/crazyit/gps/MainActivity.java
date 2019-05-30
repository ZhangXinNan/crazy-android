package org.crazyit.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

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
	private LocationManager locationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 通过getSystemService方法获得LocationManager实例
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
				0x123);
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
			// 定义“疯狂软件教育中心”的经度、纬度
			double longitude = 113.401863;
			double latitude = 23.132636;
			// 定义半径（5公里）
			float radius = 5000f;
			// 定义Intent
			Intent intent = new Intent(this, ProximityAlertReciever.class);
			// 将Intent包装成PendingIntent
			PendingIntent pi = PendingIntent.getBroadcast(this, -1, intent, 0);
			// 添加临近警告
			locationManager.addProximityAlert(latitude, longitude, radius, -1, pi);
		}
	}
}

