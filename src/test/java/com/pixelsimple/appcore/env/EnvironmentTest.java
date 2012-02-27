/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.env;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Feb 25, 2012
 */
public class EnvironmentTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.env.EnvironmentImpl#getCurrentOS()}.
	 */
	@Test
	public void checkEnvironment() {
		EnvironmentImpl impl = new EnvironmentImpl();
		
		Assert.assertEquals(impl.getCurrentOS(), OSUtils.CURRENT_OS);
		
		String path = "c:\\dev\\pixelsimple\\app";
		impl.setAppBasePath(path);
		Assert.assertEquals(impl.getAppBasePath(), path + OSUtils.folderSeparator());
	
		path = "c:\\dev\\pixelsimple\\app/";
		impl.setAppBasePath(path);
		Assert.assertEquals(impl.getAppBasePath(), path);
		
		path = "/usr/local/mypath";
		impl.setAppBasePath(path);
		Assert.assertEquals(impl.getAppBasePath(), path + OSUtils.folderSeparator());
		
	}

}
