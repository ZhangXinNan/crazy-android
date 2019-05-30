package org.crazyit.service;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

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
public class Person implements Parcelable
{
	private int id;
	private String name;
	private String pass;

	public Person()
	{
	}
	public Person(Integer id, String name, String pass)
	{
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
	}

	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPass()
	{
		return pass;
	}
	public void setPass(String pass)
	{
		this.pass = pass;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return Objects.equals(name, person.name) &&
				Objects.equals(pass, person.pass);
	}
	@Override
	public int hashCode()
	{
		return Objects.hash(name, pass);
	}

	// 实现Parcelable接口必须实现的方法
	@Override
	public int describeContents()
	{
		return 0;
	}
	// 实现Parcelable接口必须实现的方法
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		// 把该对象所包含的数据写到Parcel
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(pass);
	}
	// 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
	public static final Creator<Person> CREATOR = new Creator<Person>() //①
	{
		@Override
		public Person createFromParcel(Parcel in)
		{
			return new Person(in.readInt(), in.readString(), in.readString());
		}
		@Override
		public Person[] newArray(int size)
		{
			return new Person[size];
		}
	};
}
