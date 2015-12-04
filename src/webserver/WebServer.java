package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

	public static void main(String[] args) throws IOException{


		String clientInput;
	    ServerSocket serverSoc;
		try {
			serverSoc = new ServerSocket(8080);
			 while(true) {
			    	
			    	Socket connection = serverSoc.accept();
			    	String fullRequest = "";
			    	
			    	BufferedReader inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream( ) ) );
			    	
			    	
			    	while((clientInput = inputClient.readLine()) != null){
			    		fullRequest += clientInput;
			    	}
			    	System.out.println(fullRequest);
			    
			    	

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   
	   



	}

}
