package org.crazyit.auction.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;

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
public class AddKindFragment extends Fragment
{
	// 定义界面中两个文本框
	private EditText kindName;
	private EditText kindDesc;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.add_kind, container, false);
		// 获取界面中两个编辑框
		kindName = rootView.findViewById(R.id.kindName);
		kindDesc = rootView.findViewById(R.id.kindDesc);
		// 定义并获取界面中两个按钮
		Button bnAdd = rootView.findViewById(R.id.bnAdd);
		Button bnCancel = rootView.findViewById(R.id.bnCancel);
		// 为取消按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(getActivity()));
		bnAdd.setOnClickListener(view -> {
			// 输入校验
			if (validate())
			{
				// 获取用户输入的种类名、种类描述
				String name = kindName.getText().toString();
				String desc = kindDesc.getText().toString();
				try
				{
					// 添加物品种类
					String result = addKind(name, desc);
					// 使用对话框来显示添加结果
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

	// 对用户输入的种类名称进行校验
	private boolean validate()
	{
		String name = kindName.getText().toString().trim();
		if (name.equals(""))
		{
			DialogUtil.showDialog(getActivity(), "种类名称是必填项！", false);
			return false;
		}
		return true;
	}

	private String addKind(String name, String desc) throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<>();
		map.put("kindName", name);
		map.put("kindDesc", desc);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "kinds";
		// 发送请求
		return HttpUtil.postRequest(url, map);
	}
}
