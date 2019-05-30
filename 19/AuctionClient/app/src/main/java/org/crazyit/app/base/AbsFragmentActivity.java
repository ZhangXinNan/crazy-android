package org.crazyit.app.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
public abstract class AbsFragmentActivity extends FragmentActivity
{
	public static final int ROOT_CONTAINER_ID = 0x90001;
	protected abstract Fragment getFragment();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		setContentView(layout);
		layout.setId(ROOT_CONTAINER_ID);
		getSupportFragmentManager().beginTransaction()
				.replace(ROOT_CONTAINER_ID, getFragment())
				.commit();
	}
}
