package org.crazyit.io;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

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
	private RecyclerView recyclerView;
	private TextView textView;
	// 记录当前的父文件夹
	private File currentParent;
	// 记录当前路径下的所有文件的文件数组
	private File[] currentFiles;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取列出全部文件的RecyclerView
		recyclerView = findViewById(R.id.recycler);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 设置RecyclerView的滚动方向
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// 为RecyclerView设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		textView = findViewById(R.id.path);
		// 运行时请求获取写入SD卡的权限
		requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
		String[] permissions, int[] grantResults)
	{
		if(requestCode == 0x123)
		{
			// 如果用户同意授权访问
			if(grantResults != null &&  grantResults[0] ==
				PackageManager.PERMISSION_GRANTED )
			{
				// 获取系统的SD卡的目录
				File root = new File(Environment.getExternalStorageDirectory().getPath());
				// 如果 SD卡存在
				if (root.exists())
				{
					currentParent = root;
					currentFiles = root.listFiles();
					// 使用当前目录下的全部文件、文件夹来填充inflateRecyclerView
					inflateRecyclerView(currentFiles);
				}

				// 获取上一级目录的按钮
				Button parent = findViewById(R.id.parent);
				parent.setOnClickListener(view -> {
					try {
						if (currentParent.getCanonicalPath().equals(
								Environment.getExternalStorageDirectory().getPath())) {
							// 获取上一级目录
							currentParent = currentParent.getParentFile();
							// 列出当前目录下的所有文件
							currentFiles = currentParent.listFiles();
							// 再次更新RecyclerView
							inflateRecyclerView(currentFiles);
						}
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				});
			}
			else
			{
				// 提示用户必须允许写入SD卡的权限
				Toast.makeText(this, R.string.writesd_tip, Toast.LENGTH_LONG)
						.show();
			}
		}
	}
	private void inflateRecyclerView(File[] files)  // ①
	{
		RecyclerView.Adapter adapter = new RecyclerView.Adapter<LineViewHolder>()
		{
			@NonNull @Override
			public LineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
			{
				LinearLayout line = (LinearLayout) getLayoutInflater().inflate(R.layout.line, null);
				return new LineViewHolder(line, this);
			}

			@Override
			public void onBindViewHolder(@NonNull LineViewHolder viewHolder, int i)
			{
				viewHolder.nameView.setText(files[i].getName());
				// 如果当前File是文件夹，使用folder图标；否则使用file图标
				if (files[i].isDirectory())
				{
					viewHolder.iconView.setImageResource(R.drawable.folder);
				}
				else
				{
					viewHolder.iconView.setImageResource(R.drawable.file);
				}
			}
			@Override
			public int getItemCount()
			{
				return files.length;
			}
		};
		// 为RecyclerView设置Adapter
		recyclerView.setAdapter(adapter);
		textView.setText(getResources().getString(R.string.cur_tip, currentParent.getPath()));
	}
	class LineViewHolder extends RecyclerView.ViewHolder
	{
		ImageView iconView;
		TextView nameView;
		public LineViewHolder(LinearLayout itemView, RecyclerView.Adapter adapte) {
			super(itemView);
			// 为RecyclerView的列表项的单击事件绑定监听器
			itemView.setOnClickListener(view -> {
				int position = getAdapterPosition();
				// 用户单击了文件，直接返回，不做任何处理
				if (currentFiles[position].isFile()) return;
				// 获取用户单击的文件夹下的所有文件
				File[] tmp = currentFiles[position].listFiles();
				if (tmp == null || tmp.length == 0)
				{
					Toast.makeText(MainActivity.this, "当前路径不可访问或该路径下没有文件",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					// 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
					currentParent = currentFiles[position]; // ②
					// 保存当前父文件夹内的全部文件和文件夹
					currentFiles = tmp;
					// 再次更新RecyclerView
					inflateRecyclerView(currentFiles);
				}
			});
			this.nameView = itemView.findViewById(R.id.file_name);
			this.iconView = itemView.findViewById(R.id.icon);
		}
	}
}
