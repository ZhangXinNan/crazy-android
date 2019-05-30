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
public class ViewBidFragment extends Fragment
{
	private JSONArray jsonArray;
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.view_bid, container, false);
		// 定义并获取界面上的返回按钮
		Button bnHome = rootView.findViewById(R.id.bn_home);
		RecyclerView bidList = rootView.findViewById(R.id.bidList);
		bidList.setHasFixedSize(true);
		// 为RecyclerView设置布局管理器
		bidList.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "bids/byUser";
		String result = null;
		try
		{
			// 向指定URL发送请求，并把服务器响应包装成JSONArray对象
			result = HttpUtil.getRequest(url);
			jsonArray = new JSONArray(result);
			// 将JSONArray包装成Adapter
			JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "item"
				, this::viewBidDetail /* 查看竞价详情 */);
			bidList.setAdapter(adapter);
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

	private void viewBidDetail(int position)
	{
		// 加载bid_detail.xml界面布局代表的视图
		View detailView = getActivity().getLayoutInflater()
				.inflate(R.layout.bid_detail, null);
		// 获取bid_detail界面中的文本框
		TextView itemName = detailView.findViewById(R.id.itemName);
		TextView bidPrice = detailView.findViewById(R.id.bidPrice);
		TextView bidTime = detailView.findViewById(R.id.bidTime);
		TextView bidUser = detailView.findViewById(R.id.bidUser);
		// 获取被单击项目所包装的JSONObject
		JSONObject jsonObj = jsonArray.optJSONObject(position);
		// 使用文本框来显示竞价详情。
		try
		{
			itemName.setText(jsonObj.getString("item"));
			bidPrice.setText(jsonObj.getString("price"));
			bidTime.setText(jsonObj.getString("bidDate"));
			bidUser.setText(jsonObj.getString("user"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		DialogUtil.showDialog(getActivity(), detailView);
	}
}
