package org.crazyit.res;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

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
	private MediaPlayer mediaPlayer1;
	private MediaPlayer mediaPlayer2;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 直接根据声音文件的ID来创建MediaPlayer
		mediaPlayer1 = MediaPlayer.create(this, R.raw.bomb);
		// 获取该应用的AssetManager
		AssetManager am = getAssets();
		try {
			// 获取指定文件对应的AssetFileDescriptor
			AssetFileDescriptor afd = am.openFd("shot.mp3");
			mediaPlayer2 = new MediaPlayer();
			// 使用MediaPlayer加载指定的声音文件
			mediaPlayer2.setDataSource(afd.getFileDescriptor());
			mediaPlayer2.prepare();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		// 获取第一个按钮，并为它绑定事件监听器
		Button playRaw = findViewById(R.id.playRaw);
		playRaw.setOnClickListener(view -> mediaPlayer1.start() /* 播放声音 */);
		// 获取第二个按钮，并为它绑定事件监听器
		Button playAsset = findViewById(R.id.playAsset);
		playAsset.setOnClickListener(view -> mediaPlayer2.start()/* 播放声音 */);
	}
}
