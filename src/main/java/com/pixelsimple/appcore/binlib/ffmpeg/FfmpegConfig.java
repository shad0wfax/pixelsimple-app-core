/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.binlib.ffmpeg;

import java.io.File;

import com.pixelsimple.commons.util.Assert;

/**
 *
 * @author Akshay Sharma
 * Jan 15, 2012
 */
public final class FfmpegConfig {
	
	private String executablePath;
	
	public FfmpegConfig(String fullExecutablePath) {		
		File file = new File(fullExecutablePath);
		Assert.isTrue(file.isFile(), "Looks like the ffprobe path is not valid::" + fullExecutablePath);
		file = null; // gc it hopefully
				
		this.executablePath = fullExecutablePath;
	}
	
	/**
	 * @return the executablePath
	 */
	public String getExecutablePath() {
		return this.executablePath;
	}

	public String toString() {
		return "Ffmpeg path::" + executablePath;
	}

}
