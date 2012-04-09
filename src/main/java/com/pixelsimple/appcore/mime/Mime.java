/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.mime;

import java.util.HashMap;
import java.util.Map;

import com.pixelsimple.appcore.media.MediaType;

/**
 *
 * @author Akshay Sharma
 * Feb 27, 2012
 */
public class Mime {
	private Map<Extension, String> mimeMapping = new HashMap<Extension, String>();
	
	public String getMimeType(String extension) {
		Extension e = new Extension(extension, null);
		return this.mimeMapping.get(e);
	}
	
	public String getMimeType(String extension, MediaType mediaType) {
		Extension e = new Extension(extension, mediaType);
		String mimeType = this.mimeMapping.get(e);
		
		// Check if there is a mime mapping without mediaType (mediaType is optional remember)
		if (mimeType == null) {
			mimeType = this.getMimeType(extension);
		}
		return mimeType;
	}
	
	// Only MimeMapper can add stuff.
	protected void addMimeMapping(String extension, String mimeType) {
		Extension extensionObj = null;
		String [] mimeTypeParts = mimeType.split("/");
		
		if (mimeTypeParts.length > 1) {
			// Get the type part ("video/mp4") - we need video here.
			String type = mimeTypeParts[0];
			MediaType m = null;
			
			for (MediaType t : MediaType.values()) {
				if (t.name().equalsIgnoreCase(type)) {
					m = t;
					break;
				}
			}
			extensionObj = new Extension(extension, m);
		} else {
			extensionObj = new Extension(extension, null);
		}
		
		this.mimeMapping.put(extensionObj, mimeType.trim().toLowerCase());
	}
	
	@Override public String toString() {
		return mimeMapping.toString();
	}

	private static class Extension {
		private String extension;
		// Can be optional - especially since there can be mime types for non-media types - text/html etc.
		private MediaType mediaType;
		
		public Extension(String extension, MediaType optionalMediaType) {
			this.extension = extension.trim().toLowerCase();
			this.mediaType = optionalMediaType;
		}
		
		@Override public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof Extension))
				return false;
			
			Extension extension = (Extension) obj;
			
			if (extension == this)
				return true;

			// If extensions are same, the mediaTypes must be same as well or both null. So mp4:video/mp4 is not same as mp4:audio/mp4
			if (extension.getExtension().equalsIgnoreCase(this.getExtension()) 
					&& extension.getMediaType() == this.getMediaType()) {
					return true;
			}
			
			return false;
		}
		
		@Override public int hashCode() {
			String mimeType = (this.getMediaType() == null) ? "null" : this.getMediaType().name();
			return (this.getExtension() + ":" + mimeType).hashCode();
		}

		@Override public String toString() {
			return this.getExtension();  
		}

		/**
		 * @return the extension
		 */
		private String getExtension() {
			return extension;
		}

		/**
		 * @return the mediaType
		 */
		private MediaType getMediaType() {
			return mediaType;
		}
	}
}
