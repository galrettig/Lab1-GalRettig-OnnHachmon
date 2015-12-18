package webserver;

import java.util.HashMap;

public class ConfigurationObject {

	private String m_Port;
	String m_defaultPage;
	private static String m_MaxThreads;
	private static String m_defaultPageFullUrl;
	private static String m_rootFolder;

	public ConfigurationObject(HashMap<String, String> i_confList) {
		m_Port = i_confList.get("port");
		m_rootFolder = i_confList.get("root");
		m_defaultPage = i_confList.get("defaultPage");
		m_MaxThreads = i_confList.get("maxThreads");
		m_defaultPageFullUrl = m_rootFolder + "/" + m_defaultPage;
	}
	
	public static String getRoot()
	{
		return m_rootFolder;
	}
	
	public static String getDefaultPage()
	{
		return m_defaultPageFullUrl;
	}
	
	public String getPortNumber()
	{
		return m_Port;
	}
	
	public static int getMaxThreads()
	{
		return Integer.parseInt(m_MaxThreads);
		
	}
	
}
