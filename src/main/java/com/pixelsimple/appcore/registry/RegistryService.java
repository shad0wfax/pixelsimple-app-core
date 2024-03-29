/**
 * � PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.registry;

import com.pixelsimple.appcore.ApiConfig;
import com.pixelsimple.appcore.media.Codecs;
import com.pixelsimple.appcore.media.ContainerFormats;

/**
 *
 * @author Akshay Sharma
 * Jan 18, 2012
 */
public final class RegistryService {
	
	private RegistryService() {}

	/**
	 * Get the registered API from the registry. Abstracts the usage of registry to the modules above.
	 * Static and globally loaded registry - Return the implemented registry.
	 * 
	 * @return
	 */
	public static ApiConfig getRegisteredApiConfig() {
		return MapRegistry.INSTANCE.fetch(Registrable.API_CONFIG);
	}
	
	public static ContainerFormats getSupportedContainerFormats() {
		return MapRegistry.INSTANCE.fetch(Registrable.SUPPORTED_CONTAINER_FORMATS);
	}

	public static Codecs getSupportedCodecs() {
		return MapRegistry.INSTANCE.fetch(Registrable.SUPPORTED_CODECS);
	}
	
	public static Object getRegisteredEntry(Registrable key) {
		return MapRegistry.INSTANCE.fetch(key);
	}

	public static GenericRegistryEntry getGenericRegistryEntry() {
		return  getRegisteredApiConfig().getGenericRegistryEntry();
	}
}
