package org.crazyit.app;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class SelectCityActivity extends ExpandableListActivity
{
	// 定义省份数组
	private String[] provinces = new String[]{"广东", "广西", "湖南"};
	// 定义城市数组
	private String[][] cities = new String[][]{
			new String[]{"广州", "深圳", "珠海", "中山"},
			new String[]{"桂林", "柳州", "南宁", "北海"},
			new String[]{"长沙", "岳阳", "衡阳", "株洲"}};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 省略实现BaseExpandableListAdapter对象的代码
		BaseExpandableListAdapter adapter = new BaseExpandableListAdapter()
		{

			@Override
			public int getGroupCount()
			{
				return provinces.length;
			}

			@Override
			public int getChildrenCount(int groupPosition)
			{
				return cities[groupPosition].length;
			}

			// 获取指定组位置处的组数据
			@Override
			public Object getGroup(int groupPosition)
			{
				return provinces[groupPosition];
			}
			// 获取指定组位置、指定子列表项处的子列表项数据
			@Override
			public Object getChild(int groupPosition, int childPosition)
			{
				return cities[groupPosition][childPosition];
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

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
			{
				TextView tv;
				if( convertView == null)
				{
					tv = createTextView();
				}
				else
				{
					tv = (TextView) convertView;
				}
				tv.setText(getGroup(groupPosition).toString());
				tv.setTextSize(20f);
				tv.setPadding(90, 5, 0, 5);
				return tv;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
			{
				TextView tv;
				if(convertView == null)
				{
					tv = createTextView();
				}
				else
				{
					tv = (TextView) convertView;
				}
				tv.setText(getChild(groupPosition, childPosition).toString());
				tv.setTextSize(16f);
				tv.setPadding(115, 2, 0, 2);
				return tv;
			}

			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition)
			{
				return true;
			}
		};
		// 设置该窗口的显示列表
		setListAdapter(adapter);
		getExpandableListView().setOnChildClickListener(
			(parent, source, groupPosition, childPosition, id) -> {
			// 获取启动该Activity之前的Activity对应的Intent
			Intent intent = getIntent();
			intent.putExtra("city", cities[groupPosition][childPosition]);
			// 设置该SelectCityActivity的结果码，并设置结束之后退回的Activity
			SelectCityActivity.this.setResult(0, intent);
			// 结束SelectCityActivity
			SelectCityActivity.this.finish();
			return false;
		});
	}
	private TextView createTextView()
	{
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		TextView textView = new TextView(SelectCityActivity.this);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
		textView.setPadding(36, 0, 0, 0);
		textView.setTextSize(20f);
		return textView;
	}
}
