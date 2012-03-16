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
public class VideoCodec extends Codec {

	public VideoCodec(String name) {
		Assert.notNull(name, "A valid name needs to be supplied");

		this.codecType = CODEC_TYPE.VIDEO;
		this.name = name.trim().toLowerCase();
	}
	
}
