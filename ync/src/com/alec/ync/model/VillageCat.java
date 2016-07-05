package com.alec.ync.model;

import com.alec.ync.util.Constant;
import com.alec.ync.util.Constant.InterfaceURL;

/**
 * 乡村分类
 * @author long
 *
 */
public class VillageCat {
	
	private String cat_id;
	private String cat_name;
	private String cat_pic_url;
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public String getCat_pic_url() {
		return Constant.InterfaceURL.BASE_URL+cat_pic_url;
	}
	public void setCat_pic_url(String cat_pic_url) {
		this.cat_pic_url = cat_pic_url;
	}
	
}
