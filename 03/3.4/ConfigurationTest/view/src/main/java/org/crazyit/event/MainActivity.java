package org.crazyit.event;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
	private TextView ori;
	private TextView navigation;
	private TextView touch;
	private TextView mnc;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取应用界面中的界面组件
		ori = findViewById(R.id.ori);
		navigation = findViewById(R.id.navigation);
		touch = findViewById(R.id.touch);
		mnc = findViewById(R.id.mnc);
		Button bn = findViewById(R.id.bn);
		bn.setOnClickListener(view -> {
			// 获取系统的Configuration对象
			Configuration cfg = getResources().getConfiguration();
			String screen = cfg.orientation == Configuration.ORIENTATION_LANDSCAPE ?
				"横向屏幕" : "竖向屏幕";
			String mncCode = cfg.mnc + "";
			String naviName = cfg.orientation == Configuration.NAVIGATION_NONAV?
				"没有方向控制" :
				(cfg.orientation == Configuration.NAVIGATION_WHEEL)? "滚轮控制方向":
				(cfg.orientation == Configuration.NAVIGATION_DPAD) ? "方向键控制方向":
				 "轨迹球控制方向";
			navigation.setText(naviName);
			String touchName = cfg.touchscreen ==
					Configuration.TOUCHSCREEN_NOTOUCH? "无触摸屏": "支持触摸屏";
			ori.setText(screen);
			mnc.setText(mncCode);
			touch.setText(touchName);
		});
	}
}
