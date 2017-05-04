package com.zx.housekeeper.biz;

import java.io.File;
import java.util.List;
import java.util.Vector;

import android.os.Handler;

import com.zx.housekeeper.R;
import com.zx.housekeeper.constant.Constant;
import com.zx.housekeeper.entity.FileDetailInfo;
import com.zx.housekeeper.entity.FileInfo;

/**
 * file manager helper class
 * 
 * @author HY
 * 
 */
public class FileManager {

	/** resource */
	protected List<FileInfo> fileInfos;

	/** send message to main thread */
	protected Handler mHandler;

	/** external storage path */
	protected File mSdcardFile;

	/** all files total memory */
	protected long allFilesMem;
	/** the number of all files */
	protected int allFilesNum;

	/** text files total memory */
	protected long textFilesMem;
	/** the number of text files */
	protected int textFilesNum;

	/** video files total memory */
	protected long videoFilesMem;
	/** the number of video files */
	protected int videoFilesNum;

	/** audio files total memory */
	protected long audioFilesMem;
	/** the number of audio files */
	protected int audioFilesNum;

	/** image files total memory */
	protected long imageFilesMem;
	/** the number of image files */
	protected int imageFilesNum;

	/** archive files total memory */
	protected long zipFilesMem;
	/** the number of archive files */
	protected int zipFilesNum;

	/** application files total memory */
	protected long apkFilesMem;
	/** the number of application files */
	protected int apkFilesNum;

	/** other files total memory */
	protected long otherFilesMem;
	/** the number of other files */
	protected int otherFilesNum;

	// file detail information
	/** all files detail information */
	private Vector<FileDetailInfo> allFileDetailInfos = new Vector<FileDetailInfo>();
	/** text files detail information */
	private Vector<FileDetailInfo> textFileDetailInfos = new Vector<FileDetailInfo>();
	/** video files detail information */
	private Vector<FileDetailInfo> videoFileDetailInfos = new Vector<FileDetailInfo>();
	/** audio files detail information */
	private Vector<FileDetailInfo> audioFileDetailInfos = new Vector<FileDetailInfo>();
	/** image files detail information */
	private Vector<FileDetailInfo> imageFileDetailInfos = new Vector<FileDetailInfo>();
	/** archive files detail information */
	private Vector<FileDetailInfo> zipFileDetailInfos = new Vector<FileDetailInfo>();
	/** application files detail information */
	private Vector<FileDetailInfo> apkFileDetailInfos = new Vector<FileDetailInfo>();
	/** other files detail information */
	private Vector<FileDetailInfo> otherFileDetailInfos = new Vector<FileDetailInfo>();

	//
	/** private static field */
	private static FileManager mFileManager;

	private FileManager() {

	}

	/**
	 * get current class object
	 * 
	 * @return
	 */
	public static FileManager getInstance() {
		if (null == mFileManager) {
			synchronized (FileManager.class) {
				if (null == mFileManager) {
					mFileManager = new FileManager();
				}
			}
		}
		return mFileManager;
	}

	/**
	 * initial data
	 */
	public void initData() {
		allFilesMem = 0;
		allFilesNum = 0;
		textFilesMem = 0;
		textFilesNum = 0;
		videoFilesMem = 0;
		videoFilesNum = 0;
		audioFilesMem = 0;
		audioFilesNum = 0;
		imageFilesMem = 0;
		imageFilesNum = 0;
		zipFilesMem = 0;
		zipFilesNum = 0;
		apkFilesMem = 0;
		apkFilesNum = 0;
		otherFilesMem = 0;
		otherFilesNum = 0;

		allFileDetailInfos.clear();
		textFileDetailInfos.clear();
		videoFileDetailInfos.clear();
		audioFileDetailInfos.clear();
		imageFileDetailInfos.clear();
		zipFileDetailInfos.clear();
		apkFileDetailInfos.clear();
		otherFileDetailInfos.clear();
	}

	/**
	 * create thread to find all files and sort
	 * 
	 * @param fileInfos
	 * @param handler
	 * @param sdcardFile
	 */
	public void search(List<FileInfo> fileInfos, Handler handler,
			File sdcardFile) {
		this.fileInfos = fileInfos;
		this.mHandler = handler;
		this.mSdcardFile = sdcardFile;
		initData();
		new Thread() {
			public void run() {
				searchFiles(mSdcardFile);
				mHandler.sendEmptyMessage(Constant.ALL_SEARCH_FINISHED);
			};
		}.start();
	}

