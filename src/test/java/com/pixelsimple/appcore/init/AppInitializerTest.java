/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.registry.GenericRegistryEntry;
import com.pixelsimple.appcore.registry.GenericRegistryEntryKey;
import com.pixelsimple.appcore.registry.MapRegistry;
import com.pixelsimple.appcore.registry.Registrable;
import com.pixelsimple.appcore.registry.RegistryService;
import com.pixelsimple.commons.util.OSUtils;

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
	public void prooveAppConfigImmutability() throws Exception {
		Map<String, String> configs = getConfig();
		
		AppInitializer initializer = new AppInitializer();
		initializer.init(configs);
		
		ApiConfig config = (ApiConfig) RegistryService.getRegisteredApiConfig();
		
		Assert.assertNotNull(config);
		
		Map<String, String> appConfigs = config.getEnvironment().getImmutableApplicationConfiguratations();
		Assert.assertNotNull(appConfigs);
		Assert.assertTrue(appConfigs.size() > 0);
		
		try {
			appConfigs.put("hello", "world");
			Assert.fail();
		} catch (UnsupportedOperationException e) {
			Assert.assertTrue(true);
		}

		try {
			appConfigs.put("ffmpegPath", "new_path");
			Assert.fail();
		} catch (UnsupportedOperationException e) {
			Assert.assertTrue(true);
		}
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
		Assert.assertEquals(size, Registrable.values().length);
		
		initializer = new AppInitializer();
		initializer.addModuleInitializable(new Initializable() {
			
			@Override
			public void initialize(ApiConfig config) throws Exception {
				GenericRegistryEntry entry = config.getGenericRegistryEntry();
				entry.addEntry(TempEnum.A, "abc");
			}
			
			@Override
			public void deinitialize(ApiConfig config) throws Exception {
				GenericRegistryEntry entry = config.getGenericRegistryEntry();
				entry.removeEntry(TempEnum.A);
			}
		});
		initializer.init(configs);
		
		Assert.assertEquals(MapRegistry.INSTANCE.fetchAllValues().size(), size);
		Assert.assertNotNull(MapRegistry.INSTANCE.fetch(Registrable.SUPPORTED_CODECS));
		Assert.assertEquals(RegistryService.getRegisteredApiConfig().getGenericRegistryEntry().getEntry(TempEnum.A), "abc");

		
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
		Assert.assertEquals(MapRegistry.INSTANCE.fetchAllValues().size(), Registrable.values().length);

		// Can't do this any longer - since modules register their own stuff.
//		for (Registrable r : Registrable.values()) {
//			Assert.assertNotNull(MapRegistry.INSTANCE.fetch(r));
//		}
		initializer.shutdown();
		Assert.assertEquals(MapRegistry.INSTANCE.fetchAllValues().size(), 0);
	}

	private Map<String, String> getConfig() {
		Map<String, String> configs = new HashMap<String, String>();
		
		if (OSUtils.isWindows()) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, "c:\\dev\\pixelsimple");
			configs.put("ffprobePath", "ffprobe/32_bit/1.0/ffprobe.exe"); 
			configs.put("ffmpegPath", "ffmpeg/32_bit/1.0/ffmpeg.exe"); 
			
			// Will use the ffmpeg path for testing this... pain to setup a file on each dev system.
			configs.put("hlsPlaylistGeneratorPath", "ffmpeg/32_bit/1.0/ffmpeg.exe"); 
		} else if (OSUtils.isMac()) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR,  OSUtils.USER_SYSTEM_HOME_DIR + "/dev/pixelsimple");
			configs.put("ffprobePath",  "ffprobe/32_bit/1.0/ffprobe"); 
			configs.put("ffmpegPath",  "ffmpeg/32_bit/1.0/ffmpeg"); 

			// Will use the ffmpeg path for testing this... pain to setup a file on each dev system.
			configs.put("hlsPlaylistGeneratorPath", "ffmpeg/32_bit/1.0/ffmpeg"); 
		}  else {
			// add linux based tests when ready :-)
		}
		
		configs.put("hlsTranscodeCompleteFile", "pixelsimple_hls_transcode.complete"); 
		configs.put("hlsFileSegmentPattern", "%06d"); 
		
		return configs;
	}
	
	private enum TempEnum implements GenericRegistryEntryKey {
		A;

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.registry.GenericRegistryEntryKey#getUniqueModuleName()
		 */
		@Override
		public String getUniqueModuleName() {
			return "unittesting";
		}

		/* (non-Javadoc)
		 * @see com.pixelsimple.appcore.registry.GenericRegistryEntryKey#getUniqueId()
		 */
		@Override
		public String getUniqueId() {
			return name();
		}
	}
}
