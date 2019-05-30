package org.crazyit.client;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.TextView;

import org.crazyit.service.ICat;

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
	private ICat catService;
	private ServiceConnection conn = new ServiceConnection()
	{
		@Override public void onServiceConnected(ComponentName name, IBinder service)
		{
			// 获取远程Service的onBind方法返回的对象的代理
			catService = ICat.Stub.asInterface(service);
		}
		@Override public void onServiceDisconnected(ComponentName name)
		{
			catService = null;
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button getBn = findViewById(R.id.get);
		TextView colorTv = findViewById(R.id.color);
		TextView weightTv = findViewById(R.id.weight);
		// 创建所需绑定的Service的Intent
		Intent intent = new Intent();
		intent.setAction("org.crazyit.aidl.action.AIDL_SERVICE");
		// 设置要启动的Service所在包，也就是将该Intent变成所谓的显式Intent
		intent.setPackage("org.crazyit.service");
		// 绑定远程Service
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
		getBn.setOnClickListener(view -> {
			// 获取并显示远程Service的状态
			try {
				colorTv.setText("名字:" + catService.getColor());
				weightTv.setText("重量:" + catService.getWeight());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}
	@Override public void onDestroy()
	{
		super.onDestroy();
		// 解除绑定
		this.unbindService(conn);
	}
}
