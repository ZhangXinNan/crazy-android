package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;

public class OtherActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 设置该Activity显示的页面
		setContentView(R.layout.other);
	}
}
