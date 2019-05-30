package org.crazyit.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
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
	private TextView show;
	private String[] items = new String[]{"疯狂Java讲义", "疯狂前端开发讲义",
			"轻量级Java EE企业应用实战", "疯狂Android讲义"};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		show = findViewById(R.id.show);
	}
	public void simple(View source)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				// 设置对话框标题
				.setTitle("简单对话框")
				// 设置图标
				.setIcon(R.drawable.tools).setMessage("对话框的测试内容\n第二行内容");
		// 为AlertDialog.Builder添加“确定”按钮
		setPositiveButton(builder);
		// 为AlertDialog.Builder添加“取消”按钮
		setNegativeButton(builder).create().show();
	}

	private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder)
	{
		// 调用setPositiveButton方法添加“确定”按钮
		return builder.setPositiveButton("确定", (dialog, which) -> show.setText("单击了【确定】按钮！"));
	}
	private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder)
	{
		// 调用setNegativeButton方法添加“取消”按钮
		return builder.setNegativeButton("取消", (dialog, which) -> show.setText("单击了【取消】按钮！"));
	}

	public void simpleList(View source)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				// 设置对话框标题
				.setTitle("简单列表项对话框")
				// 设置图标
				.setIcon(R.drawable.tools)
				// 设置简单的列表项内容
				.setItems(items,  (dialog, wh) -> show.setText("你选中了《" + items[wh] + "》"));
		// 为AlertDialog.Builder添加“确定”按钮
		setPositiveButton(builder);
		// 为AlertDialog.Builder添加“取消”按钮
		setNegativeButton(builder).create().show();
	}

	public void singleChoice(View source)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				// 设置对话框标题
				.setTitle("单选列表项对话框")
				// 设置图标
				.setIcon(R.drawable.tools)
				// 设置单选列表项，默认选中第二项（索引为1）
				.setSingleChoiceItems(items, 1, (dialog, which)
						-> show.setText("你选中了《" + items[which] + "》"));
		// 为AlertDialog.Builder添加“确定”按钮
		setPositiveButton(builder);
		// 为AlertDialog.Builder添加“取消”按钮
		setNegativeButton(builder).create().show();
	}

	public void multiChoice(View source)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				// 设置对话框标题
				.setTitle("多选列表项对话框")
				// 设置图标
				.setIcon(R.drawable.tools)
				// 设置多选列表项，设置勾选第2项、第4项
				.setMultiChoiceItems(items, new boolean[]{false, true, false, true}, null);
		// 为AlertDialog.Builder添加“确定”按钮
		setPositiveButton(builder);
		// 为AlertDialog.Builder添加“取消”按钮
		setNegativeButton(builder).create().show();
	}

	public void customList(View source)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				// 设置对话框标题
				.setTitle("自定义列表项对话框")
				// 设置图标
				.setIcon(R.drawable.tools)
				// 设置自定义列表项
				.setAdapter(new ArrayAdapter(this, R.layout.array_item, items), null);
		// 为AlertDialog.Builder添加“确定”按钮
		setPositiveButton(builder);
		// 为AlertDialog.Builder添加“取消”按钮
		setNegativeButton(builder).create().show();
	}

	public void customView(View source)
	{
		// 加载\res\layout\login.xml界面布局文件
		TableLayout loginForm = (TableLayout) getLayoutInflater().inflate(R.layout.login, null);
		new AlertDialog.Builder(this)
				// 设置对话框的图标
				.setIcon(R.drawable.tools)
				// 设置对话框的标题
				.setTitle("自定义View对话框")
				// 设置对话框显示的View对象
				.setView(loginForm)
				// 为对话框设置一个“确定”按钮
				.setPositiveButton("登录", (dialog, which) -> {
					// 此处可执行登录处理
				})
				// 为对话框设置一个“取消”按钮
				.setNegativeButton("取消", (dialog, which) -> {
					// 取消登录，不做任何事情
				})
				// 创建并显示对话框
				.create().show();
	}

}
