package org.crazyit.content;

import android.Manifest;
import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.widget.Toast;

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
		// 请求获取读取短信的权限
		requestPermissions(new String[]{Manifest.permission.READ_SMS}, 0x123);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions,  @NonNull int[] grantResults)
	{
		// 如果用户授权访问短信内容
		if (grantResults[0] == 0 && requestCode == 0x123)
		{
			// 为Telephony.Sms.CONTENT_URI的数据改变注册监听器
			getContentResolver().registerContentObserver(Telephony.Sms.CONTENT_URI,
					true, new SmsObserver(new Handler()));
		} else
		{
			Toast.makeText(this, "您必须授权访问短信内容才能测试该应用",
					Toast.LENGTH_SHORT).show();
		}
	}
	// 提供自定义的ContentObserver监听器类
	private class SmsObserver extends ContentObserver
	{
		SmsObserver(Handler handler)
		{
			super(handler);
		}
		private String prevMsg = "";
		@Override
		public void onChange(boolean selfChange)
		{
			// 查询发件箱中的短信（所有已发送的短信都处于发件箱中）
			Cursor cursor = getContentResolver().query(Telephony.Sms.Sent.
					CONTENT_URI, null, null, null, null);
			// 遍历查询得到的结果集，即可获取用户正在发送的短信
			while (cursor.moveToNext())
			{
				// 只显示最近5秒内发出的短信
				if (Math.abs(System.currentTimeMillis() - cursor.
						getLong(cursor.getColumnIndex("date"))) < 5000)
				{
					StringBuilder sb = new StringBuilder();
					// 获取短信的发送地址
					sb.append("address=").append(cursor.getString(cursor.
							getColumnIndex("address")));
					// 获取短信的标题
					sb.append(";subject=").append(cursor.getString(cursor.
							getColumnIndex("subject")));
					// 获取短信的内容
					sb.append(";body=").append(cursor.getString(cursor.
							getColumnIndex("body")));
					// 获取短信的发送时间
					sb.append(";time=").append(cursor.getLong(cursor.
							getColumnIndex("date")));
					if (!prevMsg.equals(sb.toString()))
					{
						prevMsg = sb.toString();
						System.out.println("发送短信：" + prevMsg);
					}
				}
			}
			cursor.close();
		}
	}
}
