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
 * ����Volley������ͼƬ�� ���ɱ��ػ���
 * ͼƬ��ʾ/ѹ����ʽ:
 * 1��720P�����ֻ����ݴ����maxWidth,maxHeight������ʾ
 * 2��720p�����ֻ���(1)���ݴ����ImageView��߼���;(2)��ImageViewʹ�õ���match_parent�����ԣ����ȡ������ߣ�������Ļ��/2ΪͼƬ���ߴ磬����140��Ϊ�������
 * 3��ͼƬ��{@link LocalImageManager}�洢ʱ����ѹ����720p�����ֻ���ԭͼ�������ֻ�������ѹ����60%�洢
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

	private static final int CONTAINER_TAG = R.id.image_container;//���ڸ�ImageView����һ��containerʱʹ�õ�tag

	/**
	 * ��ʼ��
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
	 * ����ָ��tag����������
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
	 *            ͼƬ·��������null
	 * @param imageView
	 *            android.widget.ImageView,����null
	 * @param defaultImageResId
	 *            ���ع�������ʾ��ͼƬ����Ϊ0
	 * @param errorImageResId
	 *            ����ʧ����ʾ��ͼƬ����Ϊ0
	 * @param maxWidth
	 *            ����ȣ���Ϊ0
	 * @param maxHeight
	 *            ���߶ȣ���Ϊ0
	 * @param imageListener
	 *            �������ʱ�Ļص������Ի�ȡBitmap��������null
	 * @return ImageContainer ͼƬ���������Ի�ȡ��ͼƬ��bitmap,null��û��ͼƬ
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
			/* ������ʵĿ��ֵ */
			// LayoutParams params = imageView.getLayoutParams();
			// if (maxWidth == 0 && maxHeight == 0) {
			// if (null != params) {
			// maxWidth = params.width > 0 ? params.width : 0;
			// maxHeight = params.height > 0 ? params.height : 0;
			// }
			// }
			/* ����ͼƬ��ʾ�Ŀ�� */
			if (maxWidth <= 0 || maxHeight <= 0) {
				/* 720p�����ֻ���Ҫ������ʾ�����ϵ��������⴦�� */
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
			/* ����Ĭ��ͼ */
			if (defaultImageResId <= 0) {
				defaultImageResId = DEFAULT_IMAGE;
			}
			if (errorImageResId <= 0) {
				errorImageResId = ERROR_IMAGE;
			}

			imageView.setImageResource(defaultImageResId);

			/* ȡ��ԭImageView������ */
			cancleLoading(imageView);
			/* ��鱾�ؼ�SD������ */
			bitmap = getBitmap(url);
			/* ����/��ʾͼƬ */
			container = showImageAndCallBack(bitmap, url, imageView, defaultImageResId, errorImageResId, maxWidth, maxHeight, tag, imageListener);
		}

		return container;
	}

	/**
	 * ����ͼƬ����ImageListener�ص�
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
	 * ���ر���ͼƬ,����ʾ��ImageView��
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
	 * ͨ��url����ȡ����/SD�е�ͼƬ��Դ
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
	 * ���뻺��
	 * 
	 * @param url
	 * @param bitmap
	 */
	public static void putBitmapCache(String url, Bitmap bitmap) {
		cache.putBitmap(url, bitmap);
	}

	/**
	 * ����Ĭ�ϻص�
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
	 * ֹͣImageView�������ص�ͼƬ
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
	 * ͨ��bitmap,url����ImageContainerʵ��
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
			/* ����bitmapΪnull��������ͼƬ */
			if (imageListener == null) {
				imageListener = getImageListener(url, imageView, defaultImageResId, errorImageResId, imageView.getScaleType());
			}
			/* �������ط�����ִ�ж��ImageListener.onResponse,��һ�λص���bitmap����Ϊnull,ע���ж� */
//			imageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
//			container = imageLoader.get(url, imageListener, maxWidth, maxHeight, tag);
			container = imageLoader.get(url, imageListener, maxWidth, maxHeight);
			imageView.setTag(CONTAINER_TAG, container);

		} else {
			/* ������ʾͼƬ������bitmap���뻺�� */
			container = createImageContainer(bitmap, url, null);
			ImageDisPlayer.displayBitmap(url, bitmap, imageView);

			// imageView.setImageBitmap(bitmap);
			/* ִ�лص� */
			if (imageListener != null) {
				imageListener.onResponse(container, false);
			}
		}

		return container;
	}

	/**
	 * ����ͼƬ
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
