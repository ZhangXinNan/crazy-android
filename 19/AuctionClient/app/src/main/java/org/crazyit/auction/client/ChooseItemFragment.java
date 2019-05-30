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
public class ChooseItemFragment extends Fragment
{
	public static final int ADD_BID = 0x1009;
	private RecyclerView succList;
	private Callbacks mCallbacks;
	// 重写该方法，该方法返回的View将作为Fragment显示的组件
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.view_item, container, false);
		// 定义并获取界面中的返回按钮
		Button bnHome = rootView.findViewById(R.id.bn_home);
		succList = rootView.findViewById(R.id.succList);
		succList.setHasFixedSize(true);
		// 为RecyclerView设置布局管理器
		succList.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		// 定义并获取界面上的文本框
		TextView viewTitle = rootView.findViewById(R.id.view_titile);
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		assert getArguments() != null;
		long kindId = getArguments().getLong("kindId");
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "items/" + kindId;
		String result = null;
		try
		{
			result = HttpUtil.getRequest(url);
			// 根据种类ID获取该种类对应的所有物品
			JSONArray jsonArray = new JSONArray(result);
			ItemClickedCallback callback = position -> {
				// 获取被点击列表项的数据
				JSONObject jsonObj = jsonArray.optJSONObject(position);
				Bundle bundle = new Bundle();
				try
				{
					// 将被点击项的ID封装到Bundle中
					bundle.putInt("itemId", jsonObj.getInt("id"));
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				mCallbacks.onItemSelected(ADD_BID, bundle);
			};
			JSONArrayAdapter adapter = new JSONArrayAdapter(
					getActivity(), jsonArray, "name", callback);
			// 使用RecyclerView显示当前种类的所有物品
			succList.setAdapter(adapter);
		}
		catch (JSONException e)
		{
			if (result != null && !result.isEmpty())
			{
				DialogUtil.showDialog(getActivity(), result, true);
			}
		}
		catch(Exception e1)
		{
			DialogUtil.showDialog(getActivity(), "服务器响应异常，请稍后再试！", false);
			e1.printStackTrace();
		}
		// 修改标题
		viewTitle.setText(R.string.item_list);
		return rootView;
	}

	// 当该Fragment被添加、显示到Activity时，回调该方法
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		// 如果Activity没有实现Callbacks接口，抛出异常
		if (!(context instanceof Callbacks))
		{
			throw new IllegalStateException("ManagerItemFragment所在的Context必须实现Callbacks接口!");
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
}
