package com.zx.housekeeper.entity;

import android.graphics.drawable.Drawable;

/**
 * 
 * @author HY
 * 
 */
public class SoftInfo {

	/**
	 * application name
	 */
	private String appName;

	/**
	 * application icon
	 */
	private Drawable appIcon;

	/**
	 * application package name
	 */
	private String pkgName;

	/**
	 * application version
	 */
	private String versionName;

	/**
	 * judge is or not select
	 */
	private boolean isChecked;

	/**
	 * package information
	 */
	private boolean isUserApp;

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

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isUserApp() {
		return isUserApp;
	}

	public void setUserApp(boolean isUserApp) {
		this.isUserApp = isUserApp;
	}

}
