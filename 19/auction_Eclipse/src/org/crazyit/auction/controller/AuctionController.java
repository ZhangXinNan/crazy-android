package org.crazyit.auction.controller;

import javax.annotation.Resource;

import org.crazyit.auction.domain.AuctionUser;
import org.crazyit.auction.domain.Bid;
import org.crazyit.auction.domain.Item;
import org.crazyit.auction.domain.Kind;
import org.crazyit.auction.service.AuctionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

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
@Controller
public class AuctionController
{
	@Resource(name="mgr")
	protected AuctionManager mgr;

	// 处理登录的控制器方法
	@PostMapping("/proLogin")
	public String proLogin(AuctionUser user, String vercode,
		WebRequest webRequest, ModelMap map)
	{
		// 获取Session中随机验证码字符串
		String ver2 = (String )webRequest.getAttribute("rand", WebRequest.SCOPE_SESSION);
		// 清空Session中的随机验证码字符串。
		webRequest.setAttribute("rand" , null, WebRequest.SCOPE_SESSION);
		if (vercode.equals(ver2))
		{
			Integer userId = mgr.validLogin(user.getUsername(),user.getUserpass());
			if (userId != null && userId > 0)
			{
				webRequest.setAttribute("userId" , userId, WebRequest.SCOPE_SESSION);
				map.put("tip", "登录成功，欢迎使用电子拍卖系统");
				return "main";
			}
			else
			{
				map.put("errorTip", "用户名/密码不匹配");
				return "login";
			}
		}
		else
		{
			map.put("errorTip", "验证码不匹配,请重新输入");
			return "login";
		}
	}

	// 查看自己的竞拍的控制器方法
	@GetMapping("/viewBids")
	public String vidBids(WebRequest webRequest, ModelMap map)
	{
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		map.put("bids", mgr.getBidByUser(userId));
		return "viewBids";
	}
	// 查看流拍物品的控制器方法
	@GetMapping("/viewFails")
	public String viewFails(ModelMap map)
	{
		map.put("failItems", mgr.getFailItems());
		return "viewFails";
	}
	// 查看所有物品种类的控制器方法
	@GetMapping("/viewCategories")
	public String viewCategories(ModelMap map)
	{
		map.put("categories", mgr.getAllKind());
		return "viewCategories";
	}
	// 查看所有赢得物品的控制器方法
	@GetMapping("/viewSuccs")
	public String viewSuccs(WebRequest webRequest, ModelMap map)
	{
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		map.put("items", mgr.getItemByWiner(userId));
		return "viewSuccs";
	}
	// 查看自己的拍卖物品的控制器方法
	@GetMapping("/viewOwnerItems")
	public String viewOwnerItems(WebRequest webRequest, ModelMap map)
	{
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		map.put("items", mgr.getItemsByOwner(userId));
		return "viewOwnerItems";
	}

	@PostMapping("/proAddCategory")
	public String proAddCategory(Kind kind)throws Exception
	{
		System.out.println(kind.getKindName());
		// 添加种类
		mgr.addKind(kind);
		return "redirect:/viewCategories";
	}

	@GetMapping("/addItem")
	public String addItem(ModelMap map)
	{
		map.put("categories", mgr.getAllKind());
		return "addItem";
	}
	@PostMapping("/proAddItem")
	public String proAddItem(WebRequest webRequest, Item item, int avail, int kindId)throws Exception
	{
		// 根据用户选择有效时间选项，指定实际的有效时间
		switch(avail)
		{
			case 6 :
				avail = 7;
				break;
			case 7 :
				avail = 30;
				break;
			case 8 :
				avail = 365;
				break;
		}
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		// 添加物品
		mgr.addItem(item ,avail , kindId, userId);
		return "redirect:/viewOwnerItems";
	}
	@GetMapping("/viewInBid")
	public String viewInBid(Integer cateId, ModelMap map)
	{
		map.put("categories", mgr.getAllKind());
		if (cateId != null && cateId > 0)
		{
			map.put("items", mgr.getItemsByKind(cateId));
		}
		return "viewInBid";
	}
	@RequestMapping("/viewDetail")
	public String viewDetail(Integer itemId, ModelMap map)
	{
		if (itemId != null && itemId > 0)
		{
			map.put("item", mgr.getItem(itemId));
		}
		return "viewDetail";
	}

	// 处理用户竞价
	@PostMapping("/addBid")
	public String addBid(WebRequest webRequest, Bid bid,
		Double maxPrice, Integer itemId, String vercode, ModelMap map)
	{
		// 用户竞价必须大于物品的当前最高价
		if(bid.getBidPrice() <= maxPrice)
		{
			map.put("errorTip", "您输入的竞价必须高于当前最高价！");
			return "forward:/viewDetail?itemId=" + itemId;
		}
		// 取出Session中的userId和刚刚生成的随机验证码
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		String ver2 = (String)webRequest.getAttribute("rand", WebRequest.SCOPE_SESSION);
		webRequest.setAttribute("rand" , null, WebRequest.SCOPE_SESSION);
		// 如果用户输入的验证码和Session中的随机验证码相同
		if (vercode.equals(ver2))
		{
			mgr.addBid(itemId , bid ,userId);
			return "redirect:/viewBids";
		}
		else
		{
			map.put("errorTip", "验证码不匹配,请重新输入");
			return "forward:/viewDetail?itemId=" + itemId;
		}
	}
}