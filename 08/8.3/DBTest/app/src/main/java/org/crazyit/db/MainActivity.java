package org.crazyit.db;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
	private RecyclerView recyclerView;
	private MyDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		recyclerView = findViewById(R.id.recycler);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 设置RecyclerView的滚动方向
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// 为RecyclerView设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		Button bn = findViewById(R.id.ok);
		// 创建或打开数据库（此处需要使用绝对路径）
		dbHelper = new MyDatabaseHelper(this ,
				this.getFilesDir().toString() + "/my.db3" , 1); // ①
		loadData();
		EditText titleEt = findViewById(R.id.title);
		EditText contentEt = findViewById(R.id.content);
		bn.setOnClickListener(view -> {
			// 获取用户输入
			String title = titleEt.getText().toString().trim();
			String content = contentEt.getText().toString().trim();
			insertData(title, content);
			loadData();
		});
	}
	private void loadData()
	{
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
				"select * from news_inf", null);
		inflateRecycler(cursor);
	}
	private void insertData(String title, String content)  // ②
	{
		// 执行插入语句
		dbHelper.getReadableDatabase().execSQL("insert into news_inf values(null , ? , ?)"
				, new String[]{title, content});
	}
	private void inflateRecycler(Cursor cursor)
	{
		try {
			// 用CursorRecyclerViewAdapter封装Cursor中的数据
			CursorRecyclerViewAdapter<LineViewHolder> adapter = new CursorRecyclerViewAdapter<>(this,
					cursor, R.layout.line, new int[]{1, 2}, new String[]{"titleView", "contentView"},
					LineViewHolder.class.getConstructor(MainActivity.class, View.class));  // ③
			// 显示数据
			recyclerView.setAdapter(adapter);
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}

	}
	@Override public void onDestroy()
	{
		super.onDestroy();
		// 退出程序时关闭MyDatabaseHelper里的SQLiteDatabase
		dbHelper.close();
	}

	public class LineViewHolder extends RecyclerView.ViewHolder
	{
		TextView titleView;
		TextView contentView;
		public LineViewHolder(View itemView)
		{
			super(itemView);
			titleView = itemView.findViewById(R.id.my_title);
			contentView = itemView.findViewById(R.id.my_content);
		}
	}
}
