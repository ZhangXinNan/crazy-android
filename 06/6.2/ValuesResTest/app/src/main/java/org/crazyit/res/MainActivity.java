package org.crazyit.res;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
	// 使用字符串资源
	int[] textIds = new int[]{R.string.c1, R.string.c2,
			R.string.c3, R.string.c4, R.string.c5, R.string.c6,
			R.string.c7, R.string.c8, R.string.c9};
	// 使用颜色资源
	int[] colorIds = new int[]{R.color.c1, R.color.c2,
			R.color.c3, R.color.c4, R.color.c5, R.color.c6,
			R.color.c7, R.color.c8, R.color.c9};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView grid = findViewById(R.id.grid01);
		// 创建一个BaseAdapter对象
		BaseAdapter ba = new BaseAdapter()
		{
			// 指定一共包含9个选项
			@Override
			public int getCount()
			{
				return textIds.length;
			}

			@Override
			public Object getItem(int position)
			{
				// 返回指定位置的文本
				return getResources().getText(textIds[position]);
			}

			@Override
			public long getItemId(int position)
			{
				return position;
			}
			// 重写该方法，该方法返回的View将作为GridView的每个格子
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				TextView tv;
				if (convertView == null)
				{
					tv = new TextView(MainActivity.this);
				}
				else
				{
					tv = (TextView) convertView;
				}
				Resources res = MainActivity.this.getResources();
				// 使用尺寸资源来设置文本框的高度、宽度
				tv.setWidth((int) res.getDimension(R.dimen.cell_width));
				tv.setHeight((int) res.getDimension(R.dimen.cell_height));
				// 使用字符串资源设置文本框的内容
				tv.setText(textIds[position]);
				// 使用颜色资源来设置文本框的背景色
				tv.setBackgroundResource(colorIds[position]);
				tv.setTextSize(res.getInteger(R.integer.font_size));
				return tv;
			}
		};
		// 为GridView设置Adapter
		grid.setAdapter(ba);
	}
}
