/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.pixelsimple.appcore.media.Codec.CODEC_TYPE;
import com.pixelsimple.commons.util.StringUtils;

/**
 * Refer to the <codec name="CODEC_NAME[Ex:libmp3lame]" decode="true" encode="true" provider="LIBRARY_PROVIDER[EX:ffmpeg]" strict="STRICTNESS[Ex:experimental(needed for ffmpeg)] />
 * in mediaContainerAndCodecs.xml.
 * This class helpd build a Codec object out of that xml tag. This can be reused and it the only place where a Codec
 * should be built from.
 *
 * @author Akshay Sharma
 * Feb 7, 2012
 */
public class CodecBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(CodecBuilder.class);

	public static Codec buildCodec(Codec.CODEC_TYPE codecType, Node xmlCodecNode) throws Exception {
		if (xmlCodecNode == null || codecType == null)
			throw new CodecBuilderException("The xmlCodecNode and codecType cannot be null");
		
//		LOG.debug("buildCodec::building codec using :: {} ", xmlCodecNode.getAttributes());

		XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();

	    String name = (String) xpath.evaluate("@name", xmlCodecNode, XPathConstants.STRING);
	    String category = (String) xpath.evaluate("@category", xmlCodecNode, XPathConstants.STRING);
	    String decode = (String) xpath.evaluate("@decode", xmlCodecNode, XPathConstants.STRING);
	    String encode = (String) xpath.evaluate("@encode", xmlCodecNode, XPathConstants.STRING);
	    String provider = (String) xpath.evaluate("@provider", xmlCodecNode, XPathConstants.STRING);
	    String strict = (String) xpath.evaluate("@strict", xmlCodecNode, XPathConstants.STRING);

	    LOG.debug("buildCodec::building codec :: {} :: of type :: {} ", name, codecType);	    
	    
		if (StringUtils.isNullOrEmpty(name))
			throw new CodecBuilderException("The codec name is a required attribute.");

		Codec codec = Codec.create(codecType, name);
		codec.setCategory(category);
		codec.setProvider(provider);
		codec.setStrict(strict);
		codec.setSupportsDecoding(decode != null ? Boolean.valueOf(decode).booleanValue() : false);
		codec.setSupportsEncoding(encode != null ? Boolean.valueOf(encode).booleanValue() : false);
		
		if (codecType == CODEC_TYPE.AUDIO) {
			buildAudioCodecSettings(xpath, xmlCodecNode, (AudioCodec) codec);
		} 
	
		return codec;
	}

	/**
	 * @param xpath
	 * @param codec
	 */
	private static void buildAudioCodecSettings(XPath xpath, Node xmlCodecNode, AudioCodec codec) throws Exception {
		String maxChannels = (String) xpath.evaluate("@maxChannels", xmlCodecNode, XPathConstants.STRING);
		
		if (StringUtils.isNullOrEmpty(maxChannels))
			return;
		
		codec.setMaxChannels(Integer.valueOf(maxChannels));
	}

}
