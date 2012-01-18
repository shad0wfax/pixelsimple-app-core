/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

import com.pixelsimple.appcore.registry.MapRegistry;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public class RegistryService {

	/**
	 * Static and globally loaded registry - Return the implemented registry.
	 * 
	 * @return
	 */
	public static Registry getRegistry() {
		return MapRegistry.INSTANCE;
	}
}
