/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.binlib.ffmpeg;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public class FfmpegConfigTest {

	/**
	 * Test method for {@link com.pixelsimple.appcore.binlib.ffmpeg.FfmpegConfig#FfmpegConfig(java.lang.String)}.
	 */
	@Test
	public void ffmpegConfigInvalidFile() {
		try {
			new FfmpegConfig("invalidfile");
			fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.binlib.ffmpeg.FfmpegConfig#FfmpegConfig(java.lang.String)}.
	 */
	@Test
	public void ffmpegConfigValidFile() {
		try {
			String validFile = null;
			
			if (OSUtils.isWindows()) {
				// Keep this path up to date with ffmpeg updates
				validFile = "c:/dev/pixelsimple/ffmpeg/32_bit/0.8/ffmpeg.exe";
			} else if (OSUtils.isMac()) {
				// Keep this path up to date with ffmpeg updates
				validFile = OSUtils.USER_SYSTEM_HOME_DIR + "/dev/pixelsimple/ffmpeg/32_bit/0.8.7/ffmpeg";
			}  
			
			if (validFile != null) {
				FfmpegConfig ffmpegConfig = new FfmpegConfig(validFile);	
				Assert.assertTrue(validFile.equals(ffmpegConfig.getExecutablePath()));
			} else {
				// What to do ?
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail();
		}

	}
	
}
