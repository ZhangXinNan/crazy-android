package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
		// 创建一个BaseExpandableListAdapter对象
		BaseExpandableListAdapter adapter = new BaseExpandableListAdapter()
		{
			int[] logos = new int[]{R.drawable.p, R.drawable.z, R.drawable.t};
			String[] armTypes = new String[]{"神族兵种", "虫族兵种", "人族兵种"};
			String[][] arms = new String[][]{
					new String[]{"狂战士", "龙骑士", "黑暗圣堂", "电兵"},
					new String[]{"小狗", "刺蛇", "飞龙", "自爆飞机"},
					new String[]{"机枪兵", "护士MM", "幽灵"}};

			@Override
			public int getGroupCount()
			{
				return armTypes.length;
			}

			@Override
			public int getChildrenCount(int groupPosition)
			{
				return arms[groupPosition].length;
			}

			// 获取指定组位置处的组数据
			@Override
			public Object getGroup(int groupPosition)
			{
				return armTypes[groupPosition];
			}

			// 获取指定组位置、指定子列表项处的子列表项数据
			@Override
			public Object getChild(int groupPosition, int childPosition)
			{
				return arms[groupPosition][childPosition];
			}

			@Override
			public long getGroupId(int groupPosition)
			{
				return groupPosition;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition)
			{
				return childPosition;
			}

			@Override
			public boolean hasStableIds()
			{
				return true;
			}

			// 该方法决定每个组选项的外观
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
			{
				LinearLayout ll;
				ViewHolder viewHolder;
				if (convertView == null) {
					ll = new LinearLayout(MainActivity.this);
					ll.setOrientation(LinearLayout.HORIZONTAL);
					ImageView logo = new ImageView(MainActivity.this);
					ll.addView(logo);
					TextView textView = this.getTextView();
					ll.addView(textView);
					viewHolder = new ViewHolder(logo, textView);
					ll.setTag(viewHolder);
				} else {
					ll = (LinearLayout) convertView;
					viewHolder = (ViewHolder) ll.getTag();
				}
				viewHolder.imageView.setImageResource(logos[groupPosition]);
				viewHolder.textView.setText(getGroup(groupPosition).toString());
				return ll;
			}

			// 该方法决定每个子选项的外观
			@Override
			public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
			{
				TextView textView;
				if (convertView == null) {
					textView = this.getTextView();
				} else {
					textView = (TextView) convertView;
				}
				textView.setText(getChild(groupPosition, childPosition).toString());
				return textView;
			}
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition)
			{
				return true;
			}

			private TextView getTextView()
			{
				TextView textView = new TextView(MainActivity.this);
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams
						.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
				textView.setPadding(36, 10, 0, 10);
				textView.setTextSize(20f);
				return textView;
			}
		};
		ExpandableListView expandListView = findViewById(R.id.list);
		expandListView.setAdapter(adapter);
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
