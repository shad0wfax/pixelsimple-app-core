/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.binlib.ffprobe;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public class FfprobeConfigTest {

	/**
	 * Test method for {@link com.pixelsimple.appcore.binlib.ffmpeg.FfprobeConfig#FfprobeConfig(java.lang.String)}.
	 */
	@Test
	public void FfprobeConfigInvalidFile() {
		try {
			new FfprobeConfig("invalidfile");
			fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.binlib.ffmpeg.FfprobeConfig#FfprobeConfig(java.lang.String)}.
	 */
	@Test
	public void FfprobeConfigValidFile() {
		try {
			String validFile = null;
			
			if (OSUtils.isWindows()) {
				// Keep this path up to date with ffmpeg updates
				validFile = "c:/dev/pixelsimple/ffprobe/32_bit/0.8/ffprobe.exe";
			} else if (OSUtils.isMac()) {
				// Keep this path up to date with ffmpeg updates
				validFile =  OSUtils.USER_SYSTEM_HOME_DIR + "/dev/pixelsimple/ffprobe/32_bit/0.7_beta2/ffprobe";
			}  
			
			if (validFile != null) {
				FfprobeConfig FfprobeConfig = new FfprobeConfig(validFile);	
				Assert.assertTrue(validFile.equals(FfprobeConfig.getExecutablePath()));
			} else {
				// What to do ?
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail();
		}

	}
	
}
