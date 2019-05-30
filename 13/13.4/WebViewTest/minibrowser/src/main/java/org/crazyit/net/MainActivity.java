package org.crazyit.net;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;

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
	private EditText urlEt;
	private WebView showWv;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取页面中文本框、WebView组件
		urlEt = this.findViewById(R.id.url);
		showWv = findViewById(R.id.show);
		// 为键盘的软键盘绑定事件监听器
		urlEt.setOnEditorActionListener((view, actionId, event) -> {
			if (actionId == EditorInfo.IME_ACTION_GO)
			{
				String urlStr = urlEt.getText().toString();
				// 加载并显示urlStr对应的网页
				showWv.loadUrl(urlStr);
			}
			return true;
		});
	}
}
