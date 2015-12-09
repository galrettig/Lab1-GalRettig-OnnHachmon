package webserver;
import java.util.HashMap;
import java.util.Set;

public class Parser {

	HashMap<String, String> m_HttpRequest;

	public Parser() {
		m_HttpRequest = new HashMap<>();
	}

	public static HashMap<String, String> parseHttp(String i_httpRequest){
		//String[] httpRequestToArray = i_httpRequest.split("\n");
		//String firstVerb = httpRequestToArray[0];

		//TODO: valditlate that the request type is actually valid
		
		if(i_httpRequest == null) return null;
		
		
		HashMap<String, String> requestTrimmed = trimHTTPRequest(i_httpRequest);
		String requestType = requestTrimmed.get("RequestType");
		
		

		

		return null;
	}

	private static HashMap<String,String> trimHTTPRequest(String i_httpRequest){
		HashMap<String, String> trimmedStringToHashMap;
		String[] trimToHeaders;
		
		trimToHeaders = i_httpRequest.split("\n");
		trimmedStringToHashMap = handleFirstLineOfRequest(trimToHeaders[0]);//First line of an HTTPRequest has 3 values, parsing separately 
		String requestType = trimmedStringToHashMap.get("RequestType");
		//TODO - handle errors that might occur
		if(trimmedStringToHashMap != null) {
			
			if (requestType.equals(HttpRequestType.GET)) 
			{
				parseGetRequest(trimmedStringToHashMap.get("URI"), trimmedStringToHashMap);
			}
			else if (requestType.equals(HttpRequestType.POST))
			{
				parsePostRequest(trimmedStringToHashMap);
			}
			else if (requestType.equals(HttpRequestType.HTTP_HEAD))
			{
				parseHttpHeadRequest(trimmedStringToHashMap);
			}
			else if (requestType.equals(HttpRequestType.TRACE))
			{
				parseTraceRequest(trimmedStringToHashMap);
			}
			
			
			handleRestOfRequest(trimToHeaders, trimmedStringToHashMap);
			
		}
		

		return trimmedStringToHashMap;
	}

	/**
	 * 
	 * @param firstLine
	 * @return [Request Type, URI, HTTP-Version] || null (if fails)
	 */
	private static HashMap<String, String> handleFirstLineOfRequest(String firstLine){
		//TODO: remove debugging syso..
		String[] splitted = firstLine.split(" ");
		if(splitted.length != 3){
			//Debug
			System.out.println("error in fist line of request -- more or less then 3 arguments given");
			//
			return null;
		}
		
		// Debugging purposes
		System.out.println("Parser.java --> HandleFirstLineOfRequest --> amount of arguments give are : " 
								+ splitted.length + "\narguments given are : ");
		for(int i = 0; i < splitted.length; i++){
			System.out.println("debug( " + splitted[i] + " )");
		}
		// End of Debug
		
		
		
		
		HashMap<String, String> hashToReturn = new HashMap<>();
		hashToReturn.put("RequestType", splitted[0]);
		hashToReturn.put("URI", splitted[1]);
		hashToReturn.put("HTTPVersion", splitted[2]);

		return hashToReturn;
	}
	
	private static void handleRestOfRequest(String[] i_requestTrimmedToLines, HashMap<String,String> o_map){

		// i starts from 1 because we already parsed the first line
		for(int i = 1; i < i_requestTrimmedToLines.length; i++){
			
			int headerSeperatorIndex;
			String headerSeperator, headerName, headerValue;
			String[] trimmedLineBySeperator;
			
			headerSeperator = ": ";//notice the space after ':' 
			headerSeperatorIndex = i_requestTrimmedToLines[i].indexOf(headerSeperator);
			
			if(headerSeperatorIndex == -1){
				System.out.println("error : handleRestOfRequest --> missing ': ' string from request in line : " + i);
				handleRequestErrors();//TODO: take care for this
			} else {
				trimmedLineBySeperator = i_requestTrimmedToLines[i].split(headerSeperator);
				headerName = trimmedLineBySeperator[0];
				headerValue = trimmedLineBySeperator[1];
				
				//TODO: verify to overwrite duplicated header -- and throw error in this case
				o_map.put(headerName, headerValue);
			}
			
		}
	}


	private static void parseTraceRequest(HashMap<String, String> i_httpRequest) {

		System.out.println(i_httpRequest);

	}

	private static void parseHttpHeadRequest(HashMap<String, String> i_httpRequest) {
		// TODO Auto-generated method stub
		System.out.println(i_httpRequest);
	}

	private static void parsePostRequest(HashMap<String, String> i_httpRequest) {
		// TODO Auto-generated method stub
		System.out.println(i_httpRequest);
	}


	private static void parseGetRequest(String i_URI, HashMap<String,String> o_map) {
		String[] reqParams = GETextractParamsFromURI(i_URI);
		if(reqParams[1] != null){
			o_map.replace("URI", reqParams[0]);
			o_map.put("paramters", reqParams[1]);
		}
		
		
	}
	
	
	private static String[] GETextractParamsFromURI(String i_URI){
		int indexOfParams = i_URI.indexOf("?");
		String[] requestParamsAndURI = new String[2];
		
	
		
		if(indexOfParams > -1){
			requestParamsAndURI[0] = i_URI.substring(0,indexOfParams);
			requestParamsAndURI[1] = i_URI.substring(indexOfParams + 1);
			return requestParamsAndURI;
		} else {
			requestParamsAndURI[0] = i_URI;
			requestParamsAndURI[1] = null;
		}
		return requestParamsAndURI;
	}
	
	private static void handleRequestErrors(){
		//TODO: implement
		System.out.println("some error");
	}
	
	
	


}
