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
@Table(name="auction_user")
public class AuctionUser
{
	// 标识属性
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	// 用户名属性
	private String username;
	// 密码属性
	private String userpass;
	// 电子邮件属性
	private String email;

	// 根据属主关联的物品实体
	@OneToMany(targetEntity=Item.class ,
		mappedBy="owner")
	private Set<Item> itemsByOwner = new HashSet<Item>();
	// 根据赢取者关联的物品实体
	@OneToMany(targetEntity=Item.class ,
		mappedBy="winer")
	private Set<Item> itemsByWiner = new HashSet<Item>();
	// 该用户所参与的全部竞价
	@OneToMany(targetEntity=Bid.class ,
		mappedBy="bidUser")
	private Set<Bid> bids = new HashSet<Bid>();

	// 无参数的构造器
	public AuctionUser()
	{
	}
	// 初始化全部成员变量的构造器
	public AuctionUser(Integer id , String username
		, String userpass , String email)
	{
		this.id = id;
		this.username = username;
		this.userpass = userpass;
		this.email = email;
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

	// username的setter和getter方法
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}

	// userpass的setter和getter方法
	public void setUserpass(String userpass)
	{
		this.userpass = userpass;
	}
	public String getUserpass()
	{
		return this.userpass;
	}

	// email的setter和getter方法
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEmail()
	{
		return this.email;
	}

	// itemsByOwner的setter和getter方法
	public void setItemsByOwner(Set<Item> itemsByOwner)
	{
		this.itemsByOwner = itemsByOwner;
	}
	public Set<Item> getItemsByOwner()
	{
		return this.itemsByOwner;
	}

	// itemsByWiner的setter和getter方法
	public void setItemsByWiner(Set<Item> itemsByWiner)
	{
		this.itemsByWiner = itemsByWiner;
	}
	public Set<Item> getItemsByWiner()
	{
		return this.itemsByWiner;
	}

	// bids的setter和getter方法
	public void setBids(Set<Bid> bids)
	{
		this.bids = bids;
	}
	public Set<Bid> getBids()
	{
		return this.bids;
	}
}