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
	HashMap<String, String> m_requestHeaders;
	String m_originalRequest;
	

	public HTTPRequest(String originalRequest, HashMap<String, String> reqData, HashMap<String,String> params, HttpRequestType type, boolean isImage) {
		
		m_requestHeaders = reqData;
		m_originalRequest = originalRequest;
		this.m_RequestType = type;
		this.m_RequestedPage = reqData.get("URI");
		this.m_HTTPver = reqData.get("HTTPVersion");
		this.m_HttpRequestParams = params;
		if(reqData.containsKey("Content-Length")){
			m_contentLength = Integer.parseInt(reqData.get("Content-Length"));
		} else {
			m_contentLength = 0;
		}
		
		if(reqData.containsKey("Referer")){
			this.m_ReferrerHeader = reqData.get("Referer");
		}
		
		if(reqData.containsKey("User-Agent")){
			this.m_UserAgent = reqData.get("User-Agent"); 
		}
		
		v_IsImage = isImage;
		m_requestHeaders.put("erros", "none");
		
		if(!m_RequestedPage.equals("/")){
			int indexOfExt = m_RequestedPage.indexOf(".");
			if(indexOfExt > -1){
				m_requestHeaders.put("extension", m_RequestedPage.substring(indexOfExt + 1));
			} else {
				m_requestHeaders.replace("erros", "400 Bad Request");
			}
			
		} else {
			m_requestHeaders.put("extension", "html");
		}
		
		
		
	}
	
	public boolean ImplementedMethod(){
		if(m_RequestType.equals(HttpRequestType.GET) ||
				m_RequestType.equals(HttpRequestType.POST) || 
				m_RequestType.equals(HttpRequestType.HTTP_HEAD) ||
				m_RequestType.equals(HttpRequestType.TRACE)){
			return true;
		}
		return false;
		
	}
	
 
	
	// webserver -> read configuration file and create object -> open listener (configuration object) -> waiting for request
	
			// create http request -> parser class -> create http response -> respond to client
			
			// parse request and create request object -> create response
			
			
			

}
