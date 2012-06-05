/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import java.util.HashMap;
import java.util.Map;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.init.Initializable;

/**
 * 
 * This is an instance of {@link Initializable} so that it can be initialized correctly during the startup.  
 * 
 * @author Akshay Sharma
 * May 15, 2012
 */
public class GenericRegistryEntry implements Initializable {
	private static final String KEY_DELIM = ":";
	private Map<String, Object> entries;
	
	@SuppressWarnings("unchecked")
	public <T extends Object> T getEntry(GenericRegistryEntryKey key) {
		return (T) this.entries.get(this.getKeyString(key));
	}
	
	public GenericRegistryEntry addEntry(GenericRegistryEntryKey key, Object value) {
		if (key != null && value != null) {
			this.entries.put(this.getKeyString(key), value);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T removeEntry(GenericRegistryEntryKey key) {
		if (key != null) {
			return (T) this.entries.remove(this.getKeyString(key));
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#initialize(com.pixelsimple.appcore.Registry, com.pixelsimple.appcore.ApiConfig)
	 */
	@Override
	public void initialize(ApiConfig apiConfig) throws Exception {
		entries = new HashMap<String, Object>(8);
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#deinitialize(com.pixelsimple.appcore.Registry, com.pixelsimple.appcore.ApiConfig)
	 */
	@Override
	public void deinitialize(ApiConfig apiConfig) throws Exception {
		entries.clear();
		entries = null;
		
	}
	
	private String getKeyString(GenericRegistryEntryKey key) {
		return key.getUniqueModuleName() + KEY_DELIM + key.getUniqueId();
	}

}
