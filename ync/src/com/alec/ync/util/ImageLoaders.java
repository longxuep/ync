package com.alec.ync.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.alec.yzc.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * 基于Volley的下载图片类 集成本地缓存
 * 图片显示/压缩方式:
 * 1、720P以上手机根据传入的maxWidth,maxHeight进行显示
 * 2、720p以下手机：(1)根据传入的ImageView宽高计算;(2)若ImageView使用的是match_parent等属性，则获取不到宽高，采用屏幕宽/2为图片最大尺寸，本类140行为计算代码
 * 3、图片在{@link LocalImageManager}存储时会有压缩；720p以上手机存原图，以下手机则质量压缩到60%存储
 * @author long
 * 
 */
public class ImageLoaders {
	private static boolean isInit = false;
	private static BitmapCache cache;
	private static ImageLoader imageLoader;
	private static RequestQueue mQueue;
	private static Context context;

	private static final int DEFAULT_IMAGE = R.drawable.default_image;
	private static final int ERROR_IMAGE = R.drawable.error_image;

	private static final int CONTAINER_TAG = R.id.image_container;//用于给ImageView设置一个container时使用的tag

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void init(Context c) {

		if (!isInit) {
			context = c;
			cache = new BitmapCache(context);
			mQueue = Volley.newRequestQueue(context);
			imageLoader = new ImageLoader(mQueue, cache);
			isInit = true;
		}

	}

	/**
	 * 结束指定tag的网络请求
	 * 
	 * @param tag
	 */
	public static void cancelAll(Object tag) {
		if (tag != null) {
			mQueue.cancelAll(tag);
		}
	}

	public static ImageContainer loadImage(String url, ImageView imageView, Object tag, ImageListener imageListener) {

		return loadImage(url, imageView, DEFAULT_IMAGE, ERROR_IMAGE, 0, 0, tag, imageListener);

	}

	public static ImageContainer loadImage(String url, ImageView imageView, int defaultImageResId, int errorImageResId, Object tag) {
		return loadImage(url, imageView, defaultImageResId, errorImageResId, 0, 0, tag);
	}

	public static ImageContainer loadImage(String url, ImageView imageView, int defaultImageResId, int errorImageResId, int maxWidth, int maxHeight, Object tag) {
		return loadImage(url, imageView, defaultImageResId <= 0 ? DEFAULT_IMAGE : defaultImageResId, errorImageResId <= 0 ? ERROR_IMAGE : errorImageResId,
				maxWidth, maxHeight, tag, null);
	}

	/**
	 * 
	 * @param url
	 *            图片路径，不可null
	 * @param imageView
	 *            android.widget.ImageView,不可null
	 * @param defaultImageResId
	 *            加载过程中显示的图片，可为0
	 * @param errorImageResId
	 *            加载失败显示的图片，可为0
	 * @param maxWidth
	 *            最大宽度，可为0
	 * @param maxHeight
	 *            最大高度，可为0
	 * @param imageListener
	 *            下载完成时的回调，可以获取Bitmap，可以是null
	 * @return ImageContainer 图片容器，可以获取到图片的bitmap,null则没有图片
	 */
	public static ImageContainer loadImage(String url, ImageView imageView, int defaultImageResId, int errorImageResId, int maxWidth, int maxHeight,
			Object tag, ImageListener imageListener) {
		Bitmap bitmap = null;
		ImageContainer container = null;
		if (imageView != null) {
			if (null == url) {
				imageView.setScaleType(ScaleType.FIT_CENTER);
				imageView.setImageResource(errorImageResId);
				return null;
			}
			/* 计算合适的宽高值 */
			// LayoutParams params = imageView.getLayoutParams();
			// if (maxWidth == 0 && maxHeight == 0) {
			// if (null != params) {
			// maxWidth = params.width > 0 ? params.width : 0;
			// maxHeight = params.height > 0 ? params.height : 0;
			// }
			// }
			/* 计算图片显示的宽高 */
			if (maxWidth <= 0 || maxHeight <= 0) {
				/* 720p以下手机需要计算显示，以上的则不作特殊处理 */
				if (Constant.Appinfo.width < 720) {
					if (imageView.getWidth() > 1) {
						maxWidth = imageView.getWidth();
						maxHeight = 0;
					} else {
						maxWidth = Constant.Appinfo.width / 2;
						maxHeight = 0;
					}
				} else {
					maxWidth = 0;
					maxHeight = 0;
				}
			}
			/* 设置默认图 */
			if (defaultImageResId <= 0) {
				defaultImageResId = DEFAULT_IMAGE;
			}
			if (errorImageResId <= 0) {
				errorImageResId = ERROR_IMAGE;
			}

			imageView.setImageResource(defaultImageResId);

			/* 取消原ImageView的下载 */
			cancleLoading(imageView);
			/* 检查本地及SD卡缓存 */
			bitmap = getBitmap(url);
			/* 下载/显示图片 */
			container = showImageAndCallBack(bitmap, url, imageView, defaultImageResId, errorImageResId, maxWidth, maxHeight, tag, imageListener);
		}

		return container;
	}

