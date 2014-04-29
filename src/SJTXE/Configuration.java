package SJTXE;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	private Properties _properties;
	private static Configuration _instance;
	
	private Configuration() {
		try {
			InputStream ins = Configuration.class
					.getResourceAsStream("Configuration.properties");
			_properties = new Properties();
			_properties.load(ins);
		} catch (IOException e) {
			// stop requiring all callers to add throws term
			throw new RuntimeException(e);
		}
	}

	public static Configuration getInstance() {
		if(_instance == null) {
			_instance = new Configuration();
		}
		
		return _instance;
	}
	
	public String getWebRoot() {
		return _properties.getProperty("webroot");
	}
	
	public String[] getBrowsers() {
		return _properties.getProperty("browsers").split(",");
	}
	
	public String getUserName() {
		return _properties.getProperty("userName");
	}
	
	public String getPassword() {
		return _properties.getProperty("password");
	}
	
	public String  getCDP() {
		return _properties.getProperty("CDP");
	}
	
	public String getScreenshotFolder() {
		return _properties.getProperty("screenshotFolder");
	}
	
	/**
	 * @return implicit wait time in seconds
	 */
	public int getImplicitWait() {
		return getIntConfig("implicitWait");
	}
	/**
	 * 
	 * @return ajax wait time in seconds
	 */
	public int getAjaxWait() {
		return getIntConfig("ajaxWait");
	}
	
	private int getIntConfig(String key) {
		return Integer.parseInt(_properties.getProperty(key));
	}
}
