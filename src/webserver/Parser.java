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
		
		int indexOfFirstSpace = i_httpRequest.indexOf(" ");
		String requestType = i_httpRequest.substring(0, indexOfFirstSpace);
		HashMap<String, String> requestTrimmed = trimHTTPRequest(i_httpRequest);

		if (requestType.equals(HttpRequestType.GET)) 
		{
			parseGetRequest(i_httpRequest);
		}
		else if (requestType.equals(HttpRequestType.POST))
		{
			parsePostRequest(i_httpRequest);
		}
		else if (requestType.equals(HttpRequestType.HTTP_HEAD))
		{
			parseHttpHeadRequest(i_httpRequest);
		}
		else if (requestType.equals(HttpRequestType.TRACE))
		{
			parseTraceRequest(i_httpRequest);
		}

		return null;
	}

	private static HashMap<String,String> trimHTTPRequest(String i_httpRequest){
		HashMap<String, String> trimmedStringToHashMap;
		String[] trimToHeaders;
		
		trimToHeaders = i_httpRequest.split("\n");
		trimmedStringToHashMap = handleFirstLineOfRequest(trimToHeaders[0]);//First line of an HTTPRequest has 3 values, parsing separately 
		
		//TODO - handle errors that might occur
		if(trimmedStringToHashMap != null) {
			handleRestOfRequest(trimToHeaders, trimmedStringToHashMap);
			
		}
		

		return null;
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
		for(int i = 1; i < i_requestTrimmedToLines.length; i++){
			int headerSeperatorIndex;
			String headerSeperator, headerName, headerValue;
			String[] trimmedLineBySeperator;
			
			headerSeperator = ": ";//space after ':' 
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


	private static void parseTraceRequest(String i_httpRequest) {

		System.out.println(i_httpRequest);

	}

	private static void parseHttpHeadRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		System.out.println(i_httpRequest);
	}

	private static void parsePostRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		System.out.println(i_httpRequest);
	}


	private static void parseGetRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		System.out.println(i_httpRequest);
	}
	
	private static void handleRequestErrors(){
		//TODO: implement
		System.out.println("some error");
	}
	
	
	


}
