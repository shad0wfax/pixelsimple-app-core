/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.ApiConfigImpl;
import com.pixelsimple.appcore.Registrable;
import com.pixelsimple.appcore.Registry;
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
	private static final String APP_CONFIG_HLS_COMPLETE_FILE = "hlsTranscodeCompleteFile";
	private static final String APP_CONFIG_HLS_PLAYLIST_GENERATOR_PATH = "hlsPlaylistGeneratorPath";
	private static final String APP_CONFIG_HLS_FILE_SEGMENT_PATTERN = "hlsFileSegmentPattern";
	
	// Order is important, so a list (one initializable might depend on other)
	private static List<Initializable> MODULE_INITIALIZABLES = new ArrayList<Initializable>(8);
	
	public AppInitializer() {
		// Add core initializer as the first one to be initialized. It will also be the last to be deinitialized.
		MODULE_INITIALIZABLES.add(new CoreInitializer());
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
		this.initCore(configMap);

		// init the registered module initialzables
		for (Initializable initializable : MODULE_INITIALIZABLES) {
			initializable.initialize(MapRegistry.INSTANCE);
		}
	}

	public void shutdown() throws Exception {
		// TODO: Add any hooks as needed. Do housekeeping. 
		
		// De-initialize things in the reverse order 
		for (int i = MODULE_INITIALIZABLES.size() -1; i >= 0; i--) {
			Initializable initializable = MODULE_INITIALIZABLES.get(i);
			initializable.deinitialize(MapRegistry.INSTANCE);
		}
		// Finally remove all entries:
		MapRegistry.INSTANCE.removeAll();
		MODULE_INITIALIZABLES.clear();
	}

	
	public void initCore(Map<String, String> configMap) throws Exception {
		// Init log?? (it must already be)
		LOG.debug("init::Initing the app with the argMap:: {}", configMap);
		ApiConfigImpl apiConfigImpl = new ApiConfigImpl();
		
		// Init Environment - The first thing
		Environment env = this.initEvn(configMap);
		
		// Init binlibs - based on available params/options - framezap only vs full nova etc.
		FfmpegConfig ffmpegConfig = this.initFfmpeg(env, configMap);
		FfprobeConfig ffprobeConfig = this.initFfprobe(env, configMap);
		
		String hlsTranscodeCompleteFile = configMap.get(APP_CONFIG_HLS_COMPLETE_FILE);
		String hlsPlaylistGeneratorPath = configMap.get(APP_CONFIG_HLS_PLAYLIST_GENERATOR_PATH);
		String hlsFileSegmentPattern = configMap.get(APP_CONFIG_HLS_FILE_SEGMENT_PATTERN);
		
		// Set it to the Api config:
		apiConfigImpl.setEnvironment(env).setFfmpegConfig(ffmpegConfig).setFfprobeConfig(ffprobeConfig)
			.setHlsTranscodeCompleteFile(hlsTranscodeCompleteFile)
			.setHlsPlaylistGeneratorPath(env.getAppBasePath() + hlsPlaylistGeneratorPath)
			.setHlsFileSegmentPattern(hlsFileSegmentPattern);

		LOG.debug("init::Registered API Config:: {}", apiConfigImpl);
		
		// Init Registry and save the api config.
		Registry registry = MapRegistry.INSTANCE;
		registry.register(Registrable.API_CONFIG, apiConfigImpl);
		
		// Other inits - DB and so on...
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
		EnvironmentImpl environment = new EnvironmentImpl();
		
		String appBasePath = configMap.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR);
		environment.setAppBasePath(appBasePath);
		
		return environment;
	}
}
