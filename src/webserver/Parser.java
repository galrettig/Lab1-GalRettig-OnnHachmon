package webserver;
import java.net.Authenticator.RequestorType;
import java.util.HashMap;

public class Parser {

	
	
	public static void parseRequest(String i_httpRequest){
		
		
		String[] requestAsArray = SplitRequestToLinesIfAcceptable(i_httpRequest);
		if(requestAsArray != null){
			String[] requestLine = SplitRequestLineToHeadersIfAcceptable(requestAsArray[0]);
			if(requestLine != null){
				HttpRequestType methodType = checkIfMethodAcceptable(requestLine[0]);
			}
		}
		
		
		/*
		HashMap<String, String> params = new HashMap<>();
		boolean isImage = false;
		
		String[] requestSplitted = i_httpRequest.split("\n");
		String[] requestLine = requestSplitted[0].split(" ");
		HashMap<String, String> requestHeaders = breakRequestStringToHeaders(requestSplitted);
		HttpRequestType type = HttpRequestType.OTHER;
		
		if(verifyValidRequestLine(requestLine, requestHeaders)){
			String requestType = requestLine[0];
			if(requestType.equals(HttpRequestType.GET)){
				requestLine[1] = parseGetRequest(requestLine[1], params);
				type = HttpRequestType.GET;
				isImage = checkIfImage(requestLine[1]);
			} 
			else if(requestType.equals(HttpRequestType.POST)){
				parsePostRequest(msgBody, params);
				type = HttpRequestType.POST;
				isImage = checkIfImage(requestLine[1]);
				
			}
			else if(requestType.equals(HttpRequestType.HTTP_HEAD)){
				requestLine[1] = parseGetRequest(requestLine[1], params);
				type = HttpRequestType.HTTP_HEAD;
				isImage = checkIfImage(requestLine[1]);
				
			}
			else if(requestType.equals(HttpRequestType.TRACE)){
				type = HttpRequestType.TRACE;
			}
			//else{}
			
			//reqObj = new HTTPRequest(i_httpRequest,requestHeaders, params, type, isImage);
		}
		*/
		
		
		
		

	}
	

	public static HTTPRequest parseHttp(String i_httpRequest, String msgBody){
		
		HTTPRequest reqObj = null;
		HashMap<String, String> params = new HashMap<>();
		boolean isImage = false;
		
		String[] requestSplitted = i_httpRequest.split("\n");
		String[] requestLine = requestSplitted[0].split(" ");
		HashMap<String, String> requestHeaders = breakRequestStringToHeaders(requestSplitted);
		HttpRequestType type = HttpRequestType.OTHER;
		
		if(verifyValidRequestLine(requestLine, requestHeaders)){
			String requestType = requestLine[0];
			if(requestType.equals(HttpRequestType.GET)){
				requestLine[1] = parseGetRequest(requestLine[1], params);
				type = HttpRequestType.GET;
				isImage = checkIfImage(requestLine[1]);
			} 
			else if(requestType.equals(HttpRequestType.POST)){
				parsePostRequest(msgBody, params);
				type = HttpRequestType.POST;
				isImage = checkIfImage(requestLine[1]);
				
			}
			else if(requestType.equals(HttpRequestType.HTTP_HEAD)){
				requestLine[1] = parseGetRequest(requestLine[1], params);
				type = HttpRequestType.HTTP_HEAD;
				isImage = checkIfImage(requestLine[1]);
				
			}
			else if(requestType.equals(HttpRequestType.TRACE)){
				type = HttpRequestType.TRACE;
			}
			//else{}
			
			reqObj = new HTTPRequest(i_httpRequest,requestHeaders, params, type, isImage);
		}
		
		
		
		
		


		return reqObj;
	}
	
	
	static boolean checkIfImage(String URI){
		String[] acceptedExtensions = {".bmp", ".jpg", ".gif", ".png"};
		int minExtLength = 4;
		boolean imageFound = false;
		
		// if the uri length is less then 4 then it is not a file min length of extension is 4 without the file name
		if(URI.length() > minExtLength) {
			String tempExt = URI.substring(URI.length() - minExtLength);
			for(int i = 0; i < acceptedExtensions.length; i++){
				if(tempExt.equals(acceptedExtensions[i])){
					imageFound = true;
				}
			}
		}
		return imageFound;
	}

	
	
