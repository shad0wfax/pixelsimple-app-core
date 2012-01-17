/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

import java.util.Collection;

/**
 *
 * @author Akshay Sharma
 * Jan 15, 2012
 */
public interface Registry {
	
	void register(Registrable key, Object value);
	
	/**
	 * Registers the value, even if there was an existing value for the same key. The older value is replaced with the 
	 * newer value. 
	 * If there is no existing mapping for the key, it registers the key-value.
	 * @param key
	 * @param value
	 */
	void forceRegister(Registrable key, Object value);
	
	Object fetch(Registrable key);
	
	boolean containsKey(Registrable key);
	
	Object remove(Registrable key);
	
	void removeAll();
	
	Collection<?> fetchAllValues();

}
