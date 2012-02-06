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
public class ContainerFormats {

	private List<String> containers = new ArrayList<String>();
	
	public void addCodec(String containerName) {
		this.containers.add(containerName.toLowerCase());
	}
	
	public boolean isSupported(String containerName) {
		return this.containers.contains(containerName.toLowerCase());
	}
	
	public String toString() {
		return "ContainerFormats::" + containers;
	}
	
	// Video ones
//	flv,
//	wmv,
//	mp4,
//	m4v,
//	ogg,
//	webm,
//	mpeg, // Mpeg1 standard
//	mpegts,
//	avi,
//	mkv,
//	divx,
//	mov,
//	gggp, // 3gp - can't use enums's starting with a digit, hence gggp
//
//	// Audio ones
//	mp3,
//	wma,
//	m4a
}
