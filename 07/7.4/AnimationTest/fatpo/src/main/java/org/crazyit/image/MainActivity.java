package org.crazyit.image;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

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
		// 获取两个按钮
		Button play = findViewById(R.id.play);
		Button stop = findViewById(R.id.stop);
		ImageView imageView = findViewById(R.id.anim);
		// 获取AnimationDrawable动画对象
		AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
		play.setOnClickListener(view -> anim.start() /* 开始播放动画 */);
		stop.setOnClickListener(view -> anim.stop() /* 停止播放动画 */);
	}
}
