package com.zx.housekeeper.entity;

import android.graphics.drawable.Drawable;

/**
 * process detail info
 * 
 * @author HY
 * 
 */
public class ProcInfo {
	/** process icon */
	private Drawable procIcon;
	/** package name */
	private String pkgName;
	/** process name */
	private String procName;
	/** process name */
	private long procMem;
	/** flag to judge is or not checked */
	private boolean isChecked;
	/** flag to judge is or not user application */
	private boolean isUserApp;

	public Drawable getProcIcon() {
		return procIcon;
	}

	public void setProcIcon(Drawable icon) {
		this.procIcon = icon;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String progressName) {
		this.procName = progressName;
	}

	public long getProcMem() {
		return procMem;
	}

	public void setProcMem(long procMem) {
		this.procMem = procMem;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public boolean isUserApp() {
		return isUserApp;
	}

	public void setUserApp(boolean isUserApp) {
		this.isUserApp = isUserApp;
	}

}
