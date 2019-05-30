package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
// 实现事件监听器接口
public class MainActivity extends Activity implements View.OnClickListener
{
	private TextView show;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		show = findViewById(R.id.show);
		Button bn = findViewById(R.id.bn);
		// 直接使用Activity作为事件监听器
		bn.setOnClickListener(this);
	}
	// 实现事件处理方法
	@Override
	public void onClick(View v)
	{
		show.setText("bn按钮被单击了！");
	}
}
