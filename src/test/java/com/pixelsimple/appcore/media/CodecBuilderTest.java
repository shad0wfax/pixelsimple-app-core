/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Node;

/**
 *
 * @author Akshay Sharma
 * Feb 7, 2012
 */
public class CodecBuilderTest {

//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		TestAppInitializer.bootStrapRegistryForTesting();
//	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.media.CodecBuilder#buildCodec(com.pixelsimple.appcore.media.Codec.CODEC_TYPE, org.w3c.dom.Node)}.
	 */
	@Test
	public void testBuildCodecWithValidData() throws Exception {
		Node node = validAudioCodecXmlNode();
		AudioCodec codec1 = (AudioCodec) CodecBuilder.buildCodec(Codec.CODEC_TYPE.AUDIO, node);
		Assert.assertEquals(codec1.getName(), "libmp3lame");
		Assert.assertEquals(codec1.getKey(), "AUDIO:libmp3lame");
		Assert.assertEquals(codec1.supportsDecoding(), false);
		Assert.assertEquals(codec1.supportsEncoding(), true);
		Assert.assertEquals(codec1.getProvider(), "ffmpeg");
		Assert.assertEquals(codec1.getStrict(), "");
		Assert.assertEquals(codec1.getCategory(), "mp3");
		Assert.assertEquals(codec1.getMaxChannels(), 2);
		

		node = validVideoCodecXmlNode();
		VideoCodec codec = (VideoCodec) CodecBuilder.buildCodec(Codec.CODEC_TYPE.VIDEO, node);
		Assert.assertEquals(codec.getName(), "libvpx");
		Assert.assertEquals(codec.getKey(), "VIDEO:libvpx");
		Assert.assertEquals(codec.supportsDecoding(), true);
		Assert.assertEquals(codec.supportsEncoding(), false);
		Assert.assertEquals(codec.getProvider(), "ffmpeg");
		Assert.assertEquals(codec.getStrict(), "experimental");
		Assert.assertEquals(codec.getCategory(), "vp8");

	}

	/**
	 * Test method for {@link com.pixelsimple.appcore.media.CodecBuilder#buildCodec(com.pixelsimple.appcore.media.Codec.CODEC_TYPE, org.w3c.dom.Node)}.
	 */
	@Test
	public void testBuildCodecWithInValidData() {
		Node node = invalidCodecXmlNodeMissingName();
		try {
			CodecBuilder.buildCodec(Codec.CODEC_TYPE.AUDIO, node);
		} catch (CodecBuilderException e) {
			// ok			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}

	private Node validAudioCodecXmlNode() {
		String xml = "<codec name=\"libmp3lame\" category=\"mp3\" decode=\"false\" encode=\"true\" provider=\"ffmpeg\" maxChannels=\"2\" />";
		return asNode(xml);
	}

	private Node validVideoCodecXmlNode() {
		String xml = "<codec name=\"libvpx\" category=\"vp8\" decode=\"true\" encode=\"false\" provider=\"ffmpeg\" strict=\"experimental\" />";
		return asNode(xml);
	}

	private Node invalidCodecXmlNodeMissingName() {
		String xml = "<codec decode=\"false\" encode=\"true\" provider=\"ffmpeg\" category=\"mp3\" />";
		return asNode(xml);
	}

	private Node asNode(String xml) {
		Node node = null;
		try {
			node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new ByteArrayInputStream(xml.getBytes())).getDocumentElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		return node;

	}
}
