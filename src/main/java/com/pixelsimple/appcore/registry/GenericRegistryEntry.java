/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.init.Initializable;

/**
 * 
 * This is an instance of {@link Initializable} so that it can be initialized correctly during the startup.  
 * Synchornizes access but not blocking the readers (getEntry calls) - using Re-entrant locks.
 * 
 * @author Akshay Sharma
 * May 15, 2012
 */
public class GenericRegistryEntry implements Initializable {
	private static final String KEY_DELIM = ":";
	private Map<String, Object> entries;
	private final ReadWriteLock monitor = new ReentrantReadWriteLock();
	
	@SuppressWarnings("unchecked")
	public <T extends Object> T getEntry(GenericRegistryEntryKey key) {
		monitor.readLock().lock();
		try {
			return (T) this.entries.get(this.getKeyString(key));
		} finally {
			monitor.readLock().unlock();
		}
	}
	
	public GenericRegistryEntry addEntry(GenericRegistryEntryKey key, Object value) {
		monitor.writeLock().lock();
		try {
			if (key != null && value != null) {
				this.entries.put(this.getKeyString(key), value);
			}
			return this;
		} finally {
			monitor.writeLock().unlock();
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T removeEntry(GenericRegistryEntryKey key) {
		monitor.writeLock().lock();
		try {
			if (key != null) {
				return (T) this.entries.remove(this.getKeyString(key));
			}
			return null;
		} finally {
			monitor.writeLock().unlock();
		}
	}
	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#initialize(com.pixelsimple.appcore.Registry, com.pixelsimple.appcore.ApiConfig)
	 */
	@Override
	public void initialize(ApiConfig apiConfig) throws Exception {
		monitor.writeLock().lock();
		try {
			entries = new HashMap<String, Object>(8);
		} finally {
			monitor.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#deinitialize(com.pixelsimple.appcore.Registry, com.pixelsimple.appcore.ApiConfig)
	 */
	@Override
	public void deinitialize(ApiConfig apiConfig) throws Exception {
		monitor.writeLock().lock();
		try {
			if (entries != null)
				entries.clear();
			entries = null;
		} finally {
			monitor.writeLock().unlock();
		}
	}
	
	private String getKeyString(GenericRegistryEntryKey key) {
		return key.getUniqueModuleName() + KEY_DELIM + key.getUniqueId();
	}

}
