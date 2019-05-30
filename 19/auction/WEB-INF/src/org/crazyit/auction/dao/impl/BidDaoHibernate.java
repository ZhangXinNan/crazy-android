package org.crazyit.auction.dao.impl;

import java.util.List;

import org.crazyit.auction.dao.BidDao;
import org.crazyit.auction.domain.AuctionUser;
import org.crazyit.auction.domain.Bid;
import org.crazyit.common.dao.impl.BaseDaoHibernate5;

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
public class BidDaoHibernate
	extends BaseDaoHibernate5<Bid> implements BidDao
{
	/**
	 * 根据用户查找竞价
	 * @param id 用户id
	 * @return 用户对应的全部
	 * @return 用户对应的全部竞价
	 */
	public List<Bid> findByUser(Integer userId)
	{
		return (List<Bid>)find(
			"from Bid as bid where bid.bidUser.id=?0" , userId);
	}
	/**
	 * 根据物品id，以及出价查询用户
	 * @param itemId 物品id;
	 * @param price 竞价的价格
	 * @return 对指定物品、指定竞价对应的用户
	 */
	public AuctionUser findUserByItemAndPrice(Integer itemId , Double price)
	{
		// 执行HQL查询
		List<Bid> l = (List<Bid>)find(
			"from Bid as bid where bid.bidItem.id=?0 and bid.bidPrice=?1"
			, new Object[]{itemId , price});
		// 返回查询得到的第一个Bid对象关联的AuctionUser对象
		if (l.size() >= 1)
		{
			Bid b = (Bid)l.get(0);
			return b.getBidUser();
		}
		return null;
	}
}
