package org.crazyit.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class MainActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取界面上ViewPager组件
		ViewPager viewPager = findViewById(R.id.container);
		// 创建SectionsPagerAdapter对象
		SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		// 为ViewPager组件设置Adapter
		viewPager.setAdapter(pagerAdapter);
		// 获取界面上的TabLayout组件
		TabLayout tabLayout = findViewById(R.id.tabs);
		// 设置从ViewPager到TabLayout的关联
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		// 设置从TabLayout到ViewPager的关联
		tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
	}
	// 定义一个Fragment
	public static class DummyFragment extends Fragment
	{
		private static final String ARG_SECTION_NUMBER = "section_number";
		// 该方法用于返回一个DummyFragment实例
		public static DummyFragment newInstance(int sectionNumber)
		{
			DummyFragment fragment = new DummyFragment();
			// 定义Bundle用于向DummyFragment传入参数
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
		// 重写该方法用于生成该Fragment所显示的组件
		@Override
		public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState)
		{
			// 加载/res/layout/目录下的fragment_main.xml文件
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView textView = rootView.findViewById(R.id.section_label);
			textView.setText("Fragment页面" + getArguments().getInt(ARG_SECTION_NUMBER, 0));
			return rootView;
		}
	}
	// 定义FragmentPagerAdapter的子类，用于巍
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}
		// 根据位置返回指定的Fragment
		@Override
		public Fragment getItem(int position)
		{
			return DummyFragment.newInstance(position + 1);
		}
		// 该方法的返回值决定该Adapter包含多少项
		@Override
		public int getCount()
		{
			return 3;
		}
	}
}