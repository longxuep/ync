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
	private static int PNG_COMPRESS = 100;// 存入SD卡时，PNG的压缩率
	private static int JPG_COMPRESS = 100;// 存入SD卡时，JPG的压缩率

	/**
	 * 判断URL对应是否有本地图标
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
	 * 从SD卡读取图片
	 * 
	 * @param url
	 * @param context
	 * @return Bitmap
	 */
	public static Bitmap getImageFromSDCache(String url, Context context, int maxWidth, int maxHeight) {
		// SD卡是否有挂起，并具有读写权限
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			return null;
		}
		String filename = convertUrlToFileName(url);
		// 获取应用图片缓存路径
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
		// SD卡是否有挂起，并具有读写权限
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
	 * 通过绝对路径path,获取内存/SD卡中的图片，
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

		// SD卡是否有挂起，并具有读写权限
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			return;
		}

		String filename = convertUrlToFileName(url);// 获取文件名
		String dir = folderPath;// 获取存放目录
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
					/* 720P以下手机内存小，固压缩比例增大防止OOM */
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
	 * 将bitmap存储到SD卡
	 * 
	 * @param bm
	 * @param url
	 */
	public static void saveBmpToSd(Bitmap bm, String url, Context context) {
		saveBmpToSd(bm, url, getDirectory(context), context);
	}

	/**
	 * 根据url生成文件名
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
	 * 获取图片缓存路径
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
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return long 单位为M
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
	 * 计算压缩率
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
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
