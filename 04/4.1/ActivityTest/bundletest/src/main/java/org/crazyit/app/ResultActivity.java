package org.crazyit.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.crazyit.domain.Person;

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
public class ResultActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		TextView name = findViewById(R.id.name);
		TextView passwd = findViewById(R.id.passwd);
		TextView gender = findViewById(R.id.gender);
		// 获取启动该Activity的Intent
		Intent intent = getIntent();
		// 直接通过Intent取出它所携带的Bundle数据包中的数据
		Person p = (Person) intent.getSerializableExtra("person");
		name.setText("您的用户名为：" + p.getName());
		passwd.setText("您的密码为：" + p.getPasswd());
		gender.setText("您的性别为：" + p.getGender());
	}
}
