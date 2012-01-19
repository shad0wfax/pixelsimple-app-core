/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import junit.framework.Assert;

import org.junit.Test;

/**
 *
 * @author Akshay Sharma
 * Jan 17, 2012
 */
public class BootstrapInitializerTest {

	/**
	 * Test method for {@link com.pixelsimple.appcore.init.BootstrapInitializer#bootstrap()}.
	 */
	@Test
	public void bootstrapNoSysProperties() {
		BootstrapInitializer init = new BootstrapInitializer();
		
		try {
			init.bootstrap();
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.init.BootstrapInitializer#bootstrap()}.
	 */
	@Test
	public void bootstrapNoHomeDir() {
		BootstrapInitializer init = new BootstrapInitializer();
		System.setProperty(BootstrapInitializer.JAVA_SYS_ARG_CONFIG_FILE, "abc");
		try {
			init.bootstrap();
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.init.BootstrapInitializer#bootstrap()}.
	 */
	@Test
	public void bootstrapNoConfigFile() {
		BootstrapInitializer init = new BootstrapInitializer();
		System.setProperty(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, "abc");
		try {
			init.bootstrap();
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.init.BootstrapInitializer#bootstrap()}.
	 */
	@Test
	public void bootstrapWithInvalidConfigFile() {
		BootstrapInitializer init = new BootstrapInitializer();
		System.setProperty(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, "c:/dev");
		System.setProperty(BootstrapInitializer.JAVA_SYS_ARG_CONFIG_FILE, "invalidfile");
		
		try {
			init.bootstrap();
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * NOTE: THIS TEST METHOD IS COMMENTED UNFORTUNATELY. 
	 * The reason is that it needs to load a properties file from the resources folder, which works fine on local 
	 * testing but on CI running on a VM the paths get all messy. So have to ignore this test :(.
	 *  
	 * Test method for {@link com.pixelsimple.appcore.init.BootstrapInitializer#bootstrap()}.
	 */
//	@Test
//	public void bootstrapWithSuccess() {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
////		System.out.println("classLoader = " + classLoader.getResource(""));
//		URL url = classLoader.getResource("com/pixelsimple/appcore/init/testAppConfig.properties");
//
//		BootstrapInitializer init = new BootstrapInitializer();
//		System.setProperty(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, "abc");
//		System.setProperty(BootstrapInitializer.JAVA_SYS_ARG_CONFIG_FILE, url.getFile());
//		
//		System.out.println(" url.getFile() = " +  url.getFile());
//		
//		try {
//			init.bootstrap();
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//	}

}
