package webserver;

import java.util.HashMap;

public class ConfigurationObject {

	String m_Port;
	String m_rootFolder;
	String m_defaultPage;
	String m_MaxThreads;
	String m_defaultPageFullUrl;
	

	public ConfigurationObject(HashMap<String, String> i_confList) {
		m_Port = i_confList.get("port");
		m_rootFolder = i_confList.get("root");
		m_defaultPage = i_confList.get("defaultPage");
		m_MaxThreads = i_confList.get("maxThreads");
		m_defaultPageFullUrl = m_rootFolder + "/" + m_defaultPage;
	}
	
	
}
