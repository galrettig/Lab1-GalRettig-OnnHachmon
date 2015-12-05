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
	
	/**
	 * GET http://www.sport5.co.il/ HTTP/1.1
	 * Host: www.sport5.co.il
	 * Proxy-Connection: keep-alive
	 * Cache-Control: max-age=0
	 * Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,**;q=0.8
	 * Upgrade-Insecure-Requests: 1
	 * User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36
	 * Accept-Encoding: gzip, deflate, sdch
	 * Accept-Language: en-US,en;q=0.8
	 * Cookie: __utma=213585750.1349511689.1439593092.1439593092.1439593092.1; __utmz=213585750.1439593092.1.1.utmcsr=mako.co.il|utmccn=(referral)|utmcmd=referral|utmcct=/; __gads=ID=cd98b214498863b9:T=1439593092:S=ALNI_MYdOB-dApGIbHDKiKrwyM_8rldGqw
	 * @param i_httpRequest
	 */
	private static void parseGetRequest(String i_httpRequest) {
		// TODO Auto-generated method stub
		
	}
}
