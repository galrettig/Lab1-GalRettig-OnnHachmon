package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

	public static void main(String[] args) throws IOException
	{
		String clientInput;
		ServerSocket serverSoc;
		try {
			serverSoc = new ServerSocket(8080);
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
