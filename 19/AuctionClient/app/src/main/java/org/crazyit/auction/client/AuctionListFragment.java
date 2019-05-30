package org.crazyit.auction.client;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class AuctionListFragment extends Fragment
{
	private ListView auctionList;
	private Callbacks mCallbacks;
	// 重写该方法，该方法返回的View将作为Fragment显示的组件
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// 加载/res/layout/目录下的auction_list.xml布局文件
		View rootView = inflater.inflate(R.layout.auction_list,
				container, false);
		auctionList = rootView.findViewById(R.id.auction_list);
		// 为ListView的列表项的单击事件绑定事件监听器
		auctionList.setOnItemClickListener((parent, view, position, id) ->
				mCallbacks.onItemSelected(position, null));
		return rootView;
	}

	// 当该Fragment被添加、显示到Context时，回调该方法
	@Override
	public  void onAttach(Context context)
	{
		super.onAttach(context);
		// 如果Activity没有实现Callbacks接口，抛出异常
		if (!(context instanceof Callbacks))
		{
			throw new IllegalStateException("AuctionListFragment所在的Context必须实现Callbacks接口!");
		}
		// 把该Activity当成Callbacks对象
		mCallbacks = (Callbacks) context;
	}

	// 当该Fragment从它所属的Activity中被删除时回调该方法
	@Override
	public void onDetach()
	{
		super.onDetach();
		// 将mCallbacks赋为null。
		mCallbacks = null;
	}

	public void setActivateOnItemClick(boolean activateOnItemClick)
	{
		auctionList.setChoiceMode(activateOnItemClick
				? ListView.CHOICE_MODE_SINGLE
				: ListView.CHOICE_MODE_NONE);
	}
}