	/**
	 * 下载图片，用ImageListener回调
	 * 
	 * @param url
	 * @param maxWidth
	 * @param maxHeight
	 * @param tag
	 * @param imageListener
	 * @return ImageContainer
	 */
	public static ImageContainer loadImageOnly(final String url, int maxWidth, int maxHeight, Object tag, final ImageListener imageListener) {
		Bitmap bitmap = null;
		ImageContainer container;
		bitmap = getBitmap(url);

		if (bitmap == null) {
			loadImageByImageRequest(url, maxWidth, maxHeight, imageListener);
		} else {
			container = createImageContainer(bitmap, url, null);
			if (imageListener != null) {
				imageListener.onResponse(container, false);
			}
		}
		return null;
	}

	/**
	 * 加载本地图片,并显示到ImageView上
	 * 
	 * @param imageView
	 * @param path
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static ImageContainer loadLocalImage(ImageView imageView, String path, int maxWidth, int maxHeight) {
		Bitmap bitmap = LocalImageManager.getLocalImageBitmap(path, context, maxWidth, maxHeight);
		if (bitmap != null) {
			if (imageView != null) {

				ImageDisPlayer.displayBitmap(path, bitmap, imageView);
			}
		}
		return createImageContainer(bitmap, path, null);
	}

	/**
	 * 通过url，获取缓存/SD中的图片资源
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmap(String url) {

		Bitmap bitmap = cache.getBitmap(url);
		if (bitmap == null) {
			bitmap = LocalImageManager.getImageFromSD(url, context);
			if (bitmap != null) {
				cache.putBitmap(url, bitmap);
			}
		}
		return bitmap;
	}

	/**
	 * 存入缓存
	 * 
	 * @param url
	 * @param bitmap
	 */
	public static void putBitmapCache(String url, Bitmap bitmap) {
		cache.putBitmap(url, bitmap);
	}

	/**
	 * 生成默认回调
	 * 
	 * @param view
	 * @param defaultImageResId
	 * @param errorImageResId
	 * @param scaleType
	 * @return
	 */
	private static ImageListener getImageListener(final String url, final ImageView view, final int defaultImageResId, final int errorImageResId,
			final ScaleType scaleType) {
		return new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (errorImageResId != 0) {
					view.setImageResource(errorImageResId);
//					view.setScaleType(ScaleType.FIT_CENTER);
				}
			}

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if (response.getBitmap() != null) {
//					view.setScaleType(scaleType);

					ImageDisPlayer.displayBitmap(url, response.getBitmap(), view);
					// view.setImageBitmap(response.getBitmap());
				} else if (defaultImageResId != 0) {
					view.setImageResource(defaultImageResId);
//					view.setScaleType(ScaleType.FIT_CENTER);
				}
			}
		};
	}

	/**
	 * 停止ImageView正在下载的图片
	 * 
	 * @param imageView
	 */
	private static void cancleLoading(ImageView imageView) {
		try {
			ImageContainer container = (ImageContainer) imageView.getTag(CONTAINER_TAG);
			if (container != null) {
				// container.showImageAfterLoadingComplete(false);
				container.cancelRequest();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 通过bitmap,url生成ImageContainer实例
	 * 
	 * @param bitmap
	 * @param url
	 * @param listener
	 * @return
	 */
	private static ImageContainer createImageContainer(Bitmap bitmap, String url, ImageListener listener) {
		// TODO Auto-generated method stub
		if (bitmap != null && null != url) {
			return imageLoader.new ImageContainer(bitmap, url, url, listener);
		} else {
			return null;
		}
	}

	private static ImageContainer showImageAndCallBack(Bitmap bitmap, String url, ImageView imageView, int defaultImageResId, int errorImageResId,
			int maxWidth, int maxHeight, Object tag, ImageListener imageListener) {
		ImageContainer container = null;
		if (bitmap == null) {
			/* 本地bitmap为null，则下载图片 */
			if (imageListener == null) {
				imageListener = getImageListener(url, imageView, defaultImageResId, errorImageResId, imageView.getScaleType());
			}
			/* 以下下载方法会执行多次ImageListener.onResponse,第一次回调的bitmap可能为null,注意判断 */
//			imageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
//			container = imageLoader.get(url, imageListener, maxWidth, maxHeight, tag);
			container = imageLoader.get(url, imageListener, maxWidth, maxHeight);
			imageView.setTag(CONTAINER_TAG, container);

		} else {
			/* 否则显示图片，并将bitmap加入缓存 */
			container = createImageContainer(bitmap, url, null);
			ImageDisPlayer.displayBitmap(url, bitmap, imageView);

			// imageView.setImageBitmap(bitmap);
			/* 执行回调 */
			if (imageListener != null) {
				imageListener.onResponse(container, false);
			}
		}

		return container;
	}

	/**
	 * 下载图片
	 * 
	 * @param url
	 * @param maxWidth
	 * @param maxHeight
	 * @param imageListener
	 */
	private static void loadImageByImageRequest(final String url, int maxWidth, int maxHeight, final ImageListener imageListener) {
		ImageRequest imageRequest = new ImageRequest(url, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				if (response != null) {
					LocalImageManager.saveBmpToSd(response, url, context);
					if (imageListener != null)
						imageListener.onResponse(createImageContainer(response, url, null), false);
				} else {
					Log.d("ImageLoaders", "loadImageOnly failed:" + url);
				}
			}

		}, maxWidth, maxHeight, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (imageListener != null)
					imageListener.onErrorResponse(error);
			}
		});
		imageRequest.setRetryPolicy(new DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mQueue.add(imageRequest);
	}
}
