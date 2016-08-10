package com.alec.ync.model;

import com.alec.ync.util.Constant.InterfaceURL;

public class Jishi {
	private String cat_id;
	private String goods_id;
	private String goods_name;
	private String price;
	private String purchase_price;
	private String sell;
	private String goods_img;
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getPrice(){
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPurchase_price(){
		return purchase_price;
	}
	public void setPurchase_price(String purchase_price){
		this.purchase_price = purchase_price;
	}
	public String getSell(){
		return sell;
	}
	public void setSell(String sell){
		this.sell = sell;
	}
	public String getGoods_img() {
		return InterfaceURL.BASE_URL+goods_img;
	}
	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}
	
}
