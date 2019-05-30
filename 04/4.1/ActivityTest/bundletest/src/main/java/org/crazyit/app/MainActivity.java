package org.crazyit.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

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
public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EditText name = findViewById(R.id.name);
		EditText passwd = findViewById(R.id.passwd);
		RadioButton male = findViewById(R.id.male);
		Button bn = findViewById(R.id.bn);
		bn.setOnClickListener( view -> {
			String gender = male.isChecked() ? "男 " : "女";
			Person p = new Person(name.getText().toString(), passwd.getText().toString(), gender);
			// 创建一个Bundle对象
			Bundle data = new Bundle();
			data.putSerializable("person", p);
			// 创建一个Intent
			Intent intent = new Intent(MainActivity.this, ResultActivity.class);
			intent.putExtras(data);
			// 启动intent对应的Activity
			startActivity(intent);
		});
	}
}
