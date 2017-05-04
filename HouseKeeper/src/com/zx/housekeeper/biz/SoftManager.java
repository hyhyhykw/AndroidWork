package com.zx.housekeeper.biz;

import java.util.List;
import java.util.Vector;

import com.zx.housekeeper.constant.Constant;
import com.zx.housekeeper.entity.SoftInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SoftManager {
	private static SoftManager mSoftManager;

	/** context object */
	protected Context mContext;

	private SoftManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static SoftManager getInstance(Context context) {
		if (null == mSoftManager) {
			synchronized (SoftManager.class) {
				if (null == mSoftManager) {
					mSoftManager = new SoftManager(context);
				}
			}
		}
		return mSoftManager;
	}

	/** SoftInfo source */
	private List<SoftInfo> allSoftInfos = new Vector<SoftInfo>();
	/** user application */
	private List<SoftInfo> userSoftInfos = new Vector<SoftInfo>();
	/** system application */
	private List<SoftInfo> systemSoftInfos = new Vector<SoftInfo>();

	/** package manager */
	protected PackageManager mPackageManager;

	/**
	 * get all soft source
	 * 
	 * @return
	 */
	public List<SoftInfo> getSInfoWithPosition(int position) {
		switch (position) {
		case Constant.ALL_SOFT_POS:
			return allSoftInfos;
		case Constant.SYSTEM_SOFT_POS:
			return systemSoftInfos;
		case Constant.USER_SOFT_POS:
			return userSoftInfos;
		default:
			return null;
		}
	}

	/**
	 * initial data
	 */
	public void initData() {
		allSoftInfos.clear();
		userSoftInfos.clear();
		systemSoftInfos.clear();
	}

	/**
	 * search all soft and sort
	 */
	public void search() {
		initData();
		mPackageManager = mContext.getPackageManager();
		List<PackageInfo> packageInfos = mPackageManager
				.getInstalledPackages(0);
		for (PackageInfo pkgInfo : packageInfos) {
			allSoftInfos.add(createSoftInfo(pkgInfo));
			if (isUserApp(pkgInfo)) {
				userSoftInfos.add(createSoftInfo(pkgInfo));
			} else {
				systemSoftInfos.add(createSoftInfo(pkgInfo));
			}
		}
	}

	/**
	 * this method help create SoftInfo object
	 * 
	 * @param pkgInfo
	 * @return
	 */
	public SoftInfo createSoftInfo(PackageInfo pkgInfo) {
		SoftInfo sInfo = new SoftInfo();
		sInfo.setPkgName(pkgInfo.packageName);
		sInfo.setVersionName(pkgInfo.versionName);
		ApplicationInfo appInfo = pkgInfo.applicationInfo;
		sInfo.setAppIcon(appInfo.loadIcon(mPackageManager));
		sInfo.setAppName(appInfo.loadLabel(mPackageManager).toString());
		sInfo.setUserApp(isUserApp(pkgInfo));
		return sInfo;
	}

	/**
	 * judge is or not user app
	 * 
	 * @param pInfo
	 * @return
	 */
	public static boolean isUserApp(PackageInfo pInfo) {
		ApplicationInfo aInfo = pInfo.applicationInfo;
		if ((aInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
			// user app
			return true;
		else
			// system app
			return false;
	}

}
