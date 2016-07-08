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
 * URL �ļ� ͼƬ��� �������ص�ַ  ����
 * @author long
 *
 */
public class Constant {
	private static Context context;
	public static final boolean debug = false;//�����޸�Ϊ ture ; ��ʽ�����Ǹ�Ϊ false
	/**
	 * ���ձ������·�� ����һ���ط�Ҫ�� {@link PicPicActivity#photo}
	 */
	private static String path = "/DCIM/Ync/";
	
	public static void init(Context c) {
		context = c;
		initFilePath();
		initInterfaceURL();
		initAppinInfo();
	}
	/**
	 * ��ʼ���ӿڵ�ַ
	 */
	private static void initInterfaceURL() {
		if(debug){
			//��������
			InterfaceURL.BASE_URL="http://yzc.lzbxj.com/";
		}{
			//��ʽ���� ����
			InterfaceURL.BASE_URL="http://yzc.lzbxj.com/";
		}
	}
	public static class Url{
		//URL
		public static final String Login=InterfaceURL.BASE_URL+"api/apiLogin.php";//��¼
		
		public static final String Regist=InterfaceURL.BASE_URL+"api/apiRegist.php"; //�û�ע��
		
		public static final String VillageCat=InterfaceURL.BASE_URL+"api/apiVillageCat.php";//����
		
		public static final String Region=InterfaceURL.BASE_URL+"api/apiRegion.php";// ��λ
		
		public static final String Citylist=InterfaceURL.BASE_URL+"api/apicitylist.php";//��ȡ��λ������Դ  ���� province ��������
		
		public static final String Village=InterfaceURL.BASE_URL+"api/apiVillage.php";// ����б����������Ϣ catid=1&regionid=97
		
		public static final String Villagecunli=InterfaceURL.BASE_URL+"api/apiVillagecunli.php";// �������� ������villageid
		
		public static final String VillageHuodongList=InterfaceURL.BASE_URL+"api/apiVillageHuodongList.php";//���� ������villageid
	}
	
	@SuppressWarnings("deprecation")
	private static void initAppinInfo() {
		/* ��Ļ�ߴ� */
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Appinfo.width = wm.getDefaultDisplay().getWidth();
		Appinfo.height = wm.getDefaultDisplay().getHeight();

		/* �汾�� */
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
		 * ��ʽ���� ����������
		 */
		public static String BASE_URL;


	}
	public static class Appinfo {
		/**
		 * ��Ļ���
		 */
		public static int width;
		/**
		 * ��Ļ�߶�
		 */
		public static int height;
		/**
		 * ���нӿ� Ӧ���ύ�汾��
		 */
		public static String INTERFACE_VERSION = "1.0";
		/**
		 * �ͻ�������
		 */
		public static final int INTERFACE_CLIENT_TYPE = 1;// 1=android ; 2=ios
		/**
		 * ���ڸ��µİ汾��
		 */
		public static String NEW_VERSION = "1.0";
		
	}
	/**
	 * ��ʼ���ļ�����·��
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
		// ����������յı���·��
		MediaChooser.setFolderName("Ync");
		Log.d("SKConstant", FilePath.APK_CACHE_DIR);
	}
	public static class FilePath {
		/**
		 * ͼƬ����·��
		 */
		public static String IMAGE_CACHE_DIR;
		/**
		 * ��������ʱapk���·��
		 */
		public static String APK_CACHE_DIR;
		/**
		 * ������Ƭ�Ĵ��·��
		 */
		public static String PICTURE_CACHE_DIR;
	}
}
