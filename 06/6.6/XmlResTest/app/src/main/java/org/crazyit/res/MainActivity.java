package org.crazyit.res;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取bn按钮，并为该按钮绑定事件监听器
		Button bn = findViewById(R.id.bn);
		bn.setOnClickListener(view -> {
			// 根据XML资源的ID获取解析该资源的解析器
			// getXml()方法返回XmlResourceParser对象
			// XmlResourceParser是XmlPullParser的子类
			XmlResourceParser xrp = getResources().getXml(R.xml.books);
			try {
				StringBuilder sb = new StringBuilder();
				// 还没有到XML文档的结尾处
				while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
					// 如果遇到开始标签
					if (xrp.getEventType() == XmlResourceParser.START_TAG) {
						// 获取该标签的标签名
						String tagName = xrp.getName();
						// 如果遇到book标签
						if (tagName.equals("book")) {
							// 根据属性名来获取属性值
							String bookName = xrp.getAttributeValue(null, "price");
							sb.append("价格：");
							sb.append(bookName);
							// 根据属性索引来获取属性值
							String bookPrice = xrp.getAttributeValue(1);
							sb.append("	出版日期：");
							sb.append(bookPrice);
							sb.append(" 书名：");
							// 获取文本节点的值
							sb.append(xrp.nextText());
						}
						sb.append("\n");
					}
					// 获取解析器的下一个事件
					xrp.next(); // ①
				}
				TextView show = findViewById(R.id.show);
				show.setText(sb.toString());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
