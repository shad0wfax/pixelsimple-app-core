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
public interface ApiConfig {
	
	/**
	 * @return the ffmpegConfig
	 */
	public FfmpegConfig getFfmpegConfig();
	
	/**
	 * @return the ffprobeConfig
	 */
	public FfprobeConfig getFfprobeConfig();

	/**
	 * @return the environment
	 */
	public Environment getEnvironment();

	/**
	 * @return the hlsTranscodeCompleteFile
	 */
	public String getHlsTranscodeCompleteFile();

	/**
	 * @return the hlsPlaylistGeneratorPath
	 */
	public String getHlsPlaylistGeneratorPath();

	/**
	 * @return the hlsPlaylistGeneratorPath
	 */
	public String getHlsFileSegmentPattern();
}
