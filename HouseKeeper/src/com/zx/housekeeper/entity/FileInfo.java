package com.zx.housekeeper.entity;

/**
 * file's information
 * 
 * @author HY
 * 
 */
public class FileInfo {
	/** file type */
	protected String fileType;

	/** file total occupy memory */
	protected long fileTotalSize;

	/** the number of files */
	protected int fileTotalNum;

	public FileInfo() {

	}

	public FileInfo(String fileType, long fileTotalSize, int fileTotalNum) {
		this.fileType = fileType;
		this.fileTotalSize = fileTotalSize;
		this.fileTotalNum = fileTotalNum;

	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileTotalSize() {
		return fileTotalSize;
	}

	public void setFileTotalSize(long fileTotalSize) {
		this.fileTotalSize = fileTotalSize;
	}

	public int getFileTotalNum() {
		return fileTotalNum;
	}

	public void setFileTotalNum(int fileTotalNum) {
		this.fileTotalNum = fileTotalNum;
	}

}
