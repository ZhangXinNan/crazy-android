package org.crazyit.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

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
public class ProximityAlertReciever extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// 获取是否进入指定区域
		boolean isEnter = intent.getBooleanExtra(
				LocationManager.KEY_PROXIMITY_ENTERING, false);
		if (isEnter)
		{
			// 显示提示信息
			Toast.makeText(context, "您已经进入疯狂软件教育中心附近",
					Toast.LENGTH_LONG).show();
		}
		else
		{
			// 显示提示信息
			Toast.makeText(context, "您已经离开疯狂软件教育中心附近",
					Toast.LENGTH_LONG).show();
		}
	}
}
