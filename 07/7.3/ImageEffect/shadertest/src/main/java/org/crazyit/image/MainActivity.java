package org.crazyit.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
	// 声明Shader数组
	private Shader[] shaders = new Shader[5];
	// 声明颜色数组
	private int[] colors;
	private MyView myView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myView = findViewById(R.id.my_view);
		// 获得Bitmap实例
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.water);
		// 设置渐变的颜色组，也就是按红、绿、蓝的方式渐变
		colors = new int[]{Color.RED, Color.GREEN, Color.BLUE};
		// 实例化BitmapShader，x坐标方向重复图形，y坐标方向镜像图形
		shaders[0] = new BitmapShader(bm, Shader.TileMode.REPEAT,
				Shader.TileMode.MIRROR);
		// 实例化LinearGradient
		shaders[1] = new LinearGradient(0f, 0f, 100f, 100f,
				colors, null, Shader.TileMode.REPEAT);
		// 实例化RadialGradient
		shaders[2] = new RadialGradient(100f, 100f, 80f,
				colors, null, Shader.TileMode.REPEAT);
		// 实例化SweepGradient
		shaders[3] = new SweepGradient(160f, 160f, colors, null);
		// 实例化ComposeShader
		shaders[4] = new ComposeShader(shaders[1], shaders[2], PorterDuff.Mode.ADD);
		Button bn1 = findViewById(R.id.bn1);
		Button bn2 = findViewById(R.id.bn2);
		Button bn3 = findViewById(R.id.bn3);
		Button bn4 = findViewById(R.id.bn4);
		Button bn5 = findViewById(R.id.bn5);
		View.OnClickListener listener = source -> {
			switch (source.getId())
			{
				case R.id.bn1: myView.paint.setShader(shaders[0]); break;
				case R.id.bn2: myView.paint.setShader(shaders[1]); break;
				case R.id.bn3: myView.paint.setShader(shaders[2]); break;
				case R.id.bn4: myView.paint.setShader(shaders[3]); break;
				case R.id.bn5: myView.paint.setShader(shaders[4]); break;
			}
			// 重绘界面
			myView.invalidate();
		};
		bn1.setOnClickListener(listener);
		bn2.setOnClickListener(listener);
		bn3.setOnClickListener(listener);
		bn4.setOnClickListener(listener);
		bn5.setOnClickListener(listener);
	}
}
