/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import com.pixelsimple.commons.util.Assert;

/**
 * An enum based singleton object that implements the Registry. 
 * It uses a map (enummap) underneath to store the key-value. 
 * Only the register method is synchronized. The fetch method isn't to allow quick reads.
 * 
 * Enum as a singleton object is based on the recommendation in Effective.Java 2nd Edition by Joshua Bloch.
 * Also more info here: http://stackoverflow.com/questions/70689/efficient-way-to-implement-singleton-pattern-in-java
 *
 * Usage of MapRegistry - MapRegistry.INSTANCE.xxxyyy()
 *
 * @author Akshay Sharma
 * Jan 14, 2012
 */
public enum MapRegistry implements Registry {
	// Only one enum value can exist. This is the instance enum. Guarantees the singleton.
	INSTANCE;
	
	private Map<Registrable, Object> registry = new EnumMap<Registrable, Object>(Registrable.class);

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#register(java.lang.Enum, java.lang.Class)
	 */
	@Override
	public synchronized void register(Registrable key, Object value) {
		if (this.containsKey(key)) {
			return;
		}

		// Key is checked anyway in the contains key
		Assert.notNull(value, "The value being added to the registry cannot be null. This is by design.");
		this.registry.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#forceRegister(com.pixelsimple.appcore.registry.Registrable, java.lang.Object)
	 */
	@Override
	public synchronized void forceRegister(Registrable key, Object value) {
		Assert.notNull(key, "Pass a valid key from Registrable enum");
		Assert.notNull(value, "The value being added to the registry cannot be null. This is by design.");
		this.registry.put(key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#fetch(java.lang.Enum)
	 */
	@Override
	public Object fetch(Registrable key) {
		return this.registry.get(key);
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#remove(java.lang.Enum)
	 */
	@Override
	public synchronized Object remove(Registrable key) {
		Assert.notNull(key, "Pass a valid key from Registrable enum");
		return this.registry.remove(key);
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#removeAll()
	 */
	@Override
	public synchronized void removeAll() {
		this.registry.clear();
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#fetchAll()
	 */
	@Override
	public Collection<?> fetchAllValues() {
		return this.registry.values();
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.registry.Registry#containsKey(com.pixelsimple.appcore.registry.Registrable)
	 */
	@Override
	public boolean containsKey(Registrable key) {
		Assert.notNull(key, "Pass a valid key from Registrable enum");
		Object valueExisting = this.fetch(key);
		
		if (valueExisting != null) {
			return true;
		}
		return false;
	}

}
