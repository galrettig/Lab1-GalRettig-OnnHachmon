package webserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Properties;

// TODO: Print Request Headers
// TODO: Chunck File functional
// TODO: 

public class WebServer {
	
	public static void main(String[] args) throws IOException
	{
		HashMap<String, String> m_confList = new HashMap<>();
		m_confList = readConfFile();
		
		// create configuration object
		ConfigurationObject.getConfigurationObject().setup(m_confList);
		
		if(ConfigurationObject.getPortNumber() == null){
			throw new IOException("cannot resolve config file");
		}
		
		SimpleThreadPool threadPool = new SimpleThreadPool(ConfigurationObject.getMaxThreads());
				
		ServerEscuchare webserver = new ServerEscuchare(threadPool);
		webserver.start();
		
//		// create listener
		//ServerListener webSrv;
//		webSrv = new ServerListener();
//		webSrv.runListener();
		
	}

	//TODO: check how many read file we have and maybe do utils class
	//TODO: delete ?????? 
	public static void readFile()
	{
		Path file = FileSystems.getDefault().getPath("C:\\Sources\\Lab1-GalRettig-OnnHachmon\\configuration", "myWebConf.ini");
		StringBuilder fileData = new StringBuilder("");
		try (InputStream in = Files.newInputStream(file);
				BufferedReader reader =
						new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				fileData.append("\n");
				fileData.append(line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}


	public static HashMap<String, String> readConfFile()
	{
		Properties prop = new Properties();
		InputStream input = null;
		HashMap<String, String> confList = new HashMap<>();
		 
		String pathToConfIniFile = "C:\\Sources\\Lab1-GalRettig-OnnHachmon\\configuration\\mywebconf.ini";

		try {
			input = new FileInputStream(pathToConfIniFile);

			// load a properties file
			prop.load(input);

			// get the property value and store them in hashmap
			confList.put("port", prop.getProperty("port"));
			confList.put("root", prop.getProperty("root"));
			confList.put("maxThreads", prop.getProperty("maxThreads"));
			confList.put("defaultPage", prop.getProperty("defaultPage"));
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return confList;
	}
}
