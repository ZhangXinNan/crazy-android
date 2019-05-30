package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

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
		final ImageView image = findViewById(R.id.image);
		SeekBar seekBar = findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			// 当拖动条的滑块位置发生改变时触发该方法
			@Override public void onProgressChanged(SeekBar bar, int progress, boolean fromUser)
			{
				// 动态改变图片的透明度
				image.setImageAlpha(progress);
			}
			@Override public void onStartTrackingTouch(SeekBar bar)
			{
			}
			@Override public void onStopTrackingTouch(SeekBar bar)
			{
			}
		});
	}
}
