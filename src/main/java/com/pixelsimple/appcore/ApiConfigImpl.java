/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

import java.io.File;

import com.pixelsimple.appcore.binlib.ffmpeg.FfmpegConfig;
import com.pixelsimple.appcore.binlib.ffprobe.FfprobeConfig;
import com.pixelsimple.appcore.env.Environment;
import com.pixelsimple.commons.util.Assert;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public final class ApiConfigImpl implements ApiConfig {
	private FfmpegConfig ffmpegConfig;
	private FfprobeConfig ffprobeConfig;
	private String hlsTranscodeCompleteFile;
	// Going further all paths will be as string instead of their own objects (like ffmpeg/ffprobe)
	private String hlsPlaylistGeneratorPath;
	private String hlsFileSegmentPattern;
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
	
	/**
	 * @return the hlsTranscodeCompleteFile
	 */
	public String getHlsTranscodeCompleteFile() {
		return hlsTranscodeCompleteFile;
	}
	
	/**
	 * @param hlsTranscodeCompleteFile the hlsTranscodeCompleteFile to set
	 */
	public ApiConfigImpl setHlsTranscodeCompleteFile(String hlsTranscodeCompleteFile) {
		this.hlsTranscodeCompleteFile = hlsTranscodeCompleteFile;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.ApiConfig#getHlsPlaylistGeneratorPath()
	 */
	@Override
	public String getHlsPlaylistGeneratorPath() {
		return this.hlsPlaylistGeneratorPath;
	}

	public ApiConfigImpl setHlsPlaylistGeneratorPath(String hlsPlaylistGeneratorPath) {
		this.validateFile(hlsPlaylistGeneratorPath);
		this.hlsPlaylistGeneratorPath = hlsPlaylistGeneratorPath;
		return this;
	}
	
	private void validateFile(String fullExecutablePath) {
		File file = new File(fullExecutablePath);
		Assert.isTrue(file.isFile(), "Looks like the ffprobe path is not valid::" + fullExecutablePath);

		file = null; // gc it hopefully
	}
	
	public String getHlsFileSegmentPattern() {
		return hlsFileSegmentPattern;
	}

	public ApiConfigImpl setHlsFileSegmentPattern(String hlsFileSegmentPattern) {
		this.hlsFileSegmentPattern = hlsFileSegmentPattern;
		return this;
	}

	public String toString() {
		return "APIConfig::\n" + this.ffmpegConfig.toString()+ "\n" + this.ffprobeConfig.toString() + "\n" 
			+ this.environment.toString() + "\n" + this.hlsPlaylistGeneratorPath  + "\n" + this.hlsTranscodeCompleteFile
			+ "\n" + this.hlsFileSegmentPattern;
	}

}
