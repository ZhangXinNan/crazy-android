package org.crazyit.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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
public class MainActivity extends FragmentActivity
		implements  BookListFragment.Callbacks
{
	// 定义一个旗标，用于标识该应用是否支持大屏幕
	private boolean mTwoPane;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 指定加载R.layout.activity_book_list对应的界面布局文件
		// 但实际上该应用会根据屏幕分辨率加载不同的界面布局文件
		setContentView(R.layout.activity_book_list);
		// 如果加载的界面布局文件中包含ID为book_detail_container的组件
		if (findViewById(R.id.book_detail_container) != null)
		{
			mTwoPane = true;
			// 将显示列表的ListFragment的选择模式设置单选模式
			((BookListFragment)getSupportFragmentManager()
					.findFragmentById(R.id.book_list)).setActivateOnItemClick(true);
		}
	}

	// 实现Callbacks接口必须实现的方法
	@Override
	public void onItemSelected(int id)
	{
		if (mTwoPane) {
			// 创建Bundle，准备向Fragment传入参数
			Bundle arguments = new Bundle();
			arguments.putInt(BookDetailFragment.ITEM_ID, id);
			// 创建BookDetailFragment对象
			BookDetailFragment fragment = new BookDetailFragment();
			// 向Fragment传入参数
			fragment.setArguments(arguments);
			// 使用fragment替换book_detail_container容器当前显示的Fragment
			getSupportFragmentManager().beginTransaction().replace(
					R.id.book_detail_container, fragment).commit();
		}
		else
		{
			// 创建启动BookDetailActivity的Intent
			Intent detailIntent = new Intent(this, BookDetailActivity.class);
			// 设置传给BookDetailActivity的参数
			detailIntent.putExtra(BookDetailFragment.ITEM_ID, id);
			// 启动Activity
			startActivity(detailIntent);
		}
	}
}
