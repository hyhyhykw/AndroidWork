package com.zx.housekeeper.entity;

import java.io.File;

/**
 * file detail information
 * 
 * @author HY
 * 
 */
public class FileDetailInfo {

	/** file name */
	private String fileName;
	/** file icon */
	private int fileIcon;
	/** file */
	private File file;

	/** judge is or not selected */
	private boolean isChecked;

	private String fileSuffix;

	public FileDetailInfo() {

	}

	public FileDetailInfo(String fileName, int fileIcon, File file,
			String fileSuffix) {
		this.fileName = fileName;
		this.fileIcon = fileIcon;
		this.file = file;
		this.setFileSuffix(fileSuffix);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileIcon() {
		return fileIcon;
	}

	public void setFileIcon(int fileIcon) {
		this.fileIcon = fileIcon;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

}
