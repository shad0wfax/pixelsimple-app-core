/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.env;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.Environment;
import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 14, 2012
 */
public class EnvironmentImpl implements Environment {
	private static final Logger LOG = LoggerFactory.getLogger(EnvironmentImpl.class);
	
	// This will the path to the package directory under which we will find the jre/bin/lib dirs.
	private String appBasePath;

	private OSUtils.OS CURRENT_OS = OSUtils.CURRENT_OS;
	
	public EnvironmentImpl() {
		URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		
		LOG.debug("url loca = " + url.getPath());
		LOG.debug("user.dir = " + System.getProperty("user.dir"));
		
		// TODO: Find an algo to figure out which is the better way to choose this. For now going with user.dir
	}
	
	public static void main (String [] args) {
		EnvironmentImpl impl = new EnvironmentImpl();
	}
	
}
