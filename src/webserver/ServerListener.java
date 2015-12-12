package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

	private String ContentLengthHeader = "Content-Length: ";

	public void runListener(ConfigurationObject i_confObject)
	{
		ServerSocket serverSoc;
		try {

			serverSoc = new ServerSocket(Integer.parseInt(i_confObject.m_Port));

			while(true) {

				handleReadingFromSocket(serverSoc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	public void handleReadingFromSocket(ServerSocket serverSoc){
		Socket connection;
		BufferedReader inputClient;
		String line, fullRequest = "", messageBodyString = null;
		int contentLength = -1;
		char[] msgBody;
		StringBuilder messageBodyBuilder = null;

		try {

			while(true) {

				connection = serverSoc.accept();
				fullRequest = "";
				inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				line = inputClient.readLine();


				while(line != null && !line.equals(""))
				{
					if(line.indexOf(ContentLengthHeader) > -1){
						String leng = line.substring(ContentLengthHeader.length());
						contentLength = Integer.parseInt(leng);
					}

					fullRequest += (line + "\n");
					line = inputClient.readLine();
				}
				
				if(contentLength > 0){
					msgBody = new char[contentLength];
					inputClient.read(msgBody);
					
					messageBodyBuilder = new StringBuilder();
					for(int i = 0; i < msgBody.length; i++){
						messageBodyBuilder.append(msgBody[i]);
					}
					messageBodyString = messageBodyString.toString();
					//System.out.println("message body = " + messageBodyStr.toString());
					//fullRequest += messageBodyStr.toString();
				}
				//System.out.println(fullRequest);//full request obtained
				this.handleRequest(fullRequest, messageBodyString);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}



	public void handleRequest(String i_fullRequest, String msgBody){
		
		Parser.parseHttp(i_fullRequest, msgBody);

	}
}