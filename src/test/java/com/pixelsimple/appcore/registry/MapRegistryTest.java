/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.pixelsimple.appcore.Registrable;
import com.pixelsimple.appcore.Registry;
import com.pixelsimple.appcore.config.ffmpeg.FfmpegConfig;

/**
 *
 * @author Akshay Sharma
 * Jan 15, 2012
 */
public class MapRegistryTest {

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		MapRegistry.INSTANCE.removeAll();
	}

	/**
	 * Test method for ensuring there is just one instance that can exist.
	 */
	@Test
	public void enforceSingletonNature() {
		Registry reg1 = MapRegistry.INSTANCE;
		
		MapRegistry reg2 = MapRegistry.INSTANCE;
		
		Assert.assertEquals(reg1, reg2);
		Assert.assertTrue(reg1 == reg2);
	}

	/**
	 * Test method for ensuring there is just one instance that can exist.
	 */
	@Test
	public void enforceSingletonInstanceOfEnum() {
		Assert.assertEquals(MapRegistry.values().length, 1);
		Assert.assertEquals(MapRegistry.values()[0], MapRegistry.INSTANCE);
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#register(com.pixelsimple.appcore.registry.Registrable, java.lang.Object)}.
	 */
	@Test
	public void registerWithInvalidKeyValue() {
		Registry reg1 = MapRegistry.INSTANCE;
		
		try {
			reg1.register(null, "abc");
			fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
		
		try {
			reg1.register(Registrable.FFMPEG_CONFIG, null);
			fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#register(com.pixelsimple.appcore.registry.Registrable, java.lang.Object)}.
	 */
	@Test
	public void register() {
		Registry reg1 = MapRegistry.INSTANCE;
		Object val = new FfmpegConfig();
		
		reg1.register(Registrable.FFMPEG_CONFIG, val);
		Assert.assertEquals(reg1.containsKey(Registrable.FFMPEG_CONFIG), true);

		// Should have no effect.
		reg1.register(Registrable.FFMPEG_CONFIG, "abc");
		Object valret = reg1.fetch(Registrable.FFMPEG_CONFIG);
		
		Assert.assertEquals(valret, val);
		Assert.assertTrue(valret == val);
	}
		
	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#register(com.pixelsimple.appcore.registry.Registrable, java.lang.Object)}.
	 */
	@Test
	public void forceRegisterWithInvalidKeyValue() {
		Registry reg1 = MapRegistry.INSTANCE;
		
		try {
			reg1.forceRegister(null, "abc");
			fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
		
		try {
			reg1.forceRegister(Registrable.FFMPEG_CONFIG, null);
			fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}
	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#forceRegister(com.pixelsimple.appcore.registry.Registrable, java.lang.Object)}.
	 */
	@Test
	public void forceRegister() {
		Registry reg1 = MapRegistry.INSTANCE;
		Object val = new FfmpegConfig();
		
		reg1.forceRegister(Registrable.FFMPEG_CONFIG, val);
		Assert.assertEquals(reg1.containsKey(Registrable.FFMPEG_CONFIG), true);

		// Should have no effect.
		reg1.forceRegister(Registrable.FFMPEG_CONFIG, "abc");
		Object valret = reg1.fetch(Registrable.FFMPEG_CONFIG);
		
		Assert.assertNotSame(valret, val);
		Assert.assertTrue(valret != val);
		Assert.assertEquals(valret, "abc");
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#fetch(com.pixelsimple.appcore.registry.Registrable)}.
	 */
	@Test
	public void fetch() {
		Registry reg1 = MapRegistry.INSTANCE;
		Object val = new FfmpegConfig();
		
		reg1.register(Registrable.FFMPEG_CONFIG, val);
		Assert.assertEquals(reg1.containsKey(Registrable.FFMPEG_CONFIG), true);

		// Should have no effect.
		reg1.register(Registrable.FFMPEG_CONFIG, "abc");
		Object valret = reg1.fetch(Registrable.FFMPEG_CONFIG);
		
		Assert.assertEquals(valret, val);
		Assert.assertTrue(valret == val);
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#remove(com.pixelsimple.appcore.registry.Registrable)}.
	 */
	@Test
	public void remove() {
		Registry reg1 = MapRegistry.INSTANCE;
		Object val = new FfmpegConfig();
		
		reg1.register(Registrable.FFMPEG_CONFIG, "abc");
		Assert.assertEquals(reg1.containsKey(Registrable.FFMPEG_CONFIG), true);

		Object valret = reg1.fetch(Registrable.FFMPEG_CONFIG);
		
		Assert.assertEquals(valret, "abc");

		reg1.remove(Registrable.FFMPEG_CONFIG);
		
		reg1.register(Registrable.FFMPEG_CONFIG, val);
		valret = reg1.fetch(Registrable.FFMPEG_CONFIG);
		
		Assert.assertEquals(valret, val);
		Assert.assertTrue(valret == val);
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#removeAll()}.
	 */
	@Test
	public void testRemoveAll() {
		Registry reg1 = MapRegistry.INSTANCE;
		Object val = new FfmpegConfig();
		reg1.register(Registrable.FFMPEG_CONFIG, val);
		reg1.register(Registrable.FFPROBE_CONFIG, "abc");

		Assert.assertEquals(reg1.fetchAllValues().size(), 2);

		reg1.removeAll();
		
		Assert.assertEquals(reg1.fetchAllValues().size(), 0);
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#fetchAllValues()}.
	 */
	@Test
	public void testFetchAllValues() {
		Registry reg1 = MapRegistry.INSTANCE;
		Object val = new FfmpegConfig();
		reg1.register(Registrable.FFMPEG_CONFIG, val);
		reg1.register(Registrable.FFPROBE_CONFIG, "abc");

		Assert.assertEquals(reg1.fetchAllValues().size(), 2);
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.registry.MapRegistry#containsKey(com.pixelsimple.appcore.registry.Registrable)}.
	 */
	@Test
	public void testContainsKey() {
		Registry reg1 = MapRegistry.INSTANCE;
		Object val = new FfmpegConfig();
		
		reg1.register(Registrable.FFMPEG_CONFIG, "abc");
		Assert.assertEquals(reg1.containsKey(Registrable.FFMPEG_CONFIG), true);
		
		Assert.assertEquals(reg1.containsKey(Registrable.FFPROBE_CONFIG), false);
	}

}
