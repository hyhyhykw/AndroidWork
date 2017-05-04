package com.zx.housekeeper.biz;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.StatFs;

/**
 * 
 * @author HY
 * 
 */
public class MemManager {

	/**
	 * 
	 * @param innerPath
	 * @param outerPath
	 * @return
	 */
	public static long getInnerAndOuterTotal(String innerPath, String outerPath) {
		long outerTotal = 0;
		if (null != outerPath) {
			outerTotal = getTotal(outerPath);
		}
		return getTotal(innerPath) + outerTotal;
	}

	/**
	 * total memory
	 * 
	 * @param path
	 * @return
	 */
	public static long getTotal(String path) {
		long total = 0;
		if (null != path) {
			StatFs statfs = new StatFs(path);
			total = statfs.getBlockCountLong() * statfs.getBlockSizeLong();
		}

		return total;
	}

	/**
	 * get unused memory
	 * 
	 * @param path
	 * @return
	 */
	public static long getAvail(String path) {
		long mem = 0;
		if (null != path) {
			StatFs statfs = new StatFs(path);
			mem = statfs.getAvailableBlocksLong() * statfs.getBlockSizeLong();
		}
		return mem;
	}

	/**
	 * get used memory
	 * 
	 * @param path
	 * @return
	 */
	public static long getUsed(String path) {
		return getTotal(path) - getAvail(path);
	}

	/**
	 * get used memory percent
	 * 
	 * @param path
	 * @return
	 */
	public static float getUsedPercent(String path) {
		float percent = 0;
		if (null != path) {
			percent = getUsed(path) * 1.0f / getTotal(path);
		}
		return percent;
	}

	/**
	 * get all ram
	 * 
	 * @param context
	 * @return
	 */
	public static long getRuntimeTotalMem(Context context) {
		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		aManager.getMemoryInfo(outInfo);
		return outInfo.totalMem;
	}

	/**
	 * get available ram
	 * 
	 * @param context
	 * @return
	 */
	public static long getRuntimeAvailMem(Context context) {
		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		aManager.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}

	/**
	 * get used ram
	 * 
	 * @param context
	 * @return
	 */
	public static long getRuntimeUsedMem(Context context) {
		return getRuntimeTotalMem(context) - getRuntimeAvailMem(context);
	}

	/**
	 * get used ram percent
	 * 
	 * @return
	 */
	public static float getRamPercent(Context context) {
		return (getRuntimeUsedMem(context) * 1.0f)
				/ getRuntimeTotalMem(context);
	}

}
