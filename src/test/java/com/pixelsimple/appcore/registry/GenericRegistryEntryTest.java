/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Akshay Sharma
 * May 16, 2012
 */
public class GenericRegistryEntryTest {
	GenericRegistryEntry registry;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		registry = new GenericRegistryEntry();
		registry.initialize(null, null);
	}

	@Test
	public void enumBasedKeyTest() {
		GenericRegistryEntryKey key = TempEnum.A;

		Assert.assertEquals(key.getUniqueModuleName(), TempEnum.A.getUniqueModuleName());
		Assert.assertEquals(key.getUniqueId(), TempEnum.A.getUniqueId());
		Assert.assertFalse(key.getUniqueId().equalsIgnoreCase(TempEnum.B.getUniqueId()));
	}
	
	@Test
	public void enumBasedRegistryEntryTest() {
		GenericRegistryEntryKey key = TempEnum.A;
		
		registry.addEntry(key, "hey jude");
		registry.addEntry(TempEnum.B, "hey jude");

		Assert.assertEquals(registry.getEntry(key), "hey jude");
		Assert.assertEquals(registry.getEntry(TempEnum.B), "hey jude");

		registry.addEntry(TempEnum.B, "across the universe");
		Assert.assertEquals(registry.getEntry(key), "hey jude");
		Assert.assertEquals(registry.getEntry(TempEnum.B), "across the universe");		
	}
	
	@Test
	public void enumBasedRegistryEntryRemoveTest() {
		GenericRegistryEntryKey key = TempEnum.A;
		
		registry.addEntry(key, "hey jude");
		registry.addEntry(TempEnum.B, "hey jude");

		Assert.assertEquals(registry.getEntry(key), "hey jude");
		Assert.assertEquals(registry.getEntry(TempEnum.B), "hey jude");

		registry.removeEntry(TempEnum.B);
		Assert.assertEquals(registry.getEntry(key), "hey jude");
		Assert.assertNull(registry.getEntry(TempEnum.B));		
	}
	
	private enum TempEnum implements GenericRegistryEntryKey {
		A,B;

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.registry.GenericRegistryEntryKey#getUniqueModuleName()
		 */
		@Override
		public String getUniqueModuleName() {
			return "unittesting";
		}

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.registry.GenericRegistryEntryKey#getUniqueId()
		 */
		@Override
		public String getUniqueId() {
			return name();
		}
		
	}

}