	public void searchFiles(File sdcardFile) {

		File[] allFiles = sdcardFile.listFiles();
		if (null != allFiles) {
			for (File file : allFiles) {
				if (null != file) {
					if (file.isFile()) {
						/** all files */
						FileInfo fileInfo = fileInfos.get(0);
						allFilesMem += file.length();
						allFilesNum++;
						fileInfo.setFileTotalSize(allFilesMem);
						fileInfo.setFileTotalNum(allFilesNum);
						String suffix = fileSuffix(file.getName());

						allFileDetailInfos.add(createDetailInfoEntity(
								file.getName(), file, suffix));

						if (FileUitls.isTextType(suffix)) {// text files
							FileInfo textFileInfo = fileInfos.get(1);
							textFilesMem += file.length();
							textFilesNum++;
							textFileInfo.setFileTotalSize(textFilesMem);
							textFileInfo.setFileTotalNum(textFilesNum);
							textFileDetailInfos.add(createDetailInfoEntity(
									file.getName(), file, suffix));
						} else if (FileUitls.isVideoFile(suffix)) {// video
																	// files
							FileInfo videoFileInfo = fileInfos.get(2);
							videoFilesMem += file.length();
							videoFilesNum++;
							videoFileInfo.setFileTotalSize(videoFilesMem);
							videoFileInfo.setFileTotalNum(videoFilesNum);
							videoFileDetailInfos.add(createDetailInfoEntity(
									file.getName(), file, suffix));
						} else if (FileUitls.isAudioFile(suffix)) {// audio
																	// files
							FileInfo audioFileInfo = fileInfos.get(3);
							audioFilesMem += file.length();
							audioFilesNum++;
							audioFileInfo.setFileTotalSize(audioFilesMem);
							audioFileInfo.setFileTotalNum(audioFilesNum);
							audioFileDetailInfos.add(createDetailInfoEntity(
									file.getName(), file, suffix));
						} else if (FileUitls.isImageFile(suffix)) {// image
																	// files
							FileInfo imageFileInfo = fileInfos.get(4);
							imageFilesMem += file.length();
							imageFilesNum++;
							imageFileInfo.setFileTotalSize(imageFilesMem);
							imageFileInfo.setFileTotalNum(imageFilesNum);
							imageFileDetailInfos.add(createDetailInfoEntity(
									file.getName(), file, suffix));
						} else if (FileUitls.isZipFile(suffix)) {// zip files
							FileInfo zipFileInfo = fileInfos.get(5);
							zipFilesMem += file.length();
							zipFilesNum++;
							zipFileInfo.setFileTotalSize(zipFilesMem);
							zipFileInfo.setFileTotalNum(zipFilesNum);
							zipFileDetailInfos.add(createDetailInfoEntity(
									file.getName(), file, suffix));
						} else if (FileUitls.isProgramFile(suffix)) {// apk
																		// files
							FileInfo apkFileInfo = fileInfos.get(6);
							apkFilesMem += file.length();
							apkFilesNum++;
							apkFileInfo.setFileTotalSize(apkFilesMem);
							apkFileInfo.setFileTotalNum(apkFilesNum);
							apkFileDetailInfos.add(createDetailInfoEntity(
									file.getName(), file, suffix));
						} else {// other files
							FileInfo otherFileInfo = fileInfos.get(7);
							otherFilesMem += file.length();
							otherFilesNum++;
							otherFileInfo.setFileTotalSize(otherFilesMem);
							otherFileInfo.setFileTotalNum(otherFilesNum);
							otherFileDetailInfos.add(createDetailInfoEntity(
									file.getName(), file, suffix));
						}
					} else {
						searchFiles(file);
					}
				}

				mHandler.sendEmptyMessage(Constant.FIRST_SEARCH_FINISH);
			}
		}

	}

	/**
	 * get file's suffix
	 * 
	 * @param fileName
	 * @return
	 */
	public String fileSuffix(String fileName) {
		return null != fileName ? fileName
				.substring(fileName.lastIndexOf(".") + 1) : "null";
	}

	/**
	 * get resource
	 * 
	 * @param position
	 * @return
	 */
	public List<FileDetailInfo> getDetailByPos(int position) {
		switch (position) {
		case Constant.ALL_FILE_TYPE_POS:
			/** all files detail information */
			return allFileDetailInfos;
		case Constant.FILE_TYPE_TEXT_POS:
			/** text files detail information */
			return textFileDetailInfos;
		case Constant.FILE_TYPE_VIDEO_POS:
			/** video files detail information */
			return videoFileDetailInfos;
		case Constant.FILE_TYPE_AUDIO_POS:
			/** audio files detail information */
			return audioFileDetailInfos;
		case Constant.FILE_TYPE_IMAGE_POS:
			/** image files detail information */
			return imageFileDetailInfos;
		case Constant.FILE_TYPE_ZIP_POS:
			/** archive files detail information */
			return zipFileDetailInfos;
		case Constant.FILE_TYPE_APK_POS:
			/** application files detail information */
			return apkFileDetailInfos;
		case Constant.FILE_TYPE_OTHER_POS:
			/** other files detail information */
			return otherFileDetailInfos;
		}
		return null;
	}

	/**
	 * create file detail information object
	 * 
	 * @param fileName
	 * @param fileType
	 * @param file
	 * @return
	 */
	public FileDetailInfo createDetailInfoEntity(String fileName, File file,
			String fileSuffix) {

		return new FileDetailInfo(fileName, R.drawable.icon_file, file,
				fileSuffix);

	}
}
