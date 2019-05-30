package org.crazyit.media;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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
	// 定义播放声音的MediaPlayer
	private MediaPlayer mPlayer;
	// 定义系统的示波器
	private Visualizer mVisualizer;
	// 定义系统的均衡器
	private Equalizer mEqualizer;
	// 定义系统的重低音控制器
	private BassBoost mBass;
	// 定义系统的预设音场控制器
	private PresetReverb mPresetReverb;
	private LinearLayout layout;
	private List<Short> reverbNames = new ArrayList<>();
	private List<String> reverbVals = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 设置控制音乐声音
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0x123);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if(requestCode == 0x123	&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			// 创建MediaPlayer对象
			mPlayer = MediaPlayer.create(this, R.raw.beautiful);
			// 初始化示波器
			setupVisualizer();
			// 初始化均衡控制器
			setupEqualizer();
			// 初始化重低音控制器
			setupBassBoost();
			// 初始化预设音场控制器
			setupPresetReverb();
			// 开始播放音乐
			mPlayer.start();
		}
	}
	private void setupVisualizer()
	{
		// 创建MyVisualizerView组件，用于显示波形图
		final MyVisualizerView mVisualizerView = new MyVisualizerView(this);
		mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.
						LayoutParams.MATCH_PARENT,
				(int)(120f * getResources().getDisplayMetrics().density)));
		// 将MyVisualizerView组件添加到layout容器中
		layout.addView(mVisualizerView);
		// 以MediaPlayer的AudioSessionId创建Visualizer
		// 相当于设置Visualizer负责显示该MediaPlayer的音频数据
		mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
		mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
		// 为mVisualizer设置监听器
		mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener()
		{

			@Override
			public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate)
			{
				// 用waveform波形数据更新mVisualizerView组件
				mVisualizerView.updateVisualizer(waveform);
			}

			@Override
			public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate)
			{

			}
		}, Visualizer.getMaxCaptureRate() / 2, true, false);
		mVisualizer.setEnabled(true);
	}
	// 初始化均衡控制器的方法
	private void setupEqualizer()
	{
		// 以MediaPlayer的AudioSessionId创建Equalizer
		// 相当于设置Equalizer负责控制该MediaPlayer
		mEqualizer = new Equalizer(0, mPlayer.getAudioSessionId());
		// 启用均衡控制效果
		mEqualizer.setEnabled(true);
		TextView eqTitle = new TextView(this);
		eqTitle.setText("均衡器：");
		layout.addView(eqTitle);
		// 获取均衡控制器支持的最小值和最大值
		final short minEQLevel = mEqualizer.getBandLevelRange()[0];
		short maxEQLevel = mEqualizer.getBandLevelRange()[1];
		// 获取均衡控制器支持的所有频率
		short brands = mEqualizer.getNumberOfBands();
		for (short i = 0; i < brands; i++)
		{
			TextView eqTextView = new TextView(this);
			// 创建一个TextView，用于显示频率
			eqTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.
					LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			eqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
			// 设置该均衡控制器的频率
			eqTextView.setText(mEqualizer.getCenterFreq(i) / 1000 + " Hz");
			layout.addView(eqTextView);
			// 创建一个水平排列组件的LinearLayout
			LinearLayout tmpLayout = new LinearLayout(this);
			tmpLayout.setOrientation(LinearLayout.HORIZONTAL);
			// 创建显示均衡控制器最小值的TextView
			TextView minDbTextView = new TextView(this);
			minDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.
					LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			// 显示均衡控制器的最小值
			minDbTextView.setText(minEQLevel / 100 + " dB");
			// 创建显示均衡控制器最大值的TextView
			TextView maxDbTextView = new TextView(this);
			maxDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.
					LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			// 显示均衡控制器的最大值
			maxDbTextView.setText(maxEQLevel / 100 + " dB");
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.weight = 1f;
			// 定义SeekBar作为调整工具
			SeekBar bar = new SeekBar(this);
			bar.setLayoutParams(layoutParams);
			bar.setMax(maxEQLevel - minEQLevel);
			bar.setProgress(mEqualizer.getBandLevel(i));
			final short brand = i;
			// 为SeekBar的拖动事件设置事件监听器
			bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
			{
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
				{
					// 设置该频率的均衡值
					mEqualizer.setBandLevel(brand, (short)(progress + minEQLevel));
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar)
				{

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar)
				{

				}
			});
			// 使用水平排列组件的LinearLayout盛装三个组件
			tmpLayout.addView(minDbTextView);
			tmpLayout.addView(bar);
			tmpLayout.addView(maxDbTextView);
			// 将水平排列组件的LinearLayout添加到myLayout容器中
			layout.addView(tmpLayout);
		}
	}
	// 初始化重低音控制器
	private void setupBassBoost()
	{
		// 以MediaPlayer的AudioSessionId创建BassBoost
		// 相当于设置BassBoost负责控制该MediaPlayer
		mBass = new BassBoost(0, mPlayer.getAudioSessionId());
		// 设置启用重低音效果
		mBass.setEnabled(true);
		TextView bbTitle = new TextView(this);
		bbTitle.setText("重低音：");
		layout.addView(bbTitle);
		// 使用SeekBar作为重低音的调整工具
		SeekBar bar = new SeekBar(this);
		// 重低音的范围为0~1000
		bar.setMax(1000);
		bar.setProgress(0);
		// 为SeekBar的拖动事件设置事件监听器
		bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				// 设置重低音的强度
				mBass.setStrength((short)progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
			}
		});
		layout.addView(bar);
	}
	// 初始化预设音场控制器
	private void setupPresetReverb()
	{
		// 以MediaPlayer的AudioSessionId创建PresetReverb
		// 相当于设置PresetReverb负责控制该MediaPlayer
		mPresetReverb = new PresetReverb(0, mPlayer.getAudioSessionId());
		// 设置启用预设音场控制
		mPresetReverb.setEnabled(true);
		TextView prTitle = new TextView(this);
		prTitle.setText("音场");
		layout.addView(prTitle);
		// 获取系统支持的所有预设音场
		for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++)
		{
			reverbNames.add(i);
			reverbVals.add(mEqualizer.getPresetName(i));
		}
		// 使用Spinner作为音场选择工具
		Spinner sp = new Spinner(this);
		sp.setAdapter(new ArrayAdapter<>(MainActivity.this,
				android.R.layout.simple_spinner_item, reverbVals));
		// 为Spinner的列表项选中事件设置监听器
		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// 设定音场
				mEqualizer.usePreset(reverbNames.get(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		layout.addView(sp);
	}
	@Override public void onPause()
	{
		super.onPause();
		if (isFinishing() && mPlayer != null)
		{
			// 释放所有对象
			mVisualizer.release();
			mEqualizer.release();
			mPresetReverb.release();
			mBass.release();
			mPlayer.release();
			mPlayer = null;
		}
	}

	private static class MyVisualizerView extends View
	{
		// bytes数组保存了波形抽样点的值
		private byte[] bytes;
		private float[] points;
		private Paint paint = new Paint();
		private Rect rect = new Rect();
		private byte type = 0;

		public MyVisualizerView(Context context)
		{
			super(context);
			// 设置画笔的属性
			paint.setStrokeWidth(1f);
			paint.setAntiAlias(true);
			paint.setColor(Color.GREEN);
			paint.setStyle(Paint.Style.FILL);
		}


		public void updateVisualizer(byte[] ftt)
		{
			bytes = ftt;
			// 通知该组件重绘自己
			invalidate();
		}

		@Override
		public boolean onTouchEvent(MotionEvent me)
		{
			// 当用户触碰该组件时，切换波形类型
			if (me.getAction() != MotionEvent.ACTION_DOWN) {
				return false;
			}
			type++;
			if (type >= 3) {
				type = 0;
			}
			return true;
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			super.onDraw(canvas);
			if (bytes == null) {
				return;
			}
			// 绘制白色背景（主要为了印刷时好看）
			canvas.drawColor(Color.WHITE);
			// 使用rect对象记录该组件的宽度和高度
			rect.set(0, 0, getWidth(), getHeight());
			switch (type) {
				// -------绘制块状的波形图-------
				case 0:
					for (int i = 0; i < bytes.length - 1; i++) {
						float left = (getWidth() * i / (bytes.length - 1));
						// 根据波形值计算该矩形的高度
						float top = rect.height() - (byte) (bytes[i + 1] + 128) * rect.height() / 128;
						float right = left + 1;
						float bottom = rect.height();
						canvas.drawRect(left, top, right, bottom, paint);
					}
					break;
				// -------绘制柱状的波形图（每隔18个抽样点绘制一个矩形）-------
				case 1:
					for (int i = 0; i < bytes.length - 1; i += 18) {
						float left = rect.width() * i / (bytes.length - 1);
						// 根据波形值计算该矩形的高度
						float top = rect.height() - (byte) (bytes[i + 1] + 128)
								* rect.height() / 128;
						float right = left + 6;
						float bottom = rect.height();
						canvas.drawRect(left, top, right, bottom, paint);
						i += 18;
					}
					break;
				// -------绘制曲线波形图-------
				case 2:
					// 如果points数组还未初始化
					if (points == null || points.length < bytes.length * 4) {
						points = new float[bytes.length * 4];
					}
					for (int i = 0; i < bytes.length - 1; i++)
					{
						// 计算第i个点的x坐标
						points[i * 4] = rect.width()*i/(bytes.length - 1);
						// 根据bytes[i]的值（波形点的值）计算第i个点的y坐标
						points[i * 4 + 1] = (rect.height() / 2)
								+ ((byte) (bytes[i] + 128)) * 128
								/ (rect.height() / 2);
						// 计算第i+1个点的x坐标
						points[i * 4 + 2] = rect.width() * (i + 1)
								/ (bytes.length - 1);
						// 根据bytes[i+1]的值（波形点的值）计算第i+1个点的y坐标
						points[i * 4 + 3] = (rect.height() / 2)
								+ ((byte) (bytes[i + 1] + 128)) * 128
								/ (rect.height() / 2);
					}
					// 绘制波形曲线
					canvas.drawLines(points, paint);
					break;
			}
		}
	}
}
