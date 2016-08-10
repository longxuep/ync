package com.alec.ync.model;

public class Message {
	private String title;
	private String imag;
	private String price;
	private String purchase_price;
	private String sell;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImag() {
		return imag;
	}
	public void setImag(String imag) {
		this.imag = imag;
	}
	public String getPrice(){
		return price;
	}
	public void setPrice(String price){
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
	
}
