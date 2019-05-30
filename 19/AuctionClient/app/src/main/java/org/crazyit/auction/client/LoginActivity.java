package org.crazyit.auction.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

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
public class LoginActivity extends Activity
{
	// 定义界面中两个文本框
	private EditText etName;
	private EditText etPass;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// 获取界面中两个编辑框
		etName = findViewById(R.id.userEditText);
		etPass = findViewById(R.id.pwdEditText);
		// 定义并获取界面中两个按钮
		Button bnLogin = findViewById(R.id.bnLogin);
		Button bnCancel = findViewById(R.id.bnCancel);
		// 为bnCancal按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(this));
		bnLogin.setOnClickListener(view -> {
			// 执行输入校验
			if (validate()) // ①
			{
				// 如果登录成功
				if (loginPro()) // ②
				{
					// 启动AuctionClientActivity
					Intent intent = new Intent(LoginActivity.this, AuctionClientActivity.class);
					startActivity(intent);
					// 结束该Activity
					finish();
				}
				else
				{
					DialogUtil.showDialog(LoginActivity.this,
							"用户名或者密码错误，请重新输入！", false);
				}
			}
		});
	}

	private boolean loginPro()
	{
		// 获取用户输入的用户名、密码
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		try
		{
			String result = query(username, pwd);
			// 如果result大于0
			if (result != null && Integer.parseInt(result) > 0)
			{
				return true;
			}
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		return false;
	}

	// 对用户输入的用户名、密码进行校验
	private boolean validate()
	{
		String username = etName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "用户账户是必填项！", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		if (pwd.equals(""))
		{
			DialogUtil.showDialog(this, "用户口令是必填项！", false);
			return false;
		}
		return true;
	}

	// 定义发送请求的方法
	private String query(String username, String password) throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("userpass", password);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "users/login";
		// 发送请求
		return HttpUtil.postRequest(url, map);
	}
}
