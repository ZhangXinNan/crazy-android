package org.crazyit.image;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

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
		GridLayout grid = findViewById(R.id.grid);
		MyView mv = findViewById(R.id.mv);
		String[] bnArray = getResources().getStringArray(R.array.bnArray);
		for(String title : bnArray) {
			Button bn = new Button(this);
			bn.setText(title);
			grid.addView(bn);
			bn.setOnClickListener(view -> {
				switch (getIndex(bnArray, bn.getText()))
				{
					// 向左倾斜
					case 0:
						mv.isScale = false;
						mv.sx += 0.1f;
						mv.postInvalidate();
						break;
					// 向右倾斜
					case 1:
						mv.isScale = false;
						mv.sx -= 0.1f;
						mv.postInvalidate();
						break;
					// 放大
					case 2:
						mv.isScale = true;
						if (mv.scale < 2.0) mv.scale += 0.1f;
						mv.postInvalidate();
						break;
					// 缩小
					case 3:
						mv.isScale = true;
						if (mv.scale > 0.5) mv.scale -= 0.1f;
						mv.postInvalidate();
						break;
				}
			});
		}
	}
	// 定义一个工具方法，获取元素在数组中的出现次数
	private static <T> int getIndex(T[] arr, T target)
	{
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == target || arr[i].equals(target)) {
				return i;
			}
		}
		return -1;
	}
}
