/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import com.pixelsimple.appcore.ApiConfig;


/**
 *
 * @author Akshay Sharma
 * Feb 12, 2012
 */
public interface Initializable {

	public void initialize(ApiConfig apiConfig) throws Exception;

	public void deinitialize(ApiConfig apiConfig) throws Exception;
}
