package org.crazyit.auction.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class JSONArrayAdapter extends RecyclerView.Adapter<TextViewHolder>
{
	private Context ctx;
	// 定义需要包装的JSONArray对象
	private JSONArray jsonArray;
	// 定义列表项显示JSONObject对象的哪个属性
	private String property;
	private ItemClickedCallback callback;
	JSONArrayAdapter(Context ctx
			, JSONArray jsonArray, String property, ItemClickedCallback callback)
	{
		this.ctx = ctx;
		this.jsonArray = jsonArray;
		this.property = property;
		this.callback = callback;
	}

	@NonNull
	@Override
	public TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
	{
		LinearLayout container = new LinearLayout(ctx);
		container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		LayoutInflater.from(ctx).inflate(R.layout.item, container);
		return new TextViewHolder(container, callback);
	}

	@Override
	public int getItemCount()
	{
		// 返回RecyclerView包含的列表项的数量
		return jsonArray.length();
	}

	@Override
	public void onBindViewHolder(@NonNull TextViewHolder textViewHolder, int i)
	{
		try
		{
			// 获取JSONArray数组元素的property属性
			String itemName = jsonArray.optJSONObject(i)
					.getString(property);
			// 设置TextView所显示的内容
			textViewHolder.textView.setText(itemName);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
}
class TextViewHolder extends RecyclerView.ViewHolder
{
	TextView textView;
	TextViewHolder(View itemView, ItemClickedCallback callback)
	{
		super(itemView);
		textView = itemView.findViewById(R.id.textView);
		// 如果callback不为null，则为列表项绑定事件监听器
		if(callback != null)
		{
			itemView.findViewById(R.id.item_root).setOnClickListener(view ->
					callback.clickPosition(this.getAdapterPosition()));  // ①
		}
	}
}