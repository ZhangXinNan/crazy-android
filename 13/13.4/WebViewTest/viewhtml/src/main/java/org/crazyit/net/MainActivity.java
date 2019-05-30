package org.crazyit.net;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

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
	private WebView showWv;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取程序中的WebView组件
		showWv = this.findViewById(R.id.show);
		StringBuilder sb = new StringBuilder();
		// 拼接一段HTML代码
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title> 欢迎您 </title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<h2> 欢迎您访问<a href=\"http://www.crazyit.org\">" + "疯狂Java联盟</a></h2>");
		sb.append("</body>");
		sb.append("</html>");
		// 下面两个方法都可正常加载、显示HTML代码
		showWv.loadData(sb.toString() , "text/html" , "utf-8");
		// 加载并显示HTML代码
//		showWv.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
	}
}
