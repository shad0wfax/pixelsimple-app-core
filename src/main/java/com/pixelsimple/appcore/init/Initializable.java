/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.registry.Registry;


/**
 *
 * @author Akshay Sharma
 * Feb 12, 2012
 */
public interface Initializable {

	public void initialize(Registry registry, ApiConfig apiConfig) throws Exception;

	public void deinitialize(Registry registry, ApiConfig apiConfig) throws Exception;
}
