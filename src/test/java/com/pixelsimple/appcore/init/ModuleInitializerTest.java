/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.TestAppInitializer;

/**
 * @author Akshay Sharma
 *
 * Jun 22, 2012
 */
public class ModuleInitializerTest {
	private Map<String, String> configs;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		configs = TestAppInitializer.buildConfig();
	}

	@After
	public void tearDown() throws Exception {
		TestAppInitializer.shutdownAppInit();
	}

	@Test
	public void initializeWithoutConfig() throws Exception {
		AppInitializer initializer = new AppInitializer();
		
		SampleModuleInitializerNoConfig moduleInit = new SampleModuleInitializerNoConfig();
		initializer.addModuleInitializable(moduleInit);
		
		initializer.init(configs);
	}
	
	@Test
	public void initializeInvalidConfig() throws Exception {
		AppInitializer initializer = new AppInitializer();
		
		SampleModuleInitializerInvalidConfig moduleInit = new SampleModuleInitializerInvalidConfig();
		initializer.addModuleInitializable(moduleInit);
		
		try {
			initializer.init(configs);
			Assert.fail("Should not reach here");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}
	
	
	private class SampleModuleInitializerInvalidConfig extends ModuleInitializer {
		
		public SampleModuleInitializerInvalidConfig() {
			super("invalidconfigfile");
		}

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.init.ModuleInitializer#doInitialize(com.pixelsimple.appcore.ApiConfig)
		 */
		@Override
		public void doInitialize(ApiConfig apiConfig) throws Exception {
			Assert.fail("Should not reach here");
		}

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.init.ModuleInitializer#doDeinitialize(com.pixelsimple.appcore.ApiConfig)
		 */
		@Override
		public void doDeinitialize(ApiConfig apiConfig) throws Exception {
			// TODO: We need to fix error handling during module initializer.
			//Assert.fail("Should not reach here");
		}
		
	}

	private class SampleModuleInitializerNoConfig extends ModuleInitializer {
		
		public SampleModuleInitializerNoConfig() {
		}

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.init.ModuleInitializer#doInitialize(com.pixelsimple.appcore.ApiConfig)
		 */
		@Override
		public void doInitialize(ApiConfig apiConfig) throws Exception {
			Assert.assertNotNull(this.moduleConfigurationMap);
			Assert.assertEquals(this.moduleConfigurationMap.size(), 0);
			
		}

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.init.ModuleInitializer#doDeinitialize(com.pixelsimple.appcore.ApiConfig)
		 */
		@Override
		public void doDeinitialize(ApiConfig apiConfig) throws Exception {
			Assert.assertNotNull(this.moduleConfigurationMap);
			Assert.assertEquals(this.moduleConfigurationMap.size(), 0);
		}
		
	}
}
