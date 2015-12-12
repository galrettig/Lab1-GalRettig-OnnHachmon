package webserver;
import java.util.HashMap;

public class HTTPRequest {
	
	// GET or POST or ... 
	HttpRequestType m_RequestType;
	// Requested Page (/ or /index.html etc.)
	String m_RequestedPage;
	// Is Image – if the requested page has an extension of an image (jpg, bmp, gif...)
	Boolean v_IsImage;
	// Content Length that is written in the request
	int m_contentLength;
	// Referrer header
	String m_ReferrerHeader;
	// User Agent
	String m_UserAgent;
	//Parameters – the parameters in the request (I used java.util.HashMap<String,String> to hold the parameters).
	String m_HTTPver;
	HashMap<String, String> m_HttpRequestParams; 

	public HTTPRequest(HashMap<String, String> reqData, HashMap<String,String> params, HttpRequestType type) {
		
		
		this.m_RequestType = type;
		this.m_RequestedPage = reqData.get("URI");
		this.m_HTTPver = reqData.get("HTTPVersion");
		this.m_HttpRequestParams = params;
		if(reqData.containsKey("Content-Length")){
			m_contentLength = Integer.parseInt(reqData.get("Content-Length"));
		} else {
			m_contentLength = 0;
		}
		
		
		
	}
	
	
	// webserver -> read configuration file and create object -> open listener (configuration object) -> waiting for request
	
			// create http request -> parser class -> create http response -> respond to client
			
			// parse request and create request object -> create response
			
			
			

}
