package org.crazyit.intent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.widget.Button;
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
	private static final int PICK_CONTACT = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bn = findViewById(R.id.bn);
		// 为bn按钮绑定事件监听器
		bn.setOnClickListener(view ->{
			// 运行时获取读取联系人信息的权限
			requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 0x133); // ①
		});
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults)
	{
		if (requestCode == 0x133)
		{
			// 如果用户同意授权访问
			if(permissions.length > 0 &&  grantResults[0] ==
				PackageManager.PERMISSION_GRANTED)
			{
				// 创建Intent
				Intent intent = new Intent();
				// 设置Intent的Action属性
				intent.setAction(Intent.ACTION_PICK);
				// 设置Intent的Type属性
				intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
				// 启动Activity，并希望获取该Activity的结果
				startActivityForResult(intent, PICK_CONTACT);
			}
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case PICK_CONTACT:
				if (resultCode == Activity.RESULT_OK) {
					// 获取返回的数据
					Uri contactData = data.getData();
					CursorLoader cursorLoader = new CursorLoader(this, contactData, null, null, null, null);
					// 查询联系人信息
					Cursor cursor = cursorLoader.loadInBackground();
					// 如果查询到指定的联系人
					if (cursor!= null && cursor.moveToFirst()) {
						String contactId = cursor.getString(
								cursor.getColumnIndex(ContactsContract.Contacts._ID));
						// 获取联系人的名字
						String name = cursor.getString(
								cursor.getColumnIndexOrThrow(
										ContactsContract.Contacts.DISPLAY_NAME));
						String phoneNumber = "此联系人暂未输入电话号码";
						// 根据联系人查询该联系人的详细信息
						Cursor phones = getContentResolver().query(
								ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
										" = " + contactId, null, null);
						if (phones != null && phones.moveToFirst())
						{
							// 取出电话号码
							phoneNumber = phones.getString(
									phones.getColumnIndex(ContactsContract.
											CommonDataKinds.Phone.NUMBER));
						}
						// 关闭游标
						if (phones != null) phones.close();
						TextView show = findViewById(R.id.show);
						// 显示联系人的名称
						show.setText(name);
						TextView phone = findViewById(R.id.phone);
						// 显示联系人的电话号码
						phone.setText(phoneNumber);
					}
					// 关闭游标
					if (cursor != null) cursor.close();
				}
		}
	}
}

