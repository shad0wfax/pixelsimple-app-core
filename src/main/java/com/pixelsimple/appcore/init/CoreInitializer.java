/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import com.pixelsimple.appcore.Registrable;
import com.pixelsimple.appcore.Registry;
import com.pixelsimple.appcore.media.Codecs;
import com.pixelsimple.appcore.media.ContainerFormats;
import com.pixelsimple.appcore.media.MediaInfoParserFactory;
import com.pixelsimple.appcore.mime.Mime;
import com.pixelsimple.appcore.mime.MimeTypeMapper;

/**
 *
 * @author Akshay Sharma
 * Feb 12, 2012
 */
public class CoreInitializer implements Initializable {

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#initialize()
	 */
	@Override
	public void initialize(Registry registry) throws Exception {
		this.initContainersAndCodecs(registry);
	}

	/* (non-Javadoc)
	 * @see com.pixelsimple.appcore.init.Initializable#deinitialize()
	 */
	@Override
	public void deinitialize(Registry registry) throws Exception {
		registry.remove(Registrable.SUPPORTED_CONTAINER_FORMATS);
		registry.remove(Registrable.SUPPORTED_CODECS);
		registry.remove(Registrable.SUPPORTED_MIME_TYPES);
		
		// Ensure all of the added ones are removed.
	}

	/**
	 * @param registry
	 */
	private void initContainersAndCodecs(Registry registry) throws Exception {
		ContainerFormats containerFormats = new ContainerFormats();
		Codecs codecs = new Codecs();
		MediaInfoParserFactory factory = new MediaInfoParserFactory();
		factory.parseContainerAndCodecs(containerFormats, codecs);
		
		MimeTypeMapper mapper = new MimeTypeMapper();
		Mime mime = mapper.mapDefaultMimeTypes();
		
		// Load these objects up in registry
		registry.register(Registrable.SUPPORTED_CONTAINER_FORMATS, containerFormats);
		registry.register(Registrable.SUPPORTED_CODECS, codecs);
		registry.register(Registrable.SUPPORTED_MIME_TYPES, mime);

		// quick gc maybe!
		factory = null;
		mapper = null;
		
	}

}
