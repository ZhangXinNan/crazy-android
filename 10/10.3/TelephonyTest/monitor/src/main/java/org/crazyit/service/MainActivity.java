package org.crazyit.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 0x123);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		// 如果用户授权访问读取通话信息
		if (requestCode == 0x123 && grantResults[0] ==
				PackageManager.PERMISSION_GRANTED) {
			// 取得TelephonyManager对象
			TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			// 创建一个通话状态监听器
			PhoneStateListener listener = new PhoneStateListener()
			{
				@Override
				public void onCallStateChanged(int state, String number)
				{
					switch (state) {
						// 无任何状态
						case TelephonyManager.CALL_STATE_IDLE:
							break;
						// 挂断电话时
						case TelephonyManager.CALL_STATE_OFFHOOK:
							break;
						// 来电铃响时
						case TelephonyManager.CALL_STATE_RINGING: {
							try(
								OutputStream os = openFileOutput("phoneList", Context.MODE_APPEND);
								PrintStream ps = new PrintStream(os))
							{
								// 将来电号码记录到文件中
								ps.println(new Date().toString() + " 来电：" + number);
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					super.onCallStateChanged(state, number);
				}
			};
			// 监听电话通话状态的改变
			tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		}
	}
}
