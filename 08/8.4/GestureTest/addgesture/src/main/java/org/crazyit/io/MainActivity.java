package org.crazyit.io;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
	private GestureOverlayView gestureView;
	private Gesture gesture;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取手势编辑视图
		gestureView = findViewById(R.id.gesture);
		// 设置手势的绘制颜色
		gestureView.setGestureColor(Color.RED);
		// 设置手势的绘制宽度
		gestureView.setGestureStrokeWidth(4f);
		// 为gesture的手势完成事件绑定事件监听器
		gestureView.addOnGesturePerformedListener((source, gesture) -> {
			this.gesture = gesture;
			// 请求访问写入SD卡的权限
			requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123);
		});
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
		String[] permissions, int[] grantResults)
	{
		// 如果确认允许访问
		if(requestCode == 0x123 && grantResults != null && grantResults[0] == 0)
		{
			// 加载dialog_save.xml界面布局代表的视图
			LinearLayout saveDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_save, null);
			// 获取saveDialog里的show组件
			ImageView imageView = saveDialog.findViewById(R.id.show);
			// 获取saveDialog里的gesture_name组件
			EditText gestureName = saveDialog.findViewById(R.id.gesture_name);
			// 根据Gesture包含的手势创建一个位图
			Bitmap bitmap = gesture.toBitmap(128, 128, 10, -0x10000);
			imageView.setImageBitmap(bitmap);
			// 使用对话框显示saveDialog组件
			new AlertDialog.Builder(MainActivity.this).setView(saveDialog)
				.setPositiveButton(R.string.bn_save, (dialog, which) -> {
						// 获取指定文件对应的手势库
						GestureLibrary gestureLib = GestureLibraries.fromFile(
						Environment.getExternalStorageDirectory().getPath() + "/mygestures");
						// 添加手势
						gestureLib.addGesture(gestureName.getText().toString(), gesture);
						// 保存手势库
						gestureLib.save();
				}).setNegativeButton(R.string.bn_cancel, null).show();
		}
	}
}
