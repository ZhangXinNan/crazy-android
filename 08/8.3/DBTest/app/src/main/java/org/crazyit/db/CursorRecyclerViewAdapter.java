package org.crazyit.db;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CursorRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>
{
	private final Context context;
	private final int resId;
	private final String[] viewFields;
	private final Constructor<T> constructor;
	private final int[] columnIndexs;
	private List<Map<Integer, String>> cursorValues = new ArrayList<>();

	public CursorRecyclerViewAdapter(Context context, Cursor cursor,
		@LayoutRes int resId, int[] columnIndexs, String[] viewFields, Constructor<T> constructor)
	{
		this.context = context;
		this.resId = resId;
		this.columnIndexs = columnIndexs;
		this.viewFields = viewFields;
		this.constructor = constructor;
		// 遍历Cursor中的每条记录,将这些记录封装到List集合中
		while (cursor.moveToNext())
		{
			Map<Integer, String> row = new HashMap<>();
			for(int columnIndex : columnIndexs)
			{
				// 从Cursor中取出查询结果
				row.put(columnIndex, cursor.getString(columnIndex));
			}
			cursorValues.add(row);
		}
	}
	@NonNull @Override
	public T onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
	{
		// 加载列表项对应的布局文件
		View item = LayoutInflater.from(context).inflate(resId,
				new LinearLayout(context), false);
		try {
			// 将item封装成ViewHolder的子类后返回
			return constructor.newInstance(context, item);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void onBindViewHolder(@NonNull T viewHolder, int position)
	{
		Class<?> clazz = viewHolder.getClass();
		// 遍历viewHolder包含的各Field，为这些Field设置所显示的值
		for(int i = 0; i < viewFields.length; i++)
		{
			try {
				Field f = clazz.getDeclaredField(viewFields[i]);
				f.setAccessible(true);
				if (f.getType() == TextView.class)
				{
					TextView tv = (TextView) f.get(viewHolder);
					tv.setText(cursorValues.get(position).get(columnIndexs[i]));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public int getItemCount()
	{
		return cursorValues.size();
	}
}