
import java.net.*;
import java.io.*;
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
public class SimpleServer
{
	public static void main(String[] args)
		throws IOException
	{
		// 创建一个ServerSocket，用于监听客户端Socket的连接请求
		ServerSocket ss = new ServerSocket(30000);  // ①
		// 采用循环不断接收来自客户端的请求
		while (true)
		{
			// 每当接收到客户端Socket的请求，服务器端也对应产生一个Socket
			Socket s = ss.accept();
			OutputStream os = s.getOutputStream();
			os.write("您好，您收到了服务器的新年祝福！\n"
				.getBytes("utf-8"));
			// 关闭输出流，关闭Socket
			os.close();
			s.close();
		}
	}
}

