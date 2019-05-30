import java.net.*;
import java.io.*;
import java.util.*;

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
public class MyServer
{
	// 定义保存所有Socket的List
	public static List<Socket> socketList
		= Collections.synchronizedList(new ArrayList<Socket>());
    public static void main(String[] args)
		throws IOException
    {
		ServerSocket ss = new ServerSocket(30000);
		while(true)
		{
			// 此行代码会阻塞，将一直等待别人的连接
			Socket s = ss.accept();
			socketList.add(s);
			// 每当客户端连接后启动一条ServerThread线程为该客户端服务
			new Thread(new ServerThread(s)).start();
		}
    }
}
