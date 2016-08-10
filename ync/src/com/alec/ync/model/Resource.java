package com.alec.ync.model;

import com.alec.ync.util.Constant;

/**
 * ¹Å´å¼ò½é
 * @author long
 *
 */

public class Resource {		
		private String village_id;
		private String resource_id;
		private String resource_name;
		private String author;
		private String content;
		private String file_url;
		private String description;
		private String click;
		public String getVillage_id() {
			return village_id;
		}
		public void setVillage_id(String village_id) {
			this.village_id = village_id;
		}
		public String getResource_id() {
			return resource_id;
		}
		public void setResource_id(String resource_id) {
			this.resource_id = resource_id;
		}
		public String getResource_name() {
			return resource_name;
		}
		public void setResource_name(String resource_name) {
			this.resource_name = resource_name;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getFile_url() {
			return Constant.InterfaceURL.BASE_URL+file_url;
		}
		public void setFile_url(String file_url) {
			this.file_url = file_url;
		}
		public String getDescription(){
			return description;
		}
		public void setDescription(String description){
			this.description= description;
		}
		public String getClick() {
			return click;
		}
		public void setClick(String click) {
			this.click = click;
		}
		
	}
