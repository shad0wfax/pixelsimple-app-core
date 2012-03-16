/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import com.pixelsimple.commons.util.Assert;

/**
 *
 * @author Akshay Sharma
 * Mar 14, 2012
 */
public class AudioCodec extends Codec {
	// Audio codec specific props:
	public static final int NO_SPECIFIED_CHANNEL_LIMIT = -1;

	private int maxChannels = NO_SPECIFIED_CHANNEL_LIMIT;

	public AudioCodec(String name) {
		Assert.notNull(name, "A valid name needs to be supplied");

		this.codecType = CODEC_TYPE.AUDIO;
		this.name = name.trim().toLowerCase();
	}

	/**
	 * @return the maxChannels
	 */
	public int getMaxChannels() {
		return maxChannels;
	}

	/**
	 * @param maxChannels the maxChannels to set
	 */
	protected void setMaxChannels(int maxChannels) {
		this.maxChannels = maxChannels;
	}
	
	
}
