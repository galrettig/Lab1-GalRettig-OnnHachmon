package webserver;
import java.util.HashMap;

public class Parser {
	
	HashMap<String, String> m_HttpRequest;
	
	public Parser() {
		m_HttpRequest = new HashMap<>();
	}
	
	public static HashMap<String, String> parseHttp(String i_httpRequest){
		
		String[] httpRequestToArray = i_httpRequest.split(" ");
		String firstVerb = httpRequestToArray[0];
		
		if (firstVerb.equals(HttpRequestType.GET)) 
		{
			parseGetRequest(i_httpRequest);
		}
		else if (firstVerb.equals(HttpRequestType.POST))
		{
			parsePostRequest(i_httpRequest);
		}
		else if (firstVerb.equals(HttpRequestType.HTTP_HEAD))
		{
			parseHttpHeadRequest(i_httpRequest);
		}
		else if (firstVerb.equals(HttpRequestType.TRACE))
		{
			parseTraceRequest(i_httpRequest);
		}
		
		return null;
	}

	private static void parseTraceRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		
	}

	private static void parseHttpHeadRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		
	}

	private static void parsePostRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		
	}

	private static void parseGetRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		
	}
}
