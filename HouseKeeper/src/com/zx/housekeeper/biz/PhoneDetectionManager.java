package com.zx.housekeeper.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.DisplayMetrics;
import com.zx.housekeeper.constant.Constant;

/**
 * 
 * @author HY
 * 
 */
@SuppressWarnings("deprecation")
public class PhoneDetectionManager {
	private static final String TAG = PhoneDetectionManager.class
			.getSimpleName();

	/**
	 * get informations
	 * 
	 * @return
	 */
	public static String[][] getPhoneInfos(Context context) {
		String[][] phoneInfos = {
				{ "设备名称:" + getDeviceName(), "系统版本:" + getSystemVersion() },
				{ "全部运行内存:" + getAllRam(context),
						"剩余运行内存:" + getFreeRam(context) },
				{ "cpu名称:" + getCpuName(), "cpu数量:" + getCpuCoreNum() },
				{ "手机分辨率:" + getPhoneResolution(context),
						"相机分辨率:" + getCameraResolution() },
				{ "基带版本:" + getRadioVersion(),
						"是否root:" + (isRootByFile() ? "是" : "否") },
				{ "蓝牙状态:" + getBluetoothState(), isBluetoothEnabled() } };
		return phoneInfos;
	}

	/**
	 * get device name
	 * 
	 * @return
	 */
	public static String getDeviceName() {
		return Build.BRAND;
	}

	/**
	 * get phone model
	 * 
	 * @return
	 */
	public static String getDeviceModel() {
		return Build.MODEL;
	}

	/**
	 * get system version
	 * 
	 * @return
	 */
	public static String getSystemVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * get all ram long by read file
	 * 
	 * @return
	 */
	public static long getAllRamLen() {
		long allMem = 0;
		try {
			FileReader fr = new FileReader("/proc/meminfo");
			BufferedReader br = new BufferedReader(fr, 8192);
			String str = br.readLine();// read "memInfo" first line，total ram
			String[] array = str.split("\\s+");
			for (String num : array) {
				LogWrapper.i(str, num + "\t");
			}
			allMem = Integer.valueOf(array[1]).intValue() * 1024;// to byte
			br.close();
		} catch (IOException e) {
			LogWrapper.e(TAG, "IO ERROR");
		}
		return allMem;
	}

	/**
	 * all ram
	 * 
	 * @return
	 */
	public static String getAllRam(Context context) {
		return FileUitls.formatLength(MemManager.getRuntimeTotalMem(context));
	}

	/**
	 * free ram
	 * 
	 * @return
	 */
	public static String getFreeRam(Context context) {
		return FileUitls.formatLength(MemManager.getRuntimeAvailMem(context));
	}

	/**
	 * used ram
	 * 
	 * @return
	 */
	public static String getUsedRam(Context context) {
		return FileUitls.formatLength(MemManager.getRuntimeUsedMem(context));
	}

	/**
	 * get cpu name
	 * 
	 * @return
	 */
	public static String getCpuName() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(Constant.PATH_CUP_INFO);
			br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			LogWrapper.e(TAG, "ERROR File Not Found");
		} catch (IOException e) {
			LogWrapper.e(TAG, "IO ERROR");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				LogWrapper.e(TAG, "IO ERROR");
			}
		}
		return "unknown";
	}

	/**
	 * get cpu number
	 * 
	 * @return
	 */
	public static int getCpuCoreNum() {
		File file = new File(Constant.PATH_CUP_CORE);
		File[] files = null;
		try {
			files = file.listFiles(new FileFilter() {
				public boolean accept(File filePath) {
					return Pattern.matches("cpu[0-9]", filePath.getName());
				}
			});
		} catch (Exception e) {
			LogWrapper.e(TAG, "ERROR");
		}
		return null != files ? files.length : 0;
	}

	/**
	 * get phone resolution
	 * 
	 * @return
	 */
	public static String getPhoneResolution(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
	}

	/**
	 * get camera resolution
	 * 
	 * @return
	 */
	public static String getCameraResolution() {
		Camera camera = Camera.open();
		Parameters param = camera.getParameters();
		camera.stopPreview();
		camera.release();
		camera = null;
		List<Size> sizes = param.getSupportedPictureSizes();
		Size size = sizes.get(0);
		return size.width + "*" + size.height;
	}

	/**
	 * get base band
	 * 
	 * @return
	 */
	public static String getRadioVersion() {
		String radio = Build.getRadioVersion();
		return radio.equals("") ? "unknown" : radio;
	}

	/**
	 * get BlueTooth state
	 * 
	 * @return
	 */
	public static String isBluetoothEnabled() {
		BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
		if (null == bAdapter) {
			return "未检测到蓝牙状态";
		}
		switch (bAdapter.getState()) {
		case BluetoothAdapter.STATE_ON:
			return "点击关闭蓝牙";
		case BluetoothAdapter.STATE_OFF:
			return "点击打开蓝牙";
		case BluetoothAdapter.STATE_TURNING_ON:
			return "正在打开";
		case BluetoothAdapter.STATE_TURNING_OFF:
			return "正在关闭";
		default:
			return "未知";
		}
	}

	public static String getBluetoothState() {
		BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
		if (null == bAdapter) {
			return "未检测到蓝牙状态";
		}
		switch (bAdapter.getState()) {
		case BluetoothAdapter.STATE_ON:
			return "已打开";
		case BluetoothAdapter.STATE_OFF:
			return "已关闭";
		case BluetoothAdapter.STATE_TURNING_ON:
			return "正在打开";
		case BluetoothAdapter.STATE_TURNING_OFF:
			return "正在关闭";
		default:
			return "未知";
		}
	}

	/**
	 * set BlueTooth state
	 */
	public static void setBluetoothState() {
		BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
		if (null != bAdapter) {
			if (bAdapter.isEnabled())
				bAdapter.disable();
			else
				bAdapter.enable();
		}

	}

	/**
	 * 
	 * @return
	 */
	public static boolean isRoot() {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process proc = runtime.exec("su");
			OutputStream os = proc.getOutputStream();
			os.write("ls data \n".getBytes());
			os.write("exit \n".getBytes());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * judge is or not root
	 * 
	 * @return
	 */
	public static boolean isRootByFile() {
		File suFile1 = new File(Constant.PATH_SU_BIN);
		File suFile2 = new File(Constant.PATH_SU_XBIN);
		try {
			if (!suFile1.exists() && !suFile2.exists())
				return false;
			return true;
		} catch (Exception e) {
			LogWrapper.e(TAG, "ERROR");
		}
		return false;
	}

}
