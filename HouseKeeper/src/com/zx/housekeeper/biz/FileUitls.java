package com.zx.housekeeper.biz;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

/**
 * file helper class
 * 
 * @author Administrator
 * 
 */
public class FileUitls {
	/**
     * 
     */
	public static String getMimeType(File file) {

		String[][] MIME_TYPE = {
				// {后缀名， MIME类型}
				{ ".3gp", "video/3gpp" },
				{ ".apk", "application/vnd.android.package-archive" },
				{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" },
				{ ".bin", "application/octet-stream" },
				{ ".bmp", "image/bmp" }, { ".c", "text/plain" },
				{ ".class", "application/octet-stream" },
				{ ".conf", "text/plain" }, { ".cpp", "text/plain" },
				{ ".doc", "application/msword" },
				{ ".exe", "application/octet-stream" },
				{ ".gif", "image/gif" }, { ".gtar", "application/x-gtar" },
				{ ".gz", "application/x-gzip" }, { ".h", "text/plain" },
				{ ".htm", "text/html" }, { ".html", "text/html" },
				{ ".jar", "application/java-archive" },
				{ ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
				{ ".jpg", "image/jpeg" },
				{ ".js", "application/x-javascript" },
				{ ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" },
				{ ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
				{ ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" },
				{ ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" },
				{ ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
				{ ".mp4", "video/mp4" },
				{ ".mpc", "application/vnd.mpohun.certificate" },
				{ ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
				{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
				{ ".mpga", "audio/mpeg" },
				{ ".msg", "application/vnd.ms-outlook" },
				{ ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
				{ ".png", "image/png" },
				{ ".pps", "application/vnd.ms-powerpoint" },
				{ ".ppt", "application/vnd.ms-powerpoint" },
				{ ".prop", "text/plain" },
				{ ".rar", "application/x-rar-compressed" },
				{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
				{ ".rtf", "application/rtf" }, { ".sh", "text/plain" },
				{ ".tar", "application/x-tar" },
				{ ".tgz", "application/x-compressed" },
				{ ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
				{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" },
				{ ".wps", "application/vnd.ms-works" },
				{ ".xml", "text/plain" }, { ".z", "application/x-compress" },
				{ ".zip", "application/zip" }, { "", "*/*" } };
		String fName = file.getName();
		String type = "*";
		String end = fName.substring(fName.lastIndexOf(".")).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_TYPE.length; i++) {//
			if (end.equals(MIME_TYPE[i][0]))
				type = MIME_TYPE[i][1];
		}
		return type;
	}

	/**
	 * judge is it text file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isTextType(String suffix) {
		String[] str = { "txt", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
				"pdf", "c", "h", "cpp", "hpp", "java", "js", "html", "xml",
				"xhtml", "css" };
		for (String suff : str) {
			if (suff.equals(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it video file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isVideoFile(String suffix) {
		String[] str = { "flv", "mkv", "avi", "mp4", "rm", "rmvb", "3gp",
				"flash" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it audio file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isAudioFile(String suffix) {
		String[] str = { "mp3", "wav", "wma", "flac", "ape", "mid", "ogg" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it image
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isImageFile(String suffix) {
		String[] str = { "bmp", "jpg", "gif", "png", "jpeg", "ico", "tiff",
				"xcf" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it compressed files
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isZipFile(String suffix) {
		String[] str = { "zip", "rar", "gz", "tar", "7z" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it android application package file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isProgramFile(String suffix) {
		String[] str = { "apk" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * format file length
	 * 
	 * @param length
	 * @return
	 */
	public static String formatLength(long length) {
		DecimalFormat format = new DecimalFormat(".0");

		if (length >= 1024 && length < 1024 * 1024) {
			double len = length * 1.0 / 1024;
			String string = format.format(len);
			return string + "K";
		} else if (length >= 1024 * 1024 && length < 1024 * 1024 * 1024) {
			double len = length * 1.0 / (1024 * 1024);
			String string = format.format(len);
			return string + "M";
		} else if (length >= 1024 * 1024 * 1024) {
			double len = length * 1.0 / (1024 * 1024 * 1024);
			String string = format.format(len);
			return string + "G";
		}

		return length + "B";
	}

	/**
	 * clean cache file
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		File[] files = file.listFiles();
		boolean isSuccess = false;
		for (File file2 : files) {
			if (file2.isDirectory()) {
				deleteFile(file2);
			}
			isSuccess = file2.delete();
		}
		return isSuccess;
	}

	/**
	 * total memory
	 */
	static int sum = 0;

	/**
	 * calculate total memory
	 * 
	 * @param file
	 * @return
	 */
	public static int calAppTotal(File file) {
		sum = 0;
		File[] files = file.listFiles();
		if (null != files && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					sum += files[i].length();
				} else {
					calAppTotal(files[i]);
				}
			}
		}
		return sum;
	}

	/**
	 * get all paths
	 * 
	 * @param context
	 * @return
	 */
	public static String[] getPaths(Context context) {
		StorageManager sManager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		Class<?> cla = StorageManager.class;
		Method method = null;
		String[] paths = new String[2];
		try {
			method = cla.getDeclaredMethod("getVolumePaths");
			method.setAccessible(true);
			String[] array = (String[]) method.invoke(sManager);
			method.setAccessible(false);
			for (int i = 0; i < paths.length; i++) {
				if (i == array.length) {
					paths[i] = null;
				} else {
					paths[i] = array[i];
				}
			}
		} catch (NoSuchMethodException e) {
			return paths;
		} catch (SecurityException e) {
			return paths;
		} catch (IllegalAccessException e) {
			return paths;
		} catch (IllegalArgumentException e) {
			return paths;
		} catch (InvocationTargetException e) {
			return paths;
		}
		return paths;
	}

	/**
	 * inner sdcard
	 * 
	 * @return
	 */
	public static String getInnerSDCARDPath(Context context) {
		String[] paths = getPaths(context);
		for (String path : paths) {
			if (Environment.getExternalStorageDirectory().getAbsolutePath()
					.equals(path)) {
				return path;
			}
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath();

	}

	/**
	 * 
	 * @return
	 */
	public static String getExternelSDCARDPath(Context context) {
		String[] paths = getPaths(context);
		for (String path : paths) {
			if (!Environment.getExternalStorageDirectory().getAbsolutePath()
					.equals(path)) {
				return path;
			}
		}
		return null;
	}
}
