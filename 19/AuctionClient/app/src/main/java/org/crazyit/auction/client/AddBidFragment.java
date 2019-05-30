package org.crazyit.auction.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
public class AddBidFragment extends Fragment
{
	// 获取界面中编辑框
	private EditText bidPrice;
	// 定义当前正在拍卖的物品
	private JSONObject jsonObj;
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.add_bid, container, false);
		// 定义界面中文本框
		TextView itemName = rootView.findViewById(R.id.itemName);
		TextView itemDesc = rootView.findViewById(R.id.itemDesc);
		TextView itemRemark = rootView.findViewById(R.id.itemRemark);
		TextView itemKind = rootView.findViewById(R.id.itemKind);
		TextView initPrice = rootView.findViewById(R.id.initPrice);
		TextView maxPrice = rootView.findViewById(R.id.maxPrice);
		TextView endTime = rootView.findViewById(R.id.endTime);
		bidPrice = rootView.findViewById(R.id.bidPrice);
		// 定义并获取界面中的两个按钮
		Button bnAdd = rootView.findViewById(R.id.bnAdd);
		Button bnCancel = rootView.findViewById(R.id.bnCancel);
		// 为取消按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(getActivity()));
		assert getArguments() != null;
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "item/"
				+ getArguments().getInt("itemId");
		try
		{
			// 获取指定的拍卖物品
			jsonObj = new JSONObject(HttpUtil.getRequest(url));
			// 使用文本框来显示拍卖物品的详情
			itemName.setText(jsonObj.getString("name"));
			itemDesc.setText(jsonObj.getString("desc"));
			itemRemark.setText(jsonObj.getString("remark"));
			itemKind.setText(jsonObj.getString("kind"));
			initPrice.setText(jsonObj.getString("initPrice"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			endTime.setText(jsonObj.getString("endTime"));
		}
		catch (Exception e1)
		{
			DialogUtil.showDialog(getActivity(), "服务器响应出现异常！", false);
			e1.printStackTrace();
		}
		bnAdd.setOnClickListener(view -> {
			try
			{
				// 执行类型转换
				double curPrice = Double.parseDouble(bidPrice.getText().toString());
				// 执行输入校验
				if (curPrice < jsonObj.getDouble("maxPrice")) // ①
				{
					DialogUtil.showDialog(getActivity(),
							"您输入的竞价必须高于当前竞价", false);
				}
				else
				{
					// 添加竞价
					String result = addBid(jsonObj.getString("id"), curPrice + "");  // ②
					// 显示对话框
					DialogUtil.showDialog(getActivity(), result, true);
				}
			}
			catch (NumberFormatException ne)
			{
				DialogUtil.showDialog(getActivity(), "您输入的竞价必须是数值", false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				DialogUtil.showDialog(getActivity(), "服务器响应出现异常，请重试！", false);
			}
		});
		return rootView;
	}

	private String addBid(String itemId, String bidPrice) throws Exception
	{
		// 使用Map封装请求参数
		Map<String , String> map = new HashMap<>();
		map.put("itemId", itemId);
		map.put("bidPrice", bidPrice);
		// 定义请求将会发送到bids
		String url = HttpUtil.BASE_URL + "bids";
		// 发送请求
		return HttpUtil.postRequest(url, map);
	}
}
