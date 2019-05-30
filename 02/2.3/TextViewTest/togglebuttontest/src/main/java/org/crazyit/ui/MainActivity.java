package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

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
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ToggleButton toggle = findViewById(R.id.toggle);
		Switch switcher = findViewById(R.id.switcher);
		LinearLayout test = findViewById(R.id.test);
		CompoundButton.OnCheckedChangeListener listener = (button, isChecked) -> {
			if (isChecked) {
				// 设置LinearLayout垂直布局
				test.setOrientation(LinearLayout.VERTICAL);
				toggle.setChecked(true);
				switcher.setChecked(true);
			} else {
				// 设置LinearLayout水平布局
				test.setOrientation(LinearLayout.HORIZONTAL);
				toggle.setChecked(false);
				switcher.setChecked(false);
			}
		};
		toggle.setOnCheckedChangeListener(listener);
		switcher.setOnCheckedChangeListener(listener);
	}
}
