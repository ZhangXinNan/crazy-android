package org.crazyit.auction.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.crazyit.app.base.AbsFragmentActivity;

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
public class AddBidActivity extends AbsFragmentActivity
{
	@Override
	public Fragment getFragment()
	{
		Fragment fragment = new AddBidFragment();
		Bundle args = new Bundle();
		args.putInt("itemId", getIntent().getIntExtra("itemId", -1));
		fragment.setArguments(args);
		return fragment;
	}
}
