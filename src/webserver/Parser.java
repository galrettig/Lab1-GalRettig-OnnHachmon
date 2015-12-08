package webserver;
import java.util.HashMap;

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

		String[] trimToHeaders = i_httpRequest.split("\n");
		System.out.println(handleFirstLineOfRequest(trimToHeaders[0]));


		return null;
	}

	private static String[] handleFirstLineOfRequest(String firstLine){
		String[] splitted = firstLine.split(" ");
		if(splitted.length != 3){

			//TODO: remove debugging print..
			System.out.println("error in fist line of request -- more or less then 3 arguments given");

			return null;
		}
		
		System.out.println("Parser.java --> HandleFirstLineOfRequest --> amount of arguments give are : " 
		+ splitted.length + "\narguments given are : ");
		for(int i = 0; i < splitted.length; i++){
			System.out.println("debug( " + splitted[i] + " )");
		}

		return splitted;

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
	
	


}
