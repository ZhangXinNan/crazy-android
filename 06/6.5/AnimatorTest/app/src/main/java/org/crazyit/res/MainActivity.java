package org.crazyit.res;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout container = findViewById(R.id.container);
		// 添加MyAnimationView组件
        container.addView(new MyAnimationView(this));
    }

	public class MyAnimationView extends View
	{
		MyAnimationView(Context context)
		{
			super(context);
			// 加载动画资源
			ObjectAnimator colorAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(
					MainActivity.this, 	R.animator.color_anim);
			colorAnim.setEvaluator(new ArgbEvaluator());
			// 对该View本身应用属性动画
			colorAnim.setTarget(this);
			// 开始指定动画
			colorAnim.start();
		}
	}
}
