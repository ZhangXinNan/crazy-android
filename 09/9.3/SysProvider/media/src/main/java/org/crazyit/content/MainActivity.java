package org.crazyit.content;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
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
	private List<String> names = new ArrayList<>();
	private List<String> descs = new ArrayList<>();
	private List<String> fileNames = new ArrayList<>();
	private RecyclerView show;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button addBn = findViewById(R.id.add);
		Button viewBn = findViewById(R.id.view);
		show = findViewById(R.id.show);
		// 为RecyclerView设置布局管理器
		show.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));
		// 为viewBn按钮的单击事件绑定监听器
		viewBn.setOnClickListener(view ->
				// 请求读取外部存储器的权限
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x123));

		// 为addBn按钮的单击事件绑定监听器
		addBn.setOnClickListener(view ->
				// 请求写入外部存储器的权限
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x456));
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
		@NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (grantResults[0] == 0) {
			if (requestCode == 0x123) {
				// 清空names、descs、fileNames集合里原有的数据
				names.clear();
				descs.clear();
				fileNames.clear();
				// 通过ContentResolver查询所有图片信息
				Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
				while (cursor.moveToNext())
				{
					// 获取图片的显示名
					String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
					// 获取图片的详细描述
					String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
					// 获取图片的保存位置的数据
					byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
					// 将图片名添加到names集合中
					names.add(name);
					// 将图片描述添加到descs集合中
					descs.add(desc);
					// 将图片保存路径添加到fileNames集合中
					fileNames.add(new String(data, 0, data.length - 1));
				}
				cursor.close();
				RecyclerView.Adapter adapter = new RecyclerView.Adapter<LineViewHolder>(){

					@NonNull @Override
					public LineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
					{
						View itemView = getLayoutInflater().inflate(R.layout.line,
								new LinearLayout(MainActivity.this), false);
						return new LineViewHolder(itemView);
					}

					@Override
					public void onBindViewHolder(@NonNull LineViewHolder lineViewHolder, int i)
					{
						lineViewHolder.nameView.setText(names.get(i) == null ? "null": names.get(i));
						lineViewHolder.descView.setText(descs.get(i) == null ? "null": descs.get(i));
					}

					@Override
					public int getItemCount()
					{
						return names.size();
					}
				};
				// 为show RecyclerView组件设置Adapter
				show.setAdapter(adapter);
			}
			if (requestCode == 0x456) {
// 创建ContentValues对象，准备插入数据
ContentValues values = new ContentValues();
values.put(MediaStore.Images.Media.DISPLAY_NAME, "jinta");
values.put(MediaStore.Images.Media.DESCRIPTION, "金塔");
values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
// 插入数据，返回所插入数据对应的Uri
Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
// 加载应用程序下的jinta图片
Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
	R.drawable.jinta);
try(
	// 获取刚插入的数据的Uri对应的输出流
	OutputStream os = getContentResolver().openOutputStream(uri)) // ①
{
	// 将bitmap图片保存到Uri对应的数据节点中
	bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
} catch (IOException e)	{
	e.printStackTrace();
}
Toast.makeText(MainActivity.this,"图片添加成功", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, R.string.permisssion_tip, Toast.LENGTH_SHORT)
					.show();
		}
	}
	class LineViewHolder extends RecyclerView.ViewHolder
	{
		TextView nameView, descView;
		public LineViewHolder(@NonNull View itemView)
		{
			super(itemView);
			nameView = itemView.findViewById(R.id.name);
			descView = itemView.findViewById(R.id.desc);
			itemView.setOnClickListener(view -> {
				// 加载view.xml界面布局代表的视图
				View viewDialog = getLayoutInflater().inflate(R.layout.view, null);
				// 获取viewDialog中ID为image的组件
				ImageView image = viewDialog.findViewById(R.id.image);
				// 设置image显示指定图片
				image.setImageBitmap(BitmapFactory.decodeFile(fileNames.get(getAdapterPosition())));
				// 使用对话框显示用户单击的图片
				new AlertDialog.Builder(MainActivity.this)
						.setView(viewDialog).setPositiveButton("确定", null).show();
			});
		}
	}
}
