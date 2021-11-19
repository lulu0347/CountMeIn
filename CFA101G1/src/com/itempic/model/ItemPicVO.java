package com.itempic.model;

import java.io.Serializable;

import javax.servlet.http.Part;

public class ItemPicVO implements Serializable {
	
	private Integer itemPicNo;
	private byte[] itemPic;
	private Integer itemNo;
	private Part part;
	private String path;
	
	public Integer getItemPicNo() {
		return itemPicNo;
	}
	public void setItemPicNo(Integer itemPicNo) {
		this.itemPicNo = itemPicNo;
	}
	public byte[] getItemPic() {
		return itemPic;
	}
	public void setItemPic(byte[] itemPic) {
		this.itemPic = itemPic;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public Part getPart() {
		return part;
	}
	public void setPart(Part part) {
		this.part = part;
	}
	
	
	
}

