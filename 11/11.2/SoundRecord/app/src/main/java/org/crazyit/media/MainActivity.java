package org.crazyit.media;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

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
	// 定义界面上的两个按钮
	private ImageButton recordBn;
	private ImageButton stopBn;
	// 系统的音频文件
	private File soundFile;
	private MediaRecorder mRecorder;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面中的两个按钮
		recordBn = findViewById(R.id.record);
		stopBn = findViewById(R.id.stop);
		stopBn.setEnabled(false);
		requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,
				Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
		@NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == 0x123 && grantResults.length == 2
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED
				&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
			View.OnClickListener listener = source ->
			{
				switch (source.getId()) {
					// 单击录音按钮
					case R.id.record:
						if (!Environment.getExternalStorageState()
								.equals(Environment.MEDIA_MOUNTED)) {
							Toast.makeText(MainActivity.this,
									"SD卡不存在，请插入SD卡！",
									Toast.LENGTH_SHORT).show();
							return;
						}
						// 创建保存录音的音频文件
						soundFile = new File(Environment.getExternalStorageDirectory()
								.toString() + "/sound.amr");
						mRecorder = new MediaRecorder();
						// 设置录音的声音来源
						mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
						// 设置录制的声音的输出格式（必须在设置声音编码格式之前设置）
						mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
						// 设置声音编码格式
						mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
						mRecorder.setOutputFile(soundFile.getAbsolutePath());
						try {
							mRecorder.prepare();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// 开始录音
						mRecorder.start();  // ①
						recordBn.setEnabled(false);
						stopBn.setEnabled(true);
						break;
					// 单击停止录制按钮
					case R.id.stop:
						if (soundFile != null && soundFile.exists()) {


							// 停止录音
							mRecorder.stop();  // ②
							// 释放资源
							mRecorder.release();  // ③
							mRecorder = null;
							recordBn.setEnabled(true);
							stopBn.setEnabled(false);
						}
						break;
				}
			};
			// 为两个按钮的单击事件绑定监听器
			recordBn.setOnClickListener(listener);
			stopBn.setOnClickListener(listener);
		}
	}

	@Override
	public void onDestroy()
	{
		if (soundFile != null && soundFile.exists()) {
			// 停止录音
			mRecorder.stop();
			// 释放资源
			mRecorder.release();
			mRecorder = null;
		}
		super.onDestroy();
	}
}
