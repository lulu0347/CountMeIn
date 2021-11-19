package com.item.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ItemVO implements Serializable {
	
	private Integer itemNo;
	private Integer kindNo;
	private String itemName;
	private Integer itemPrice;
	private Integer itemState;
	private Timestamp soldTime;
	private Timestamp launchedTime;
	private Double warrantyDate;
	private String itemProdDescription;
	private Integer Quantity;
	
	
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public Integer getKindNo() {
		return kindNo;
	}
	public void setKindNo(Integer kindNo) {
		this.kindNo = kindNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Integer itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Integer getItemState() {
		return itemState;
	}
	public void setItemState(Integer itemState) {
		this.itemState = itemState;
	}
	public Timestamp getSoldTime() {
		return soldTime;
	}
	public void setSoldTime(Timestamp soldTime) {
		this.soldTime = soldTime;
	}
	public Timestamp getLaunchedTime() {
		return launchedTime;
	}
	public void setLaunchedTime(Timestamp launchedTime) {
		this.launchedTime = launchedTime;
	}
	public Double getWarrantyDate() {
		return warrantyDate;
	}
	public void setWarrantyDate(Double warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	public String getItemProdDescription() {
		return itemProdDescription;
	}
	public void setItemProdDescription(String itemProdDescription) {
		this.itemProdDescription = itemProdDescription;
	}
	public Integer getQuantity() {
		return Quantity;
	}
	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}	
	
}

	