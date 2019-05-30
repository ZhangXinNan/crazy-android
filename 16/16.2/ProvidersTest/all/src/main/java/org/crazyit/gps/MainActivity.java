package org.crazyit.gps;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

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
	private ListView providersLv;
	private LocationManager lm;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		providersLv = findViewById(R.id.providers);
		// 获取系统的LocationManager对象
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 获取系统所有的LocationProvider的名称
		List<String> providerNames = lm.getAllProviders();
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, providerNames);
		// 使用ListView来显示所有可用的LocationProvider
		providersLv.setAdapter(adapter);
	}
}
