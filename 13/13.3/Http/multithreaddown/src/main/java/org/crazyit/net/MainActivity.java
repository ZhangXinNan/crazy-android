package org.crazyit.net;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

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
	private EditText url;
	private EditText target;
	private Button downBn;
	private ProgressBar bar;
	private DownUtil downUtil;

	static class MyHandler extends Handler
	{
		private WeakReference<MainActivity> mainActivity;

		MyHandler(WeakReference<MainActivity> mainActivity)
		{
			this.mainActivity = mainActivity;
		}

		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				mainActivity.get().bar.setProgress(msg.arg1);
			}
		}
	}

	// 创建一个Handler对象
	private MyHandler handler = new MyHandler(new WeakReference<>(this));

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面中的三个界面控件
		url = findViewById(R.id.url);
		target = findViewById(R.id.target);
		downBn = findViewById(R.id.down);
		bar = findViewById(R.id.bar);
		requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x456);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == 0x456 && grantResults.length == 1
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			downBn.setOnClickListener(view ->
			{
				// 初始化DownUtil对象（最后一个参数指定线程数）
				downUtil = new DownUtil(url.getText().toString(), target.getText().toString(), 6);
				new Thread(() ->
				{
					try
					{
						// 开始下载
						downUtil.download();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					// 定义每秒调度获取一次系统的完成进度
					final Timer timer = new Timer();
					timer.schedule(new TimerTask()
					{
						@Override
						public void run()
						{
							// 获取下载任务的完成比例
							double completeRate = downUtil.getCompleteRate();
							System.out.println(completeRate);
							Message msg = new Message();
							msg.what = 0x123;
							msg.arg1 = (int) (completeRate * 100);
							// 发送消息通知界面更新进度条
							handler.sendMessage(msg);
							// 下载完全后取消任务调度
							if (completeRate >= 1)
							{
								timer.cancel();
							}
						}
					}, 0, 100);
				}).start();
			});
		}
	}
}