	// check if the the request-line is acceptable , returns the the headers of the request-line splitted to spaces into array
	// or null if it cannot
	static String[] SplitRequestLineToHeadersIfAcceptable(String i_requestLine){
		String[] requestLineHeaders = null;
		if(i_requestLine.indexOf(" ") > -1){
			String[] requestLineHeadersTemp = i_requestLine.split(" ");
			if(requestLineHeadersTemp.length == 3){
				requestLineHeaders = requestLineHeadersTemp;
			}
		}
		return requestLineHeaders;
	}
	
	
	// check if the the request is split able and returns the the request splitted to lines in array
	// or null if it cannot
	static String[] SplitRequestToLinesIfAcceptable(String originalMessage){
		String[] requestSplitted = null;
		if(originalMessage.indexOf("\n") > -1){
			requestSplitted = originalMessage.split("\n");
		}
		return requestSplitted;
	}
	
	
	
	
	
	// check if the request method is accpeptable by the server, if not returns -1
	static HttpRequestType checkIfMethodAcceptable(String requestMethod){
		HttpRequestType returnValue = HttpRequestType.OTHER;
		switch (requestMethod) {
		case "GET":
			returnValue = HttpRequestType.GET;
			break;
		case "POST":
			returnValue = HttpRequestType.POST;
			break;
		case "HEAD":
			returnValue = HttpRequestType.HTTP_HEAD;
			break;
		case "TRACE":
			returnValue = HttpRequestType.TRACE;
			break;
		default:
			break;
		}
		
		return returnValue;
	}
	
	private static boolean verifyValidRequestLine(String[] reqLine, HashMap<String, String> headersMap){
		boolean valid = false;
		if(reqLine.length == 3){
			valid = true;
			if(reqLine[1].indexOf("/") == 0 && reqLine[1].length() > 1){
				reqLine[1] = reqLine[1].substring(1);
			}
			headersMap.put("RequestType", reqLine[0]);
			headersMap.put("URI", reqLine[1]);
			headersMap.put("HTTPVersion", reqLine[2]);
		}
		return valid;
	}
	
	
	
	
	

	static HashMap<String, String> breakRequestStringToHeaders(String[] requestHeaders){
		HashMap<String, String> headers = new HashMap<>();
		
		for(int i = 1; i < requestHeaders.length; i++){
			int indexOfSeperator = requestHeaders[i].indexOf(": ");
			if(indexOfSeperator > -1){
				String[] keyAndValue = requestHeaders[i].split(": ");
				if(keyAndValue.length == 2){
					headers.put(keyAndValue[0], keyAndValue[1]);
				}
				
			}
		}
		return headers;
	}

	
	static void parsePostRequest(String messageBody, HashMap<String, String> params) {
		if(messageBody.length() == 0){
			handleRequestErrors();
			params = null;
		}
		else {
			params = handleEncodedParams(messageBody);
		}
		
		
	}
	
	
	static String parseGetRequest(String i_URI, HashMap<String, String> params) {
		String parsedURI = "";
		int indexOfSeperator = i_URI.indexOf("?");
		if(indexOfSeperator > -1){
			String[] URIandParams = i_URI.split("?");
			if(URIandParams.length == 2){
				params = handleEncodedParams(URIandParams[1]);
				parsedURI = URIandParams[0];
				
			}
		} else {
			parsedURI = i_URI;
			params = null;
		}
		
		return parsedURI;
	}
	
	private static HashMap<String, String> handleEncodedParams(String paramsEncoded){
		String[] paramsTuples = breakEncodedParamsToTuples(paramsEncoded);
		return extractTupleParmas(paramsTuples);
	}
	
	private static String[] breakEncodedParamsToTuples(String paramsEncoded){
		String[] tuples = null;
		int paramsTupleSeperator = paramsEncoded.indexOf("&");
		if(paramsTupleSeperator > -1){
			tuples = paramsEncoded.split("&");
		} else {
			tuples = new String[]{paramsEncoded};
		}
		
		return tuples;
	}
	
	private static HashMap<String,String> extractTupleParmas(String[] tuples){
		HashMap<String, String> params = new HashMap<>();
		for(int i = 0; i < tuples.length; i++){
			if(tuples[i].indexOf("=") != -1){
				String[] keyValueParams = tuples[i].split("=");
				params.put(keyValueParams[0], keyValueParams[1]);
			}
		}
		return params;
	}
	
	
	

	private static void parseTraceRequest(HashMap<String, String> i_httpRequest) {

		System.out.println(i_httpRequest);

	}

	

	private static void parseHttpHeadRequest(HashMap<String, String> i_httpRequest) {
		// TODO Auto-generated method stub
		System.out.println(i_httpRequest);
	}


	


	
	private static void handleRequestErrors(){
		//TODO: implement
		System.out.println("some error");
	}





}
