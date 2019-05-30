package org.crazyit.io;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

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
	private TextToSpeech tts;
	private EditText editText;
	private Button speech;
	private Button record;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化TextToSpeech对象
		tts = new TextToSpeech(this, status -> {
			// 如果装载TTS引擎成功
			if (status == TextToSpeech.SUCCESS)
			{
				// 设置使用美式英语朗读
				int result = tts.setLanguage(Locale.US);
				// 如果不支持所设置的语言
				if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE &&
						result != TextToSpeech.LANG_AVAILABLE)
				{
					Toast.makeText(MainActivity.this,
							"TTS暂时不支持这种语言的朗读。",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					editText = findViewById(R.id.txt);
					speech = findViewById(R.id.speech);
					record = findViewById(R.id.record);
					speech.setOnClickListener(view ->
					{
						// 执行朗读
						tts.speak(editText.getText().toString(),
								TextToSpeech.QUEUE_ADD, null, "speech");
					});
					record.setOnClickListener(view ->
					{
						// 将朗读文本的音频记录到指定文件中
						tts.synthesizeToFile(editText.getText().toString(), null,
								new File(getFilesDir().toString() + "/sound.wav"), "record");
						Toast.makeText(MainActivity.this, "声音记录成功！",
								Toast.LENGTH_SHORT).show();
					});
				}
			}
		});
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 关闭TextToSpeech对象
		tts.shutdown();
	}
}
