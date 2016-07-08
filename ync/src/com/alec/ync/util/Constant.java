package com.alec.ync.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import com.learnncode.mediachooser.MediaChooser;

/**
 * URL 文件 图片存放 更新下载地址  配置
 * @author long
 *
 */
public class Constant {
	private static Context context;
	public static final boolean debug = false;//测试修改为 ture ; 正式发布是改为 false
	/**
	 * 拍照保存相对路径 还有一个地方要改 {@link PicPicActivity#photo}
	 */
	private static String path = "/DCIM/Ync/";
	
	public static void init(Context c) {
		context = c;
		initFilePath();
		initInterfaceURL();
		initAppinInfo();
	}
	/**
	 * 初始化接口地址
	 */
	private static void initInterfaceURL() {
		if(debug){
			//测试域名
			InterfaceURL.BASE_URL="http://yzc.lzbxj.com/";
		}{
			//正式发布 域名
			InterfaceURL.BASE_URL="http://yzc.lzbxj.com/";
		}
	}
	public static class Url{
		//URL
		public static final String Login=InterfaceURL.BASE_URL+"api/apiLogin.php";//登录
		
		public static final String Regist=InterfaceURL.BASE_URL+"api/apiRegist.php"; //用户注册
		
		public static final String VillageCat=InterfaceURL.BASE_URL+"api/apiVillageCat.php";//分类
		
		public static final String Region=InterfaceURL.BASE_URL+"api/apiRegion.php";// 定位
		
		public static final String Citylist=InterfaceURL.BASE_URL+"api/apicitylist.php";//获取定位城市资源  参数 province 城市名称
		
		public static final String Village=InterfaceURL.BASE_URL+"api/apiVillage.php";// 乡村列表和乡村基础信息 catid=1&regionid=97
		
		public static final String Villagecunli=InterfaceURL.BASE_URL+"api/apiVillagecunli.php";// 村里内容 参数：villageid
		
		public static final String VillageHuodongList=InterfaceURL.BASE_URL+"api/apiVillageHuodongList.php";//村里活动 参数：villageid
	}
	
	@SuppressWarnings("deprecation")
	private static void initAppinInfo() {
		/* 屏幕尺寸 */
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Appinfo.width = wm.getDefaultDisplay().getWidth();
		Appinfo.height = wm.getDefaultDisplay().getHeight();

		/* 版本号 */
		PackageManager pm = context.getPackageManager();
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (pi != null) {
			Appinfo.INTERFACE_VERSION = pi.versionName;
		} else {
			Appinfo.INTERFACE_VERSION = "1.0";
		}
	}
	public static class InterfaceURL {

		/**
		 * 正式域名 测试用域名
		 */
		public static String BASE_URL;


	}
	public static class Appinfo {
		/**
		 * 屏幕宽度
		 */
		public static int width;
		/**
		 * 屏幕高度
		 */
		public static int height;
		/**
		 * 所有接口 应该提交版本号
		 */
		public static String INTERFACE_VERSION = "1.0";
		/**
		 * 客户端类型
		 */
		public static final int INTERFACE_CLIENT_TYPE = 1;// 1=android ; 2=ios
		/**
		 * 用于更新的版本号
		 */
		public static String NEW_VERSION = "1.0";
		
	}
	/**
	 * 初始化文件缓存路径
	 */
	private static void initFilePath() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				FilePath.APK_CACHE_DIR = context.getExternalCacheDir().getAbsolutePath() + "/";
				FilePath.IMAGE_CACHE_DIR = context.getExternalCacheDir().getAbsolutePath() + "/";
				FilePath.PICTURE_CACHE_DIR = Environment.getExternalStorageDirectory() + path;
			} catch (Exception e) {
				FilePath.APK_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
				FilePath.IMAGE_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
				FilePath.PICTURE_CACHE_DIR = context.getExternalCacheDir() + "/Ync/";
			}
		} else {
			FilePath.APK_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
			FilePath.IMAGE_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
			FilePath.PICTURE_CACHE_DIR = context.getExternalCacheDir() + "/Ync/";
		}
		// 设置相册拍照的保存路径
		MediaChooser.setFolderName("Ync");
		Log.d("SKConstant", FilePath.APK_CACHE_DIR);
	}
	public static class FilePath {
		/**
		 * 图片缓存路径
		 */
		public static String IMAGE_CACHE_DIR;
		/**
		 * 更新下载时apk存放路径
		 */
		public static String APK_CACHE_DIR;
		/**
		 * 拍摄照片的存放路径
		 */
		public static String PICTURE_CACHE_DIR;
	}
}
