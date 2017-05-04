package com.zx.housekeeper.biz;

import java.util.List;
import java.util.Vector;

import com.zx.housekeeper.constant.Constant;
import com.zx.housekeeper.entity.ProcInfo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;

/**
 * manage progress
 * 
 * @author HY
 * 
 */
public class ProcManager {

	/** context object */
	protected Context mContext;
	/** activity manager */
	protected ActivityManager mActivityManager;
	/** package manager */
	protected PackageManager mPackageManager;

	public ProcManager(Context mContext) {
		super();
		this.mContext = mContext;
		mActivityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		mPackageManager = mContext.getPackageManager();
	}

	/**
	 * user process info
	 */
	private List<ProcInfo> userProcInfos = new Vector<ProcInfo>();
	/**
	 * system process info
	 */
	private List<ProcInfo> sysProcInfos = new Vector<ProcInfo>();

	/**
	 * get process info collections by flag
	 * 
	 * @param flag
	 *            0:system process 1:user process
	 * @return
	 */
	public List<ProcInfo> getProcInfos(int flag) {
		switch (flag) {
		case Constant.SHOW_SYSTEM_PROGRESS:
			return sysProcInfos;
		case Constant.SHOW_USER_PROGRESS:
			return userProcInfos;
		}
		return null;
	}

	/**
	 * reset data
	 */
	public void initData() {
		userProcInfos.clear();
		sysProcInfos.clear();
	}

	/**
	 * search running process and add to collection
	 */
	public void search() {
		initData();
		// get running process
		List<RunningAppProcessInfo> processInfos = mActivityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo runProcInfo : processInfos) {
			String[] pkgList = runProcInfo.pkgList;
			int[] pids = { runProcInfo.pid };
			for (String pkgName : pkgList) {
				PackageInfo pInfo = creatPkgInfo(pkgName);
				if (null != pInfo) {
					if (SoftManager.isUserApp(pInfo)) {
						userProcInfos.add(creatProcInfo(pInfo, pids));
					} else {
						sysProcInfos.add(creatProcInfo(pInfo, pids));
					}
				}

			}
		}
	}

	/**
	 * 
	 * @param pkgName
	 * @return
	 */
	public PackageInfo creatPkgInfo(String pkgName) {
		PackageInfo pInfo = null;
		try {
			pInfo = mPackageManager.getPackageInfo(pkgName, 0);
		} catch (NameNotFoundException e) {
			return null;
		}
		return pInfo;
	}

	/**
	 * process info
	 * 
	 * @param pInfo
	 *            package info
	 * @param pids
	 *            process pid
	 * @return
	 */
	public ProcInfo creatProcInfo(PackageInfo pInfo, int[] pids) {
		ProcInfo procInfo = new ProcInfo();

		ApplicationInfo aInfo = pInfo.applicationInfo;
		// process icon
		Drawable icon = aInfo.loadIcon(mPackageManager);
		procInfo.setProcIcon(icon);
		// process name
		String progressName = aInfo.loadLabel(mPackageManager).toString();
		procInfo.setProcName(progressName);
		// process package name
		procInfo.setPkgName(aInfo.packageName);

		// process used memory
		MemoryInfo mInfo = mActivityManager.getProcessMemoryInfo(pids)[0];
		long appMem = mInfo.dalvikPrivateDirty * 1024;
		procInfo.setProcMem(appMem);
		// flag to judge is or not user application
		procInfo.setUserApp(SoftManager.isUserApp(pInfo));

		return procInfo;
	}

}
