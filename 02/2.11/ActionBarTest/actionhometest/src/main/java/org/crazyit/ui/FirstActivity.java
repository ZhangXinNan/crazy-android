package org.crazyit.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class FirstActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Button button = new Button(this);
		button.setText("启动第二个");
		setContentView(button);

		button.setOnClickListener(view -> {
			Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
			startActivity(intent);
		});
	}
}
