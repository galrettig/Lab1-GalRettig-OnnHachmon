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
	HashMap<String, String> m_HttpRequestParams = new HashMap<>();
	HashMap<String, String> m_requestHeaders;
	String m_originalRequest;
	//
	//
	String m_httpMessageBody;
	int m_errorCodeIfOccured = -1;//-1 not occured , 4 bad-request , 5 not implemented


	public HTTPRequest(String io_originalRequest, String io_httpMessageBody, int contentLength){
		m_originalRequest = io_originalRequest;
		m_httpMessageBody = io_httpMessageBody;

		String[] requestAsArray = Parser.SplitRequestToLinesIfAcceptable(m_originalRequest);
		
		if(requestAsArray == null){
			m_errorCodeIfOccured = 4;
			
		} else {
			String[] requestLine = Parser.SplitRequestLineToHeadersIfAcceptable(requestAsArray[0]);
			
			if(requestLine == null){
				m_errorCodeIfOccured = 4;
				
			} else {
				this.m_RequestedPage = requestLine[1];
				this.m_HTTPver = requestLine[2];
				this.m_RequestType = checkIfMethodAcceptable(requestLine[0]);//has to be last
				
				if(!HttpRequestType.OTHER.equals(this.m_RequestType)){
					//can fully parse headers
					this.handleRequestHeaders(requestAsArray);
					
					
					
					
				} else {
					m_errorCodeIfOccured = 5;
				}
			}
		}

	}
	
	
	private void handleRequestHeaders(String[] request){
		this.m_requestHeaders = Parser.breakRequestStringToHeaders(request);
		this.v_IsImage = Parser.checkIfImage(m_RequestedPage);
		
		if(this.m_requestHeaders.containsKey("Referer")){
			this.m_ReferrerHeader = this.m_requestHeaders.get("Referer");
		}
		if(this.m_requestHeaders.containsKey("User-Agent")){
			this.m_ReferrerHeader = this.m_requestHeaders.get("User-Agent");
		}
		m_requestHeaders.put("URI", this.m_RequestedPage);
		m_requestHeaders.put("HTTPVersion", this.m_HTTPver);
		m_requestHeaders.put("RequestType", m_RequestType.displayName());
		
		
		m_requestHeaders.put("errors", this.mapErrorValueInRequestToResponseType().displayName());
		
		
		if(!m_RequestedPage.equals("/")){
			int indexOfExt = m_RequestedPage.indexOf(".");
			if(indexOfExt > -1){
				m_requestHeaders.put("extension", m_RequestedPage.substring(indexOfExt + 1));
			} else {
				m_requestHeaders.replace("errors", "400 Bad Request");
			}

		} else {
			m_requestHeaders.put("extension", "html");
		}
		
		
		
	}
	
	// check if the request method is acceptable by the server, if not returns -1
		private HttpRequestType checkIfMethodAcceptable(String requestMethod){
			HttpRequestType returnValue = HttpRequestType.OTHER;
			switch (requestMethod) {
			case "GET":
				returnValue = HttpRequestType.GET;
				handleGetRequest();
				break;
			case "POST":
				returnValue = HttpRequestType.POST;
				handlePostRequest();
				break;
			case "HEAD":
				returnValue = HttpRequestType.HTTP_HEAD;
				handleHeadRequest();
				break;
			case "TRACE":
				returnValue = HttpRequestType.TRACE;
				handleTraceRequest();
				break;
			default:
				handleErroredRequest();
				break;
			}
			
			return returnValue;
		}
	
	private void handleGetRequest(){
		m_RequestedPage = Parser.parseGetRequest(m_RequestedPage, m_HttpRequestParams);
	}
	private void handlePostRequest(){
		if(this.m_httpMessageBody != null){
			if(this.m_httpMessageBody.length() > 0){
				Parser.parsePostRequest(this.m_httpMessageBody, this.m_HttpRequestParams);
			}
		}
		
	}
	
	
	
	private void handleHeadRequest(){}
	private void handleTraceRequest(){}
	private void handleErroredRequest(){}
		
		
	
	
	

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
		m_requestHeaders.put("errors", "none");

		if(!m_RequestedPage.equals("/")){
			int indexOfExt = m_RequestedPage.indexOf(".");
			if(indexOfExt > -1){
				m_requestHeaders.put("extension", m_RequestedPage.substring(indexOfExt + 1));
			} else {
				m_requestHeaders.replace("errors", "400 Bad Request");
			}

		} else {
			m_requestHeaders.put("extension", "html");
		}



	}

	public HashMap<String, String> getMap (){
		return this.m_requestHeaders;
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

	public HTTPResponseCode mapErrorValueInRequestToResponseType(){
		HTTPResponseCode code = HTTPResponseCode.OK;
		switch (this.m_errorCodeIfOccured) {
		case -1:
			code = HTTPResponseCode.OK;
			break;
		case 4:
			code = HTTPResponseCode.BAD_REQUEST;
			break;
		case 5:
			code = HTTPResponseCode.NOT_IMPLEMENTED;
			break;

		default:
			code = HTTPResponseCode.BAD_REQUEST;
			break;
		}
		return code;
	}


	// webserver -> read configuration file and create object -> open listener (configuration object) -> waiting for request

	// create http request -> parser class -> create http response -> respond to client

	// parse request and create request object -> create response




}
