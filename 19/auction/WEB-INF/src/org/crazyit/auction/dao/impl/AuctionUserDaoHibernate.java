package org.crazyit.auction.dao.impl;

import java.util.List;

import org.crazyit.auction.dao.AuctionUserDao;
import org.crazyit.auction.domain.AuctionUser;
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
public class AuctionUserDaoHibernate
	extends BaseDaoHibernate5<AuctionUser> implements AuctionUserDao
{

	/**
	 * 根据用户名，密码查找用户
	 * @param username 查询所需的用户名
	 * @param pass 查询所需的密码
	 * @return 指定用户名、密码对应的用户
	 */
	public AuctionUser findUserByNameAndPass(String username , String pass)
	{
		// 执行HQL查询
		List<AuctionUser> ul = (List<AuctionUser>)find(
			"from AuctionUser au where au.username=?0 and au.userpass=?1" ,
			username , pass);
		// 返回查询得到的第一个AuctionUser对象
		if (ul.size() == 1)
		{
			return (AuctionUser)ul.get(0);
		}
		return null;
	}
}