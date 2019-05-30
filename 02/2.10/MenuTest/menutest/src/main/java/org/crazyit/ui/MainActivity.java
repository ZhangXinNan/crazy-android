package org.crazyit.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

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
	// 定义“字体大小”菜单项的标识
	private static final int FONT_10 = 0x111;
	private static final int FONT_12 = 0x112;
	private static final int FONT_14 = 0x113;
	private static final int FONT_16 = 0x114;
	private static final int FONT_18 = 0x115;
	// 定义“普通菜单项”的标识
	private static final int PLAIN_ITEM = 0x11b;
	// 定义“字体颜色”菜单项的标识
	private static final int FONT_RED = 0x116;
	private static final int FONT_BLUE = 0x117;
	private static final int FONT_GREEN = 0x118;
	private TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = findViewById(R.id.txt);
	}
	// 当用户单击MENU键时触发该方法
	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		// -------------向menu中添加“字体大小”的子菜单-------------
		SubMenu fontMenu = menu.addSubMenu("字体大小");
		// 设置菜单的图标
		fontMenu.setIcon(R.drawable.font);
		// 设置菜单头的图标
		fontMenu.setHeaderIcon(R.drawable.font);
		// 设置菜单头的标题
		fontMenu.setHeaderTitle("选择字体大小");
		fontMenu.add(0, FONT_10, 0, "10号字体");
		fontMenu.add(0, FONT_12, 0, "12号字体");
		fontMenu.add(0, FONT_14, 0, "14号字体");
		fontMenu.add(0, FONT_16, 0, "16号字体");
		fontMenu.add(0, FONT_18, 0, "18号字体");
		// -------------向menu中添加“普通菜单项”-------------
		menu.add(0, PLAIN_ITEM, 0, "普通菜单项");
		// -------------向menu中添加“字体颜色”的子菜单-------------
		SubMenu colorMenu = menu.addSubMenu("字体颜色");
		colorMenu.setIcon(R.drawable.color);
		// 设置菜单头的图标
		colorMenu.setHeaderIcon(R.drawable.color);
		// 设置菜单头的标题
		colorMenu.setHeaderTitle("选择文字颜色");
		colorMenu.add(0, FONT_RED, 0, "红色");
		colorMenu.add(0, FONT_GREEN, 0, "绿色");
		colorMenu.add(0, FONT_BLUE, 0, "蓝色");
		return super.onCreateOptionsMenu(menu);
	}
	// 选项菜单的菜单项被单击后的回调方法
	@Override public boolean onOptionsItemSelected(MenuItem mi)
	{
		// 判断单击的是哪个菜单项，并有针对性地做出响应
		switch (mi.getItemId())
		{
			case FONT_10: text.setTextSize(10 * 2);	break;
			case FONT_12: text.setTextSize(12 * 2); break;
			case FONT_14: text.setTextSize(14 * 2); break;
			case FONT_16: text.setTextSize(16 * 2); break;
			case FONT_18: text.setTextSize(18 * 2); break;
			case FONT_RED: text.setTextColor(Color.RED); break;
			case FONT_GREEN: text.setTextColor(Color.GREEN); break;
			case FONT_BLUE: text.setTextColor(Color.BLUE); break;
			case PLAIN_ITEM:
				Toast.makeText(MainActivity.this,
				"您单击了普通菜单项", Toast.LENGTH_SHORT)
					.show();
				break;
		}
		return true;
	}
}
