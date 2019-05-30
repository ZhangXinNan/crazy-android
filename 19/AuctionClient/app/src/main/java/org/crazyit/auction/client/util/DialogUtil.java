package org.crazyit.auction.client.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import org.crazyit.auction.client.AuctionClientActivity;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class DialogUtil
{
	// 定义一个显示消息的对话框
	public static void showDialog(Context ctx, String msg, boolean goHome)
	{
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
				.setMessage(msg).setCancelable(false);
		if (goHome)
		{
			builder.setPositiveButton("确定", (dialog, which) -> {
				Intent i = new Intent(ctx, AuctionClientActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				ctx.startActivity(i);
			});
		}
		else
		{
			builder.setPositiveButton("确定", null);
		}
		builder.create().show();
	}

	// 定义一个显示指定组件的对话框
	public static void showDialog(Context ctx, View view)
	{
		new AlertDialog.Builder(ctx)
				.setView(view).setCancelable(false)
				.setPositiveButton("确定", null)
				.create()
				.show();
	}
}
