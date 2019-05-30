package org.crazyit.resolver;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.crazyit.content.Words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		Button insertBn = findViewById(R.id.insert);
		Button searchBn = findViewById(R.id.search);
		EditText wordEt = findViewById(R.id.word);
		EditText detailEt = findViewById(R.id.detail);
		EditText keyEt = findViewById(R.id.key);
		// 为insertBn按钮的单击事件绑定事件监听器
		insertBn.setOnClickListener(view -> {
			// 获取用户输入
			String word = wordEt.getText().toString();
			String detail = detailEt.getText().toString();
			// 插入单词记录
			ContentValues values = new ContentValues();
			values.put(Words.Word.WORD, word);
			values.put(Words.Word.DETAIL, detail);
			getContentResolver().insert(Words.Word.DICT_CONTENT_URI, values);
			// 显示提示信息
			Toast.makeText(MainActivity.this, "添加单词成功！",
					Toast.LENGTH_SHORT).show();
		});
		// 为searchBn按钮的单击事件绑定事件监听器
		searchBn.setOnClickListener(view ->{
			// 获取用户输入
			String key = keyEt.getText().toString();
			// 执行查询
			Cursor cursor = getContentResolver().query(
					Words.Word.DICT_CONTENT_URI, null,
					"word like ? or detail like ?", new String[] {
							"%" + key + "%", "%" + key + "%" }, null);
			// 创建一个Bundle对象
			Bundle data = new Bundle();
			data.putSerializable("data", converCursorToList(cursor));
			// 创建一个Intent
			Intent intent = new Intent(MainActivity.this, ResultActivity.class);
			intent.putExtras(data);
			// 启动Activity
			startActivity(intent);
		});

	}
	private ArrayList<Map<String, String>> converCursorToList(Cursor cursor)
	{
		ArrayList<Map<String, String>> result = new ArrayList<>();
		// 遍历Cursor结果集
		while (cursor.moveToNext())
		{
			// 将结果集中的数据存入ArrayList中
			Map<String, String> map = new HashMap<>();
			// 取出查询记录中第2列、第3列的值
			map.put("word", cursor.getString(1));
			map.put("detail", cursor.getString(2));
			result.add(map);
		}
		return result;
	}
}
