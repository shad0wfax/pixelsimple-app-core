/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

/**
 *
 * @author Akshay Sharma
 * May 16, 2012
 */
public interface GenericRegistryEntryKey {
	String getUniqueModuleName();
	String getUniqueId();
	
	@Override 
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();
}
