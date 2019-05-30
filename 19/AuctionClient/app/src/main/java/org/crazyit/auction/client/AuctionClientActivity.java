package org.crazyit.auction.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

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
public class AuctionClientActivity extends FragmentActivity
		implements Callbacks
{
	// 定义一个旗标，用于标识该应用是否支持大屏幕
	private boolean mTwoPane;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 指定加载R.layout.activity_main对应的界面布局文件
		// 但实际上该应用会根据屏幕分辨率家在不同的界面布局文件
		setContentView(R.layout.activity_main);
		// 如果加载的界面布局文件中包含ID为auction_detail_container的组件
		if (findViewById(R.id.auction_detail_container) != null)
		{
			mTwoPane = true;
			AuctionListFragment listFragment = (AuctionListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.auction_list);
			if (listFragment != null)
			{
				listFragment.setActivateOnItemClick(true);
			}
		}
	}

	@Override
	public void onItemSelected(Integer id, Bundle bundle)
	{
		if (mTwoPane)
		{
			Fragment fragment = null;
			switch (id)
			{
				// 查看竞得物品
				case 0:
					// 创建ViewItemFragment
					fragment = new ViewItemFragment();
					// 创建Bundle，准备向Fragment传入参数
					Bundle arguments = new Bundle();
					arguments.putString("action", "items/byWiner");
					// 向Fragment传入参数
					fragment.setArguments(arguments);
					break;
				// 浏览流拍物品
				case 1:
					// 创建ViewItemFragment
					fragment = new ViewItemFragment();
					// 创建Bundle，准备向Fragment传入参数
					Bundle arguments2 = new Bundle();
					arguments2.putString("action", "items/fail");
					// 向Fragment传入参数
					fragment.setArguments(arguments2);
					break;
				// 管理物品种类
				case 2:
					// 创建ManageKindFragment
					fragment = new ManageKindFragment();
					break;
				// 管理物品
				case 3:
					// 创建ManageItemFragment
					fragment = new ManageItemFragment();
					break;
				// 浏览拍卖物品（选择物品种类）
				case 4:
					// 创建ChooseKindFragment
					fragment = new ChooseKindFragment();
					break;
				// 查看自己的竞标
				case 5:
					// 创建ViewBidFragment
					fragment = new ViewBidFragment();
					break;
				case ManageItemFragment.ADD_ITEM:
					fragment = new AddItemFragment();
					break;
				case ManageKindFragment.ADD_KIND:
					fragment = new AddKindFragment();
					break;
				case ChooseKindFragment.CHOOSE_ITEM:
					fragment = new ChooseItemFragment();
					Bundle args = new Bundle();
					args.putLong("kindId", bundle.getLong("kindId"));
					fragment.setArguments(args);
					break;
				case ChooseItemFragment.ADD_BID:
					fragment = new AddBidFragment();
					Bundle args2 = new Bundle();
					args2.putInt("itemId", bundle.getInt("itemId"));
					fragment.setArguments(args2);
					break;
			}
			// 使用fragment替换auction_detail_container容器当前显示的Fragment
			assert fragment != null;
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.auction_detail_container, fragment)
					.addToBackStack(null).commit();
		}
		else
		{
			Intent intent;
			switch (id)
			{
				// 查看竞得物品
				case 0:
					// 启动ViewItemActivity
					intent = new Intent(this, ViewItemActivity.class);
					// action属性为请求的URL。
					intent.putExtra("action", "items/byWiner");
					startActivity(intent);
					break;
				// 浏览流拍物品
				case 1:
					// 启动ViewItemActivity
					intent = new Intent(this, ViewItemActivity.class);
					// action属性为请求的URL。
					intent.putExtra("action", "items/fail");
					startActivity(intent);
					break;
				// 管理物品种类
				case 2:
					// 启动ManageKindActivity
					intent = new Intent(this, ManageKindActivity.class);
					startActivity(intent);
					break;
				// 管理物品
				case 3:
					// 启动ManageItemActivity
					intent = new Intent(this, ManageItemActivity.class);
					startActivity(intent);
					break;
				// 浏览拍卖物品（选择物品种类）
				case 4:
					// 启动ChooseKindActivity
					intent = new Intent(this, ChooseKindActivity.class);
					startActivity(intent);
					break;
				// 查看自己的竞标
				case 5:
					// 启动ViewBidActivity
					intent = new Intent(this, ViewBidActivity.class);
					startActivity(intent);
					break;
			}
		}
	}
}
