package org.crazyit.service;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
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
	private EditText numberEt;
	private EditText contentEt;
	private Button sendBn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面上的两个文本框和按钮
		numberEt = findViewById(R.id.number);
		contentEt = findViewById(R.id.content);
		sendBn = findViewById(R.id.send);
		requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 0x123);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		// 如果用户允许发送短信
		if (requestCode == 0x123
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			// 获取SmsManager
			SmsManager sManager = SmsManager.getDefault();
			// 为sendBn按钮的单击事件绑定监听器
			sendBn.setOnClickListener(view ->
			{
				// 创建一个PendingIntent对象
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, new Intent(), 0);
				// 发送短信
				sManager.sendTextMessage(numberEt.getText().toString(),
						null, contentEt.getText().toString(), pi, null);
				// 提示短信发送完成
				Toast.makeText(MainActivity.this, "短信发送完成",
						Toast.LENGTH_SHORT).show();
			});
		} else {
			Toast.makeText(this, R.string.permission_tip,
					Toast.LENGTH_SHORT).show();
		}
	}
}
