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
@Table(name="item")
public class Item
{
	// 标识属性
	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	// 物品Remark
	@Column(name="item_remark")
	private String itemRemark;
	// 物品名称
	@Column(name="item_name")
	private String itemName;
	// 物品描述
	@Column(name="item_desc")
	private String itemDesc;
	// 物品添加时间
	private Date addtime;
	// 物品结束拍卖时间
	private Date endtime;
	// 物品的起拍价
	@Column(name="init_price")
	private double initPrice;
	// 物品的最高价
	@Column(name="max_price")
	private double maxPrice;
	// 该物品的所有者
	@ManyToOne(targetEntity=AuctionUser.class)
	@JoinColumn(name="owner_id", nullable=false)
	private AuctionUser owner;
	// 该物品所属的种类
	@ManyToOne(targetEntity=Kind.class)
	@JoinColumn(name="kind_id", nullable=false)
	private Kind kind;
	// 该物品的赢取者
	@ManyToOne(targetEntity=AuctionUser.class)
	@JoinColumn(name="winer_id", nullable=true)
	private AuctionUser winer;
	// 该物品所处的状态
	@ManyToOne(targetEntity=State.class)
	@JoinColumn(name="state_id", nullable=false)
	private State itemState;
	// 该物品对应的全部竞价记录
	@OneToMany(targetEntity=Bid.class ,
		mappedBy="bidItem")
	private Set<Bid> bids = new HashSet<Bid>();

	// 无参数的构造器
	public Item()
	{
	}
	// 初始化全部基本属性的构造器
	public Item( String itemName , String itemDesc
		, String itemRemark , double initPrice)
	{
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.itemRemark = itemRemark;
		this.initPrice = initPrice;
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

	// itemRemark的setter和getter方法
	public void setItemRemark(String itemRemark)
	{
		this.itemRemark = itemRemark;
	}
	public String getItemRemark()
	{
		return this.itemRemark;
	}

	// itemName的setter和getter方法
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	public String getItemName()
	{
		return this.itemName;
	}

	// itemDesc的setter和getter方法
	public void setItemDesc(String itemDesc)
	{
		this.itemDesc = itemDesc;
	}
	public String getItemDesc()
	{
		return this.itemDesc;
	}

	// addtime的setter和getter方法
	public void setAddtime(Date addtime)
	{
		this.addtime = addtime;
	}
	public Date getAddtime()
	{
		return this.addtime;
	}

	// endtime的setter和getter方法
	public void setEndtime(Date endtime)
	{
		this.endtime = endtime;
	}
	public Date getEndtime()
	{
		return this.endtime;
	}

	// initPrice的setter和getter方法
	public void setInitPrice(double initPrice)
	{
		this.initPrice = initPrice;
	}
	public double getInitPrice()
	{
		return this.initPrice;
	}

	// maxPrice的setter和getter方法
	public void setMaxPrice(double maxPrice)
	{
		this.maxPrice = maxPrice;
	}
	public double getMaxPrice()
	{
		return this.maxPrice;
	}

	// owner的setter和getter方法
	public void setOwner(AuctionUser owner)
	{
		this.owner = owner;
	}
	public AuctionUser getOwner()
	{
		return this.owner;
	}

	// kind的setter和getter方法
	public void setKind(Kind kind)
	{
		this.kind = kind;
	}
	public Kind getKind()
	{
		return this.kind;
	}

	// winer的setter和getter方法
	public void setWiner(AuctionUser winer)
	{
		this.winer = winer;
	}
	public AuctionUser getWiner()
	{
		return this.winer;
	}

	// itemState的setter和getter方法
	public void setItemState(State itemState)
	{
		this.itemState = itemState;
	}
	public State getItemState()
	{
		return this.itemState;
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