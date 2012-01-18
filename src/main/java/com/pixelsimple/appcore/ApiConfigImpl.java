/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

import com.pixelsimple.appcore.binlib.ffmpeg.FfmpegConfig;
import com.pixelsimple.appcore.binlib.ffprobe.FfprobeConfig;
import com.pixelsimple.appcore.env.Environment;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public final class ApiConfigImpl implements ApiConfig {
	private FfmpegConfig ffmpegConfig;
	private FfprobeConfig ffprobeConfig;
	private Environment environment;
	
	/**
	 * @return the ffmpegConfig
	 */
	public FfmpegConfig getFfmpegConfig() {
		return ffmpegConfig;
	}
	/**
	 * @param ffmpegConfig the ffmpegConfig to set
	 */
	public ApiConfigImpl setFfmpegConfig(FfmpegConfig ffmpegConfig) {
		this.ffmpegConfig = ffmpegConfig;
		return this;
	}
	/**
	 * @return the ffprobeConfig
	 */
	public FfprobeConfig getFfprobeConfig() {
		return ffprobeConfig;
	}
	/**
	 * @param ffprobeConfig the ffprobeConfig to set
	 */
	public ApiConfigImpl setFfprobeConfig(FfprobeConfig ffprobeConfig) {
		this.ffprobeConfig = ffprobeConfig;
		return this;
	}
	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}
	/**
	 * @param environment the environment to set
	 */
	public ApiConfigImpl setEnvironment(Environment environment) {
		this.environment = environment;
		return this;
	}

}
