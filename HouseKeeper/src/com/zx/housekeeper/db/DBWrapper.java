package com.zx.housekeeper.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zx.housekeeper.biz.LogWrapper;
import com.zx.housekeeper.constant.Constant;
import com.zx.housekeeper.entity.TelInfo;

/**
 * operate database
 * 
 * @author HY
 * 
 */
public class DBWrapper {
	public static final String TAG = DBWrapper.class.getName();
	static File mFile;

	/**
	 * help copy database file
	 * 
	 * @author HY
	 * 
	 */
	public static class FileHelper {
		/**
		 * copy file
		 * 
		 * @param context
		 */
		public void copyFromAssets(Context context) {
			// get asset manager object
			AssetManager assetManager = context.getAssets();
			InputStream in = null;
			FileOutputStream fos = null;
			// target file path
			File pFile = new File(Constant.DB_FILE_PATH);
			try {
				if (!pFile.exists()) {
					pFile.mkdirs();
				}
				// open database file
				in = assetManager.open(Constant.ASSET_DB_FILE_PATH
						+ Constant.DB_FILE_NAME);
				if (null == mFile) {
					mFile = new File(pFile, Constant.DB_FILE_NAME);
					fos = new FileOutputStream(mFile);
					byte[] bs = new byte[1024];
					int length = 0;
					while ((length = in.read(bs)) != -1) {
						fos.write(bs, 0, length);
					}
				}

			} catch (IOException e) {
				LogWrapper.e(TAG, "IO ERROR");
			} finally {
				if (null != fos) {
					try {
						fos.close();
					} catch (IOException e) {
						LogWrapper.e(TAG, "IO CLOSE ERROR");
					}
				}
			}
		}
	}

	/**
	 * help operate database file
	 * 
	 * @author HY
	 * 
	 */
	public static class DBHelper {
		/**
		 * get telephone number item name
		 * 
		 * @return
		 */
		public List<String> readClassList() {
			// source which you read
			List<String> classList = new ArrayList<String>();

			// open database file and get SQLiteDatabase object
			SQLiteDatabase db = SQLiteDatabase
					.openOrCreateDatabase(mFile, null);

			// read database file
			Cursor cursor = db.query(Constant.TITLE_TABLE_NAME, null, null,
					null, null, null, null);
			while (cursor.moveToNext()) {
				String item = cursor.getString(cursor.getColumnIndex("name"));
				classList.add(item);
			}
			cursor.close();
			return classList;
		}

		/**
		 * get item
		 * 
		 * @param position
		 * @return
		 */
		public List<TelInfo> readItems(int position) {
			// source which you read
			List<TelInfo> telInfos = new ArrayList<TelInfo>();

			// open database file and get SQLiteDatabase object
			SQLiteDatabase db = SQLiteDatabase
					.openOrCreateDatabase(mFile, null);

			// read database file
			Cursor cursor = db.query(Constant.TABLE_NAME_PREFIX
					+ (position + 1), null, null, null, null, null, null);
			while (cursor.moveToNext()) {
				String itemName = cursor.getString(cursor
						.getColumnIndex(Constant.TEL_TABLE_COLUMN_NAME_NAME));
				String itemNum = cursor.getString(cursor
						.getColumnIndex(Constant.TEL_TABLE_COLUMN_NAME_NUMBER));
				TelInfo telInfo = new TelInfo(itemName, itemNum);
				telInfos.add(telInfo);
			}
			cursor.close();
			return telInfos;
		}
	}

}
