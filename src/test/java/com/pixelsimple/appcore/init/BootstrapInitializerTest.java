/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.commons.util.StringUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 17, 2012
 */
public class BootstrapInitializerTest {
	private static final Logger LOG = LoggerFactory.getLogger(BootstrapInitializerTest.class);

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

	@Test
	public void variableSubstitutionTest() {
		// A hack test - copied the code below to facilitate testing :(
		System.setProperty("test.app.home", "/something");
		System.setProperty("temp.dir", "c:\\tmp");
		System.setProperty("log.dir", "/log/path\\dir\\");
		BootstrapInitializer init = new BootstrapInitializer();
		Properties props = new Properties();
		props.put("1", "${test.app.home}");
		props.put("2", "/something${temp.dir}");
		props.put("3", "${test.app.home}${temp.dir}");
		props.put("4", "${test.app.home.invalid}${invalid.temp.dir}");
		props.put("5", "/something${test.app.home.invalid}/something");
		props.put("6", "");
		props.put("7", "        ");
		props.put("8", "/var${test.app.home}/opt\\${temp.dir}\\hello\\${log.dir}test");
		props.put("9", "bin/hls_playlist_generator.sh");
		
		Map<String, String> configs = init.asMapWithVariableSubstitution(props);
		
		Assert.assertEquals("/something", configs.get("1"));
		Assert.assertEquals("/somethingc:\\tmp", configs.get("2"));
		Assert.assertEquals("/somethingc:\\tmp", configs.get("3"));
		Assert.assertEquals("${test.app.home.invalid}${invalid.temp.dir}", configs.get("4"));
		Assert.assertEquals("/something${test.app.home.invalid}/something", configs.get("5"));
		Assert.assertEquals("", configs.get("6")); 
		Assert.assertEquals("        ", configs.get("7"));
		Assert.assertEquals("/var/something/opt\\c:\\tmp\\hello\\/log/path\\dir\\test", configs.get("8"));
		Assert.assertEquals("bin/hls_playlist_generator.sh", configs.get("9"));
		
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
