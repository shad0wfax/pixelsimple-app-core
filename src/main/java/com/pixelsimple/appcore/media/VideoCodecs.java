/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akshay Sharma
 * Jan 20, 2012
 */
public class VideoCodecs {
	
	private List<String> codecs = new ArrayList<String>();
	
	public void addCodec(String codecName) {
		this.codecs.add(codecName.toLowerCase());
	}

	public String toString() {
		return "VideoCodecs::" + codecs;
	}
	
	public boolean isSupported(String codecName) {
		return this.codecs.contains(codecName.toLowerCase());
	}

//	flv: flv, libx264, vp6
//	fl9, mpegts: libx264
//	wmv, zune: wmv2, msmpeg4
//	3gp, android: h263, mpeg4, libx264
//	m4v: mpeg4
//	mp4, ipod, iphone, ipad, appletv, psp: mpeg4, libx264
//	ogg: libtheora
//	webm: libvpx
//	mp3, wma: none
//	mpeg2: mpeg2video

}
