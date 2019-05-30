package org.crazyit.service;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Pet implements Parcelable
{
	private String name;
	private double weight;
	public Pet()
	{
	}
	public Pet(String name, double weight)
	{
		super();
		this.name = name;
		this.weight = weight;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		// 把该对象所包含的数据写到Parcel
		dest.writeString(name);
		dest.writeDouble(weight);
	}
	// 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
	public static final Creator<Pet> CREATOR = new Creator<Pet>()
	{
		@Override
		public Pet createFromParcel(Parcel in)
		{
			// 从Parcel中读取数据，返回Person对象
			return new Pet(in.readString()
					, in.readDouble());
		}
		@Override
		public Pet[] newArray(int size)
		{
			return new Pet[size];
		}
	};
	@Override
	public String toString()
	{
		return "Pet [name=" + name + ", weight=" + weight + "]";
	}
}
