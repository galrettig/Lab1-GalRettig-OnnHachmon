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
		readConfFile();
	}

	//TODO: delete
	public static void readFile()
	{
		Path file = FileSystems.getDefault().getPath("/Users/onncho/Dropbox/Study/IDC/Semester 5/תקשורת/lab1/Lab1-GalRettig-OnnHachmon/configuration", "myWebConf.ini");
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


	public static void readConfFile()
	{
		Properties prop = new Properties();
		InputStream input = null;
		HashMap<String, String> confList = new HashMap<>(); 

		try {
			input = new FileInputStream("/Users/onncho/Dropbox/Study/IDC/Semester 5/תקשורת/lab1/Lab1-GalRettig-OnnHachmon/configuration/myWebconf.ini");

			// load a properties file
			prop.load(input);

			// get the property value and store them in hashmap
			confList.put("port", prop.getProperty("port"));
			confList.put("root", prop.getProperty("root"));
			confList.put("maxThreads", prop.getProperty("maxThreads"));
			confList.put("defaultPage", prop.getProperty("defaultPage"));
			
			// create conf object
			ConfigurationObject m_appConfigObject = new ConfigurationObject(confList);
			
			//

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
	}
}
