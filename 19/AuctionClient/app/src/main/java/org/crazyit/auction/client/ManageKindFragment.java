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

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;

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
public class ManageKindFragment extends Fragment
{
	public static final  int ADD_KIND = 0x1007;
	private Callbacks mCallbacks;
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.manage_kind, container, false);
		// 定义并获取界面布局上的两个按钮
		Button bnHome = rootView.findViewById(R.id.bn_home);
		Button bnAdd = rootView.findViewById(R.id.bnAdd);
		RecyclerView kindList = rootView.findViewById(R.id.kindList);
		kindList.setHasFixedSize(true);
		// 为RecyclerView设置布局管理器
		kindList.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		// 为添加按钮的单击事件绑定事件监听器
		bnAdd.setOnClickListener(view ->
			// 当添加按钮被单击时，
			// 调用该Fragment所在Activity的onItemSelected方法
			mCallbacks.onItemSelected(ADD_KIND, null));
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "kinds";
		String result = null;
		try
		{
			// 向指定URL发送请求，并把响应包装成JSONArray对象
			result = HttpUtil.getRequest(url);
			JSONArray jsonArray = new JSONArray(result);
			// 把JSONArray对象包装成Adapter
			kindList.setAdapter(new KindArrayAdapter(jsonArray, getActivity(), null));
		}
		catch (JSONException e)
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
		// 如果Context没有实现Callbacks接口，抛出异常
		if (!(context instanceof Callbacks))
		{
			throw new IllegalStateException(
					"ManageKindFragment所在的Context必须实现Callbacks接口!");
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
}
