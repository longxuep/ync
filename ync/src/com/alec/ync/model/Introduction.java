package com.alec.ync.model;

import com.alec.ync.util.Constant;

/**
 * ¹Å´å¼ò½é
 * @author long
 *
 */
public class Introduction {
	
	private String village_id;
	private String province_id;
	private String city_id;
	private String address;
	private String village_name;
	private String file_url;
	private String keywords;
	private String score;
	private String click;
	public String getVillage_id() {
		return village_id;
	}
	public void setVillage_id(String village_id) {
		this.village_id = village_id;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getVillage_name() {
		return village_name;
	}
	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}
	public String getFile_url() {
		return Constant.InterfaceURL.BASE_URL+file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getClick() {
		return click;
	}
	public void setClick(String click) {
		this.click = click;
	}
	
}
