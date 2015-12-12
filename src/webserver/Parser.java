package webserver;
import java.util.HashMap;
import java.util.Set;

public class Parser {

	

	public static HTTPRequest parseHttp(String i_httpRequest, String msgBody){
		
		HTTPRequest reqObj;
		
		String[] requestSplitted = i_httpRequest.split("\n");
		String[] requestLine = requestSplitted[0].split(" ");
		HashMap<String, String> requestHeaders = breakRequestStringToHeaders(requestSplitted);
		
		
		


		return null;
	}
	
	
	private static HashMap<String, String> breakRequestStringToHeaders(String[] requestHeaders){
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

	

	
	private static void parsePostRequest(String messageBody, HashMap<String, String> params) {
		if(messageBody.length() == 0){
			handleRequestErrors();
			params = null;
		}
		else {
			params = handleEncodedParams(messageBody);
		}
		
		
	}
	
	
	private static String parseGetRequest(String i_URI, HashMap<String, String> params) {
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
		String[] tuples;
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
