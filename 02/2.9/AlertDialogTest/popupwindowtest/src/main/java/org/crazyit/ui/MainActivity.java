package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

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
		// 加载R.layout.popup对应的界面布局文件
		View root = this.getLayoutInflater().inflate(R.layout.popup, null);
		// 创建PopupWindow对象
		PopupWindow popup = new PopupWindow(root, 560, 720);
		Button button = findViewById(R.id.bn);
		button.setOnClickListener(view ->
			// 以下拉方式显示
			// popup.showAsDropDown(view);
			// 将PopupWindow显示在指定位置
			popup.showAtLocation(findViewById(R.id.bn), Gravity.CENTER, 20, 20));
		// 获取PopupWindow中的“关闭”按钮，并绑定事件监听器
		root.findViewById(R.id.close).setOnClickListener(view -> popup.dismiss() /* ① */);
	}
}
