/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.commons.util.StringUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 16, 2012
 */
public class Bootstrap {
	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);
	
	/**
	 * TODO: How to prevent multiple instances running? Error out if Webme is already running??
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.debug("main with command line args::\n{}", StringUtils.arrayToCommaDelimitedString(args));
		LOG.debug("main with system arguments ::\n{}", System.getProperties());
		

		// We are expecting system properties to be passed in (with -D argument)
//		if (args == null || args.length == 0) {
//			throw new IllegalStateException("Looks like the application wasn't bootstrapped correctly. Check the passed in parameters");
//		}
		
		try {
			BootstrapInitializer initializer = new BootstrapInitializer();
			Map<String, String> configMap = initializer.bootstrap();

			AppInitializer appInitializer = new AppInitializer();
			appInitializer.init(configMap);
		} catch (Exception e) {
			LOG.error("Error occurred initalizing the app with system properties passed as : \n\n Exiting the app", e);
			
			// TODO: Hook up a different way to indicate to the user that the system is going down.- Listener?
			System.exit(0);
		}
	}
	
	
	

}
