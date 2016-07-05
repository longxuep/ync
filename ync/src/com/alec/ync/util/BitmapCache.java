package com.alec.ync.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 
 * @author volley中Imageloader使用的ImageCache的实现
 */
public class BitmapCache implements ImageCache {
	private static int MAX_CACHE = calCache();
	private LruCache<String, Bitmap> mCache;
	private Context context;

	private static int calCache() {
		int size = (int) Runtime.getRuntime().maxMemory();
		int x = size / 1024 / 1024;
		switch (x) {
		case 32:
			return size / 8;

		case 64:
			return size / 8;

		case 96:
			return size / 8;

		default:
			return size / 8;
		}
	}

	public BitmapCache(Context c) {
		context = c;
		mCache = new LruCache<String, Bitmap>(MAX_CACHE) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
		LocalImageManager.saveBmpToSd(bitmap, url, context);
	}

}
