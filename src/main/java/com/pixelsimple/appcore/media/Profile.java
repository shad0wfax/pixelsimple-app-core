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
	private String videoBitRate;
	private String vidoeQuality;
	private String audioBitRate; 
	private String aspectRatio; 
	private int maxWidth; 
	private String frameRateFPS; 
	private String optionalAdditionalParameters; 

	// Will have the order maintained. The position also determines the priority.
	private List<VideoCodec> videoCodecs = new ArrayList<VideoCodec>();  
	private List<AudioCodec> audioCodecs = new ArrayList<AudioCodec>();  
	private List<Codec> videoCodecsComputed = new ArrayList<Codec>();
	private List<Codec> audioCodecsComputed = new ArrayList<Codec>();

	public Profile(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	// Todo - improve criteria api
	private List<String> criteria = new ArrayList<String>(); 
	
	/**
	 *
	 * @author Akshay Sharma
	 * Feb 7, 2012
	 */
	private class VideoCodec {
		private Codec videoCodec;
		// Order is important!
		private List<Codec> associatedAudioCodecs = new ArrayList<Codec>();
		
		public VideoCodec(Codec videoCodec) {
			this.videoCodec = videoCodec;
			videoCodecsComputed.add(videoCodec);
		}
		
		public VideoCodec addAudioCodec(Codec audioCodec) {
			this.associatedAudioCodecs.add(audioCodec);
			audioCodecsComputed.add(audioCodec);
			return this;
		}

		/**
		 * @return the videoCodec
		 */
		public Codec getVideoCodec() {
			return videoCodec;
		}

		/**
		 * @return the associatedAudioCodecs
		 */
		public List<Codec> getAssociatedAudioCodecs() {
			return associatedAudioCodecs;
		}
		
		@Override public String toString() {
			return "video codec:" + this.getVideoCodec() + "and associated audio codecs :" + this.getAssociatedAudioCodecs(); 
		}
		
	}
	
	/**
	 *
	 * @author Akshay Sharma
	 * Feb 7, 2012
	 */
	private class AudioCodec {
		private Codec audioCodec;
		
		public AudioCodec(Codec audioCodec) {
			this.audioCodec = audioCodec;
			audioCodecsComputed.add(audioCodec);
		}

		/**
		 * @return the audioCodec
		 */
		public Codec getAudioCodec() {
			return audioCodec;
		}

		@Override public String toString() {
			return "audio codec:" + this.getAudioCodec(); 
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

	protected Profile addAudioOnlyCodec(Codec codec) {
		if (this.mediaType != MediaType.AUDIO)
			throw new IllegalStateException("An audio only codec can be added only if the media is of type Audio. " +
				"Use adding Audio through an associated video codec if the media is of type video");
		AudioCodec audioCodec = new AudioCodec(codec);
		this.audioCodecs.add(audioCodec);
		return this;
	}
	
	protected Profile addVideoCodec(Codec codec) {
		if (this.mediaType != MediaType.VIDEO)
			throw new IllegalStateException("A video only codec can be added only if the media is of type Video. " +
				"Use adding Audio with addAudioOnlyCodec() for media type Audio.");

		VideoCodec videoCodec = new VideoCodec(codec);		
		this.videoCodecs.add(videoCodec);
		return this;
	}
	
	protected Profile addAssociatedAudioCodec(Codec videoCodec, Codec audioCodec) {
		VideoCodec vidCodec = null;
		for (VideoCodec vcodec : this.videoCodecs) {
			
			if (vcodec.getVideoCodec().equals(videoCodec)) {
				vidCodec = vcodec;
				break;
			}
		}
		
		if (vidCodec != null) {
			vidCodec.addAudioCodec(audioCodec);
		} else {
			throw new IllegalStateException("Looks like the videoCodec has not been added to the profile yet.");
			// TODO: consider adding it videoCodec chain??
		}

		return this;
	}
	
	public List<Codec> getVideoCodecs() {
		return this.videoCodecsComputed;
	}
	
	public List<Codec> getAudioCodecs() {
		return this.audioCodecsComputed;
	}
	
	public List<Codec> getAssociatedAudioCodec(Codec videoCodec) {
		
		if (videoCodec.getCodecType() != Codec.CODEC_TYPE.AUDIO)
			throw new IllegalStateException("Only a video codec can have an associated list of Audio codecs for a profile");
		
		List<Codec> associatedCodecs = null;
		for (VideoCodec vcodec : this.videoCodecs) {
			
			if (vcodec.getVideoCodec().equals(videoCodec)) {
				associatedCodecs = vcodec.getAssociatedAudioCodecs();
				break;
			}
		}
		
		return associatedCodecs;
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
		return "\nid:" + this.getId() + "::Container:" + this.getContainerFormat() + "::Video Codecs supported:" + this.videoCodecs
				+ "\t:: audio codecs supported:" + this.audioCodecs;  
	}
	
}
