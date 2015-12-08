package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

	public void runListener(ConfigurationObject i_confObject)
	{
		ServerSocket serverSoc;
		try {
			
			serverSoc = new ServerSocket(Integer.parseInt(i_confObject.m_Port));
			
			while(true) {

				Socket connection = serverSoc.accept();
				String fullRequest = "";
				BufferedReader inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = inputClient.readLine();
				
				while(line != null && !line.equals(""))
				{
					System.out.println(line);
					fullRequest += line;
					line = inputClient.readLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
