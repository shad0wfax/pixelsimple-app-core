/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.env;

import com.pixelsimple.commons.util.OSUtils;

/**
 * 
 * Should be Immutable to outside app-core packages. 
 * @author Akshay Sharma
 * Jan 16, 2012
 */
public interface Environment {
	
	public OSUtils.OS getCurrentOS();

	public String getAppBasePath();


}
