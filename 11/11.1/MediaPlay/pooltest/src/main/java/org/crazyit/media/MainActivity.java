package org.crazyit.media;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

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
	// 定义一个SoundPool
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundMap = new HashMap<>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bombBn = findViewById(R.id.bomb);
		Button shotBn = findViewById(R.id.shot);
		Button arrowBn = findViewById(R.id.arrow);
		AudioAttributes attr = new AudioAttributes.Builder().setUsage(
			AudioAttributes.USAGE_GAME) // 设置音效使用场景
			// 设置音效的类型
			.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
		soundPool = new SoundPool.Builder().setAudioAttributes(attr) // 设置音效池的属性
				.setMaxStreams(10) // 设置最多可容纳10个音频流
				.build();  // ①
		// 使用load方法加载指定的音频文件，并返回所加载的音频ID
		// 此处使用HashMap来管理这些音频流
		soundMap.put(1, soundPool.load(this, R.raw.bomb, 1));  // ②
		soundMap.put(2, soundPool.load(this, R.raw.shot, 1));
		soundMap.put(3, soundPool.load(this, R.raw.arrow, 1));
		// 定义一个按钮的单击监听器
		View.OnClickListener listener = source -> {
			// 判断哪个按钮被单击
			switch (source.getId())
			{
				case R.id.bomb:
					soundPool.play(soundMap.get(1), 1f, 1f, 0, 0, 1f);  // ③
					break;
				case R.id.shot:
					soundPool.play(soundMap.get(2), 1f, 1f, 0, 0, 1f);
					break;
				case R.id.arrow:
					soundPool.play(soundMap.get(3), 1f, 1f, 0, 0, 1f);
					break;
			}
		};
		bombBn.setOnClickListener(listener);
		shotBn.setOnClickListener(listener);
		arrowBn.setOnClickListener(listener);
	}
}
