/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.queue;

import com.pixelsimple.appcore.registry.MapRegistry;
import com.pixelsimple.appcore.registry.Registrable;

/**
 * @author Akshay Sharma
 *
 * Jun 5, 2012
 */
public class QueueService {
	
	private QueueService() {}
	
	public static Queue getQueue() {
		
		return MapRegistry.INSTANCE.fetch(Registrable.QUEUE);
	}
	
	/**
	 * Will register a queue, iff there is not queue already registered. This will prevent multiple registring.
	 * The method is not thread safe, which means this should be called only at the startup, typically using a 
	 * module initializer. 
	 * @param queue
	 */
	public static void registerQueue(Queue queue) {
		if (MapRegistry.INSTANCE.containsKey(Registrable.QUEUE))
			return;
		
		MapRegistry.INSTANCE.register(Registrable.QUEUE, queue);
	}

	
}
