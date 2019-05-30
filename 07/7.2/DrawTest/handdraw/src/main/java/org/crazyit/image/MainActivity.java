package org.crazyit.image;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

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
	private EmbossMaskFilter emboss;
	private BlurMaskFilter blur;
	private DrawView drawView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LinearLayout line = new LinearLayout(this);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		// 获取创建的宽度和高度
		getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
		// 创建一个DrawView，该DrawView的宽度、高度与该Activity保持相同
		drawView = new DrawView(this, displayMetrics.widthPixels, displayMetrics.heightPixels);
		line.addView(drawView);
		setContentView(line);
		emboss = new EmbossMaskFilter(new float[]{1.5f, 1.5f, 1.5f}, 0.6f, 6f, 4.2f);
		blur = new BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL);
	}
	// 负责创建选项菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// 加载R.menu.menu_main对应的菜单，并添加到menu中
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单项被单击后的回调方法
	@Override
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		// 判断单击的是哪个菜单项，并有针对性地做出响应
		switch (mi.getItemId())
		{
			case R.id.red:
				drawView.paint.setColor(Color.RED);
				mi.setChecked(true);
				break;
			case R.id.green:
				drawView.paint.setColor(Color.GREEN);
				mi.setChecked(true);
				break;
			case R.id.blue:
				drawView.paint.setColor(Color.BLUE);
				mi.setChecked(true);
				break;
			case R.id.width_1: drawView.paint.setStrokeWidth(1f); break;
			case R.id.width_3: drawView.paint.setStrokeWidth(3f); break;
			case R.id.width_5: drawView.paint.setStrokeWidth(5f); break;
			case R.id.blur: drawView.paint.setMaskFilter(blur); break;
			case R.id.emboss: drawView.paint.setMaskFilter(emboss); break;
		}
		return true;
	}
}
