package org.crazyit.res;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
		ImageView image = findViewById(R.id.image);
		// 加载动画资源
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.my_anim);
		// 设置动画结束后保留结束状态
		anim.setFillAfter(true);  // ①
		Button bn = findViewById(R.id.bn);
		bn.setOnClickListener(view -> image.startAnimation(anim) /* 开始动画 */);
	}
}
