package org.crazyit.auction.client;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class KindArrayAdapter extends RecyclerView.Adapter<KindContentHolder>
{
	// 需要包装的JSONArray
	private JSONArray kindArray;
	private Context ctx;
	private ItemClickedCallback callback;
	KindArrayAdapter(JSONArray kindArray
			,Context ctx, ItemClickedCallback callback)
	{
		this.kindArray = kindArray;
		this.ctx = ctx;
		this.callback = callback;
	}

	@NonNull
	@Override
	public KindContentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
	{
		LinearLayout container = new LinearLayout(ctx);
		container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		LayoutInflater.from(ctx).inflate(R.layout.kind_item, container);
		return new KindContentHolder(container, callback);
	}

	@Override
	public void onBindViewHolder(@NonNull KindContentHolder kindContentHolder, int i)
	{
		try
		{
			// 获取JSONArray数组元素的kindName属性
			String kindName = kindArray.optJSONObject(i)
					.getString("kindName");
			// 设置TextView所显示的内容
			kindContentHolder.nameTv.setText(kindName);
			// 获取JSONArray数组元素的kindDesc属性
			String kindDesc = kindArray.optJSONObject(i)
					.getString("kindDesc");
			// 设置TextView所显示的内容
			kindContentHolder.descTv.setText(kindDesc);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount()
	{
		// 返回RecyclerView包含的列表项的数量
		return kindArray.length();
	}
}
class KindContentHolder extends RecyclerView.ViewHolder
{
	TextView nameTv;
	TextView descTv;
	KindContentHolder(View itemView, ItemClickedCallback callback)
	{
		super(itemView);
		nameTv = itemView.findViewById(R.id.name_tv);
		descTv = itemView.findViewById(R.id.desc_tv);
		// 如果callback不为null，则为列表项绑定事件监听器
		if(callback != null)
		{
			itemView.findViewById(R.id.item_root).setOnClickListener(view ->
					callback.clickPosition(this.getAdapterPosition()));
		}
	}
}
