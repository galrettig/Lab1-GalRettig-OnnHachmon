package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Runnable {

	private final String ContentLengthHeader = "Content-Length: ";

	//TODO: figure how to call runListener
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println(Thread.activeCount());
		runListener();
		
	}	

	// Open Socket From Configuration Object And Read Input
	public void runListener()
	{
		ServerSocket serverSoc;
		try {

			//TODO: Threads from here
			serverSoc = new ServerSocket(Integer.parseInt(ConfigurationObject.getPortNumber()));

			if (!serverSoc.isClosed()) {				
				handleReadingFromSocket(serverSoc);
			}

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

				if (connection.isConnected()) {
					handleResponse(http_response, connection);					
				}
			} catch (IOException e) {

				e.printStackTrace();
			} 
		}

	}


	public void handleResponse(HTTPResponse res, Socket connection){

		String response = res.GenerateResponse();
		DataOutputStream writer;
		try {
			if (connection.getOutputStream() != null ) {
				writer = new DataOutputStream(connection.getOutputStream());
				System.out.println(response);


				writer.writeBytes(response);
				writer.flush();

				if(res.getPathToFile() != null && res.fileIsExpected()){
					if(!res.isChunked){
						byte[] fileToSend = readFile(new File(res.getPathToFile()));

						writer.write(fileToSend, 0, fileToSend.length);
						writer.flush();
					}
					else {
						writeChunkData((new File(res.getPathToFile())), writer);
					}
				}
				

				writer.writeBytes("\r\n");
				writer.flush();
				writer.close();
			}

			// TODO: Add here the option how to send the file Regular or Chuncked
			// Send The File and Close Response As Http protocol request
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private void writeChunkData(File file, DataOutputStream writer){

			try
			{
				FileInputStream fis = new FileInputStream(file);
				byte[] bFile = new byte[1024];
				int chunkSize = 0;
				// read until the end of the stream.
				while((chunkSize = fis.read(bFile)) != -1)
				{
					
					writer.write(chunkSize);
					writer.writeBytes("\r\n");
					writer.flush();
					writer.write(bFile, 0, chunkSize);
					writer.writeBytes("\r\n");
					writer.flush();
				}
				fis.close();
				
			}
			catch(FileNotFoundException e)
			{
				// do something
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