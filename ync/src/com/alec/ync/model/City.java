package com.alec.ync.model;

import com.alec.ync.util.Constant;

/**
 * ³ÇÊÐ
 * @author long
 *
 */
public class City {
	
	private String region_id;
	private String region_name;
	private double longitude;
	private double latitude;
	private String file_url;
	
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLoginitude(double longitude){
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getFile_url() {
		return Constant.InterfaceURL.BASE_URL+file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	
}
