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
/*
				Socket connection = serverSoc.accept();
				String fullRequest = "";
				BufferedReader inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = inputClient.readLine();


				while(line != null && !line.equals(""))
				{

					fullRequest += (line + "\n");
					line = inputClient.readLine();
				}
				System.out.println(fullRequest);//full request obtained
				this.handleRequest(fullRequest);*/
				handleReadingFromSocket(serverSoc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleReadingFromSocket(ServerSocket serverSoc){
		Socket connection;
		int contentLengthValue = 0;
		BufferedReader inputClient;
		String line;
		String fullRequest = "";
		boolean keepReading = true, readMsgBody = false;

		try {
			connection = serverSoc.accept();

			inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			line = inputClient.readLine();


			while(keepReading)
			{
				if(line == null){
					break;
				}
				if(line.indexOf(ContentLengthHeader) > -1){
					contentLengthValue = Integer.parseInt(line.substring(ContentLengthHeader.length()));
				}
				if(contentLengthValue == 0 && line.equals("")){
					break;
				}
				if(contentLengthValue > 0 && line.equals("") && !readMsgBody){
					line = inputClient.readLine();
					contentLengthValue = 0;
				} else {


					line = inputClient.readLine();
				}
				fullRequest += line+"\n";
			}
			System.out.println(fullRequest);



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/// move to parsing class later
	public String[] parseFirstLine(String firstLine){
		String[] firstLineParams = firstLine.split(" ");
		if(firstLineParams.length != 3){
			System.out.println("error in params");
			return null;
		}

		return firstLineParams;
	}

	public void handleRequest(String i_fullRequest){
		Parser parser = new Parser();
		parser.parseHttp(i_fullRequest);

	}
}