/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.commons.util.Assert;

/**
 *
 * @author Akshay Sharma
 * Jan 17, 2012
 */
public class BootstrapInitializer {

	// Want the java cli params statically identified here.  
	// The system arg passed from script during the startup: -Dnova.home=\COMPUTED_PATH\..
	public static final String JAVA_SYS_ARG_APP_HOME_DIR = "nova.home";

	// Want the java cli params statically identified here.  
	// The system arg passed from script during the startup: -Dnova.home=\XYZ\..
	public static final String JAVA_SYS_ARG_CONFIG_FILE = "nova.appConfigFile";

	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(BootstrapInitializer.class);
	
	/**
	 * Return a Map of configuration by reading the system properties that matter. 
	 * @return
	 */
	public Map<String, String> bootstrap() throws Exception {
		String novaHomeDir = System.getProperty(JAVA_SYS_ARG_APP_HOME_DIR);
		String configFile = System.getProperty(JAVA_SYS_ARG_CONFIG_FILE);
		
		// TODO: Add more static checks (we need this strong)
		Assert.notNull(novaHomeDir, "Looks like the home directory wasn't supplied");
		Assert.notNull(configFile, "Looks like the config file wasn't supplied");
		
		// Check its a valid file:
		File file = new File(configFile);
		Assert.isTrue(file.isFile(), "Looks like the config file in not valid");
		
		Map<String, String> configs = new HashMap<String, String>();
		configs.put(JAVA_SYS_ARG_APP_HOME_DIR, novaHomeDir);
		
		this.loadConfigFile(file, configs);
		
		LOG.debug("bootstrap::Final computed config map::{}", configs);
		
		return configs;
	}

	// Note: not all param is a key-value pair. It could be a single value. Interpret it however needed. 
	private void loadConfigFile(File configFile, Map<String, String> configs) throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream(configFile));
		Set<Map.Entry<Object, Object>> entries = props.entrySet();

		for (Map.Entry<Object, Object> entry : entries) {
			configs.put((String) entry.getKey(), (String) entry.getValue());
		}
			
	}


}
