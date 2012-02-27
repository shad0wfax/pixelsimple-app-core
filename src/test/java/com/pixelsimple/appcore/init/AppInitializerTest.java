/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.Registrable;
import com.pixelsimple.appcore.Registry;
import com.pixelsimple.appcore.RegistryService;
import com.pixelsimple.appcore.registry.MapRegistry;
import com.pixelsimple.commons.util.OSUtils;
import com.pixelsimple.commons.util.OSUtils.OS;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public class AppInitializerTest {

	/**
	 * Test method for {@link com.pixelsimple.appcore.init.AppInitializer#init(java.util.Map)}.
	 */
	@Test
	public void initWithValidValues() throws Exception {
		Map<String, String> configs = getConfig();
		
		AppInitializer initializer = new AppInitializer();
		initializer.init(configs);
		
		ApiConfig config = (ApiConfig) RegistryService.getRegisteredApiConfig();
		
		Assert.assertNotNull(config);
		Assert.assertEquals(config.getFfmpegConfig().getExecutablePath(),  
			configs.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR) + OSUtils.folderSeparator() + configs.get("ffmpegPath"));
		Assert.assertEquals(config.getFfprobeConfig().getExecutablePath(), 
			configs.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR) + OSUtils.folderSeparator() + configs.get("ffprobePath"));
		Assert.assertEquals(config.getEnvironment().getAppBasePath(), configs.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR) + OSUtils.folderSeparator());
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.init.AppInitializer#init(java.util.Map)}.
	 */
	@Test
	public void addModuleInitializers() throws Exception {
		Map<String, String> configs = getConfig();
		
		AppInitializer initializer = new AppInitializer();
		initializer.init(configs);
		int size = MapRegistry.INSTANCE.fetchAllValues().size();
		Assert.assertEquals(size, 3);
		
		initializer = new AppInitializer();
		initializer.addModuleInitializable(new Initializable() {
			
			@Override
			public void initialize(Registry registry) throws Exception {
				registry.register(Registrable.MEDIA_PROFILES, "abc");
			}
			
			@Override
			public void deinitialize(Registry registry) throws Exception {
				registry.remove(Registrable.MEDIA_PROFILES);
			}
		});
		initializer.init(configs);
		
		Assert.assertEquals(MapRegistry.INSTANCE.fetchAllValues().size(), size + 1);
		Assert.assertEquals(MapRegistry.INSTANCE.fetch(Registrable.MEDIA_PROFILES), "abc");
		
		initializer.shutdown();
		Assert.assertEquals(MapRegistry.INSTANCE.fetchAllValues().size(), 0);
	}

	/**
	 * A test to ensure that as we add more items to registry after init, they are tested here :-)
	 * Test method for {@link com.pixelsimple.appcore.init.AppInitializer#init(java.util.Map)}.
	 */
	@Test
	public void verifyRegistryEntries() throws Exception {
		Map<String, String> configs = getConfig();
		
		AppInitializer initializer = new AppInitializer();
		initializer.init(configs);
		
		// Keep track on the count here and ensure it is updated based on init code
		Assert.assertEquals(MapRegistry.INSTANCE.fetchAllValues().size(), 3);

		// Can't do this any longer - since modules register their own stuff.
//		for (Registrable r : Registrable.values()) {
//			Assert.assertNotNull(MapRegistry.INSTANCE.fetch(r));
//		}
		initializer.shutdown();
		Assert.assertEquals(MapRegistry.INSTANCE.fetchAllValues().size(), 0);
	}

	private Map<String, String> getConfig() {
		Map<String, String> configs = new HashMap<String, String>();
		
		if (OSUtils.CURRENT_OS == OS.WINDOWS) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, "c:\\dev\\pixelsimple");
			configs.put("ffprobePath", "ffprobe/32_bit/0.8/ffprobe.exe"); 
			configs.put("ffmpegPath", "ffmpeg/32_bit/0.8/ffmpeg.exe"); 
		} else if (OSUtils.CURRENT_OS == OS.MAC) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR,  OSUtils.USER_SYSTEM_HOME_DIR + "/dev/pixelsimple");
			configs.put("ffprobePath",  "ffprobe/32_bit/0.7_beta2/ffprobe"); 
			configs.put("ffmpegPath",  "ffmpeg/32_bit/0.8.7/ffmpeg"); 
		}  else {
			// add linux based tests when ready :-)
		}
		
		return configs;
	}
}
