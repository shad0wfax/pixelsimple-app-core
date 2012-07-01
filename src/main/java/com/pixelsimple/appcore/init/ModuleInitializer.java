/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.commons.util.Assert;

/**
 * @author Akshay Sharma
 *
 * Jun 22, 2012
 */
public abstract class ModuleInitializer implements Initializable {
	private String moduleConfigFile;
	protected Map<String, String> moduleConfigurationMap;
	
	/**
	 * A default constructor that does not load any module config files. Implementations that don't care about having 
	 * their module configs inside config/module directory can use this.
	 */
	public ModuleInitializer() {}
	
	/**
	 * A constructor that lets us have implementations that provide a module config file to load.
	 * But the implementation class can pass a module config file as first argument to this constructor vis super().
	 * @param moduleConfigFiles
	 */
	public ModuleInitializer(String moduleConfigFile) {
		this.moduleConfigFile = moduleConfigFile;
	}
	
	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#initialize(com.pixelsimple.appcore.ApiConfig)
	 */
	@Override
	public final void initialize(ApiConfig apiConfig) throws Exception {
		if (this.moduleConfigFile != null) {
			if (this.moduleConfigurationMap == null) {
				String moduleConfigFileCompletePath = apiConfig.getEnvironment().getModuleConfigDirectory() + moduleConfigFile;
				File file = new File(moduleConfigFileCompletePath);
				Assert.isTrue(file.isFile(), "Looks like the config file in not valid. Will fail to initialize the module. - "
						+ moduleConfigFileCompletePath);
		
				Properties props = new Properties();
				props.load(new FileInputStream(moduleConfigFileCompletePath));
				this.moduleConfigurationMap = new BootstrapInitializer().asMapWithVariableSubstitution(props);
			}
		} else {
			// Empty map - there are no config files to load
			this.moduleConfigurationMap = new HashMap<String, String>(1);
		}
		doInitialize(apiConfig);
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#deinitialize(com.pixelsimple.appcore.ApiConfig)
	 */
	@Override
	public final void deinitialize(ApiConfig apiConfig) throws Exception {
		doDeinitialize(apiConfig);
		
		if (this.moduleConfigurationMap != null) {
			this.moduleConfigurationMap.clear();
			this.moduleConfigurationMap = null;
		}
	}
	
	abstract public void doInitialize(ApiConfig apiConfig) throws Exception;
	abstract public void doDeinitialize(ApiConfig apiConfig) throws Exception;

}
