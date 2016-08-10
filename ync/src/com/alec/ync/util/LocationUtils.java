package com.alec.ync.util;

import android.util.Log;

public class LocationUtils {
		public final static String CoorType_GCJ02 = "gcj02";
		public final static String CoorType_BD09LL= "bd09ll";
		public final static String CoorType_BD09MC= "bd09";
		/***
		 *61 �� GPS��λ�����GPS��λ�ɹ���
		 *62 �� �޷���ȡ��Ч��λ���ݣ���λʧ�ܣ�������Ӫ���������wifi�����Ƿ�����������������������λ��
		 *63 �� �����쳣��û�гɹ������������������ȷ�ϵ�ǰ�����ֻ������Ƿ�ͨ����������������λ��
		 *65 �� ��λ����Ľ����
		 *66 �� ���߶�λ�����ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ����
		 *67 �� ���߶�λʧ�ܡ�ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ����
		 *68 �� ��������ʧ��ʱ�����ұ������߶�λʱ��Ӧ�ķ��ؽ����
		 *161�� ���綨λ��������綨λ��λ�ɹ���
		 *162�� �������Ľ���ʧ�ܡ�
		 *167�� ����˶�λʧ�ܣ���������Ƿ���û�ȡλ����ϢȨ�ޣ�������������λ��
		 *502�� key���������밴��˵���ĵ���������KEY��
		 *505�� key�����ڻ��߷Ƿ����밴��˵���ĵ���������KEY��
		 *601�� key���񱻿������Լ����ã��밴��˵���ĵ���������KEY��
		 *602�� key mcode��ƥ�䣬����ak���ù����а�ȫ�����������⣬��ȷ����sha1��ȷ����;���ֺ���Ӣ��״̬���Ұ���������ǰ����Ӧ�õİ������밴��˵���ĵ���������KEY��
		 *501��700��key��֤ʧ�ܣ��밴��˵���ĵ���������KEY��
		 */

		public static float[] EARTH_WEIGHT = {0.1f,0.2f,0.4f,0.6f,0.8f}; // �������Ȩ��_����
		//public static float[] MOON_WEIGHT = {0.0167f,0.033f,0.067f,0.1f,0.133f}; 
		//public static float[] MARS_WEIGHT = {0.034f,0.068f,0.152f,0.228f,0.304f}; 
		private static final double EARTH_RADIUS = 6378.137;
		
		private static double rad(double d) {  
	        return d * Math.PI / 180.0;  
	    }  
		
		   /** 
		 * ��������侭γ�����꣨doubleֵ���������������룬 
		 *  
		 * @param lat1 
		 * @param lng1 
		 * @param lat2 
		 * @param lng2 
		 * @return ���룺��λΪ����
		 */ 
		public static double DistanceOfTwoPoints(double lat1,double lng1,   
		         double lat2,double lng2) {  
		    double radLat1 = rad(lat1);  
		    double radLat2 = rad(lat2);  
		    double a = radLat1 - radLat2;  
		    double b = rad(lng1) - rad(lng2);  
		    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
		            + Math.cos(radLat1) * Math.cos(radLat2)  
		            * Math.pow(Math.sin(b / 2), 2)));  
		    s = s * EARTH_RADIUS;  
		    s = Math.round(s * 10000) / 10000;  
		    Log.i("����",s+"");
		    return s;  
		}
		
		
		
		
		
		
	}
