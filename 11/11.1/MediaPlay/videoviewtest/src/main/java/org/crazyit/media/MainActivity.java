package org.crazyit.media;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

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
	private VideoView videoView;
	private MediaController mController;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取界面上的VideoView组件
		videoView = findViewById(R.id.video);
		// 创建MediaController对象
		mController = new MediaController(this);
		requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x123);
	}

	@Override public void onRequestPermissionsResult(int requestCode,
		@NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == 0x123
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			// 设为横屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			File video = new File("/mnt/sdcard/movie.mp4");
			if (video.exists()) {
				videoView.setVideoPath(video.getAbsolutePath()); // ①
				// 设置videoView与mController建立关联
				videoView.setMediaController(mController);  // ②
				// 设置mController与videoView建立关联
				mController.setMediaPlayer(videoView);  // ③
				// 让VideoView获取焦点
				videoView.requestFocus();
				videoView.start(); // 开始播放
			}
		}
	}
}
