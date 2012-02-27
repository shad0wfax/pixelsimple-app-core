/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.mime;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


/**
 *
 * @author Akshay Sharma
 * Feb 27, 2012
 */
public class MimeTypeMapper {
	private static final Logger LOG = LoggerFactory.getLogger(MimeTypeMapper.class);

	public Mime mapDefaultMimeTypes() throws Exception {
//
//		Properties props = new Properties();
//		props.load(is);
//		
//		Mime mime = new Mime();
//		
//		Set<Map.Entry<Object, Object>> entries = props.entrySet();
//
//		for (Map.Entry<Object, Object> entry : entries) {
//			mime.addMimeMapping((String) entry.getKey(), (String) entry.getValue());
//		}

//		ResourceBundle bundle = ResourceBundle.getBundle("com.pixelsimple.appcore.mime.defaultMimeTypes");
//		Enumeration<String> keys = bundle.getKeys();
//		
//		while (keys.hasMoreElements()) {
//			String key = keys.nextElement();
//			String [] values = bundle.getStringArray(key);
//			
//			for (String value : values) {
//				mime.addMimeMapping(key, value);
//			}
//		}

		InputStream is = MimeTypeMapper.class.getResourceAsStream("/com/pixelsimple/appcore/mime/defaultMimeTypes.xml");
		Mime mime = new Mime();

		try {
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); // never forget this!
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(is);
		    
			XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    NodeList nodes = (NodeList) xpath.evaluate("//mime-mapping", doc, XPathConstants.NODESET);
		    
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	String extension = (String) xpath.evaluate("extension", nodes.item(i), XPathConstants.STRING);
		    	String mimeType = (String) xpath.evaluate("mime-type", nodes.item(i), XPathConstants.STRING);
		    	
		    	mime.addMimeMapping(extension, mimeType);
		    }
		} finally {
			if (is != null) {
				is.close();
			}
		}
		
		LOG.debug("mapDefaultMimeTypes:: mime types supported :: {} ", mime);
		
		return mime;
	}
	
}
