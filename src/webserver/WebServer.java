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

public class WebServer {
	

	
	public static void main(String[] args) throws IOException
	{
		ServerListener webSrv;
		ConfigurationObject m_appConfigObject;
		
		// create configuration object
		m_appConfigObject = new ConfigurationObject(readConfFile());
		
		if(m_appConfigObject.getPortNumber() == null){
			throw new IOException("cannot resolve config file");
		}
		// create listener
		webSrv = new ServerListener();
		webSrv.runListener(m_appConfigObject);
		
		// create http request
		
		
	}

	//TODO: delete ?????? 
	public static void readFile()
	{
		// gal path: C:\\Users\\gal\\workspace\\Lab1-GalRettig-OnnHachmon\\configuration
		Path file = FileSystems.getDefault().getPath("C:\\Sources\\Lab1-GalRettig-OnnHachmon\\configuration", "myWebConf.ini");
		//Path file = FileSystems.getDefault().getPath("C:\\Users\\gal\\workspace\\Lab1-GalRettig-OnnHachmon\\configuration", "myWebConf.ini");
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
		
		// Change Path For Windows
		// Gal Path: C:\\Users\\gal\\workspace\\Lab1-GalRettig-OnnHachmon\\configuration\\myWebconf.ini
		//C:\\Sources\\Lab1-GalRettig-OnnHachmon\\configuration\\mywebconf.ini";
		String pathToConfIniFile = "C:\\Sources\\Lab1-GalRettig-OnnHachmon\\configuration\\myWebconf.ini";

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
