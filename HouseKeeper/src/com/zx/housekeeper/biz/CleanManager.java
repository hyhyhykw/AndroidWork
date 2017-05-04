package com.zx.housekeeper.biz;

import java.io.File;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import com.zx.housekeeper.entity.CleanChildInfo;

/**
 * help search cache file
 * 
 * @author HY
 * 
 */
public class CleanManager {

	/**
	 * 
	 */
	private static CleanManager mCleanManager;

	private CleanManager(Context context) {
		this.mContext = context;
	}

	public static CleanManager getInstance(Context context) {
		if (null == mCleanManager) {
			synchronized (CleanManager.class) {
				if (null == mCleanManager) {
					mCleanManager = new CleanManager(context);
				}
			}
		}
		return mCleanManager;
	}

	/** package manager */
	protected PackageManager mPackageManager;
	/** context object */
	protected Context mContext;
	/** phone cache */
	protected Vector<CleanChildInfo> phoneCacheInfos = new Vector<CleanChildInfo>();
	/** phone file */
	protected Vector<CleanChildInfo> phoneFileInfos = new Vector<CleanChildInfo>();
	/** sdcard cache */
	protected Vector<CleanChildInfo> sdCardCacheInfos = new Vector<CleanChildInfo>();
	/** sdcard file */
	protected Vector<CleanChildInfo> sdCardFileInfos = new Vector<CleanChildInfo>();

	/**
	 * get phone cache file
	 * 
	 * @return
	 */
	public Vector<CleanChildInfo> getPhoneCacheInfos() {
		return phoneCacheInfos;
	}

	/**
	 * get phone file
	 * 
	 * @return
	 */
	public Vector<CleanChildInfo> getPhoneFileInfos() {
		return phoneFileInfos;
	}

	/**
	 * get phone sdcard cache file
	 * 
	 * @return
	 */
	public Vector<CleanChildInfo> getSdCardCacheInfos() {
		return sdCardCacheInfos;
	}

	/**
	 * get phone sdcardfile
	 * 
	 * @return
	 */
	public Vector<CleanChildInfo> getSdCardFileInfos() {
		return sdCardFileInfos;
	}

	/**
	 * reset the source
	 */
	void reset() {
		phoneCacheInfos.clear();
		phoneFileInfos.clear();
		sdCardCacheInfos.clear();
		sdCardFileInfos.clear();
	}

	/**
	 * search files
	 */
	public void search() {
		reset();
		mPackageManager = mContext.getPackageManager();
		List<PackageInfo> pkgInfos = mPackageManager.getInstalledPackages(0);
		for (PackageInfo pkgInfo : pkgInfos) {
			if (SoftManager.isUserApp(pkgInfo)) {
				String pkgName = pkgInfo.packageName;
				Context context = creatContext(pkgName);

				if (null != context) {
					// phone cache file
					File phoneCacheFile = context.getCacheDir();
					if (null != phoneCacheFile && !phoneCacheFile.exists()) {
						phoneCacheFile.mkdir();
					}
					CleanChildInfo pChildCacheInfo = createChildInfo(
							phoneCacheFile, pkgInfo);
					if (null != pChildCacheInfo
							&& pChildCacheInfo.getAppTotelMem() != 0) {
						phoneCacheInfos.add(pChildCacheInfo);
					}

					// phone file
					File phoneFile = context.getFilesDir();
					if (null != phoneFile && !phoneFile.exists()) {
						phoneFile.mkdir();
					}

					CleanChildInfo pChildFileInfo = createChildInfo(phoneFile,
							pkgInfo);
					if (null != pChildFileInfo
							&& pChildFileInfo.getAppTotelMem() != 0) {
						phoneFileInfos.add(pChildFileInfo);
					}

					// sd card cache file
					File sdCardCacheFile = context.getExternalCacheDir();

					if (null != sdCardCacheFile && !sdCardCacheFile.exists()) {
						sdCardCacheFile.mkdir();
					}

					CleanChildInfo sdChildCacheInfo = createChildInfo(
							sdCardCacheFile, pkgInfo);
					if (null != sdChildCacheInfo
							&& sdChildCacheInfo.getAppTotelMem() != 0) {
						sdCardCacheInfos.add(sdChildCacheInfo);
					}

					// sd card file
					File sdCardFile = context.getExternalFilesDir(null);
					if (null != sdCardFile && !sdCardFile.exists()) {
						sdCardFile.mkdir();
					}

					CleanChildInfo sdChildFileInfo = createChildInfo(
							sdCardFile, pkgInfo);
					if (null != sdChildFileInfo
							&& sdChildFileInfo.getAppTotelMem() != 0) {
						sdCardFileInfos.add(sdChildFileInfo);
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
	public Context creatContext(String pkgName) {
		Context context = null;
		try {
			context = mContext.createPackageContext(pkgName,
					Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			return null;
		} catch (SecurityException e) {
			return null;
		}
		return context;
	}

	/**
	 * 
	 * @param file
	 * @param pkgInfo
	 * @return
	 */
	public CleanChildInfo createChildInfo(File file, PackageInfo pkgInfo) {
		CleanChildInfo childInfo = new CleanChildInfo();
		if (null != file) {
			childInfo.setAppFile(file);
			ApplicationInfo appInfo = pkgInfo.applicationInfo;
			Drawable appIcon = appInfo.loadIcon(mPackageManager);
			childInfo.setAppIcon(appIcon);
			childInfo.setAppName(appInfo.loadLabel(mPackageManager).toString());
			childInfo.setAppTotelMem(FileUitls.calAppTotal(file));
		}
		return childInfo;
	}

}
