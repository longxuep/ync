package com.alec.ync.model;

import com.alec.ync.util.Constant;

/**
 * ¹Å´å¼ò½é
 * @author long
 *
 */
public class VillageCunli {
	
	private String village_id;
	private String province_id;
	private String city_id;
	private String address;
	private String village_name;
	private String village_english;
	private String content;
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
	public String getVillage_english(){
		return village_english;
	}
	public void setVillage_english(String village_english){
		this.village_english = village_english;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content= content;
	}
	
}
