package org.crazyit.domain;

import java.io.Serializable;

public class Person implements Serializable
{
	private String name;
	private String passwd;
	private String gender;

	public Person(String name, String passwd, String gender)
	{
		this.name = name;
		this.passwd = passwd;
		this.gender = gender;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPasswd()
	{
		return passwd;
	}

	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}
}
