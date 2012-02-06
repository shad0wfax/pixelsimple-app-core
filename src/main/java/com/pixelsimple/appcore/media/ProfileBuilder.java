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

import com.pixelsimple.appcore.RegistryService;
import com.pixelsimple.commons.util.StringUtils;

/**
 * Refer the <profile>...</profile> tag in the mediaProfiles.xml file. This profile builder class builds one profile 
 * object per <profile> tag passed. Ideally this will the single place for building the profiles. So the system could
 * load profiles from the xml as well as user defined profile, but they will have to adhere to the semantics here. 
 * @author Akshay Sharma
 * Feb 4, 2012
 */
public class ProfileBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(ProfileBuilder.class);
	
	private ProfileBuilder() {}

	public static Profile buildProfile(Node xmlProfileNode) throws Exception {
		if (xmlProfileNode == null)
			throw new ProfileBuilderException("The xmlNode cannot be null");
		
		LOG.debug("buildProfile::building profile using :: {} ", xmlProfileNode.getTextContent());
	    
		XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();

	    String type = (String) xpath.evaluate("type", xmlProfileNode, XPathConstants.STRING);
		if (StringUtils.isNullOrEmpty(type))
			throw new ProfileBuilderException("Profile should have a type associated");
	    
	    MediaType mediaType = null; 
	    for (MediaType mt : MediaType.values()) {
	    	if (type.equalsIgnoreCase(mt.name())) {
	    		mediaType = mt;
	    		break;
	    	}
	    }
		if (mediaType == null)
			throw new ProfileBuilderException("The mediaType cannot be null");	

		Profile profile = new Profile(mediaType);

		profile.setId((String) xpath.evaluate("id", xmlProfileNode, XPathConstants.STRING));
	    profile.setName((String) xpath.evaluate("name", xmlProfileNode, XPathConstants.STRING));
	    profile.setContainerFormat((String) xpath.evaluate("container", xmlProfileNode, XPathConstants.STRING));
	    profile.setFileExtension((String) xpath.evaluate("fileExtension", xmlProfileNode, XPathConstants.STRING));
	    profile.setVideoBitRate((String) xpath.evaluate("videoBitRate", xmlProfileNode, XPathConstants.STRING));
	    profile.setVidoeQuality((String) xpath.evaluate("vidoeQuality", xmlProfileNode, XPathConstants.STRING));
	    profile.setAudioBitRate((String) xpath.evaluate("audioBitRate", xmlProfileNode, XPathConstants.STRING));
	    profile.setAspectRatio((String) xpath.evaluate("aspectRatio", xmlProfileNode, XPathConstants.STRING));
	    
	    Number maxWidth = (Number) xpath.evaluate("maxWidth", xmlProfileNode, XPathConstants.NUMBER);
	    if (maxWidth != null) {
	    	profile.setMaxWidth(maxWidth.intValue());	
	    }	    
	    profile.setFrameRateFPS((String) xpath.evaluate("frameRateFPS", xmlProfileNode, XPathConstants.STRING));
	    profile.setOptionalAdditionalParameters((String) xpath.evaluate("optionalAdditionalParameters", xmlProfileNode, XPathConstants.STRING));
	    
	    String criteria = (String) xpath.evaluate("criteria", xmlProfileNode, XPathConstants.STRING);
	    if (criteria != null) {
	    	String [] arr = StringUtils.delimitedListToStringArray(criteria, ",");
	    	for (String a : arr) {
	    		profile.addCriteria(a);
	    	}
	    }
	    
	    String audioCodec = (String) xpath.evaluate("audioCodec", xmlProfileNode, XPathConstants.STRING);
	    String videoCodec = (String) xpath.evaluate("videoCodec", xmlProfileNode, XPathConstants.STRING);
 
	    if (mediaType == MediaType.AUDIO && !StringUtils.isNullOrEmpty(audioCodec)) {
		    // Format will be: <audioCodec>mp3,libvorbis,aac,ac3</audioCodec>
	    	// Ignore the video codecs. So we will not have any pipe separated entries. Only a comma separated list.
	    	// Note: Order is important - it indicates priority/prefefence
	    	String [] codecs = StringUtils.commaDelimitedListToStringArray(audioCodec);
	    	for (String acodec : codecs) {
	    		profile.addCodec(new Profile.ContainerSupportedCodec().addStream(StreamType.AUDIO, acodec));
	    	}	    	
	    } else if (mediaType == MediaType.VIDEO && !StringUtils.isNullOrEmpty(videoCodec)) {
		    // Format will be: <videoCodec>webm|libx264</videoCodec>
		    // Format will be: <audioCodec>mp3,libvorbis|mp3,aac,ac3</audioCodec>
	    	// Note: audioCodec can also be missing - TODO: What is the solution in such cases?

	    	String [] vcodecs = StringUtils.delimitedListToStringArray(videoCodec, "|");
	    	String [] acodecs = null;
	    	
	    	if (!StringUtils.isNullOrEmpty(audioCodec)) {
	    		acodecs = StringUtils.delimitedListToStringArray(audioCodec, "|");	
	    	}
	    	for (int i = 0; i < vcodecs.length; i++) {
	    		String vcodec = vcodecs[i];
	    		
	    		if (acodecs != null && acodecs.length > i) {
	    			String [] acodecForVid = StringUtils.commaDelimitedListToStringArray(acodecs[i]);
	    	    	for (String acodec : acodecForVid) {
	    	    		profile.addCodec(new Profile.ContainerSupportedCodec().addStream(StreamType.AUDIO, acodec)
	    	    			.addStream(StreamType.VIDEO, vcodec));
	    	    	}	    	
	    		} else {
    	    		profile.addCodec(new Profile.ContainerSupportedCodec().addStream(StreamType.VIDEO, vcodec));
	    		}
	    	}
	    }
	    
	    // Validate if the profile is fine -
	   validate(profile);
	    
		return profile;
	}

	/**
	 * Throws ProfileBuilderException if there are basic validation errors 
	 */
	private static void validate(Profile profile) {
		if (StringUtils.isNullOrEmpty(profile.getId()))
			throw new ProfileBuilderException("Looks like the profile Id is missing - " + profile.toString());
		
		if (StringUtils.isNullOrEmpty(profile.getContainerFormat()))
			throw new ProfileBuilderException("Looks like the container format is missing - " + profile.toString());

		if (profile.getMediaType() == MediaType.AUDIO) {
			// At least one audio codec should be supplied:
			if (profile.getCodecs().size() == 0 || StringUtils.isNullOrEmpty(profile.getCodecs().get(0).getAudioCodec()))
				throw new ProfileBuilderException("Looks like the audio codec for profile type audio is missing - " 
					+ profile.toString());
		} else if (profile.getMediaType() == MediaType.VIDEO) {
			// At least one audio codec should be supplied:
			if (profile.getCodecs().size() == 0 || StringUtils.isNullOrEmpty(profile.getCodecs().get(0).getVideoCodec()))
				throw new ProfileBuilderException("Looks like the video codec for profile type video is missing - " 
					+ profile.toString());
		}
		
		validateCodecsAndContainerSupported(profile);
	}

	/**
	 * @param profile
	 */
	private static void validateCodecsAndContainerSupported(Profile profile) {
		ContainerFormats formats = RegistryService.getSupportedContainerFormats();
		AudioCodecs acodecs = RegistryService.getSupportedAudioCodecs();
		VideoCodecs vcodecs = RegistryService.getSupportedVideoCodecs();
	
		String format = profile.getContainerFormat();
		
		if (!StringUtils.isNullOrEmpty(format) && !formats.isSupported(format))
			throw new ProfileBuilderException("Looks like the container format - " + format + " is not supported");			
		
		for (Profile.ContainerSupportedCodec codec : profile.getCodecs()) {
			String acodec = codec.getAudioCodec();
			String vcodec = codec.getVideoCodec();
			
			if (!StringUtils.isNullOrEmpty(acodec) && !acodecs.isSupported(acodec))
				throw new ProfileBuilderException("Looks like the audio codec - " + acodec + " is not supported");			

			if (!StringUtils.isNullOrEmpty(vcodec) && !vcodecs.isSupported(vcodec))
				throw new ProfileBuilderException("Looks like the video codec - " + vcodec + " is not supported");			
		}
	}

}
