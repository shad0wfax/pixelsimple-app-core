/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.config;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Feb 25, 2012
 */
public class EnvironmentTest {

	/**
	 * Test method for {@link com.pixelsimple.appcore.config.EnvironmentImpl#getCurrentOS()}.
	 */
	@Test
	public void checkEnvironment() {
		Map<String, String> appConfigs = new HashMap<String, String>();

		String path = "c:\\dev\\pixelsimple\\app";		
		appConfigs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, path);
		Environment impl = new Environment(appConfigs);
		Assert.assertEquals(impl.getCurrentOS(), OSUtils.CURRENT_OS);
		Assert.assertEquals(impl.getAppBasePath(), path + OSUtils.folderSeparator());
	
		path = "c:\\dev\\pixelsimple\\app/";
		appConfigs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, path);
		impl = new Environment(appConfigs);
		Assert.assertEquals(impl.getAppBasePath(), path.substring(0, path.length() - 1) + OSUtils.folderSeparator());
		
		path = "/usr/local/mypath";
		appConfigs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, path);
		impl = new Environment(appConfigs);
		Assert.assertEquals(impl.getAppBasePath(), path + OSUtils.folderSeparator());
		
	}

}
