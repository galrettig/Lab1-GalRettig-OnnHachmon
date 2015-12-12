package webserver;
import java.io.File;
import java.util.HashMap;

public class HTTPResponse {

	String m_HttpVersion;
	String m_ContentType; // text or image
	String m_ContentExtension;
	String m_RequestedPage; // TODO: check if valid exist
	int m_ContentLength; // TODO: define max content length in parser class
	String m_RequestType;
	String m_ReferrerHeader;
	//String m_UserAgent;
	HTTPResponseCode m_requestStatusCode;
	HashMap<String, String> m_HttpRequestParams;

	String m_Response;
	String v_ContentType = "content-type: ";
	String v_ContentLength = "Content-Length: "; 

	public HTTPResponse(HashMap<String, String> i_HttpRequest) 
	{

		m_Response = null;

		m_HttpVersion = i_HttpRequest.get("HTTPVersion");
		m_ContentType = i_HttpRequest.get(v_ContentType);
		
		// TODO: transfer the extension from the parse
		m_ContentExtension = i_HttpRequest.get("extension");
		
		m_RequestedPage = i_HttpRequest.get("URI");
		
		// TODO: check header in hash
		m_ReferrerHeader = i_HttpRequest.get("head");
		
		
		// TODO: status
		// m_requestStatusCode = HTTPResponseCode.getStatusCode(i_HttpRequest.get("status"));
		
		m_ContentLength = Integer.parseInt(i_HttpRequest.get(v_ContentLength));
		
	}


	public String constructResponse() {

		boolean responseDefualt = false;
		
		String statusLine = "";

		if (!checkResource(m_RequestedPage)) {
			responseDefualt = true;
			m_requestStatusCode = HTTPResponseCode.NOT_FOUND;
		}
		
		// Construct the first status line
		statusLine += (m_HttpVersion + " " + m_requestStatusCode);
		m_Response += statusLine;
		
		m_Response += constructContentTypeResponse();
		m_Response += ("\n" + v_ContentLength + m_ContentLength);

		// TODO: Print the request Headers with the response
		

		return null;
	}



	private boolean checkResource(String i_RequestedPage) {

		// TODO Handle the case windows or mac senarios

		String pathname = ConfigurationObject.getRoot() + "/" + i_RequestedPage;

		File file = new File(pathname);

		if (file.exists())
		{
			return true;
		}

		return false;
	}


	public String constructContentTypeResponse()
	{
		// TODO: check if the request good or not

		if (m_ContentType.equals("text")) {
			m_Response += (v_ContentType + "\n" + "text/" + m_ContentType);

			// TODO: think of a system how to call the resource after the response was sent
		}
		else if (m_ContentType.equals("image")) 
		{
			String extension = ImageTypes.getExtestion(m_ContentExtension);

			m_Response += (v_ContentType + "\n" + "image/" + extension);

			// TODO: send the image after the request was sent

		} 
		else 
		{
			m_Response += (v_ContentType + "\n" + "application/octet-stream");

			// TODO: send steam to user
		}

		return m_Response;
	}

}
