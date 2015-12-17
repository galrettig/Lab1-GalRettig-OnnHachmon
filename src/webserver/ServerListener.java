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

	private final String ContentLengthHeader = "Content-Length: ";

	// Open Socket From Configuration Object And Read Input
	public void runListener(ConfigurationObject i_confObject)
	{
		ServerSocket serverSoc;
		try {

			serverSoc = new ServerSocket(Integer.parseInt(i_confObject.getPortNumber()));
			
			handleReadingFromSocket(serverSoc);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	public void handleReadingFromSocket(ServerSocket serverSoc){
		
			while(true) {
				Socket connection;
				BufferedReader inputClient;
				String line, fullRequest = "";
				String messageBodyString = null;
				int contentLength = -1;
				char[] msgBodyCharBuffer;
				StringBuilder messageBodyBuilder = null;
				try {

				connection = serverSoc.accept();
				fullRequest = "";
				inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				line = inputClient.readLine();
				
				// Read Request According to Http Protocol
				while(line != null && !line.equals(""))
				{
					// Check For Request With A Body Message
					if(line.indexOf(ContentLengthHeader) > -1){
						String bodyContentLengthAsString = line.substring(ContentLengthHeader.length());
						contentLength = Integer.parseInt(bodyContentLengthAsString);
					}

					fullRequest += (line + "\n");
					line = inputClient.readLine();
				}
				
				// Handle With Request that Contain Body Message
				if(contentLength > 0){
					msgBodyCharBuffer = new char[contentLength];
					inputClient.read(msgBodyCharBuffer);
					
					messageBodyBuilder = new StringBuilder();
					
					for(int i = 0; i < msgBodyCharBuffer.length; i++)
					{
						messageBodyBuilder.append(msgBodyCharBuffer[i]);
					}
					
					messageBodyString = messageBodyBuilder.toString();
					
					// TODO: Del
					//System.out.println("message body = " + messageBodyStr.toString());
					//fullRequest += messageBodyStr.toString();
				}
				//System.out.println(fullRequest);//full request obtained
				HTTPResponse http_response = this.handleRequest(fullRequest, messageBodyString, contentLength);
				handleResponse(http_response, connection);
				} catch (IOException e) {
					
					e.printStackTrace();
				} 
			}
		
	}


	public void handleResponse(HTTPResponse res, Socket connection){
		
		String response = res.GenerateResponse();
		
		try {
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(response);
			
			// TODO: Add here the option how to send the file Regular or Chuncked
			// Send The File and Close Response As Http protocol request
			if(res.getPathToFile() != null){
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
	
	// TODO: check if it's right to create http response here
	public HTTPResponse handleRequest(String i_fullRequest, String msgBody, int contentLength){
		
		// TODO: Del
		/*HTTPRequest req = Parser.parseHttp(i_fullRequest, msgBody);
		HTTPResponse res = new HTTPResponse(req.m_requestHeaders);
		return res;*/
		
		HTTPRequest req = new HTTPRequest(i_fullRequest, msgBody, contentLength);
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