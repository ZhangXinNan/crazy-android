package org.crazyit.auction.dao.impl;

import java.util.List;

import org.crazyit.auction.dao.ItemDao;
import org.crazyit.auction.domain.Item;
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
public class ItemDaoHibernate
	extends BaseDaoHibernate5<Item> implements ItemDao
{
	/**
	 * 根据产品分类，获取当前拍卖的全部商品
	 * @param kindId 种类id;
	 * @return 该类的全部产品
	 */
	public List<Item> findItemByKind(Integer kindId)
	{
		return find(
			"from Item as i where i.kind.id=?0 and i.itemState.id=1"
			, kindId);
	}

	/**
	 * 根据所有者查找处于拍卖中的物品
	 * @param useId 所有者Id;
	 * @return 指定用户处于拍卖中的全部物品
	 */
	public List<Item> findItemByOwner(Integer userId)
	{
		return (List<Item>)find(
			"from Item as i where i.owner.id=?0 and i.itemState.id=1"
			, userId);
	}

	/**
	 * 根据赢取者查找物品
	 * @param userId 赢取者Id;
	 * @return 指定用户赢取的全部物品
	 */
	public List<Item> findItemByWiner(Integer userId)
	{
		return find("from Item as i where i.winer.id =?0 and i.itemState.id=2"
			,userId);
	}

	/**
	 * 根据物品状态查找物品
	 * @param stateId 状态Id;
	 * @return 该状态下的全部物品
	 */
	public List<Item> findItemByState(Integer stateId)
	{
		return find("from Item as i where i.itemState.id = ?0" , stateId);
	}
}