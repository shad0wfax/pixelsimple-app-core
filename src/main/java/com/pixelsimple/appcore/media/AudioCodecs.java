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
public class AudioCodecs {

	private List<String> codecs = new ArrayList<String>();
	
	public void addCodec(String codecName) {
		this.codecs.add(codecName.toLowerCase());
	}
	
	public String toString() {
		return "AudioCodecs::" + codecs;
	}
	
	public boolean isSupported(String codecName) {
		return this.codecs.contains(codecName.toLowerCase());
	}

//	mp3: libmp3lame
//	m4a: libfaac
//	flv: libmp3lame, libfaac
//	fl9, mp4, m4v, ipod, iphone, ipad, appletv, psp: libfaac
//	wmv, wma, zune: wmav2, libmp3lame
//	ogg, webm: libvorbis
//	3gp: libamr_nb
//	android: libamr_nb, libfaac
//	mpeg2: pcm_s16be, pcm_s16le
//	mpegts: ac3
	
}
