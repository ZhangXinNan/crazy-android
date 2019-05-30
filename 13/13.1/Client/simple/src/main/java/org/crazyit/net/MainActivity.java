package org.crazyit.net;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
	private TextView show;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		show = findViewById(R.id.show);
		new Thread()
		{
			@Override
			public void run()
			{
				try(
					// 建立连接到远程服务器的Socket
					Socket socket = new Socket("192.168.1.88", 30000);  // ①
					// 将Socket对应的输入流包装成BufferedReader
					BufferedReader br = new BufferedReader(
							new InputStreamReader(socket.getInputStream())))
				{
					// 进行普通IO操作
					String line = br.readLine();
					show.setText("来自服务器的数据：" + line);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
