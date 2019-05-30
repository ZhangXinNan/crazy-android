package org.crazyit.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

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
public class BookDetailActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 指定加载/res/layout目录下的activity_book_detail.xml布局文件
		// 该界面布局文件内只定义了一个名为book_detail_container的FrameLayout
		setContentView(R.layout.activity_book_detail);
		// 将ActionBar上的应用图标转换成可点击的按钮
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null)
		{
			// 创建BookDetailFragment对象
			BookDetailFragment fragment = new BookDetailFragment();
			// 创建Bundle对象
			Bundle arguments = new Bundle();
			arguments.putInt(BookDetailFragment.ITEM_ID,
					getIntent().getIntExtra(BookDetailFragment.ITEM_ID, 0));
			// 向Fragment传入参数
			fragment.setArguments(arguments);
			// 将指定fragment添加到book_detail_container容器中
			getSupportFragmentManager().beginTransaction()
					.add(R.id.book_detail_container, fragment).commit();
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			// 创建启动MainActivity的Intent
			Intent intent = new Intent(this, MainActivity.class);
			// 添加额外的Flag，将Activity栈中处于MainActivity之上的Activity弹出
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// 启动intent对应的Activity
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
