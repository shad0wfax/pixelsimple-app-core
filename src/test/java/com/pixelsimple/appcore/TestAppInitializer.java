/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.commons.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public class TestAppInitializer {
	
	public static Map<String, String> buildConfig() {
		Map<String, String> configs = new HashMap<String, String>();
		
		if (OSUtils.isWindows()) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, "c:\\dev\\pixelsimple");
			configs.put("ffprobePath", "ffprobe\\32_bit\\1.0\\ffprobe.exe"); 
			configs.put("ffmpegPath", "ffmpeg\\32_bit\\1.0\\ffmpeg.exe"); 

			// Will use the ffmpeg path for testing this... pain to setup a file on each dev system.
			configs.put("hlsPlaylistGeneratorPath", "ffmpeg\\32_bit\\1.0\\ffmpeg.exe");
			configs.put("mediaScannerPath", "ffmpeg\\32_bit\\1.0\\ffmpeg.exe");
		} else if (OSUtils.isMac()) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR,  OSUtils.USER_SYSTEM_HOME_DIR + "/dev/pixelsimple");
			configs.put("ffprobePath",  "ffprobe/32_bit/1.0/ffprobe"); 
			configs.put("ffmpegPath",  "ffmpeg/32_bit/1.0/ffmpeg"); 

			// Will use the ffmpeg path for testing this... pain to setup a file on each dev system.
			configs.put("hlsPlaylistGeneratorPath", "ffmpeg/32_bit/1.0/ffmpeg");
			configs.put("mediaScannerPath", "ffmpeg/32_bit/1.0/ffmpeg");
		}  else {
			// add linux based tests when ready :-)
		}
		configs.put("tempDirectory", configs.get(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR) + "/tmp");
		configs.put("hlsTranscodeCompleteFile", "pixelsimple_hls_transcode.complete"); 
		configs.put("hlsFileSegmentPattern", "%06d"); 
		configs.put("transcoderNinjaInputFilePattern", "\\$if"); 
		configs.put("transcoderNinjaOutputFilePattern", "\\$of"); 
		configs.put("transcoderNinjaVideoBitratePattern", "\\$vb"); 
		configs.put("transcoderNinjaAudioBitratePattern", "\\$ab"); 
		
		return configs;
		}
	
	
	
	/**
	 * This test case is needed to ensure junit passes this class. 
	 */
	@Test
	public void alwaysTrue() {
		Assert.assertTrue(true);
	}
}
