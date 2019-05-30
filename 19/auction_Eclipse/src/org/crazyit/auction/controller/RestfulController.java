package org.crazyit.auction.controller;

import java.util.List;

import javax.annotation.Resource;

import org.crazyit.auction.business.BidBean;
import org.crazyit.auction.business.ItemBean;
import org.crazyit.auction.business.KindBean;
import org.crazyit.auction.domain.AuctionUser;
import org.crazyit.auction.domain.Bid;
import org.crazyit.auction.domain.Item;
import org.crazyit.auction.domain.Kind;
import org.crazyit.auction.service.AuctionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RestController // 使用该注解后相当于每个控制器方法自动添加@ResponseBody注解
@RequestMapping("/api")
public class RestfulController
{
	@Resource(name="mgr")
	protected AuctionManager auctionService;

	// 通过赢取者获取物品的方法
	@GetMapping("/items/byWiner")
	public ResponseEntity<List<ItemBean>> getItemByWiner(WebRequest webRequest)
		throws Exception
	{
		// 从HttpSession中取出userId属性
		Integer winerId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		List<ItemBean> itemBeans =  auctionService.getItemByWiner(winerId);
		if (itemBeans.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(itemBeans, HttpStatus.OK);
	}

	// 获取所有流拍物品的方法
	@GetMapping("/items/fail")
	public ResponseEntity<List<ItemBean>> getFailItems() throws Exception
	{
		List<ItemBean> failItems = auctionService.getFailItems();
		if (failItems.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(failItems, HttpStatus.OK);
	}

	// 处理用户登录的方法
	@PostMapping(value="/users/login")
	public ResponseEntity<String> validLogin(AuctionUser user
		, String vercode , WebRequest webRequest) throws Exception
	{
		String rand = (String)webRequest.getAttribute("rand", WebRequest.SCOPE_SESSION);
		webRequest.setAttribute("rand", null, WebRequest.SCOPE_SESSION);
		if (rand != null && !rand.equalsIgnoreCase(vercode))
		{
			return new ResponseEntity<>("-2", HttpStatus.OK);
		}
		int result = auctionService.validLogin(user.getUsername() , user.getUserpass());
		if (result > 0)
		{
			webRequest.setAttribute("userId", result, WebRequest.SCOPE_SESSION);
			return new ResponseEntity<>("1", HttpStatus.OK);
		}
		return new ResponseEntity<>("-1", HttpStatus.OK);
	}

	// 获取用户竞价的方法
	@GetMapping("/bids/byUser")
	public ResponseEntity<List<BidBean>> getBidByUser(WebRequest webRequest) throws Exception
	{
		// 从HttpSession中取出userId属性
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		List<BidBean> bids = auctionService.getBidByUser(userId);
		if (bids.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(bids, HttpStatus.OK);
	}

	@GetMapping("/items/byOwner")
	// 根据属主获取物品的方法
	public ResponseEntity<List<ItemBean>> getItemsByOwner(WebRequest webRequest) throws Exception
	{
		// 从HttpSession中取出userId属性
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		List<ItemBean> itemBeans = auctionService.getItemsByOwner(userId);
		if (itemBeans.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(itemBeans, HttpStatus.OK);
	}

	// 获取所有物品种类的方法
	@GetMapping("/kinds")
	public ResponseEntity<List<KindBean>> getAllKind() throws Exception
	{
		List<KindBean> kindBeans = auctionService.getAllKind();
		if (kindBeans.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(kindBeans, HttpStatus.OK);
	}

	// 根据种类获取物品的方法
	@GetMapping("/items/{kindId}")
	public ResponseEntity<List<ItemBean>> getItemsByKind(@PathVariable Integer kindId) throws Exception
	{
		List<ItemBean> itemBeans = auctionService.getItemsByKind(kindId);
		if (itemBeans.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(itemBeans, HttpStatus.OK);
	}

	// 根据物品ID获取物品的方法
	@GetMapping("/item/{itemId}")
	public ResponseEntity<ItemBean> getItemsById(@PathVariable Integer itemId) throws Exception
	{
		ItemBean itemBean = auctionService.getItem(itemId);
		if (itemBean == null)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(itemBean, HttpStatus.OK);
	}

	// 添加物品的方法
	@RequestMapping("/items")
	public ResponseEntity<String> addItem(Item item, Integer avail, Integer kindId,
		WebRequest webRequest)
		throws Exception
	{
		// 从HttpSession中取出userId属性
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		int rowNum = auctionService.addItem(item, avail, kindId, userId);
		// 显式设置响应使用utf-8字符集的响应头
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "text/plain;charset=utf-8");
		return rowNum > 0 ? new ResponseEntity<>("恭喜您，物品添加成功!", headers, HttpStatus.OK)
			: new ResponseEntity<>("对不起，物品添加失败!", headers, HttpStatus.OK);
	}

	// 添加种类的方法
	@PostMapping("/kinds")
	public ResponseEntity<String> addKind(Kind kind)
		throws Exception
	{
		int rowNum = auctionService.addKind(kind);
		// 显式设置响应使用utf-8字符集的响应头
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "text/plain;charset=utf-8");
		return rowNum > 0 ? new ResponseEntity<>("恭喜您，种类添加成功!", headers, HttpStatus.OK)
			: new ResponseEntity<>("对不起，种类添加失败!", headers, HttpStatus.OK);
	}

	// 添加竞价记录的方法
	@PostMapping("/bids")
	public ResponseEntity<String> addBid(Integer itemId , Bid bid , WebRequest webRequest)
		throws Exception
	{
		Integer userId = (Integer)webRequest.getAttribute("userId", WebRequest.SCOPE_SESSION);
		int id = auctionService.addBid(itemId , bid , userId);
		// 显式设置响应使用utf-8字符集的响应头
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "text/plain;charset=utf-8");
		return id > 0 ? new ResponseEntity<>("恭喜您，竞价成功!", headers, HttpStatus.OK)
			: new ResponseEntity<>("对不起，竞价失败!", headers, HttpStatus.OK);
	}
}
