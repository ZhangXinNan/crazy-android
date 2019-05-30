package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
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
	public final static String UPPER_NUM = "upper";
	private EditText etNum;
	private CalThread calThread;
	// 定义一个线程类
	class CalThread extends Thread
	{
		private Handler mHandler;
		@Override
		public void run()
		{
			Looper.prepare();
			mHandler = new Handler()
			{
				// 定义处理消息的方法
				@Override public void handleMessage(Message msg)
				{
					if (msg.what == 0x123) {
						int upper = msg.getData().getInt(UPPER_NUM);
						List<Integer> nums = new ArrayList<Integer>();
						// 计算从2开始、到upper的所有质数
						outer:
						for (int i  = 2; i <= upper; i++) {
							// 用i除以从2开始、到i的平方根的所有数
							int j = 2;
							while (j <= Math.sqrt(i)) {
								// 如果可以整除，则表明这个数不是质数
								if (i != 2 && i % j == 0) {
									continue outer;
								}
								j++;
							}
							nums.add(i);
						}
						// 使用Toast显示统计出来的所有质数
						Toast.makeText(MainActivity.this, nums.toString(),
								Toast.LENGTH_LONG).show();
					}
				}
			};
			Looper.loop();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etNum = findViewById(R.id.et_num);
		calThread = new CalThread();
		// 启动新线程
		calThread.start();
	}

	// 为按钮的点击事件提供事件处理方法
	public void cal(View source)
	{
		// 创建消息
		Message msg = new Message();
		msg.what = 0x123;
		Bundle bundle = new Bundle();
		bundle.putInt(UPPER_NUM, Integer.parseInt(etNum.getText().toString()));
		msg.setData(bundle);
		// 向新线程中的Handler发送消息
		calThread.mHandler.sendMessage(msg);
	}
}
