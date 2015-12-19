package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEscuchare {
	
	private SimpleThreadPool m_threadPool;
	
	public ServerEscuchare(SimpleThreadPool i_ThreadPool)
	{
		this.m_threadPool = i_ThreadPool;
	}
	
	public void start() throws IOException
	{
		int port = Integer.parseInt(ConfigurationObject.getPortNumber());
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		System.out.println("start listeneing on port: " + port);
		
		while (true) {
			Socket connection = serverSocket.accept();
			HandleRequest handleRequest = new HandleRequest(connection);
			this.m_threadPool.push(handleRequest);
		}
		
		
	}
		
}
