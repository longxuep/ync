package com.alec.ync.model;

import com.alec.ync.util.Constant;

public class ImageEntity {
	private String ad_code;
	private String ad_id;
	private String ad_name;
	public ImageEntity() {
		super();
	}
	public String getAd_code() {
		return Constant.InterfaceURL.BASE_URL+ad_code;
	}
	public void setAd_code(String ad_code) {
		this.ad_code = ad_code;
	}
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}
	public String getAd_name() {
		return ad_name;
	}
	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}
	
}
