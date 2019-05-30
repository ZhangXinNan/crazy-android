package org.crazyit.service;

import android.app.Activity;
import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

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
	private AudioManager aManager;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取系统的音频服务
		aManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		// 获取界面中三个按钮和一个ToggleButton控件
		Button playBn = findViewById(R.id.play);
		Button upBn = findViewById(R.id.up);
		Button downBn = findViewById(R.id.down);
		ToggleButton muteTb = findViewById(R.id.mute);
		// 为playBn按钮的单击事件绑定监听器
		playBn.setOnClickListener(view -> {
			// 初始化MediaPlayer对象，准备播放音乐
			MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.earth);
			// 设置循环播放
			mPlayer.setLooping(true);
			// 开始播放
			mPlayer.start();
		});
		upBn.setOnClickListener(view ->
			// 指定调节音乐的音频，增大音量，而且显示音量图形示意
			aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI));
		downBn.setOnClickListener(view ->
			// 指定调节音乐的音频，降低音量，而且显示音量图形示意
			aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI));

		muteTb.setOnCheckedChangeListener((source, isChecked) -> {
			// 指定调节音乐的音频，根据isChecked确定是否需要静音
			aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				isChecked ? AudioManager.ADJUST_MUTE
				: AudioManager.ADJUST_UNMUTE,
						AudioManager.FLAG_SHOW_UI);
			});
	}
}
