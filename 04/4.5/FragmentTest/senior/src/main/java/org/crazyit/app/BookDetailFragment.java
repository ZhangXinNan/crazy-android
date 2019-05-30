package org.crazyit.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.crazyit.app.model.BookManager;

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
public class BookDetailFragment extends Fragment
{
	public static final  String ITEM_ID = "item_id";
	// 保存该Fragment显示的Book对象
	private BookManager.Book book;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 如果启动该Fragment时包含了ITEM_ID参数
		if (getArguments().containsKey(ITEM_ID))
		{
			book = BookManager.ITEM_MAP.get(getArguments().getInt(ITEM_ID)); // ①
		}
	}
	// 重写该方法，该方法返回的View将作为Fragment显示的组件
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		// 加载/res/layout/目录下的fragment_book_detail.xml布局文件
		View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);
		if (book != null)
		{
			// 让book_title文本框显示book对象的title属性
			((TextView)rootView.findViewById(R.id.book_title)).setText(book.title);
			// 让book_desc文本框显示book对象的desc属性
			((TextView)rootView.findViewById(R.id.book_desc)).setText(book.desc);
		}
		return rootView;
	}
}
