/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import junit.framework.Assert;


/**
 *
 * @author Akshay Sharma
 * Feb 6, 2012
 */
public class Codec {
	public static enum CODEC_TYPE {VIDEO, AUDIO};	
	
	private String name;
	private CODEC_TYPE codecType;
	private boolean supportsDecoding;
	private boolean supportsEncoding;
	private String provider;
	private String strict;
	
	public static Codec create(CODEC_TYPE codecType, String codec) {
		return new Codec(codecType, codec);
	}
	
	public Codec(CODEC_TYPE codecType, String codec) {
		Assert.assertNotNull("A valid name type needs to be supplied", codecType);
		Assert.assertNotNull("A valid name needs to be supplied", codec);

		this.codecType = codecType;
		this.name = codec.trim().toLowerCase();
	}
	
	/**
	 * @return the videoCodec
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the audioCodec
	 */
	public CODEC_TYPE getCodecType() {
		return this.codecType;
	}

	@Override public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Codec))
			return false;
		Codec codec = (Codec) obj;
		
		if (codec == this)
			return true;
		
		if ((this.getCodecType() == codec.getCodecType()) && (this.getName().equalsIgnoreCase(codec.getName())))
			return true;
		
		return false;
	}
	
	/**
	 * A critical method that identifies a codec uniquely. 
	 * It is a the combination of "Codec Type [audio/video]-[name]"
	 * @return
	 */
	public String getKey() {
		return this.getCodecType().name() + ":" + this.getName();
	}
	
	/**
	 * Dumb implementation - painful Java :).
	 */
	@Override public int hashCode() {
		return (this.getKey()).hashCode();
	}
	
	@Override public String toString() {
		return this.name + ":" + this.codecType.name(); 
	}

	/**
	 * @return the decoder
	 */
	public boolean supportsDecoding() {
		return supportsDecoding;
	}

	/**
	 * @param decoder the decoder to set
	 */
	public void setSupportsDecoding(boolean supportsDecoding) {
		this.supportsDecoding = supportsDecoding;
	}

	/**
	 * @return the encoder
	 */
	public boolean supportsEncoding() {
		return supportsEncoding;
	}

	/**
	 * @param encoder the encoder to set
	 */
	protected void setSupportsEncoding(boolean supportsEncoding) {
		this.supportsEncoding = supportsEncoding;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the strict
	 */
	public String getStrict() {
		return strict;
	}

	/**
	 * @param strict the strict to set
	 */
	public void setStrict(String strict) {
		this.strict = strict;
	}

}
