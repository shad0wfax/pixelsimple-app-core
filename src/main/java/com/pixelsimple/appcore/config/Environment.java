/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.config;

import java.util.Map;

import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 14, 2012
 */
public final class Environment {
	// This will the path to the package directory under which we will find the jre/bin/lib dirs.
	private String appBasePath;
	private OSUtils.OS CURRENT_OS = OSUtils.CURRENT_OS;
	private Map<String, String> appConfigs;

	public Environment(Map<String, String> appConfigs) {
		this.appConfigs = appConfigs;
		this.appBasePath = OSUtils.appendFolderSeparator(appConfigs.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR));
	}
	
	/**
	 * @return the cURRENT_OS
	 */
	public OSUtils.OS getCurrentOS() {
		return CURRENT_OS;
	}

	/**
	 * @return the appBasePath
	 */
	public String getAppBasePath() {
		return appBasePath;
	}

	/**
	 * Get the app_config_win/mac.properties file as a map of key value pair. This is useful for modules that need
	 * to get their configurations.  The returned map is immutable to ensure no module can change the 
	 * configuration once bootstrapped.
	 * @return
	 */
	public Map<String, String> getImmutableApplicationConfiguratations() {
		return this.appConfigs;
	}

	public String toString() {
		return "appBasePath::" + appBasePath + "::CURRENT_OS::" + CURRENT_OS
				+ "::App configs::" + appConfigs;
	}

}
