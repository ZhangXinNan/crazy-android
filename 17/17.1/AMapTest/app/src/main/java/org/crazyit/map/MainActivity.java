package org.crazyit.map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

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
	private MapView mapView;
	private AMap aMap;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = findViewById(R.id.map);
		// 必须回调MapView的onCreate()方法
		mapView.onCreate(savedInstanceState);
		init();
		ToggleButton tb = findViewById(R.id.tb);
		tb.setOnCheckedChangeListener((buttonView, isChecked) -> {
			if (isChecked)
			{
				// 设置使用卫星地图
				aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
			}
			else
			{
				// 设置使用普通地图
				aMap.setMapType(AMap.MAP_TYPE_NORMAL);
			}
		});
	}
	// 初始化AMap对象
	private void init()
	{
		if (aMap == null)
		{
			aMap = mapView.getMap();
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		// 必须回调MapView的onResume()方法
		mapView.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// 必须回调MapView的onPause()方法
		mapView.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		// 必须回调MapView的onSaveInstanceState()方法
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 必须回调MapView的onDestroy()方法
		mapView.onDestroy();
	}
}
