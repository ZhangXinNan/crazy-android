package org.crazyit.media;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

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
	private SurfaceView surfaceView;
	private MediaPlayer mPlayer;
	private ImageButton playBn, pauseBn, stopBn;
	// 记录当前视频的播放位置
	int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建MediaPlayer
		mPlayer = new MediaPlayer();
		surfaceView = this.findViewById(R.id.surfaceView);
		// 设置播放时打开屏幕
		surfaceView.getHolder().setKeepScreenOn(true);
		surfaceView.getHolder().addCallback(new SurfaceListener());
		// 获取界面上的三个按钮
		playBn = findViewById(R.id.play);
		pauseBn = findViewById(R.id.pause);
		stopBn = findViewById(R.id.stop);
		requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x123);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == 0x123 && grantResults[0] ==
				PackageManager.PERMISSION_GRANTED) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			View.OnClickListener listener = source -> {
				switch (source.getId())
				{
					// “播放”按钮被单击
					case R.id.play:
						play();
						break;
					// “暂停”按钮被单击
					case R.id.pause:
						if (mPlayer.isPlaying()) {
							mPlayer.pause();
						} else {
							mPlayer.start();
						}
						break;
					// “停止”按钮被单击
					case R.id.stop:
						if (mPlayer.isPlaying())
							mPlayer.stop();
				}
			};
			// 为三个按钮的单击事件绑定事件监听器
			playBn.setOnClickListener(listener);
			pauseBn.setOnClickListener(listener);
			stopBn.setOnClickListener(listener);
		}
	}

	private void play()
	{
		mPlayer.reset();
		AudioAttributes audioAttributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_MEDIA)
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.build();
		mPlayer.setAudioAttributes(audioAttributes);
		try {
			// 设置需要播放的视频
			mPlayer.setDataSource(Environment.getExternalStorageDirectory().toString() + "/movie.3gp");
			// 把视频画面输出到SurfaceView
			mPlayer.setDisplay(surfaceView.getHolder());  // ①
			mPlayer.prepare();
		}
		catch(IOException e)
		{ e.printStackTrace(); }
		// 获取窗口管理器
		WindowManager wManager = getWindowManager();
		DisplayMetrics metrics = new DisplayMetrics();
		// 获取屏幕大小
		wManager.getDefaultDisplay().getMetrics(metrics);
		// 设置视频保持纵横比缩放到占满整个屏幕
		surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels,
				mPlayer.getVideoHeight() * metrics.widthPixels / mPlayer.getVideoWidth()));
		mPlayer.start();
	}

	private class SurfaceListener implements SurfaceHolder.Callback
	{
		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
			if (position > 0) {
				// 开始播放
				play();
				// 并直接从指定位置开始播放
				mPlayer.seekTo(position);
				position = 0;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{

		}
	}

	// 当其他Activity被打开时，暂停播放
	@Override
	public void onPause()
	{
		super.onPause();
		if (mPlayer.isPlaying()) {
			// 保存当前的播放位置
			position = mPlayer.getCurrentPosition();
			mPlayer.stop();
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 停止播放
		if (mPlayer.isPlaying()) mPlayer.stop();
		// 释放资源
		mPlayer.release();
	}
}
