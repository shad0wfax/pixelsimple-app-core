/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.queue;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Out of the box provided queue. A simple map based stuff. Is not cluster aware.
 *  
 * @author Akshay Sharma
 *
 * Jun 5, 2012
 */
public class DefaultMapQueue implements Queue {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultMapQueue.class);
	
	private Map<Queueable, Object> queue = new HashMap<Queueable, Object>();
	
	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.queue.Queue#enqueue(com.pixelsimple.appcore.queue.Queueable, java.lang.Object)
	 */
	@Override
	public void enqueue(Queueable queueId, Object obj) {
		queue.put(queueId, obj);
		LOG.debug("enqueue::Added to the queue - queueId {} object {}", queueId, obj);
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.queue.Queue#dequeue(com.pixelsimple.appcore.queue.Queueable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T dequeue(Queueable queueId) {
		LOG.debug("dequeue::Removed from queue - queueId {}", queueId);
		
		return (T) (queue.remove(queueId));
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.queue.Queue#peek(com.pixelsimple.appcore.queue.Queueable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T peek(Queueable queueId) {
		T obj = (T) queue.get(queueId); 

		LOG.debug("peek::Peeking from queue - queueId {} with value {}", queueId, obj);
		
		return obj;
	}

}
