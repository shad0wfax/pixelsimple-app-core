/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.config;

import java.util.Map;

import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.commons.util.Assert;
import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 14, 2012
 */
public final class Environment {
	// This will the path to the package directory under which we will find the jre/bin/lib dirs.
	private String appBasePath;
	private String tempDirectory;
	private String configDirectory;

	private OSUtils.OS CURRENT_OS = OSUtils.CURRENT_OS;
	private Map<String, String> appConfigs;

	public Environment(Map<String, String> appConfigs) {
		this.appConfigs = appConfigs;
		String appBasePath = appConfigs.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR);
		Assert.notNullAndNotEmpty(appBasePath, "The app base path system property has to be set for app to startup."); 

		String tempDir = appConfigs.get("tempDirectory");
		Assert.notNullAndNotEmpty(tempDir, "The temp directory has to be configured for app to startup."); 
		
		String configDir = appConfigs.get("configDirectory");
		Assert.notNullAndNotEmpty(configDir, "The config directory has to be configured for app to startup."); 
		
		this.appBasePath = OSUtils.appendFolderSeparator(appBasePath);
		this.tempDirectory = OSUtils.appendFolderSeparator(tempDir);
		// Config directory is always relative
		this.configDirectory = this.appBasePath + OSUtils.appendFolderSeparator(configDir);
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

	public String getTempDirectory() {
		return this.tempDirectory;
	}

	public String getConfigDirectory() {
		return this.configDirectory;
	}
	
	/**
	 * Always returns the path to the directory called 'module' that is under config directory. 
	 * @return
	 */
	public String getModuleConfigDirectory() {
		return this.configDirectory + "module" + OSUtils.folderSeparator();
	}
	
	public String toString() {
		return "appBasePath::" + appBasePath + "::CURRENT_OS::" + CURRENT_OS + "::configDirectory::" + configDirectory 
				+ "::tempDirectory::" + tempDirectory + "::App configs::" + appConfigs;
	}

}
