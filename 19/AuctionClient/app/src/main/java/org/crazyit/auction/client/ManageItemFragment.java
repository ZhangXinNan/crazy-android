package org.crazyit.auction.client;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class ManageItemFragment extends Fragment
{
	public static final  int ADD_ITEM = 0x1006;
	private JSONArray jsonArray;
	private Callbacks mCallbacks;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.manage_item, container, false);
		//  定义并获取界面上两个按钮
		Button bnHome = rootView.findViewById(R.id.bn_home);
		Button bnAdd = rootView.findViewById(R.id.bnAdd);
		RecyclerView itemList = rootView.findViewById(R.id.itemList);
		itemList.setHasFixedSize(true);
		// 为RecyclerView设置布局管理器
		itemList.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		// 为添加按钮绑定事件监听器
		bnAdd.setOnClickListener(view -> mCallbacks.onItemSelected(ADD_ITEM, null));
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "items/byOwner";
		String result = null;
		try
		{
			// 向指定URL发送请求
			result = HttpUtil.getRequest(url);
			jsonArray = new JSONArray(result);
			// 将服务器响应包装成Adapter
			JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "name",
					this::viewItemInBid);  // ①
			itemList.setAdapter(adapter);
		}
		catch(JSONException e)
		{
			if (result != null && !result.isEmpty())
			{
				DialogUtil.showDialog(getActivity(), result, true);
			}
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(getActivity(), "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		return rootView;
	}

	// 当该Fragment被添加、显示到Context时，回调该方法
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		// 如果Activity没有实现Callbacks接口，抛出异常
		if (!(context instanceof Callbacks))
		{
			throw new IllegalStateException("ManagerItemFragment所在的Context必须实现Callbacks接口!");
		}
		// 把该Context当成Callbacks对象
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

	private void viewItemInBid(int position)
	{
		// 加载detail_in_bid.xml界面布局代表的视图
		View detailView = getActivity().getLayoutInflater()
				.inflate(R.layout.detail_in_bid, null);
		// 获取detail_in_bid.xml界面中的文本框
		TextView itemName = detailView.findViewById(R.id.itemName);
		TextView itemKind = detailView.findViewById(R.id.itemKind);
		TextView maxPrice = detailView.findViewById(R.id.maxPrice);
		TextView initPrice = detailView.findViewById(R.id.initPrice);
		TextView endTime = detailView.findViewById(R.id.endTime);
		TextView itemRemark = detailView.findViewById(R.id.itemRemark);
		// 获取被单击列表项所包装的JSONObject
		JSONObject jsonObj = jsonArray.optJSONObject(position);
		// 通过文本框显示物品详情
		try
		{
			itemName.setText(jsonObj.getString("name"));
			itemKind.setText(jsonObj.getString("kind"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			itemRemark.setText(jsonObj.getString("desc"));
			initPrice.setText(jsonObj.getString("initPrice"));
			endTime.setText(jsonObj.getString("endTime"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		DialogUtil.showDialog(getActivity(), detailView);
	}
}
