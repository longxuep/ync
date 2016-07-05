package com.alec.ync.util;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alec.yzc.R;


public class ImageDisPlayer {

	private static Map<String, String> mapString = new HashMap<String, String>();

	/**
	 * 
	 * @param bitmap
	 * @param imageView
	 */
	public static void displayBitmap(String url, Bitmap bitmap, ImageView imageView) {

		imageView.setImageBitmap(bitmap);
		if (imageView.getTag(R.id.image_url_tag) != null) {
			if (mapString.get(url) != null) {
				return;
			}
//			Log.d("displayBitmap", "no null");
		}
//		Log.d("displayBitmap", "animate");
		animate(imageView, 500);
		mapString.put(url, url);
		imageView.setTag(R.id.image_url_tag, url);

	}

	/**
	 * 
	 * @param imageView
	 * @param durationMillis
	 */
	public static void animate(ImageView imageView, int durationMillis) {
		AlphaAnimation fadeImage = (AlphaAnimation) AnimationUtils.loadAnimation(imageView.getContext(), R.anim.alpha_anim);
		imageView.startAnimation(fadeImage);
	}
}
