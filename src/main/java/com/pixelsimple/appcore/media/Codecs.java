/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Akshay Sharma
 * Jan 20, 2012
 */
public class Codecs {

	// Map For faster lookups instead of a set
	private Map<String, Codec> codecs = new HashMap<String, Codec>();
	
	public void addCodec(Codec codec) {
		this.codecs.put(codec.getKey(), codec);
	}
	
	public String toString() {
		return "Codecs::" + codecs;
	}
	
	public boolean isSupported(Codec codec) {
		return this.codecs.containsKey(codec.getKey());
	}
	
	public Codec findCodec(Codec.CODEC_TYPE codecType, String codecName) {
		Codec codec = new Codec(codecType, codecName);
		return this.codecs.get(codec.getKey());
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
