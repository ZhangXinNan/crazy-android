package org.crazyit.io;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取只能被本应用程序读写的SharedPreferences对象
		preferences = getSharedPreferences("crazyit", Context.MODE_PRIVATE);
		editor = preferences.edit();
		Button read = findViewById(R.id.read);
		Button write = findViewById(R.id.write);
		read.setOnClickListener(view -> {
			// 读取字符串数据
			String time = preferences.getString("time", null);
			// 读取int类型的数据
			int randNum = preferences.getInt("random", 0);
			String result = time == null ? "您暂时还未写入数据" : "写入时间为：" +
						time + "\n上次生成的随机数为：" + randNum;
			// 使用Toast提示信息
			Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
		});
		write.setOnClickListener(view -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 " + "hh:mm:ss");
			// 存入当前时间
			editor.putString("time", sdf.format(new Date()));
			// 存入一个随机数
			editor.putInt("random", (int) (Math.random() * 100));
			// 提交所有存入的数据
			editor.apply();
		});
	}
}
