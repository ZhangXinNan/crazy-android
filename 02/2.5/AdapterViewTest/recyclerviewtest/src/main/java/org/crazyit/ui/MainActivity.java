package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
	private RecyclerView recyclerView;
	private List<Person> personList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		recyclerView = findViewById(R.id.recycler);
		// 设置RecyclerView保持固定大小,这样可优化RecyclerView的性能
		recyclerView.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 设置RecyclerView的滚动方向
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// 为RecyclerView设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		initData();
		RecyclerView.Adapter adapter = new RecyclerView.Adapter<PersonViewHolder>()
		{
			// 创建列表项组件的方法，该方法创建组件会被自动缓存
			@Override
			public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
			{
				View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
				return new PersonViewHolder(view, this);
			}
			// 为列表项组件绑定数据的方法，每次组件重新显示出来时都会重新执行该方法
			@Override
			public void onBindViewHolder(PersonViewHolder viewHolder, int i)
			{
				viewHolder.nameTv.setText(personList.get(i).name);
				viewHolder.descTv.setText(personList.get(i).desc);
				viewHolder.headerIv.setImageResource(personList.get(i).header);
			}
			// 该方法的返回值决定包含多少个列表项
			@Override
			public int getItemCount()
			{
				return personList.size();
			}
		};
		recyclerView.setAdapter(adapter);
	}
	private void initData()
	{
		String[] names = new String[]{"虎头", "弄玉", "李清照", "李白"};
		String[] descs = new String[]{"可爱的小孩", "一个擅长音乐的女孩",
				"一个擅长文学的女性", "浪漫主义诗人"};
		int[] imageIds = new int[]{R.drawable.tiger,
				R.drawable.nongyu, R.drawable.qingzhao, R.drawable.libai};
		for(int i = 0; i < names.length; i++){
			this.personList.add(new Person(names[i], descs[i], imageIds[i]));
		}
	}
	class PersonViewHolder extends RecyclerView.ViewHolder
	{
		View rootView;
		TextView nameTv;
		TextView descTv;
		ImageView headerIv;

		public PersonViewHolder(View itemView, RecyclerView.Adapter adapter) {
			super(itemView);
			this.nameTv = itemView.findViewById(R.id.name);
			this.descTv = itemView.findViewById(R.id.desc);
			this.headerIv = itemView.findViewById(R.id.header);
			this.rootView = itemView.findViewById(R.id.item_root);
			// 为rootView(列表项组件)绑定事件监听器
			rootView.setOnClickListener(view -> {
				int i = (int)(Math.random() * (personList.size() + 1));
				Person person = new Person(personList.get(i).name,
						personList.get(i).desc, personList.get(i).header);
				adapter.notifyItemInserted(2);
				personList.add(2, person);
				adapter.notifyItemRangeChanged(2, adapter.getItemCount());
			});
			// 为rootView(列表项组件)绑定事件监听器
			rootView.setOnLongClickListener(view -> {
				int position = this.getAdapterPosition();
				// 通知RecyclerView执行动画
				adapter.notifyItemRemoved(position);
				// 删除底层数据模型中的数据
				MainActivity.this.personList.remove(position);
				// 通知RecyclerView执行实际的删除操作
				adapter.notifyItemRangeChanged(position, adapter.getItemCount());
				return false;
			});
		}
	}
}
