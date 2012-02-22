/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

/**
 * An enum to let modules/components to register the keys. 
 * The reason to use a enum is, it is type saf over other keys. 
 * 
 * The idea to list the registerables here as enum is to allow static typing. We dont want unknown registrations
 * happening. All registry additions should be listed here statically.
 * 
 * @author Akshay Sharma
 * Jan 15, 2012
 */
public enum Registrable {
	
	API_CONFIG,
	SUPPORTED_CONTAINER_FORMATS,
	SUPPORTED_CODECS,
	MEDIA_PROFILES,
	TRANSCODE_COMMAND_CHAIN,
	TRANSCODER_QUEUE,
	// Add more:

}
