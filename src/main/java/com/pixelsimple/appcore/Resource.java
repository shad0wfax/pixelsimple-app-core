/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore;

import java.io.File;

import com.pixelsimple.commons.util.Assert;

/**
 * @author Akshay Sharma
 *
 * Jun 16, 2012
 */
public class Resource {
	// Support some of the common resource types (say file, ftp, http, 
	public static enum RESOURCE_TYPE {FILE, DIRECTORY, };
	private String resource;
	private RESOURCE_TYPE resourceType;
	
	// Cached attributes - for avoiding repeated expensive calls.
	private Boolean valid;
	
	public Resource(String resource, RESOURCE_TYPE resourceType) {
		Assert.notNullAndNotEmpty(resource, "Invalid resource supplied.::" + resource);
		Assert.notNull(resourceType, "Invalid resourceType supplied.::" + resourceType);
		
		this.resource = resource;
		this.resourceType = resourceType;
	}

	// Any file:// resource. Includes mounted drives/mapped drives.
	public boolean isLocalFileSystemResource() {
		return (this.resourceType == RESOURCE_TYPE.FILE || this.resourceType == RESOURCE_TYPE.DIRECTORY);
	}
	
	public boolean isValid() {
		if (this.valid != null)
			return this.valid;
		
		// Perform a validation based on resource type. Can be expensive, so cache the result.
		if (this.resourceType == RESOURCE_TYPE.FILE) {
			File file = new File(this.resource);
			this.valid = file.exists() && file.isFile();
			file = null; // gc it hopefully
		} else if (this.resourceType == RESOURCE_TYPE.DIRECTORY) {
			File file = new File(this.resource);
			this.valid = file.exists() && file.isDirectory();
			file = null; // gc it hopefully
			
			// TODO: Add additional protoco/resource_type validations
		} else {
			this.valid = false;
		}
		return this.valid;
	}
	
	@Override public String toString() {
		return "resource:" + this.resource + "\t of type:" + this.resourceType;
	}
	
	/**
	 * @return the resource
	 */
	public String getResourceAsString() {
		return this.resource;
	}

	/**
	 * @return the resourceType
	 */
	public RESOURCE_TYPE getResourceType() {
		return this.resourceType;
	}

}
