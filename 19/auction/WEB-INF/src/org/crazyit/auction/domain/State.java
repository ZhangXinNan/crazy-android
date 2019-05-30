package org.crazyit.auction.domain;

import java.util.*;

import javax.persistence.*;

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
@Entity
@Table(name="state")
public class State
{
	// 标识属性
	@Id
	@Column(name="state_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	// 物品的状态名
	@Column(name="state_name")
	private String stateName;
	// 该状态下的所有物品
	@OneToMany(targetEntity=Item.class ,
		mappedBy="itemState")
	private Set<Item> items = new HashSet<Item>();

	// 无参数的构造器
	public State()
	{
	}
	// 初始化全部基本属性的构造器
	public State(Integer id , String stateName)
	{
		this.id = id;
		this.stateName = stateName;
	}

	// id的setter和getter方法
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId()
	{
		return this.id;
	}

	// stateName的setter和getter方法
	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}
	public String getStateName()
	{
		return this.stateName;
	}

	// items的setter和getter方法
	public void setItems(Set<Item> items)
	{
		this.items = items;
	}
	public Set<Item> getItems()
	{
		return this.items;
	}
}