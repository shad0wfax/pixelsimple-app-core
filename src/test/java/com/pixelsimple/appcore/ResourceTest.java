/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.Resource.RESOURCE_TYPE;
import com.pixelsimple.commons.util.OSUtils;

/**
 * @author Akshay Sharma
 *
 * Jun 16, 2012
 */
public class ResourceTest {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceTest.class);
	private String aDir;
	private String aFile;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		if (OSUtils.isWindows()) {
			this.aDir = "c:\\dev\\pixelsimple";
			this.aFile = this.aDir + "\\ffprobe\\32_bit\\1.0\\ffprobe.exe"; 
		} else if (OSUtils.isMac()) {
			this.aDir = OSUtils.USER_SYSTEM_HOME_DIR + "/dev/pixelsimple";
			this.aFile = this.aDir + "/ffprobe/32_bit/1.0/ffprobe"; 
		}  else {
			// add linux based tests when ready :-)
		}

	}

	@Test
	public void invalidResourceCreation() {
		try {
			new Resource(null, RESOURCE_TYPE.FILE);
			fail();
		} catch (IllegalArgumentException e) {}

		try {
			new Resource("  ", RESOURCE_TYPE.FILE);
			fail();
		} catch (IllegalArgumentException e) {}
	
		try {
			new Resource("somepath", null);
			fail();
		} catch (IllegalArgumentException e) {}
	}

	@Test
	public void validResourceCreation() {
		Resource res = new Resource(aFile, RESOURCE_TYPE.FILE);
		LOG.debug("Resource for testing = {}", res);
		Assert.assertTrue(res.isValid());
		Assert.assertTrue(res.isLocalFileSystemResource());
				
		res = new Resource(aDir, RESOURCE_TYPE.DIRECTORY);
		LOG.debug("Resource for testing = {}", res);
		Assert.assertTrue(res.isValid());
		Assert.assertTrue(res.isLocalFileSystemResource());
				
	}

	@Test
	public void validResourceCreationButInvalidType() {
		Resource res = new Resource(aDir, RESOURCE_TYPE.FILE);
		LOG.debug("Resource for testing = {}", res);
		Assert.assertFalse(res.isValid());
		Assert.assertTrue(res.isLocalFileSystemResource());
				
		res = new Resource(aFile, RESOURCE_TYPE.DIRECTORY);
		LOG.debug("Resource for testing = {}", res);
		Assert.assertFalse(res.isValid());
		Assert.assertTrue(res.isLocalFileSystemResource());
	}
}
