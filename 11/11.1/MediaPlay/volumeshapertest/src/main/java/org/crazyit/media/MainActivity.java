package org.crazyit.media;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.widget.Button;

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
	// 定义播放声音的MediaPlayer
	private MediaPlayer mPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建MediaPlayer对象
		mPlayer = MediaPlayer.create(this, R.raw.beautiful);
		Button bnBn = findViewById(R.id.play);
				// 为“播放”按钮绑定事件处理函数
		bnBn.setOnClickListener(view -> mPlayer.start());
		Button shaperBn = findViewById(R.id.shaper);
				// 为“应用效果”按钮绑定事件处理函数
		shaperBn.setOnClickListener(view -> {
			VolumeShaper.Configuration config = new VolumeShaper.Configuration.Builder()
				//  设置插值方式
				.setInterpolatorType(VolumeShaper.
						Configuration.INTERPOLATOR_TYPE_LINEAR)
				// 设置音量曲线
				.setCurve(new float[]{0f, 0.5f, 1f}, // 时间点
						new float[]{0f, 1f, 0f}) // 各时间点对应的音量
				// 设置持续时间
				.setDuration(1000 * 60 * 2)
				.build();
			VolumeShaper volumeShaper = mPlayer.createVolumeShaper(config);
			volumeShaper.apply(VolumeShaper.Operation.PLAY);
		});
	}
}
