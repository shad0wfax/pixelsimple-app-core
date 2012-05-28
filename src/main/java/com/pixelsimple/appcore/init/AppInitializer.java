/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.ApiConfigImpl;
import com.pixelsimple.appcore.binlib.ffmpeg.FfmpegConfig;
import com.pixelsimple.appcore.binlib.ffprobe.FfprobeConfig;
import com.pixelsimple.appcore.config.Environment;
import com.pixelsimple.appcore.registry.GenericRegistryEntry;
import com.pixelsimple.appcore.registry.MapRegistry;
import com.pixelsimple.appcore.registry.Registrable;
import com.pixelsimple.appcore.registry.Registry;

/**
 *
 * @author Akshay Sharma
 * Jan 17, 2012
 */
public class AppInitializer {

	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
	
	// Again static dependencies - I want complete control on the loading/initializing.
	private static final String APP_CONFIG_FFMPEG_PATH_KEY = "ffmpegPath";
	private static final String APP_CONFIG_FFPROBE_PATH_KEY = "ffprobePath";
	
	// Order is important, so a list (one initializable might depend on other)
	private static List<Initializable> MODULE_INITIALIZABLES = new ArrayList<Initializable>(8);
	
	public AppInitializer() {
		// Init Registry.
		Registry registry = MapRegistry.INSTANCE;

		// Add core initializer as the first one to be initialized. It will also be the last to be deinitialized.
		MODULE_INITIALIZABLES.add(new CoreInitializer());
		// Then initialize the config object. This will set itself up for others to register configs.
		GenericRegistryEntry genericRegistryEntry = new GenericRegistryEntry();
		MODULE_INITIALIZABLES.add(genericRegistryEntry);
		registry.register(Registrable.GENERIC_REGISTRY_ENTRY, genericRegistryEntry);
	}

	public void addModuleInitializable(Initializable object) {
		if (object != null) {
			MODULE_INITIALIZABLES.add(object);
		}
	}
	
	/**
	 * @param argMap
	 */
	public void init(Map<String, String> configMap) throws Exception {
		// Make the map immutable - Note: Make it after all configs are properly added (in case db override is needed).
		Map<String, String> immutableConfigMap = Collections.unmodifiableMap(configMap);
		
		ApiConfig apiConfig = this.initCore(immutableConfigMap);

		// init the registered module initialzables
		for (Initializable initializable : MODULE_INITIALIZABLES) {
			initializable.initialize(MapRegistry.INSTANCE, apiConfig);
		}
	}

	public void shutdown() throws Exception {
		ApiConfig apiConfig = (ApiConfig) MapRegistry.INSTANCE.fetch(Registrable.API_CONFIG);
		
		// TODO: Add any hooks as needed. Do housekeeping. 
		
		// De-initialize things in the reverse order 
		for (int i = MODULE_INITIALIZABLES.size() -1; i >= 0; i--) {
			Initializable initializable = MODULE_INITIALIZABLES.get(i);
			initializable.deinitialize(MapRegistry.INSTANCE, apiConfig);
		}
		// Finally remove all entries:
		MapRegistry.INSTANCE.removeAll();
		MODULE_INITIALIZABLES.clear();
	}

	
	private ApiConfig initCore(Map<String, String> immutableConfigMap) throws Exception {
		// Init log?? (it must already be)
		LOG.debug("init::Initing the app with the argMap:: {}", immutableConfigMap);
		ApiConfigImpl apiConfigImpl = new ApiConfigImpl();
		
		// Init Environment - The first thing
		Environment env = this.initEvn(immutableConfigMap);
		
		// Init binlibs - based on available params/options - framezap only vs full nova etc.
		FfmpegConfig ffmpegConfig = this.initFfmpeg(env, immutableConfigMap);
		FfprobeConfig ffprobeConfig = this.initFfprobe(env, immutableConfigMap);
		
		// Set it to the Api config:
		GenericRegistryEntry genericRegistryEntry = (GenericRegistryEntry) MapRegistry.INSTANCE.fetch(
				Registrable.GENERIC_REGISTRY_ENTRY);
		
		apiConfigImpl.setEnvironment(env).setFfmpegConfig(ffmpegConfig).setFfprobeConfig(ffprobeConfig)
			.setGenericConfig(genericRegistryEntry);

		LOG.debug("init::Registered API GenericConfig:: {}", apiConfigImpl);
		
		MapRegistry.INSTANCE.register(Registrable.API_CONFIG, apiConfigImpl);
		
		// Other inits - DB and so on...
		
		return apiConfigImpl;
	}

	/**
	 * @param configMap
	 * @return
	 */
	private FfprobeConfig initFfprobe(Environment env, Map<String, String> configMap) {
		String ffmpegPath = configMap.get(APP_CONFIG_FFPROBE_PATH_KEY);
		FfprobeConfig ffprobeConfig = new FfprobeConfig(env.getAppBasePath() + ffmpegPath);
		return ffprobeConfig;
	}


	/**
	 * @param configMap
	 * @return
	 */
	private FfmpegConfig initFfmpeg(Environment env, Map<String, String> configMap) {
		String ffmpegPath = configMap.get(APP_CONFIG_FFMPEG_PATH_KEY);
		FfmpegConfig ffmpegConfig = new FfmpegConfig(env.getAppBasePath() + ffmpegPath);
		return ffmpegConfig;
	}

	/**
	 * @param configMap
	 * @return
	 */
	private Environment initEvn(Map<String, String> configMap) {
		Environment environment = new Environment(configMap);
		return environment;
	}
}
