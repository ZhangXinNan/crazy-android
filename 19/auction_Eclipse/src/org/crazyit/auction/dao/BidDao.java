package org.crazyit.auction.dao;

import java.util.List;

import org.crazyit.auction.domain.AuctionUser;
import org.crazyit.auction.domain.Bid;
import org.crazyit.common.dao.BaseDao;

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
public interface BidDao extends BaseDao<Bid>
{
	/**
	 * 根据用户查找竞价
	 * @param id 用户id
	 * @return 用户对应的全部
	 * @return 用户对应的全部竞价
	 */
	List<Bid> findByUser(Integer userId);

	/**
	 * 根据物品id，以及出价查询用户
	 * @param itemId 物品id;
	 * @param price 竞价的价格
	 * @return 对指定物品、指定竞价对应的用户
	 */
	AuctionUser findUserByItemAndPrice(Integer itemId , Double price);
}
