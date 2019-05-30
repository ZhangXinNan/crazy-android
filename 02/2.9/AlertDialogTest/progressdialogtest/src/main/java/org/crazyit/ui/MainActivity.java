package org.crazyit.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

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
	public static final int MAX_PROGRESS = 100;
	// 该程序模拟填充长度为100的数组
	private int[] data = new int[100];
	// 记录进度对话框的完成百分比
	private int progressStatus = 0;
	private int hasData = 0;
	private ProgressDialog pd1;
	private ProgressDialog pd2;
	static class MyHandler extends Handler
	{
		WeakReference<MainActivity> activity;
		MyHandler(WeakReference<MainActivity> activity){
			this.activity = activity;
		}
		@Override public void handleMessage(Message msg)
		{
			// 表明消息是由该程序发送的
			if (msg.what == 0x123)
			{
				activity.get().pd2.setProgress(activity.get().progressStatus);
			}
		}
	}
	// 定义一个负责更新进度的Handler
	Handler handler = new MyHandler(new WeakReference<>(this));
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void showSpinner(View source)
	{
		// 调用静态方法显示环形进度条
		ProgressDialog.show(this, "任务执行中", "任务执行中，请等待", false, true); // ①
	}

	public void showIndeterminate(View source )
	{
		pd1 = new ProgressDialog(MainActivity.this);
		// 设置对话框的标题
		pd1.setTitle("任务正在执行中");
		// 设置对话框显示的内容
		pd1.setMessage("任务正在执行中，敬请等待...");
		// 设置对话框能用“取消”按钮关闭
		pd1.setCancelable(true);
		// 设置对话框的进度条风格
		pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 设置对话框的进度条是否显示进度
		pd1.setIndeterminate(true);
		pd1.show(); // ②
	}

	public void showProgress(View source)
	{
		// 将进度条的完成进度重设为0
		progressStatus = 0;
		// 重新开始填充数组
		hasData = 0;
		pd2 = new ProgressDialog(MainActivity.this);
		pd2.setMax(MAX_PROGRESS);
		// 设置对话框的标题
		pd2.setTitle("任务完成百分比");
		// 设置对话框显示的内容
		pd2.setMessage("耗时任务的完成百分比");
		// 设置对话框不能用“取消”按钮关闭
		pd2.setCancelable(false);
		// 设置对话框的进度条风格
		pd2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 设置对话框的进度条是否显示进度
		pd2.setIndeterminate(false);
		pd2.show(); // ③
		new Thread()
		{
			@Override public void run()
			{
				while (progressStatus < MAX_PROGRESS)
				{
					// 获取耗时操作的完成百分比
					progressStatus = MAX_PROGRESS * doWork() / data.length;
					// 发送空消息到Handler
					handler.sendEmptyMessage(0x123);
				}
				// 如果任务已经完成
				if (progressStatus >= MAX_PROGRESS)
				{
					// 关闭对话框
					pd2.dismiss();
				}
			}
		}.start();
	}

	// 模拟一个耗时的操作
	public int doWork()
	{
		// 为数组元素赋值
		data[hasData++] = (int)(Math.random() * 100);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return hasData;
	}
}
