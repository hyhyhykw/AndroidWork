package com.zx.housekeeper.constant;

public class Constant {
	// DataUitls constant
	/** format data pattern */
	public static final String PATTERN = "yyyy-MM-dd HH-mm-ss";

	// FileManager constant
	/** file first search finished */
	public static final int FIRST_SEARCH_FINISH = 1;
	/** all file has searched finished */
	public static final int ALL_SEARCH_FINISHED = 0;

	// lead page constant
	/** Preferences file name */
	public static final String SHARED_PRE_FIRET = "first";

	// DBWrapper
	/** database file name */
	public static final String DB_FILE_NAME = "commonnum.db";
	/** database file path */
	public static final String DB_FILE_PATH = "/data/data/com.zx.housekeeper/databases";
	/** the title data of telephone manager main surface */
	public static final String TITLE_TABLE_NAME = "classlist";
	/** telephone table name prefix */
	public static final String TABLE_NAME_PREFIX = "table";
	/** table column name name */
	public static final String TEL_TABLE_COLUMN_NAME_NAME = "name";
	/** table column name number */
	public static final String TEL_TABLE_COLUMN_NAME_NUMBER = "number";
	/** asset database file path */
	public static final String ASSET_DB_FILE_PATH = "db/";

	// SoftManager
	/** position of all soft */
	public static final int ALL_SOFT_POS = 0;
	/** position of system soft */
	public static final int SYSTEM_SOFT_POS = 1;
	/** position of user soft */
	public static final int USER_SOFT_POS = 2;

	// FileManager
	/** all file type */
	public static final int ALL_FILE_TYPE_POS = 0;
	/** file type text */
	public static final int FILE_TYPE_TEXT_POS = 1;
	/** file type video */
	public static final int FILE_TYPE_VIDEO_POS = 2;
	/** file type audio */
	public static final int FILE_TYPE_AUDIO_POS = 3;
	/** file type image */
	public static final int FILE_TYPE_IMAGE_POS = 4;
	/** file type archive */
	public static final int FILE_TYPE_ZIP_POS = 5;
	/** file type application */
	public static final int FILE_TYPE_APK_POS = 6;
	/** other file */
	public static final int FILE_TYPE_OTHER_POS = 7;

	// speed
	/** show system progress */
	public static final int SHOW_SYSTEM_PROGRESS = 0;
	/** show user progress */
	public static final int SHOW_USER_PROGRESS = 1;

	// Phone Manager
	/** path of cpu core */
	public static final String PATH_CUP_CORE = "/sys/devices/system/cpu/";
	/** path of cpu info */
	public static final String PATH_CUP_INFO = "/proc/cpuinfo";

	public static final String PATH_SU_BIN = "/system/bin/su";
	public static final String PATH_SU_XBIN = "/system/xbin/su";

}
