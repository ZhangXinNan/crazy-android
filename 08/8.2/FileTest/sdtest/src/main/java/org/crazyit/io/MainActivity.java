package org.crazyit.io;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

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
	private static final String FILE_NAME = "/crazyit.bin";
	private EditText edit1;
	private TextView edit2;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取两个按钮
		Button read = findViewById(R.id.read);
		Button write = findViewById(R.id.write);
		// 获取两个文本框
		edit1 = findViewById(R.id.edit1);
		edit2 = findViewById(R.id.edit2);
		// 为write按钮绑定事件监听器
		write.setOnClickListener(view ->
			// 运行时请求获取写入SD卡的权限
			requestPermissions(new String[]
				{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123)); // ①

		read.setOnClickListener(view ->
			requestPermissions(new String[]
				{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x456));
	}
	private String read()
	{
		// 如果手机插入了SD卡，而且应用程序具有访问SD卡的权限
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			// 获取SD卡对应的存储目录
			File sdCardDir = Environment.getExternalStorageDirectory();
			try(
				// 获取指定文件对应的输入流
				FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + FILE_NAME);
				// 将指定输入流包装成BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(fis))
			) {
				StringBuilder sb = new StringBuilder();
				String line = null;
				// 循环读取文件内容
				while ((line = br.readLine()) != null)
				{
					sb.append(line);
				}
				return sb.toString();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	private void write(String content)
	{
		// 获取SD卡的目录
		File sdCardDir = Environment.getExternalStorageDirectory();
		try
		{
			File targetFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);
			// 以指定文件创建 RandomAccessFile对象
			RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
			// 将文件记录指针移动到最后
			raf.seek(targetFile.length());
			// 输出文件内容
			raf.write(content.getBytes());
			// 关闭RandomAccessFile
			raf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
		String[] permissions, int[] grantResults)
	{
		if (requestCode == 0x123)
		{
			// 如果用户同意授权访问
			if(grantResults != null &&  grantResults[0] ==
				PackageManager.PERMISSION_GRANTED )
			{
				write(edit1.getText().toString());
				edit1.setText("");
			}
			else
			{
				// 提示用户必须允许写入SD卡的权限
				Toast.makeText(this, R.string.writesd_tip, Toast.LENGTH_LONG)
						.show();
			}
		}
		if (requestCode == 0x456)
		{
			// 如果用户同意授权访问
			if(grantResults != null && grantResults[0] ==
				PackageManager.PERMISSION_GRANTED )
			{
				// 读取指定文件中的内容，并显示出来
				edit2.setText(read());
			}
			else
			{
				// 提示用户必须允许写入SD卡的权限
				Toast.makeText(this, R.string.writesd_tip, Toast.LENGTH_LONG)
						.show();
			}
		}
	}
}
