package org.crazyit.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView myList = findViewById(R.id.myList);
		BaseAdapter adapter = new BaseAdapter()
		{
			@Override
			public int getCount()
			{
				// 指定一共包含40个选项
				return 40;
			}
			@Override
			public Object getItem(int position)
			{
				return null;
			}
			// 重写该方法，该方法的返回值将作为列表项的ID
			@Override
			public long getItemId(int position)
			{
				return position;
			}
			// 重写该方法，该方法返回的View将作为列表框
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				LinearLayout linearLayout;
				ViewHolder viewHolder;
				if (convertView == null) {
					// 创建一个LinearLayout，并向其中添加两个组件
					linearLayout = new LinearLayout(MainActivity.this);
					linearLayout.setOrientation(LinearLayout.HORIZONTAL);
					ImageView image = new ImageView(MainActivity.this);
					TextView text = new TextView(MainActivity.this);
					linearLayout.addView(image);
					linearLayout.addView(text);
					viewHolder = new ViewHolder(image, text);
					linearLayout.setTag(viewHolder);
				} else {
					linearLayout = (LinearLayout) convertView;
					viewHolder = (ViewHolder) linearLayout.getTag();
				}
				viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
				viewHolder.textView.setText("第" + (position + 1) + "个列表项");
				viewHolder.textView.setTextSize(20f);
				viewHolder.textView.setTextColor(Color.RED);
				// 返回linearLayout实例
				return linearLayout;
			}
		};
		myList.setAdapter(adapter);
	}
	class ViewHolder{
		ImageView imageView;
		TextView textView;
		public ViewHolder(ImageView imageView, TextView textView)
		{
			this.imageView = imageView;
			this.textView = textView;
		}
	}
}
