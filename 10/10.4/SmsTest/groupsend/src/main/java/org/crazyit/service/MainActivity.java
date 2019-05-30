package org.crazyit.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
	private TextView numbersTv;
	private EditText contentEt;
	private Button selectBn;
	private Button sendBn;
	// 记录需要群发的号码列表
	private List<String> sendList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取界面上的文本框、按钮组件
		numbersTv = findViewById(R.id.numbers);
		contentEt = findViewById(R.id.content);
		selectBn = findViewById(R.id.select);
		sendBn = findViewById(R.id.send);
		requestPermissions(new String[]{Manifest.permission.SEND_SMS,
				Manifest.permission.READ_CONTACTS}, 0x123);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == 0x123 && grantResults[0] == PackageManager.PERMISSION_GRANTED
				&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
			SmsManager sManager = SmsManager.getDefault();
			// 为sendBn按钮的单击事件绑定监听器
			sendBn.setOnClickListener(view ->
			{
				for (String number : sendList) {
					// 创建一个PendingIntent对象
					PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, new Intent(), 0);
					// 发送短信
					sManager.sendTextMessage(number, null,
							contentEt.getText().toString(), pi, null);
				}
				// 提示短信群发完成
				Toast.makeText(MainActivity.this, "短信群发完成",
						Toast.LENGTH_SHORT).show();
			});
			// 为selectBn按钮的单击事件绑定监听器
			selectBn.setOnClickListener(view ->
			{
				// 查询联系人的电话号码
				Cursor cursor = getContentResolver().query(ContactsContract.
						CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
				List<String> numberList = new ArrayList<>();
				while (cursor.moveToNext()) {
					numberList.add(cursor.getString(cursor.getColumnIndex(
							ContactsContract.CommonDataKinds.Phone.NUMBER))
							.replace("-", "").replace(" ", ""));
				}
				cursor.close();
				BaseAdapter adapter = new BaseAdapter()
				{
					@Override
					public int getCount()
					{
						return numberList.size();
					}

					@Override
					public Object getItem(int position)
					{
						return position;
					}

					@Override
					public long getItemId(int position)
					{
						return position;
					}

					@Override
					public View getView(int position, View convertView, ViewGroup parent)
					{
						CheckBox rb;
						if(convertView == null)
						{
							rb = new CheckBox(MainActivity.this);
						} else {
							rb = (CheckBox) convertView;
						}
						// 获取联系人的电话号码
						String number = numberList.get(position);
						rb.setText(number);
						// 如果该号码已经被加入发送人名单，默认勾选该号码
						if (sendList.contains(number)) {
							rb.setChecked(true);
						}
						return rb;
					}
				};
				// 加载list.xml布局文件对应的View
				View selectView = getLayoutInflater().inflate(R.layout.list, null);
				// 获取selectView中名为list的ListView组件
				ListView listView = selectView.findViewById(R.id.list);
				listView.setAdapter(adapter);
				new AlertDialog.Builder(MainActivity.this).setView(selectView)
					.setPositiveButton("确定", (v, which) ->{
						// 清空sendList集合
						sendList.clear();
						// 遍历listView组件的每个列表项
						for (int i = 0; i < listView.getCount(); i++)
						{
							CheckBox checkBox = (CheckBox) listView.getChildAt(i);
							// 如果该列表项被勾选
							if (checkBox.isChecked())
							{
								// 添加该列表项的电话号码
								sendList.add(checkBox.getText().toString());
							}
						}
						numbersTv.setText(sendList.toString());

					}).show();
			});
		} else {
			Toast.makeText(this, R.string.permission_tip,
					Toast.LENGTH_SHORT).show();
		}
	}
}
