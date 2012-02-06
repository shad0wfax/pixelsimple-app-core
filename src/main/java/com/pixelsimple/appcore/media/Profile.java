/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.media;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akshay Sharma
 * Feb 3, 2012
 */
public final class Profile {
	private String id;
	private String name;
	private MediaType mediaType;
	private String containerFormat;
	private String fileExtension;
	// Will have the order maintained. The position also determines the priority.
	private List<ContainerSupportedCodec> codecs = new ArrayList<ContainerSupportedCodec>();  
	private String videoBitRate;
	private String vidoeQuality;
	private String audioBitRate; 
	private String aspectRatio; 
	private int maxWidth; 
	private String frameRateFPS; 
	private String optionalAdditionalParameters; 
	
	public Profile(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	// Todo - improve criteria api
	private List<String> criteria = new ArrayList<String>(); 
	
	/**
	 * Represents a combination of video and audio codec that is possible, with in the given containerformat.
	 *
	 * @author Akshay Sharma
	 * Feb 4, 2012
	 */
	public static class ContainerSupportedCodec {
		String videoCodec;
		String audioCodec;
		
		/**
		 * @return the videoCodec
		 */
		public String getVideoCodec() {
			return videoCodec;
		}

		/**
		 * @return the audioCodec
		 */
		public String getAudioCodec() {
			return audioCodec;
		}

		protected ContainerSupportedCodec addStream(StreamType streamType, String value) {
			if (streamType == StreamType.VIDEO) {
				this.videoCodec = value;
			} else if (streamType == StreamType.AUDIO) {
				this.audioCodec= value;
			}  
			
			return this;
		}
		
		@Override public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof ContainerSupportedCodec))
				return false;
			ContainerSupportedCodec codec = (ContainerSupportedCodec) obj;
			
			if (codec == this)
				return true;
			
			if ((codec.getAudioCodec() == null && this.getAudioCodec() != null) ||
					(this.getAudioCodec() == null && codec.getAudioCodec() != null))
				return false;
			
			if ((codec.getVideoCodec() == null && this.getVideoCodec() != null) ||
					(this.getVideoCodec() == null && codec.getVideoCodec() != null))
				return false;
			
			// Ok each pair is either null or not
			if (((codec.getAudioCodec() == this.getAudioCodec()) || (codec.getAudioCodec().equalsIgnoreCase(this.getAudioCodec())))
					&& ((codec.getVideoCodec() == this.getVideoCodec()) || (codec.getVideoCodec().equalsIgnoreCase(this.getVideoCodec()))))
				return true;
			
			return false;
		}
		
		/**
		 * Dumb implementation - painful Java :).
		 */
		@Override public int hashCode() {
			String fullCodecString = this.getAudioCodec() != null ? this.getAudioCodec() : "-" 
				+ ":" + this.getVideoCodec() != null ? this.getVideoCodec() : "-";
			
			return fullCodecString.hashCode();
		}
		
		@Override public String toString() {
			return "video-codec:" + this.videoCodec + "_audio-codec:" + this.audioCodec; 
		}
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mediaType
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * @return the containerFormat
	 */
	public String getContainerFormat() {
		return containerFormat;
	}

	/**
	 * @param containerFormat the containerFormat to set
	 */
	protected void setContainerFormat(String containerFormat) {
		this.containerFormat = containerFormat;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension the fileExtension to set
	 */
	protected void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * @return the codecs
	 */
	public List<ContainerSupportedCodec> getCodecs() {
		return codecs;
	}

	/**
	 * @param codecs the codecs to set
	 */
	protected Profile addCodec(ContainerSupportedCodec codec) {
		this.codecs.add(codec);
		return this;
	}

	/**
	 * @return the videoBitRate
	 */
	public String getVideoBitRate() {
		return videoBitRate;
	}

	/**
	 * @param videoBitRate the videoBitRate to set
	 */
	protected void setVideoBitRate(String videoBitRate) {
		this.videoBitRate = videoBitRate;
	}

	/**
	 * @return the vidoeQuality
	 */
	public String getVidoeQuality() {
		return vidoeQuality;
	}

	/**
	 * @param vidoeQuality the vidoeQuality to set
	 */
	protected void setVidoeQuality(String vidoeQuality) {
		this.vidoeQuality = vidoeQuality;
	}

	/**
	 * @return the audioBitRate
	 */
	public String getAudioBitRate() {
		return audioBitRate;
	}

	/**
	 * @param audioBitRate the audioBitRate to set
	 */
	protected void setAudioBitRate(String audioBitRate) {
		this.audioBitRate = audioBitRate;
	}

	/**
	 * @return the aspectRatio
	 */
	public String getAspectRatio() {
		return aspectRatio;
	}

	/**
	 * @param aspectRatio the aspectRatio to set
	 */
	protected void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * @param maxWidth the maxWidth to set
	 */
	protected void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * @return the frameRateFPS
	 */
	public String getFrameRateFPS() {
		return frameRateFPS;
	}

	/**
	 * @param frameRateFPS the frameRateFPS to set
	 */
	protected void setFrameRateFPS(String frameRateFPS) {
		this.frameRateFPS = frameRateFPS;
	}

	/**
	 * @return the optionalAdditionalParameters
	 */
	public String getOptionalAdditionalParameters() {
		return optionalAdditionalParameters;
	}

	/**
	 * @param optionalAdditionalParameters the optionalAdditionalParameters to set
	 */
	protected void setOptionalAdditionalParameters(String optionalAdditionalParameters) {
		this.optionalAdditionalParameters = optionalAdditionalParameters;
	}

	/**
	 * @return the criteria
	 */
	public List<String> getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria the criteria to set
	 */
	protected Profile addCriteria(String criterion) {
		this.criteria.add(criterion);
		return this;
	}
	
	@Override public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Profile))
			return false;
		
		Profile profile = (Profile) obj;
		
		if (profile == this)
			return true;

		if (profile.getId().equalsIgnoreCase(this.getId()))
			return true;
		
		return false;
	}
	
	@Override public int hashCode() {
		return this.getId().hashCode();
	}

	@Override public String toString() {
		return "\nid:" + this.getId() + "::Container:" + this.getContainerFormat() + "::Codecs supported:" + this.getCodecs();  
	}
	
}
