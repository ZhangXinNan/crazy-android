package org.crazyit.auction.client;

import android.content.Intent;
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
public class ChooseItemActivity extends AbsFragmentActivity
		implements Callbacks
{
	@Override
	protected Fragment getFragment()
	{
		Fragment fragment = new ChooseItemFragment();
		Bundle args = new Bundle();
		args.putLong("kindId", getIntent()
				.getLongExtra("kindId", -1));
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onItemSelected(Integer id, Bundle bundle)
	{
		Intent intent = new Intent(this, AddBidActivity.class);
		intent.putExtra("itemId", bundle.getInt("itemId"));
		startActivity(intent);
	}
}
