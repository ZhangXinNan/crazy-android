package org.crazyit.io;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
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
	// 定义手势编辑组件
	private GestureOverlayView gestureView;
	// 记录手机上已有的手势库
	private GestureLibrary gestureLibrary;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
											String[] permissions, int[] grantResults)
	{
		// 如果用户授权访问SD卡
		if(requestCode == 0x123 && grantResults != null
				&& grantResults[0] == 0)
		{
			// 读取上一个程序所创建的手势库
			gestureLibrary = GestureLibraries.fromFile(
					Environment.getExternalStorageDirectory().getPath() + "/mygestures");
			if (gestureLibrary.load())
			{
				Toast.makeText(MainActivity.this, "手势文件装载成功！",
					Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(MainActivity.this, "手势文件装载失败！",
					Toast.LENGTH_SHORT).show();
			}
			// 获取手势编辑组件
			gestureView = findViewById(R.id.gesture);
			// 为手势编辑组件绑定事件监听器
			gestureView.addOnGesturePerformedListener((source, gesture) -> {
				// 识别用户刚刚所绘制的手势
				List<Prediction> predictions = gestureLibrary.recognize(gesture);
				List<String> result = new ArrayList<>();
				// 遍历所有找到的Prediction对象
				for (Prediction pred : predictions)
				{
					// 只有相似度大于2.0的手势才会被输出
					if (pred.score > 2.0)
					{
						result.add("与手势【" + pred.name + "】相似度为" + pred.score);
					}
				}
				if (result.size() > 0)
				{
					ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
					android.R.layout.simple_dropdown_item_1line, result);
					// 使用一个带List的对话框来显示所有匹配的手势
					new AlertDialog.Builder(MainActivity.this).setAdapter(adapter, null)
						.setPositiveButton("确定", null).show();
				}
				else
				{
					Toast.makeText(MainActivity.this, "无法找到能匹配的手势！",
						Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
