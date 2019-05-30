package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
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
public class MainActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取界面上rg、show两个组件
		RadioGroup rg = findViewById(R.id.rg);
		TextView show = findViewById(R.id.show);
		// 为RadioGroup组件的OnCheckedChange事件绑定事件监听器
		rg.setOnCheckedChangeListener((group, checkedId) -> {
			// 根据用户选中的单选钮来动态改变tip字符串的值
			String tip = checkedId == R.id.male ? "您的性别是男人" :
					"您的性别是女人";
			// 修改show组件中的文本
			show.setText(tip);
		});
	}
}
