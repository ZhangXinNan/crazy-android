package org.crazyit.media;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.SurfaceView;
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
	private ImageButton recordBn;
	private ImageButton stopBn;
	// 系统的视频文件
	private File videoFile;
	private MediaRecorder mRecorder;
	// 显示视频预览的SurfaceView
	private SurfaceView sView;
	// 记录是否正在进行录制
	private boolean isRecording;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序界面中的两个按钮
		recordBn = findViewById(R.id.record);
		stopBn = findViewById(R.id.stop);
		// 让stopBn按钮不可用
		stopBn.setEnabled(false);
		requestPermissions(new String[]{Manifest.permission.CAMERA,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
											@NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == 0x123 && grantResults.length == 3
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED
				&& grantResults[1] == PackageManager.PERMISSION_GRANTED
				&& grantResults[2] == PackageManager.PERMISSION_GRANTED) {
			View.OnClickListener listener = source ->
			{
				switch (source.getId()) {
					// 单击录制按钮
					case R.id.record:
						if (!Environment.getExternalStorageState().equals(
								android.os.Environment.MEDIA_MOUNTED)) {
							Toast.makeText(MainActivity.this,"SD卡不存在，请插入SD卡！",
									Toast.LENGTH_SHORT).show();
							return;
						}
						// 创建保存录制视频的视频文件
						videoFile = new File(Environment.getExternalStorageDirectory()
								.toString() + "/myvideo.mp4");
						// 创建MediaRecorder对象
						mRecorder = new MediaRecorder();
						mRecorder.reset();
						// 设置从麦克风采集声音
						mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
						// 设置从摄像头采集图像
						mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
						// 设置视频文件的输出格式
						// 必须在设置声音编码格式、图像编码格式之前设置
						mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
						// 设置声音编码格式
						mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
						// 设置图像编码格式
						mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
						// 设置视频尺寸，此处要求摄像头本身支持该尺寸，否则会在调用MediaRecorder的play方法时出现异常
//						mRecorder.setVideoSize(640, 560);
						mRecorder.setVideoSize(1920, 1080);
						// 每秒 12帧
						mRecorder.setVideoFrameRate(12);
						mRecorder.setOutputFile(videoFile.getAbsolutePath());
						// 指定使用SurfaceView来预览视频
						mRecorder.setPreviewDisplay(sView.getHolder().getSurface()) ; // ①
						try {
							mRecorder.prepare();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// 开始录制
						mRecorder.start();
						System.out.println("---recording---");
						// 让recordBn按钮不可用
						recordBn.setEnabled(false);
						// 让stopBn按钮可用
						stopBn.setEnabled(true);
						isRecording = true;
						break;
					// 单击停止按钮
					case R.id.stop:
						// 如果正在进行录制
						if (isRecording) {
							// 停止录制
							mRecorder.stop();
							// 释放资源
							mRecorder.release();
							mRecorder = null;
							// 让recordBn按钮可用
							recordBn.setEnabled(true);
							// 让stopBn按钮不可用
							stopBn.setEnabled(false);
						}
						break;
				}
			};
			// 为两个按钮的单击事件绑定监听器
			recordBn.setOnClickListener(listener);
			stopBn.setOnClickListener(listener);
			// 获取程序界面中的SurfaceView
			sView = findViewById(R.id.sView);
			// 设置分辨率
			sView.getHolder().setFixedSize(320, 280);
			// 设置该组件让屏幕不会自动关闭
			sView.getHolder().setKeepScreenOn(true);
		}
	}
}
