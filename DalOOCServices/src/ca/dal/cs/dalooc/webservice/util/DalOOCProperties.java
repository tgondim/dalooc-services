package ca.dal.cs.dalooc.webservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DalOOCProperties {
	
	private static Properties properties;
	
	static {
		try {
//			File propertiesFile = new File("DalOOCServices.properties");
			File propertiesFile = new File("webapps/DalOOCServices/WEB-INF/conf/DalOOCServices.properties");
			
			properties = new Properties();
			properties.load(new FileInputStream(propertiesFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

}
