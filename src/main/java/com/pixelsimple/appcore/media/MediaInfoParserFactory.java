/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author Akshay Sharma
 * Jan 21, 2012
 */
public class MediaInfoParserFactory {
	private static final Logger LOG = LoggerFactory.getLogger(MediaInfoParserFactory.class);
	
	public static void parseContainerAndCodecs(ContainerFormats containerFormats, Codecs codecs) throws Exception {
		InputStream is = MediaInfoParserFactory.class.getResourceAsStream("/com/pixelsimple/appcore/media/mediaContainerAndCodecs.xml");
		
		try {
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); // never forget this!
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(is);
		    
			XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    NodeList nodes = (NodeList) xpath.evaluate("//containerFormats/format/text()", doc, XPathConstants.NODESET);
		    
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	containerFormats.addCodec(nodes.item(i).getNodeValue());
		    }
			
		    nodes = (NodeList) xpath.evaluate("//audioCodecs/codec", doc, XPathConstants.NODESET);		    
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	Codec codec = CodecBuilder.buildCodec(Codec.CODEC_TYPE.AUDIO, nodes.item(i));
		    	codecs.addCodec(codec);
		    }
			
		    nodes = (NodeList) xpath.evaluate("//videoCodecs/codec", doc, XPathConstants.NODESET);
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	Codec codec = CodecBuilder.buildCodec(Codec.CODEC_TYPE.VIDEO, nodes.item(i));
		    	codecs.addCodec(codec);
		    }
			
		    LOG.debug("containerFormats supported :: {} ", containerFormats);
		    LOG.debug("codecs supported :: {} ", codecs);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	public static Map<String, Profile> parseMediaProfiles() throws Exception {
		InputStream is = MediaInfoParserFactory.class.getResourceAsStream("/com/pixelsimple/appcore/media/mediaProfiles.xml");
		
		// TODO: A multi-key map with criteria as keys? - Uniqueness is a problem and lookups might not be best.
		Map<String, Profile> profiles = new HashMap<String, Profile>();
		
		try {
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(is);
		    
			XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    NodeList nodes = (NodeList) xpath.evaluate("//profiles/profile", doc, XPathConstants.NODESET);
		    
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	Node node = nodes.item(i);
		    	Profile profile = ProfileBuilder.buildProfile(node); 
		    	profiles.put(profile.getId(), profile);
		    }		    
		} finally {
			if (is != null) {
				is.close();
			}
		}
		LOG.debug("profiles build :: {} ", profiles);
		return profiles;
	}
	
	public static void main (String [] args) {
		try {
			MediaInfoParserFactory.parseMediaProfiles();
			
//			String [] a = StringUtils.delimitedListToStringArray("abc,edf,edo|1", "|");
//			for (String b : a) 
//				LOG.debug("Testing hola :: {} ", b);			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
