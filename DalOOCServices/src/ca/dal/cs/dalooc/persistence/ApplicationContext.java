package ca.dal.cs.dalooc.persistence;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ca.dal.cs.dalooc.webservice.util.DalOOCProperties;

public class ApplicationContext {
	
	private static ConfigurableApplicationContext instance;
	
	static {
		ApplicationContext.instance = new FileSystemXmlApplicationContext(DalOOCProperties.getProperty("ApplicationContextXmlFile"));
//		ApplicationContext.instance = new FileSystemXmlApplicationContext("applicationContext.xml");
	}
	
	public static ConfigurableApplicationContext getInstance() {
		return ApplicationContext.instance;
	}
	
}
