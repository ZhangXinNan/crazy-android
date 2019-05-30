package org.crazyit.client;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.crazyit.service.IPet;
import org.crazyit.service.Person;
import org.crazyit.service.Pet;

import java.util.List;

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
	private IPet petService;
	private ServiceConnection conn = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			// 获取远程Service的onBind方法返回的对象的代理
			petService = IPet.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			petService = null;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EditText personView = findViewById(R.id.person);
		ListView showView = findViewById(R.id.show);
		Button getBn = findViewById(R.id.get);
		// 创建所需绑定的Service的Intent
		Intent intent = new Intent();
		intent.setAction("org.crazyit.aidl.action.PARCELABLE_SERVICE");
		intent.setPackage("org.crazyit.service");
		// 绑定远程Service
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
		getBn.setOnClickListener(view ->
		{
			String personName = personView.getText().toString();
			try {
				// 调用远程Service的方法
				List<Pet> pets = petService.getPets(new Person(1, personName, personName)); // ①
				// 将程序返回的List包装成ArrayAdapter
				ArrayAdapter<Pet> adapter = new ArrayAdapter<>(MainActivity.this,
						android.R.layout.simple_list_item_1, pets);
				showView.setAdapter(adapter);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 解除绑定
		this.unbindService(conn);
	}
}
