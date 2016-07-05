package com.alec.ync.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * 
 * @see 
 * @author L
 * 
 */
public class LocalImageManager {
	static String tag = "LocalImageManager";
	private static Config BITMAP_CONFIG = Bitmap.Config.RGB_565;
	private static int PNG_COMPRESS = 100;// ����SD��ʱ��PNG��ѹ����
	private static int JPG_COMPRESS = 100;// ����SD��ʱ��JPG��ѹ����

	/**
	 * �ж�URL��Ӧ�Ƿ��б���ͼ��
	 * 
	 * @param url
	 * @param context
	 * @return
	 */
	public static boolean hasTheFile(String url, Context context) {
		String filename = convertUrlToFileName(url);
		String dir = getDirectory(context);
		File file = new File(dir, filename);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * ��SD����ȡͼƬ
	 * 
	 * @param url
	 * @param context
	 * @return Bitmap
	 */
	public static Bitmap getImageFromSDCache(String url, Context context, int maxWidth, int maxHeight) {
		// SD���Ƿ��й��𣬲����ж�дȨ��
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			return null;
		}
		String filename = convertUrlToFileName(url);
		// ��ȡӦ��ͼƬ����·��
		String dir = getDirectory(context);
		File file = new File(dir, filename);
		String path = dir + "/" + filename;
		if (file.exists()) {
			try {
				return decodeBitmapFile(path, maxHeight, maxHeight);
			} catch (Exception e) {

				Log.i(tag, "Get image from sd failed");
			}
		}
		return null;
	}

	public static Bitmap getImageFromSD(String url, Context context) {
		// SD���Ƿ��й��𣬲����ж�дȨ��
		return getImageFromSDCache(url, context, 0, 0);
	}

	public static Bitmap getImageFromSDCard(String path, Context context, int maxWidth, int maxHeight) {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return null;
		}

		File file = new File(path);

		if (file.exists()) {

			try {
				return decodeBitmapFile(path, maxHeight, maxHeight);
			} catch (Exception e) {

				Log.i(tag, "Get image from sd failed");
			}
		}
		return null;
	}

	/**
	 * ͨ������·��path,��ȡ�ڴ�/SD���е�ͼƬ��
	 * 
	 * @param path
	 * @param maxWidth
	 * @param maxHeight
	 * @return Bitmap
	 */
	public static Bitmap getLocalImageBitmap(String path, Context context, int maxWidth, int maxHeight) {
		Bitmap bitmap = ImageLoaders.getBitmap(path);
		if (bitmap == null) {
			bitmap = getImageFromSDCard(path, context, maxWidth, maxHeight);
			if (bitmap != null) {
				ImageLoaders.putBitmapCache(path, bitmap);
			}
		}
		return bitmap;
	}

	public static Bitmap decodeBitmapFile(String path, int maxWidth, int maxHeight) {
		BitmapFactory.Options opt = new BitmapFactory.Options();

		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opt);
		if (maxWidth > 0 && maxWidth > 0) {
			opt.inSampleSize = computeSampleSize(opt, -1, maxWidth * maxHeight);
		} else {
			opt.inSampleSize = computeSampleSize(opt, -1, (Constant.Appinfo.width * 2 / 3) * (Constant.Appinfo.height * 2 / 3));
		}

		opt.inPreferredConfig = BITMAP_CONFIG;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inTempStorage = new byte[16 * 1024];
		opt.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(path, opt);
	}

	
	public static void saveBmpToSd(Bitmap bm, String url, String folderPath,Context context) {
		if (bm == null) {
			return;
		}

		// SD���Ƿ��й��𣬲����ж�дȨ��
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			return;
		}

		String filename = convertUrlToFileName(url);// ��ȡ�ļ���
		String dir = folderPath;// ��ȡ���Ŀ¼
		File dirFile = new File(dir);
		File file = new File(dir, filename);
		try {

			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			if (file.exists()) {
				return;
			}

			file.createNewFile();
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(file));
			if (url.indexOf(".png") >= 0) {
				if (Constant.Appinfo.width < 720) {
					/* 720P�����ֻ��ڴ�С����ѹ�����������ֹOOM */
					bm.compress(Bitmap.CompressFormat.PNG, 80, outStream);
				} else {
					bm.compress(Bitmap.CompressFormat.PNG, PNG_COMPRESS, outStream);
				}
			} else {
				if (Constant.Appinfo.width < 720) {
					bm.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
				} else {
					bm.compress(Bitmap.CompressFormat.JPEG, JPG_COMPRESS, outStream);
				}
			}
			outStream.flush();
			outStream.close();
			Log.i(tag, "Image saved tosd");
		} catch (FileNotFoundException e) {

			Log.i(tag, "FileNotFoundException");
		} catch (IOException e) {

			Log.i(tag, "IOException");
		}
	}
	
	/**
	 * ��bitmap�洢��SD��
	 * 
	 * @param bm
	 * @param url
	 */
	public static void saveBmpToSd(Bitmap bm, String url, Context context) {
		saveBmpToSd(bm, url, getDirectory(context), context);
	}

	/**
	 * ����url�����ļ���
	 * 
	 * @param url
	 * @return
	 */
	public static String convertUrlToFileName(String url) {

		int index = url.lastIndexOf("/");
		if (index != -1) {
			String name = url.substring(index);
			if (name.lastIndexOf(".jpg") == -1 && name.lastIndexOf(".png") == -1 && name.lastIndexOf(".jpeg") == -1) {
				name = name.replace("\\", "").replace("/", "").replace(":", "").replace("*", "").replace("?", "").replace("\"", "").replace("<", "")
						.replace(">", "").replace("|", "")
						+ ".jpg";
			}
			return name;
		} else
			return url;
	}

	/**
	 * ��ȡͼƬ����·��
	 * 
	 * @param context
	 * @return
	 */
	public static String getDirectory(Context context) {
		String path = "";
		try {
			path = Constant.FilePath.IMAGE_CACHE_DIR;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return path;
	}

	/**
	 * ��ȡ�ļ��д�С
	 * 
	 * @param file
	 *            Fileʵ��
	 * @return long ��λΪM
	 * @throws Exception
	 */
	public static float getFolderSize(java.io.File file) {
		float size = 0;
		java.io.File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size / 1048576;
	}

	/**
	 * ����ѹ����
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	public static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static boolean cleanLocalCache() {
		File dir = new File(Constant.FilePath.IMAGE_CACHE_DIR);
		boolean b=deleteDir(dir);
		File file = new File(Constant.FilePath.IMAGE_CACHE_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}
		return b;
	}

	public static boolean cleanLocalCache(String dirs) {
		File dir = new File(dirs);
		boolean b=deleteDir(dir);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return b;
	}
	
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// �ݹ�ɾ��Ŀ¼�е���Ŀ¼��
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// Ŀ¼��ʱΪ�գ�����ɾ��
		return dir.delete();
	}
}
