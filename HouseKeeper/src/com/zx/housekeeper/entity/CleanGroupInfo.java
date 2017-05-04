package com.zx.housekeeper.entity;

/**
 * clean group info
 * 
 * @author HY
 * 
 */
public class CleanGroupInfo {
	/** group title */
	private String groupTitle;
	/** total memory */
	private long groupTotalMem;

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public long getGroupTotalMem() {
		return groupTotalMem;
	}

	public void setGroupTotalMem(long groupTotalMem) {
		this.groupTotalMem = groupTotalMem;
	}
}
