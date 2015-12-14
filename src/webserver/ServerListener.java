package webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
				HTTPResponse res = this.handleRequest(fullRequest, messageBodyString);
				handleResponse(res, connection);
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}


	public void handleResponse(HTTPResponse res, Socket connection){
		String response = res.GenerateResponse();
		try {
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(response);
			if(res.m_PathTofile != null){
				byte[] fileToSend = readFile(new File(res.getPathToFile()));
				
				writer.flush();
				writer.write(fileToSend, 0, fileToSend.length);
				writer.flush();
				writer.writeBytes("/r/n");
				writer.flush();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HTTPResponse handleRequest(String i_fullRequest, String msgBody){
		
		HTTPRequest req = Parser.parseHttp(i_fullRequest, msgBody);
		HTTPResponse res = new HTTPResponse(req.m_requestHeaders);
		return res;
	}
	
	
	
	private byte[] readFile(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			byte[] bFile = new byte[(int)file.length()];
			// read until the end of the stream.
			while(fis.available() != 0)
			{
				fis.read(bFile, 0, bFile.length);
			}
			fis.close();
			return bFile;
		}
		catch(FileNotFoundException e)
		{
			// do something
		}
		catch(IOException e)
		{
			// do something
		}
		return null;
	}
	
	
	
	
	
	
}