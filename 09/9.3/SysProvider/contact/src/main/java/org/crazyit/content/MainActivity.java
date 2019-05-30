package org.crazyit.content;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取系统界面中查找、添加两个按钮
		Button searchBn = findViewById(R.id.search);
		Button addBn = findViewById(R.id.add);
		searchBn.setOnClickListener(view ->
			// 请求读取联系人信息的权限
			requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 0x123));
		// 为addBn按钮的单击事件绑定监听器
		addBn.setOnClickListener(view ->
		// 请求写入联系人信息的权限
			requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 0x456));
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if(grantResults[0] == 0)
		{
			if(requestCode == 0x123)
			{
				// 定义两个List来封装系统的联系人信息、指定联系人的电话号码、Email等详情
				List<String> names = new ArrayList<>();
				List<List<String>> details = new ArrayList<>();
				// 使用ContentResolver查找联系人数据
				Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
						null, null, null, null);
				// 遍历查询结果，获取系统中所有联系人
				while (cursor.moveToNext())
				{
					// 获取联系人ID
					String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
					// 获取联系人的名字
					String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					names.add(name);
					// 使用ContentResolver查找联系人的电话号码
					Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
							null, null);
					List<String> detail = new ArrayList<>();
					// 遍历查询结果，获取该联系人的多个电话号码
					while (phones.moveToNext())
					{
						// 获取查询结果中电话号码列中数据
						String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						detail.add("电话号码：" + phoneNumber);
					}
					phones.close();
					// 使用ContentResolver查找联系人的E-mail地址
					Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
					// 遍历查询结果，获取该联系人的多个E-mail地址
					while (emails.moveToNext())
					{
						// 获取查询结果中E-mail地址列中数据
						String emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						detail.add("邮件地址：" + emailAddress);
					}
					emails.close();
					details.add(detail);
				}
				cursor.close();
				// 加载result.xml界面布局代表的视图
				View resultDialog = getLayoutInflater().inflate(R.layout.result, null);
				// 获取resultDialog中ID为list的ExpandableListView
				ExpandableListView list = resultDialog.findViewById(R.id.list);
				// 创建一个ExpandableListAdapter对象
				ExpandableListAdapter adapter = new BaseExpandableListAdapter()
				{
					@Override
					public int getGroupCount()
					{
						return names.size();
					}

					@Override
					public int getChildrenCount(int groupPosition)
					{
						return details.get(groupPosition).size();
					}

					// 获取指定组位置处的组数据
					@Override
					public Object getGroup(int groupPosition)
					{
						return names.get(groupPosition);
					}

					// 获取指定组位置、指定子列表项处的子列表项数据
					@Override
					public Object getChild(int groupPosition, int childPosition)
					{
						return details.get(groupPosition).get(childPosition);
					}

					@Override
					public long getGroupId(int groupPosition)
					{
						return groupPosition;
					}

					@Override
					public long getChildId(int groupPosition, int childPosition)
					{
						return childPosition;
					}

					@Override
					public boolean hasStableIds()
					{
						return true;
					}

					// 该方法决定每个组选项的外观
					@Override
					public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
					{
						TextView textView;
						if (convertView == null)
						{
							textView = createTextView();
						}
						else
						{
							textView = (TextView) convertView;
						}
						textView.setTextSize(18f);
						textView.setPadding(90, 10, 0, 10);
						textView.setText(getGroup(groupPosition).toString());
						return textView;
					}

					// 该方法决定每个子选项的外观
					@Override
					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
					{
						TextView textView;
						if (convertView == null)
						{
							textView = createTextView();
						}
						else
						{
							textView = (TextView) convertView;
						}
						textView.setText(getChild(groupPosition, childPosition).toString());
						return textView;
					}

					@Override
					public boolean isChildSelectable(int groupPosition, int childPosition)
					{
						return true;
					}

					private TextView createTextView()
					{
						AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
								ViewGroup.LayoutParams.MATCH_PARENT,
								ViewGroup.LayoutParams.WRAP_CONTENT);
						TextView textView = new TextView(MainActivity.this);
						textView.setLayoutParams(lp);
						textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.START);
						textView.setPadding(40, 5, 0, 5);
						textView.setTextSize(15f);
						return textView;
					}
				};
				// 为ExpandableListView设置Adapter对象
				list.setAdapter(adapter);
				// 使用对话框来显示查询结果
				new AlertDialog.Builder(MainActivity.this)
						.setView(resultDialog)
						.setPositiveButton("确定", null).show();
			}
			if(requestCode == 0x456)
			{
				// 获取程序界面中的三个文本框的内容
				String name = ((EditText)findViewById(R.id.name)).getText().toString().trim();
				String phone = ((EditText)findViewById(R.id.phone)).getText().toString().trim();
				String email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
				if (name.equals(""))
				{
					return;
				}
				// 创建一个空的ContentValues
				ContentValues values = new ContentValues();
				// 向RawContacts.CONTENT_URI执行一个空值插入
				// 目的是获取系统返回的rawContactId
				Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
				long rawContactId = ContentUris.parseId(rawContactUri);
				values.clear();
				values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
				// 设置内容类型
				values.put(ContactsContract.RawContacts.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
				// 设置联系人名字
				values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
				// 向联系人URI添加联系人名字
				getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
				values.clear();
				values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
				values.put(ContactsContract.RawContacts.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
				// 设置联系人的电话号码
				values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
				// 设置电话类型
				values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
						ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
				// 向联系人电话号码URI添加电话号码
				getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
				values.clear();
				values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
				values.put(ContactsContract.RawContacts.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
				// 设置联系人的E-mail地址
				values.put(ContactsContract.CommonDataKinds.Email.DATA, email);
				// 设置该电子邮件的类型
				values.put(ContactsContract.CommonDataKinds.Email.TYPE,
						ContactsContract.CommonDataKinds.Email.TYPE_WORK);
				// 向联系人E-mail URI添加E-mail数据
				getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
				Toast.makeText(MainActivity.this, "联系人数据添加成功", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(this, R.string.permission_tip, Toast.LENGTH_SHORT)
					.show();
		}
	}
}
