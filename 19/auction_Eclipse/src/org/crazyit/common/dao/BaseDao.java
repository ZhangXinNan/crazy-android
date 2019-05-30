package org.crazyit.common.dao;

import java.util.List;
import java.io.Serializable;

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
public interface BaseDao<T>
{
	// 根据ID加载实体
	T get(Class<T> entityClazz , Serializable id);
	// 保存实体
	Serializable save(T entity);
	// 更新实体
	void update(T entity);
	// 删除实体
	void delete(T entity);
	// 根据ID删除实体
	void delete(Class<T> entityClazz , Serializable id);
	// 获取所有实体
	List<T> findAll(Class<T> entityClazz);
	// 获取实体总数
	long findCount(Class<T> entityClazz);
}
