/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.ApiConfigImpl;
import com.pixelsimple.appcore.Registrable;
import com.pixelsimple.appcore.Registry;
import com.pixelsimple.appcore.RegistryService;
import com.pixelsimple.appcore.binlib.ffmpeg.FfmpegConfig;
import com.pixelsimple.appcore.binlib.ffprobe.FfprobeConfig;
import com.pixelsimple.appcore.env.Environment;
import com.pixelsimple.appcore.env.EnvironmentImpl;
import com.pixelsimple.appcore.registry.MapRegistry;

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
	

	/**
	 * @param argMap
	 */
	public void init(Map<String, String> configMap) {
		// Init log?? (it must already be)
		LOG.debug("init::Initing the app with the argMap:: {}", configMap);
		ApiConfigImpl apiConfigImpl = new ApiConfigImpl();
		
		// Init Environment
		Environment env = this.initEvn(configMap);
		
		// Init binlibs - based on available params/options - transcoder only vs full webme etc.
		FfmpegConfig ffmpegConfig = this.initFfmpeg(configMap);
		FfprobeConfig ffprobeConfig = this.initFfprobe(configMap);
		
		// DB and so on...

		// Set it to the Api config:
		apiConfigImpl.setEnvironment(env).setFfmpegConfig(ffmpegConfig).setFfprobeConfig(ffprobeConfig);
		
		// Init Registry and save the api config.
		Registry registry = MapRegistry.INSTANCE;
		registry.register(Registrable.API_CONFIG, apiConfigImpl);
		
		LOG.debug("init::Registered API Config:: {}", apiConfigImpl);
	}


	/**
	 * @param configMap
	 * @return
	 */
	private FfprobeConfig initFfprobe(Map<String, String> configMap) {
		String ffmpegPath = configMap.get(APP_CONFIG_FFPROBE_PATH_KEY);
		FfprobeConfig ffprobeConfig = new FfprobeConfig(ffmpegPath);
		return ffprobeConfig;
	}


	/**
	 * @param configMap
	 * @return
	 */
	private FfmpegConfig initFfmpeg(Map<String, String> configMap) {
		String ffmpegPath = configMap.get(APP_CONFIG_FFMPEG_PATH_KEY);
		FfmpegConfig ffmpegConfig = new FfmpegConfig(ffmpegPath);
		return ffmpegConfig;
	}


	/**
	 * @param configMap
	 * @return
	 */
	private Environment initEvn(Map<String, String> configMap) {
		EnvironmentImpl environment = new EnvironmentImpl();
		
		String appBasePath = configMap.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR);
		environment.setAppBasePath(appBasePath);
		
		return environment;
	}
}
