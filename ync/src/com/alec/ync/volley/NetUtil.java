package com.alec.ync.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

public class NetUtil {
	private RequestQueue imageRequestQueue;  
    private RequestQueue jsonRequestQueue;    
    private ImageLoader imageLoader;  
    private ImageCache imageCache;  
    private static final int LRU_CACHE_SIZE = 20;  
    private static NetUtil instance = null;  
    
    
    /** 
     * 构造函数 
     *  
     * @param context android应用上下文 
     */  
    private NetUtil(Context context){  
        this.jsonRequestQueue = Volley.newRequestQueue(context);  
        this.imageRequestQueue = Volley.newRequestQueue(context);  
        this.imageLoader = new ImageLoader(imageRequestQueue, imageCache);  
    }  
      
    /** 
     * 单例模式 
     *  
     * @param context 
     * @return NetUtil 
     */  
    public static NetUtil getInstance(Context context){  
        if (instance == null) {  
            synchronized (NetUtil.class) {  
                if (instance == null) {  
                    instance = new NetUtil(context);  
                }  
            }  
        }  
        return instance;  
    }  
    /** 
     * 得到图片加载器 
     *  
     * @return ImageLoader 
     */  
    private ImageLoader getImageLoader() {  
        return imageLoader;  
    }  

    /** 
     * 得到json的请求队列 
     * @return RequestQueue 
     */  
    public RequestQueue getJsonRequestQueue() {  
        return jsonRequestQueue;  
    }  
}
