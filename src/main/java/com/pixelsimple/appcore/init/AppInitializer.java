/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Akshay Sharma
 * Jan 17, 2012
 */
public class AppInitializer {

	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);

	/**
	 * @param argMap
	 */
	public void init(Map<String, String> configMap) {
		// Init log?? (it must already be)
		LOG.debug("Initing the app with the argMap:: {}", configMap);
		
		// Init Registry
		
		// Init Environment
		
		// Init Configs - based on available params/options - transcoder only vs full webme etc.
		
		// DB and so on...
	}
}
