/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import com.pixelsimple.commons.util.Assert;


/**
 *
 * @author Akshay Sharma
 * Feb 6, 2012
 */
public class Codec {
	public static enum CODEC_TYPE {VIDEO, AUDIO};	
	
	private String name;
	private String category;
	private CODEC_TYPE codecType;
	private boolean supportsDecoding;
	private boolean supportsEncoding;
	private String provider;
	private String strict;
	
	public static Codec create(CODEC_TYPE codecType, String codec) {
		return new Codec(codecType, codec);
	}
	
	public Codec(CODEC_TYPE codecType, String name) {
		Assert.notNull(codecType, "A valid name type needs to be supplied");
		Assert.notNull(name, "A valid name needs to be supplied");

		this.codecType = codecType;
		this.name = name.trim().toLowerCase();
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
	protected void setSupportsDecoding(boolean supportsDecoding) {
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
	protected void setProvider(String provider) {
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
	protected void setStrict(String strict) {
		this.strict = strict;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	protected void setCategory(String category) {
		this.category = category;
	}

}
