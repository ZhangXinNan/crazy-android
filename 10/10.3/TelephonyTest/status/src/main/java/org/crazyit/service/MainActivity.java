package org.crazyit.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class MainActivity extends ListActivity
{
	// 声明代表手机状态的集合
	private List<String> statusValues = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.READ_PHONE_STATE}, 0x123);
	}

	@Override @SuppressLint("MissingPermission")
	public void onRequestPermissionsResult(int requestCode,
		@NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == 0x123 && grantResults[0] == PackageManager.PERMISSION_GRANTED
				&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
			// 获取系统的TelephonyManager对象
			TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			// 获取代表状态名称的数组
			String[] statusNames = getResources().getStringArray(R.array.statusNames);
			// 获取代表SIM卡状态的数组
			String[] simState = getResources().getStringArray(R.array.simState);
			// 获取代表电话网络类型的数组
			String[] phoneType = getResources().getStringArray(R.array.phoneType);
			// 获取设备编号
			statusValues.add(tManager.getImei());
			// 获取系统平台的版本
			statusValues.add(tManager.getDeviceSoftwareVersion() != null ?
					tManager.getDeviceSoftwareVersion() : "未知");
			// 获取网络运营商代号
			statusValues.add(tManager.getNetworkOperator());
			// 获取网络运营商名称
			statusValues.add(tManager.getNetworkOperatorName());
			// 获取手机网络类型
			statusValues.add(phoneType[tManager.getPhoneType()]);
			// 获取设备的蜂窝状态信息（包括位置）
			statusValues.add(tManager.getAllCellInfo() != null ?
					tManager.getAllCellInfo().toString() : "未知信息");
			// 获取SIM卡的国别
			statusValues.add(tManager.getSimCountryIso());
			// 获取SIM卡序列号
			statusValues.add(tManager.getSimSerialNumber());
			// 获取SIM卡状态
			statusValues.add(simState[tManager.getSimState()]);
			// 获得ListView对象
			List<Map<String, String>> status = new ArrayList<>();
			// 遍历statusValues集合，将statusNames、statusValues
			// 的数据封装到List<Map<String , String>>集合中
			for (int i = 0; i < statusValues.size(); i++) {
				Map<String, String> map = new HashMap<>();
				map.put("name", statusNames[i]);
				map.put("value", statusValues.get(i));
				status.add(map);
			}
			// 使用SimpleAdapter封装List数据
			SimpleAdapter adapter = new SimpleAdapter(this, status, R.layout.line,
					new String[]{"name", "value"}, new int[]{R.id.name, R.id.value});
			// 为ListActivity设置Adapter
			setListAdapter(adapter);
		} else {
			Toast.makeText(this, R.string.permission_tip,
					Toast.LENGTH_SHORT).show();
		}
	}
}