package com.alec.ync.model;

import com.alec.ync.util.Constant;

/**
 * ¹ã¸æ
 * @author he
 *
 */
public class Ad {
	
	private String ad_id;
	private String ad_name;
	private String ad_core;
	
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
	public String getAd_core() {
		return Constant.InterfaceURL.BASE_URL+"data/afficheimg/"+ad_core;
	}
	public void setAd_core(String ad_core) {
		this.ad_core = Constant.InterfaceURL.BASE_URL+"data/afficheimg/"+ad_core;
	}
	
}
