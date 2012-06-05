/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.queue;

/**
 * @author Akshay Sharma
 *
 * Jun 5, 2012
 */
public interface Queue {

	public void enqueue(Queueable queueId, Object obj);
	
	public <T extends Object> T dequeue(Queueable queueId);
	
	public <T extends Object> T peek(Queueable queueId);
	
	// Potential operations that can be iteratively supported: 
	// purge / enqueueOnlyOnce() etc.
}
