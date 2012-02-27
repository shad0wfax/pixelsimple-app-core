/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.mime;

import junit.framework.Assert;

import org.junit.Test;

import com.pixelsimple.appcore.media.MediaType;

/**
 *
 * @author Akshay Sharma
 * Feb 27, 2012
 */
public class MimeTest {

	/**
	 * Test method for {@link com.pixelsimple.appcore.mime.Mime#addMimeMapping(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void addMimeMapping() {
		Mime mime = new Mime();
		
		mime.addMimeMapping("mp4", "video/mp4");
		mime.addMimeMapping("mp4", "AUDIO/mp4");
		
		System.out.println(mime);
		
		Assert.assertEquals(mime.getMimeType("mp4", MediaType.VIDEO), "video/mp4");
		Assert.assertEquals(mime.getMimeType("mp4", MediaType.AUDIO), "audio/mp4");

		mime.addMimeMapping("xml", "text/xml");
		mime.addMimeMapping("default", "application/octet-stream");

		System.out.println(mime);

		Assert.assertNull(mime.getMimeType("xml", MediaType.AUDIO));
		Assert.assertEquals(mime.getMimeType("xml", null), "text/xml");
		Assert.assertEquals(mime.getMimeType("default", null), "application/octet-stream");

	}
	
	@Test
	public void mapDefaultMimeTypes() {
		try {
			Mime mime = new MimeTypeMapper().mapDefaultMimeTypes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}


}
