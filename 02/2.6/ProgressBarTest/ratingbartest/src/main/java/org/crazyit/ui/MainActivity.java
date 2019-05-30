package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;

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
		RatingBar ratingBar = findViewById(R.id.rating);
		ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
			// 当星级评分条的评分发生改变时触发该方法
			// 动态改变图片的透明度，其中255是星级评分条的最大值
			// 5个星星就代表最大值255
			image.setImageAlpha((int)(rating * 255 / 5));
		});
	}
}
