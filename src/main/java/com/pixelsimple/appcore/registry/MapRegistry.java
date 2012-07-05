/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.pixelsimple.commons.util.Assert;

/**
 * An enum based singleton object that implements the Registry. 
 * It uses a map (enummap) underneath to store the key-value. 
 * 
 * Enum as a singleton object is based on the recommendation in Effective.Java 2nd Edition by Joshua Bloch.
 * Also more info here: http://stackoverflow.com/questions/70689/efficient-way-to-implement-singleton-pattern-in-java
 *
 * Usage of MapRegistry - MapRegistry.INSTANCE.xxxyyy()
 * 
 * IMPORTANT: 
 * App Core classes can see MapRegistry.INSTANCE directly. The registry cannot be exposed outside of core directly.
 *
 * @author Akshay Sharma
 * Jan 14, 2012
 * Modified July 5, 2012 to use RenentrantLocks instead of full synchronized code. This is a much better performant 
 * code and does not block the reader threads. For more info, refer the book:
 * Programming Concurrency on JVM Sec: 5.7 Ensure Atomicity - by Venkat Subramaniam 
 */
public enum MapRegistry implements Registry {
	// Only one enum value can exist. This is the instance enum. Guarantees the singleton.
	INSTANCE;
	
	private Map<Registrable, Object> registry = new EnumMap<Registrable, Object>(Registrable.class);
	private final ReadWriteLock monitor = new ReentrantReadWriteLock();

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#register(java.lang.Enum, java.lang.Class)
	 */
	@Override
	public void register(Registrable key, Object value) {
		Assert.notNull(key, "Pass a valid key from Registrable enum");
		Assert.notNull(value, "The value being added to the registry cannot be null. This is by design.");

		monitor.writeLock().lock();
		try {
			if (this.registry.containsKey(key)) {
				return;
			}
			this.registry.put(key, value);
		} finally {
			monitor.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#forceRegister(com.pixelsimple.appcore.registry.Registrable, java.lang.Object)
	 */
	@Override
	public void forceRegister(Registrable key, Object value) {
		Assert.notNull(key, "Pass a valid key from Registrable enum");
		Assert.notNull(value, "The value being added to the registry cannot be null. This is by design.");

		monitor.writeLock().lock();
		try {
			this.registry.put(key, value);
		} finally {
			monitor.writeLock().unlock();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#fetch(java.lang.Enum)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T fetch(Registrable key) {
		// Will synchronize the read calls as well - This is to ensure that memory barrier is crossed.
		monitor.readLock().lock();
		try {
			return (T) this.registry.get(key);
		} finally {
			monitor.readLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#remove(java.lang.Enum)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T remove(Registrable key) {
		Assert.notNull(key, "Pass a valid key from Registrable enum");
		monitor.writeLock().lock();
		try {
			return (T) this.registry.remove(key);
		} finally {
			monitor.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#removeAll()
	 */
	@Override
	public void removeAll() {
		monitor.writeLock().lock();
		try {
			this.registry.clear();
		} finally {
			monitor.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#fetchAll()
	 */
	@Override
	public Collection<?> fetchAllValues() {
		// Will synchronize the read calls as well - This is to ensure that memory barrier is crossed.
		monitor.readLock().lock();
		try {
			return this.registry.values();
		} finally {
			monitor.readLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#containsKey(com.pixelsimple.appcore.registry.Registrable)
	 */
	@Override
	public boolean containsKey(Registrable key) {
		Assert.notNull(key, "Pass a valid key from Registrable enum");
		// Will synchronize the read calls as well - This is to ensure that memory barrier is crossed.
		monitor.readLock().lock();
		try {
			Object valueExisting = this.registry.get(key);
			if (valueExisting != null)
				return true;
			return false;
		} finally {
			monitor.readLock().unlock();
		}
		
	}

}
