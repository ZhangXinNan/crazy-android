package org.fkjava;

import org.gradle.api.*
import org.gradle.api.tasks.*
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
class ShowTask extends DefaultTask {
	@Optional
	File file = new File('dist.txt')

	@TaskAction
	def show(){
		println file.text
	}

	// 该注解用于把该方法标注为默认执行的action
	@TaskAction
	def multiShow(){
		println "-------------"
		println file.text
		println "-------------"
	}
}
