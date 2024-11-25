package com.qa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	Properties prop;
	//private String path=System.getProperty("user.dri")+"src//test//resources";
	
	
	/**
	 * This method load the properties from Config.properties file
	 * 
	 * @param Config properties file path
	 * @return It returns Properties object
	 */
	
	
	public Properties init_prop(String path) {

		Properties prop = new Properties();

		try {
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {

			ex.printStackTrace();
		}
		return prop;
	}

}
