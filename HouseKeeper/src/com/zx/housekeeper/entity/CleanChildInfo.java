package com.zx.housekeeper.entity;

import java.io.File;

import android.graphics.drawable.Drawable;

/**
 * clean child info
 * 
 * @author HY
 * 
 */
public class CleanChildInfo {
	/**
	 * application name
	 */
	private String appName;
	/**
	 * application icon
	 */
	private Drawable appIcon;
	/**
	 * application occupy memory
	 */
	private int appTotelMem;
	/**
	 * application cache file path
	 */
	private File appFile;
	/**
	 * judge is or not checked
	 */
	private boolean isChecked;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public int getAppTotelMem() {
		return appTotelMem;
	}

	public void setAppTotelMem(int appTotelMem) {
		this.appTotelMem = appTotelMem;
	}

	public File getAppFile() {
		return appFile;
	}

	public void setAppFile(File appFile) {
		this.appFile = appFile;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
