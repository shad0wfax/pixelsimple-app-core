/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.queue;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.pixelsimple.appcore.TestAppInitializer;
import com.pixelsimple.appcore.init.AppInitializer;
import com.pixelsimple.appcore.registry.MapRegistry;

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
		TestAppInitializer.shutdownAppInit();
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
			new AppInitializer().init(TestAppInitializer.buildConfig());
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
			new AppInitializer().init(TestAppInitializer.buildConfig());
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
}
