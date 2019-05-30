package org.crazyit.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

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
	// 定义与服务器通信的子线程
	private ClientThread clientThread;
	static class MyHandler extends Handler // ②
	{
		private WeakReference<MainActivity> mainActivity;
		MyHandler(WeakReference<MainActivity> mainActivity)
		{
			this.mainActivity = mainActivity;
		}
		@Override
		public void handleMessage(Message msg)
		{
			// 如果消息来自子线程
			if (msg.what == 0x123)
			{
				// 将读取的内容追加显示在文本框中
				mainActivity.get().show.append("\n" + msg.obj.toString());
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 定义界面上的两个文本框
		EditText input = findViewById(R.id.input);
		show = findViewById(R.id.show);
		// 定义界面上的一个按钮
		Button send = findViewById(R.id.send);
		MyHandler handler = new MyHandler(new WeakReference<>(this));
		clientThread = new ClientThread(handler);
		// 客户端启动ClientThread线程创建网络连接，读取来自服务器的数据
		new Thread(clientThread).start();  // ①
		send.setOnClickListener(view -> {
			// 当用户单击“发送”按钮后，将用户输入的数据封装成Message
			// 然后发送给子线程的Handler
			Message msg = new Message();
			msg.what = 0x345;
			msg.obj = input.getText().toString();
			clientThread.revHandler.sendMessage(msg);
			// 清空input文本框
			input.setText("");
		});
	}
}
