package org.crazyit.gps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.rtt.RangingRequest;
import android.net.wifi.rtt.RangingResult;
import android.net.wifi.rtt.RangingResultCallback;
import android.net.wifi.rtt.WifiRttManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executors;

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
	WifiRttManager mWifiRttManager;
	// 定义监听Wi-Fi状态改变的BroadcastReceiver
	public class WifiChangeReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction()))
			{
				// 开始执行Wi-Fi定位
				startWifiLoc();
			}
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 定义一个监听网络状态改变、Wi-Fi状态改变的IntentFilter
		IntentFilter wifiFilter = new IntentFilter();
		wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		wifiFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		// 为IntentFilter注册BroadcastReceiver
		registerReceiver(new WifiChangeReceiver(), wifiFilter);
	}
	// 定义执行WIFI定位的方法
	@SuppressLint("MissingPermission")
	private void startWifiLoc()
	{
		// 获取WIFI管理器
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// 判断是否支持室内Wi-Fi定位功能
		boolean hasRtt = getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_WIFI_RTT);
		System.out.println("是否具有室内WIFI定位功能：" + hasRtt);
		// 只有当版本大于Android 9时候才能使用室内WIFI定位功能
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P)
		{
			// 获取室内Wi-Fi定位管理器
			mWifiRttManager = (WifiRttManager)
					getSystemService(Context.WIFI_RTT_RANGING_SERVICE);  // ①
			RangingRequest request = new RangingRequest.Builder()
					// 添加Wi-Fi的扫描结果（即添加Wi-Fi访问点）
					.addAccessPoints(wifiManager.getScanResults())
					// 创建RangingRequest对象
					.build();  // ②
			// 开始请求执行WIFI室内定位
			mWifiRttManager.startRanging(request, Executors.newCachedThreadPool(),
					new RangingResultCallback()   // ③
					{
						// 如果Wi-Fi定位出错时触发该方法
						@Override
						public void onRangingFailure(int code)
						{ }
						// 室内Wi-Fi定位返回结果时触发该方法
						@Override
						public void onRangingResults(@NonNull List<RangingResult> results)
						{
							// 通过RangingResult集合可获取与特定WIFI接入点之间的距离
							for(RangingResult rr : results)
							{
								System.out.println("与" + rr.getMacAddress()
										+ "WIFI的距离是:" + rr.getDistanceMm());
							}
						}
					});
		}
	}
}

