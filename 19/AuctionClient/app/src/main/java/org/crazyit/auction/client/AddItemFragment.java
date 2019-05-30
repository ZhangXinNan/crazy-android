package org.crazyit.auction.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
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
public class AddItemFragment extends Fragment
{
	// 定义界面中文本框
	private EditText itemName;
	private EditText itemDesc;
	private EditText itemRemark;
	private EditText initPrice;
	private Spinner itemKind;
	private Spinner availTime;
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.add_item, container, false);
		// 获取界面中的文本框
		itemName = rootView.findViewById(R.id.itemName);
		itemDesc = rootView.findViewById(R.id.itemDesc);
		itemRemark = rootView.findViewById(R.id.itemRemark);
		initPrice = rootView.findViewById(R.id.initPrice);
		itemKind = rootView.findViewById(R.id.itemKind);
		availTime = rootView.findViewById(R.id.availTime);
		// 定义发送请求的地址
		String url = HttpUtil.BASE_URL + "kinds";
		JSONArray jsonArray = null;
		try
		{
			// 获取系统中所有的物品种类
			// 向执行URL发送请求，并把服务器响应包装成JSONArray
			jsonArray = new JSONArray(HttpUtil.getRequest(url));  // ①
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		// 将JSONArray包装成Adapter
		KindSpinnerAdapter adapter = new KindSpinnerAdapter(getActivity(), jsonArray, "kindName");
		// 显示物品种类列表
		itemKind.setAdapter(adapter);
		// 定义、并获取界面中两个按钮
		Button bnAdd = rootView.findViewById(R.id.bnAdd);
		Button bnCancel = rootView.findViewById(R.id.bnCancel);
		// 为取消按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(getActivity()));
		bnAdd.setOnClickListener(view -> {
			// 执行输入校验
			if (validate())
			{
				// 获取用户输入的物品名、物品描述等信息
				String name = itemName.getText().toString();
				String desc = itemDesc.getText().toString();
				String remark = itemRemark.getText().toString();
				String price = initPrice.getText().toString();
				JSONObject kind = (JSONObject)itemKind.getSelectedItem();
				int avail = availTime.getSelectedItemPosition();
				//根据用户选择有效时间选项，指定实际的有效时间
				switch (avail)
				{
					case 5: avail = 7; break;
					case 6: avail = 30; break;
					default: avail += 1; break;
				}
				try
				{
					// 添加物品
					String result = addItem(name, desc, remark, price, kind.getInt("id"), avail);
					// 显示对话框
					DialogUtil.showDialog(getActivity(), result, true);
				}
				catch (Exception e)
				{
					DialogUtil.showDialog(getActivity(), "服务器响应异常，请稍后再试！", false);
					e.printStackTrace();
				}
			}
		});
		return rootView;
	}

	// 对用户输入的物品名、起拍价格进行校验
	private boolean validate()
	{
		String name = itemName.getText().toString().trim();
		if (name.equals(""))
		{
			DialogUtil.showDialog(getActivity(), "物品名称是必填项！", false);
			return false;
		}
		String price = initPrice.getText().toString().trim();
		if (price.equals(""))
		{
			DialogUtil.showDialog(getActivity(), "起拍价格是必填项！", false);
			return false;
		}
		try
		{
			// 尝试把起拍价格转换为浮点数
			java.lang.Double.parseDouble(price);
		}
		catch (NumberFormatException e)
		{
			DialogUtil.showDialog(getActivity(), "起拍价格必须是数值！", false);
			return false;
		}
		return true;
	}

	private String addItem(String name, String desc, String remark,
						String initPrice, int kindId, int availTime) throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<>();
		map.put("itemName", name);
		map.put("itemDesc", desc);
		map.put("itemRemark", remark);
		map.put("initPrice", initPrice);
		map.put("kindId", kindId + "");
		map.put("avail" , availTime + "");
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "items";
		// 发送请求
		return HttpUtil.postRequest(url, map);
	}
}
