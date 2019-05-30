package org.crazyit.net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

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
	private ImageView show;
	// 代表从网络下载得到的图片
	private Bitmap bitmap;
	static class MyHandler extends Handler
	{
		private WeakReference<MainActivity> mainActivity;
		MyHandler(WeakReference<MainActivity> mainActivity)
		{
			this.mainActivity = mainActivity;
		}
		@Override public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				// 使用ImageView显示该图片
				mainActivity.get().show.setImageBitmap(mainActivity.get().bitmap);
			}
		}
	}
	private MyHandler handler = new MyHandler(new WeakReference<>(this));
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
				try {
					// 定义一个URL对象
					URL url = new URL("http://img10.360buyimg.com/n0"
							+ "/jfs/t15760/240/1818365159/368378/350e622b/"
							+ "5a60cbaeN0ecb487a.jpg"
					);
					// 打开该URL对应的资源的输入流
					InputStream is = url.openStream();
					// 从InputStream中解析出图片
					bitmap = BitmapFactory.decodeStream(is);
					// 发送消息，通知UI组件显示该图片
					handler.sendEmptyMessage(0x123);
					is.close();
					// 再次打开URL对应的资源的输入流
					is = url.openStream();
					// 打开手机文件对应的输出流
					OutputStream os = openFileOutput("crazyit.png", Context.MODE_PRIVATE);
					byte[] buff = new byte[1024];
					int hasRead = -1;
					// 将URL对应的资源下载到本地
					while ((hasRead = is.read(buff)) > 0) {
						os.write(buff, 0, hasRead);
					}
					is.close();
					os.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
