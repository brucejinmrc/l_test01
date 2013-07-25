/**
 * 
 */
package com.mrc.xmlfile.appspecs;

import com.mrc.xmlfile.appspecs.AppSpecs.OverWriteOption;

/**
 * @author bruce
 *
 */
public class SpecStatus {

	private String description;
	
	private OverWriteOption overWriteOption;

	public SpecStatus(String description, OverWriteOption overWriteOption) {
		super();
		this.description = description;
		this.overWriteOption = overWriteOption;
	}
	
	public String toString() {
		String str = description + " " + overWriteOption;
		return str;
	}

	public String getDescription() {
		return description;
	}

	public OverWriteOption getOverWriteOption() {
		return overWriteOption;
	}
	
}
