package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class WebServer {

	public static void main(String[] args) throws IOException
	{
		// @TODO: open the file and read the data to conf object
		readFile();
	}
	
	
	public static void readFile()
	{
		Path file = FileSystems.getDefault().getPath("/Users/onncho/Dropbox/Study/IDC/Semester 5/תקשורת/lab1/Lab1-GalRettig-OnnHachmon/configuration", "myWebConf.ini");
		
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		} catch (IOException e) {
		    System.err.println(e);
		}

	}
}
