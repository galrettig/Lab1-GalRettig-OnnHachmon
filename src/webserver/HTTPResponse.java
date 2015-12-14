package webserver;
import java.io.File;
import java.util.HashMap;

public class HTTPResponse {

	String m_HttpVersion; // client error
	String m_ContentExtension;
	String m_RequestedPage; // TODO: check if valid exist
	String m_RequestType;
	HTTPResponseCode m_ErrorsFoundInRequest;

	HashMap<String, String> m_HttpRequestParams;

	String m_Response;
	HTTPResponseCode m_responseStatusCode;
	int m_ContentLength; 
	String m_ContentType;
	String v_ContentType = "Content-Type: ";
	String v_ContentLength = "Content-Length: ";
	String m_PathTofile;

	public HTTPResponse(HashMap<String, String> i_HttpRequest) 
	{
		m_Response = "";
		m_responseStatusCode = HTTPResponseCode.OK;//

		if (i_HttpRequest.get("erros").equals(HTTPResponseCode.BAD_REQUEST)) 
		{
			m_ErrorsFoundInRequest = HTTPResponseCode.BAD_REQUEST;
			m_responseStatusCode = HTTPResponseCode.BAD_REQUEST;
			// bla bla bla
		}
		else 
		{
			m_HttpVersion = i_HttpRequest.get("HTTPVersion");

			// TODO: transfer the extension from the parse
			m_ContentExtension = i_HttpRequest.get("extension");
			
			m_ContentType = constructExtensionToContentType();

			m_RequestedPage = i_HttpRequest.get("URI");

			m_RequestType = i_HttpRequest.get("RequestType");
		}

	}

	// The Method for clients of this object
	public String GenerateResponse() 
	{
		String result = constructResponse();
		System.out.println(result);
		return result;
	}
	
	private String constructResponse() 
	{

		// Construct the first status line
		m_Response += m_HttpVersion + " ";

		if (m_responseStatusCode.equals(HTTPResponseCode.BAD_REQUEST)) 
		{
			m_Response += m_responseStatusCode + "\r\n";
			return m_Response;
		}
		else
		{
			constructResponseCode();
			m_Response += (m_responseStatusCode + 
					"\r\n" + m_ContentType + "\r\n" + 
					v_ContentLength + m_ContentLength +"\r\n\r\n");
		}

		// TODO: Print the request Headers with the response

		return m_Response;
	}



	private void constructResponseCode() {

		if (m_RequestType.equals("Other")) 
		{
			m_responseStatusCode = HTTPResponseCode.NOT_IMPLEMENTED;
		}
		else if (!checkResource(m_RequestedPage)) 
		{
			if (!m_responseStatusCode.equals(HTTPResponseCode.INTERNAL_SERVER_ERROR)) {				
				m_responseStatusCode = HTTPResponseCode.NOT_FOUND;
			}
		}
		else
		{
			m_responseStatusCode = HTTPResponseCode.OK;
		}

	}


	private boolean checkResource(String i_RequestedPage) {

		// TODO Handle the case windows or mac senarios
		System.out.println(i_RequestedPage);
		String pathname = ConfigurationObject.getRoot() + "\\" + i_RequestedPage;
		System.out.println(pathname);

		File file = new File(pathname);

		try 
		{
			if (file.exists())
			{
				// TODO: check if int or long
				m_ContentLength = (int) file.length();
				m_PathTofile = pathname;
				return true;
			}
		} 
		catch (Exception e) {
			m_responseStatusCode = HTTPResponseCode.INTERNAL_SERVER_ERROR;
			return false;
		}
		return false;
	}


	private String constructExtensionToContentType()
	{
		switch (m_ContentExtension) {
		case "html"	: 
			m_ContentType = v_ContentType + "text/html";
			break;
		case "css"	: 
			m_ContentType = v_ContentType + "text/css";
		case "jpg"	: 
			m_ContentType = v_ContentType + "image/jpeg";
			break;
		case "htm"	: 
			m_ContentType = v_ContentType + "text/html";
			break;
		case "jpeg"	: 
			m_ContentType = v_ContentType + "image/jpeg";
			break;
		case "js"	: 
			m_ContentType = v_ContentType + "application/javascript";
			break;
		case "bmp"	: 
			m_ContentType = v_ContentType + "image/bmp";
			break;
		case "gif"	: 
			m_ContentType = v_ContentType + "image/gif";
		case "txt"	: 
			m_ContentType = v_ContentType + "text/plain";
			break;
		default		:
			m_ContentType = v_ContentType + "application/octet-stream";
			break;
		}
		return m_ContentType;
	}
	
	public String getPathToFile() {
		if(m_responseStatusCode.equals(HTTPResponseCode.NOT_FOUND)){
			return null;
		}
		if (m_PathTofile.equals("/")) {
			return ConfigurationObject.getRoot() + "/" + "index.html";
		}
		return m_PathTofile;
	}

	/*
	public String constructContentTypeResponse()
	{
		if (m_ContentType.equals("text")) {
			m_Response += (v_ContentType + "text/" + m_ContentType);

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
	*/

}
