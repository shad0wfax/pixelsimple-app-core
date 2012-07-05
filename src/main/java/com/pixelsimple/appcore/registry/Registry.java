/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import java.util.Collection;

/**
 * Synchornizes access but not blocking the readers (fetch and containsKey calls) - using Re-entrant locks.
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
	
	<T extends Object> T fetch(Registrable key);
	
	boolean containsKey(Registrable key);
	
	<T extends Object> T remove(Registrable key);
	
	void removeAll();
	
	Collection<?> fetchAllValues();

}
