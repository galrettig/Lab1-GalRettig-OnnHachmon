package webserver;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HandleRequest implements Runnable {

	private final Socket connection;

	private HTTPResponse response;

	BufferedReader inputClient;
	String line, fullRequest;
	String messageBodyString;
	int contentLength;
	char[] msgBodyCharBuffer;
	StringBuilder messageBodyBuilder;

	private final String ContentLengthHeader = "Content-Length: ";

	public HandleRequest(Socket i_connection)
	{
		this.connection = i_connection;
		line = "";
		fullRequest = "";
		messageBodyString = null;
		contentLength = -1;
		messageBodyBuilder = null;
		response = null;

	}

	@Override
	public void run() {			

		try {
			//TODO: handle the connection
			if (connection.isClosed()) {
				return;
			} 
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

			if (connection.isConnected()) {
				handleResponse(http_response, connection);					
			}
		} catch (IOException e) {

			e.printStackTrace();
		} 
		System.out.println("Numbers Of Threads : " + Thread.activeCount());
	}

	public void handleResponse(HTTPResponse res, Socket connection){

		String response = res.GenerateResponse();
		DataOutputStream writer;
		
		try {
			if (connection.getOutputStream() != null ) {
				writer = new DataOutputStream(connection.getOutputStream());
				System.out.println(response);

				if(!connection.isClosed()){
					writer.writeBytes(response);
					writer.flush();
				}
				
				// Send The File and Close Response As Http protocol request
				if(res.getPathToFile() != null && res.fileIsExpected()){
					byte[] fileToSend = readFile(new File(res.getPathToFile()));

					if(!connection.isClosed()){
						writer.write(fileToSend, 0, fileToSend.length);
						writer.flush();
					}
				}

				if(!connection.isClosed()){
					writer.writeBytes("\r\n");
					writer.flush();
				}
				writer.close();
			} 

			// TODO: Add here the option how to send the file Regular or Chuncked
			
		} catch (IOException e) {
			System.err.println("Network Problem: Socket was Closed");
		} 
	}

	// TODO: check if it's right to create http response here
	public HTTPResponse handleRequest(String i_fullRequest, String msgBody, int contentLength){

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
