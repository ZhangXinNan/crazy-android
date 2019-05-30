package org.fkjava;

import org.gradle.api.*
/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
class ItemPlugin implements Plugin<Project> {
	// 重写接口中apply方法
	void apply(Project project){
		// 为项目额外定义一个Item类型的item属性
		project.extensions.create("item", Item)
		// 调用task方法定义showInfo任务
		project.task('showItem') {
			doLast{
				println '商品名:' + project.item.name
				println '商品销售价：' + project.item.price * project.item.discount
			}
		}
	}
}
