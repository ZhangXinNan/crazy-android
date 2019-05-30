package org.crazyit.auction.client;

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
public class ViewItemFragment extends Fragment
{
	private JSONArray jsonArray;
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.view_item, container, false);
		// 获取界面上的返回按钮
		Button bnHome = rootView.findViewById(R.id.bn_home);
		RecyclerView succList = rootView.findViewById(R.id.succList);
		succList.setHasFixedSize(true);
		// 为RecyclerView设置布局管理器
		succList.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		TextView viewTitle = rootView.findViewById(R.id.view_titile);
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		assert getArguments() != null;
		String action = getArguments().getString("action");
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + action;
		// 如果是查看流拍物品，修改标题
		if (action != null && action.equals("items/fail"))
		{
			viewTitle.setText(R.string.view_fail);
		}
		String result = null;
		try
		{
			// 向指定URL发送请求，并把服务器响应转换成JSONArray对象
			result = HttpUtil.getRequest(url); // ①
			jsonArray = new JSONArray(result);
			// 将JSONArray包装成Adapter
			JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "name",
					this::viewItemDetail /* 查看指定物品的详细情况 */);  // ②
			succList.setAdapter(adapter);
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

	private void viewItemDetail(int position)
	{
		// 加载detail.xml界面布局代表的视图
		View detailView = getActivity().getLayoutInflater()
				.inflate(R.layout.detail, null);
		// 获取detail.xml界面布局中的文本框
		TextView itemName = detailView.findViewById(R.id.itemName);
		TextView itemKind = detailView.findViewById(R.id.itemKind);
		TextView maxPrice = detailView.findViewById(R.id.maxPrice);
		TextView itemRemark = detailView.findViewById(R.id.itemRemark);
		// 获取被单击的列表项
		JSONObject jsonObj = jsonArray.optJSONObject(position);
		// 通过文本框显示物品详情
		try
		{
			itemName.setText(jsonObj.getString("name"));
			itemKind.setText(jsonObj.getString("kind"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			itemRemark.setText(jsonObj.getString("desc"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		DialogUtil.showDialog(getActivity(), detailView);
	}
}
