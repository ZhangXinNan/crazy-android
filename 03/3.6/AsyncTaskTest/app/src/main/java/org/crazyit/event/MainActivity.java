package org.crazyit.event;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
	}
	// 重写该方法，为界面上的按钮提供事件响应方法
	public void download(View source) throws MalformedURLException
	{
		DownTask task = new DownTask(this, (ProgressBar)findViewById(R.id.progressBar));
		task.execute(new URL("http://www.crazyit.org/index.php"));
	}
	class DownTask extends AsyncTask<URL, Integer, String>
	{

		private ProgressBar progressBar;
		// 定义记录已经读取行的数量
		int hasRead = 0;
		Context mContext;
		public DownTask(Context ctx, ProgressBar progressBar)
		{
			mContext = ctx;
			this.progressBar = progressBar;
		}
		@Override
		protected String doInBackground(URL... params)
		{
			StringBuilder sb = new StringBuilder();
			try
			{
				URLConnection conn = params[0].openConnection();
				// 打开conn连接对应的输入流，并将它包装成BufferedReader
				BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
					hasRead++;
					publishProgress(hasRead);
					Thread.sleep(100);
				}
				return sb.toString();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result)
		{
			// 返回HTML页面的内容
			show.setText(result);
			// 设置进度条不可见
			progressBar.setVisibility(View.INVISIBLE);
		}
		@Override
		protected void onPreExecute()
		{
			// 设置进度条可见
			progressBar.setVisibility(View.VISIBLE);
			// 设置进度条的当前值
			progressBar.setProgress(0);
			// 设置该进度条的最大进度值
			progressBar.setMax(120);
		}
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			// 更新进度
			show.setText("已经读取了【" + values[0] + "】行！");
			progressBar.setProgress(values[0]);
		}
	}
}
