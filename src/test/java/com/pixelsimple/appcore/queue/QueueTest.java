/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.queue;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.pixelsimple.appcore.init.AppInitializer;
import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.appcore.registry.MapRegistry;
import com.pixelsimple.commons.util.OSUtils;

/**
 * @author Akshay Sharma
 *
 * Jun 5, 2012
 */
public class QueueTest {
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		MapRegistry.INSTANCE.removeAll();
	}
	

	/**
	 * Test method for {@link com.pixelsimple.appcore.queue.QueueService#getQueue()}.
	 */
	@Test
	public void initQueue() {
		Queue queue = QueueService.getQueue();
		Assert.assertNull(queue);
		
		QueueService.registerQueue(new DefaultMapQueue());
		queue = QueueService.getQueue();
		Assert.assertNotNull(queue);

		Assert.assertSame(queue, QueueService.getQueue());
		
		// Add another queue, should not accept it
		QueueService.registerQueue(new Queue() {
			
			@Override
			public <T> T peek(Queueable queueId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void enqueue(Queueable queueId, Object obj) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public <T> T dequeue(Queueable queueId) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		Assert.assertSame(queue, QueueService.getQueue());

	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.queue.QueueService#getQueue()}.
	 */
	@Test
	public void initQueueViaAppInitializer() {
		Queue queue = QueueService.getQueue();
		Assert.assertNull(queue);

		try {
			new AppInitializer().init(getConfig());
			queue = QueueService.getQueue();
			Assert.assertNotNull(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} 
		
	}
	
	@Test
	public void queueMethods() {
		Queue queue = QueueService.getQueue();
		Assert.assertNull(queue);

		try {
			new AppInitializer().init(getConfig());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} 
		queue = QueueService.getQueue();
		Assert.assertNotNull(queue);
		
		QueueIt a = new QueueIt("abc");
		QueueIt b = new QueueIt("def");
		
		queue.enqueue(a, "abc");
		queue.enqueue(b, "def");
		
		Assert.assertEquals("abc", queue.peek(a));
		Assert.assertEquals("def", queue.peek(b));
		
		String bval = queue.dequeue(b);
		Assert.assertEquals("def",bval);
		
		Assert.assertNull(queue.peek(b));

	}
	
	private class QueueIt implements Queueable {
		String x;
		public QueueIt(String x) { this.x = x; }
		@Override public boolean equals(Object o) { return this.x.equals(o); }
		@Override public int hashCode() { return this.x.hashCode(); }
	}
	

	private Map<String, String> getConfig() {
		Map<String, String> configs = new HashMap<String, String>();
		
		if (OSUtils.isWindows()) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR, "c:\\dev\\pixelsimple");
			configs.put("ffprobePath", "ffprobe/32_bit/1.0/ffprobe.exe"); 
			configs.put("ffmpegPath", "ffmpeg/32_bit/1.0/ffmpeg.exe"); 
			
			// Will use the ffmpeg path for testing this... pain to setup a file on each dev system.
			configs.put("hlsPlaylistGeneratorPath", "ffmpeg/32_bit/1.0/ffmpeg.exe"); 
		} else if (OSUtils.isMac()) {
			// Keep this path up to date with ffmpeg updates
			configs.put(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR,  OSUtils.USER_SYSTEM_HOME_DIR + "/dev/pixelsimple");
			configs.put("ffprobePath",  "ffprobe/32_bit/1.0/ffprobe"); 
			configs.put("ffmpegPath",  "ffmpeg/32_bit/1.0/ffmpeg"); 

			// Will use the ffmpeg path for testing this... pain to setup a file on each dev system.
			configs.put("hlsPlaylistGeneratorPath", "ffmpeg/32_bit/1.0/ffmpeg"); 
		}  else {
			// add linux based tests when ready :-)
		}
		
		configs.put("hlsTranscodeCompleteFile", "pixelsimple_hls_transcode.complete"); 
		configs.put("hlsFileSegmentPattern", "%06d"); 
		
		return configs;
	}
}
