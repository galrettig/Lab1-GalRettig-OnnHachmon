package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebServer {

	public static void main(String[] args) throws IOException
	{
		// @TODO: open the file and read the data to conf object
		readFile();
	}
	
	
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
		
		fillConfigurationObject(fileData);

	}


	private static void fillConfigurationObject(StringBuilder i_StringFromConfFile) {
		// TODO Auto-generated method stub
		System.out.println(i_StringFromConfFile);
		String portRegex = "port=[' ']*([0-9]+)";
		String rootRegex = "root=[' ']*(.*)";
		String defaultPageRegex = "defaultPage=[' ']*(.*)";
		String maxThreadsRegex = "maxThreads=[' ']*(.*)";
		
		// Construct Conf Object
		ConfigurationObject myConfObject = new ConfigurationObject();
		
		// Create a Pattern object
	    Pattern patternObject = Pattern.compile(portRegex);
	    
	    // Now create matcher object.
	    Matcher matcherObject = patternObject.matcher(i_StringFromConfFile);
		
	    if (matcherObject.find( )) {
	    	myConfObject.m_Port = matcherObject.group(0);
	         
	      } else {
	         System.err.println("REGEX CANT FIND Configuration");
	      }
		
		
	}
}
