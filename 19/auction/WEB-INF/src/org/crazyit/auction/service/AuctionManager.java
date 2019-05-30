package org.crazyit.auction.service;

import java.util.List;

import org.crazyit.auction.business.BidBean;
import org.crazyit.auction.business.ItemBean;
import org.crazyit.auction.business.KindBean;
import org.crazyit.auction.domain.Bid;
import org.crazyit.auction.domain.Item;
import org.crazyit.auction.domain.Kind;
import org.crazyit.auction.exception.AuctionException;

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
public interface AuctionManager
{
	/**
	 * 根据赢取者查询物品
	 * @param winerId 赢取者的ID
	 * @return 赢取者获得的全部物品
	 */
	List<ItemBean> getItemByWiner(Integer winerId)
		throws AuctionException;

	/**
	 * 查询流拍的全部物品
	 * @return 全部流拍物品
	 */
	List<ItemBean> getFailItems()throws AuctionException;

	/**
	 * 根据用户名，密码验证登录是否成功
	 * @param username 登录的用户名
 	 * @param pass 登录的密码
	 * @return 登录成功返回用户ID，否则返回-1
	 */
	int validLogin(String username , String pass)
		throws AuctionException;

	/**
	 * 查询用户的全部出价
	 * @param userId 竞价用户的ID
	 * @return 用户的全部出价
	 */
	List<BidBean> getBidByUser(Integer userId)
		throws AuctionException;

	/**
	 * 根据用户查找目前仍在拍卖中的全部物品
	 * @param userId 所属者的ID
	 * @return 属于当前用户的、处于拍卖中的全部物品。
	 */
	List<ItemBean> getItemsByOwner(Integer userId)
		throws AuctionException;

	/**
	 * 查询全部种类
	 * @return 系统中全部全部种类
	 */
	List<KindBean> getAllKind() throws AuctionException;

	/**
	 * 添加物品
	 * @param item 新增的物品
	 * @param avail 有效天数
	 * @param kindId 物品种类ID
	 * @param userId 添加者的ID
	 * @return 新增物品的主键
	 */
	int addItem(Item item, int avail , int kindId , Integer userId)
		throws AuctionException;

	/**
	 * 添加种类
	 * @param kind 新增的种类
	 * @return 新增种类的主键
	 */
	int addKind(Kind kind) throws AuctionException;

	/**
	 * 根据产品分类，获取处于拍卖中的全部物品
	 * @param kindId 种类id;
	 * @return 该类的全部产品
	 */
	List<ItemBean> getItemsByKind(int kindId) throws AuctionException;

	/**
	 * 根据种类id获取种类名
	 * @param kindId 种类id;
	 * @return 该种类的名称
	 */
	String getKind(int kindId) throws AuctionException;

	/**
	 * 根据物品id，获取物品
	 * @param itemId 物品id;
	 * @return 指定id对应的物品
	 */
	ItemBean getItem(int itemId) throws AuctionException;

	/**
	 * 增加新的竞价，并对竞价用户发邮件通知
	 * @param itemId 物品id;
	 * @param bid 竞价
	 * @param userId 竞价用户的ID
	 * @return 返回新增竞价记录的ID
	 */
	int addBid(int itemId , Bid bid ,Integer userId)
		throws AuctionException;

	/**
	 * 根据时间来修改物品的赢取者
	 */
	void updateWiner()throws AuctionException;
}